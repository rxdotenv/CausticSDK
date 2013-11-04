////////////////////////////////////////////////////////////////////////////////
// Copyright 2013 Michael Schmalle - Teoti Graphix, LLC
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
// http://www.apache.org/licenses/LICENSE-2.0 
// 
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, 
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and 
// limitations under the License
// 
// Author: Michael Schmalle, Principal Architect
// mschmalle at teotigraphix dot com
////////////////////////////////////////////////////////////////////////////////

package com.teotigraphix.caustk.live;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;
import com.teotigraphix.caustk.controller.ICaustkApplicationContext;
import com.teotigraphix.caustk.controller.IRackSerializer;
import com.teotigraphix.caustk.core.CausticException;
import com.teotigraphix.caustk.core.osc.RackMessage;
import com.teotigraphix.caustk.rack.IRack;
import com.teotigraphix.caustk.rack.mixer.MasterDelay;
import com.teotigraphix.caustk.rack.mixer.MasterEqualizer;
import com.teotigraphix.caustk.rack.mixer.MasterLimiter;
import com.teotigraphix.caustk.rack.mixer.MasterReverb;
import com.teotigraphix.caustk.rack.tone.RackTone;

/**
 * @author Michael Schmalle
 */
public class RackSet implements ICaustkComponent, IRackSerializer {

    private transient IRack rack;

    private transient ICaustkFactory factory;

    //--------------------------------------------------------------------------
    // Serialized API
    //--------------------------------------------------------------------------

    @Tag(0)
    private ComponentInfo info;

    @Tag(1)
    private File causticFile;

    @Tag(2)
    private boolean isInternal;

    @Tag(3)
    private Map<Integer, Machine> machines = new HashMap<Integer, Machine>(14);

    @Tag(4)
    private MasterMixer masterMixer;

    @Tag(5)
    private MasterSequencer masterSequencer;

    //--------------------------------------------------------------------------
    // Public API :: Properties
    //--------------------------------------------------------------------------

    //----------------------------------
    // rack
    //----------------------------------

    /**
     * Returns the instance of the {@link IRack} that created this instance or
     * the session rack that exists when this RackSet was deserialized.
     */
    public IRack getRack() {
        return rack;
    }

    //----------------------------------
    // factory
    //----------------------------------

    public ICaustkFactory getFactory() {
        return factory;
    }

    //----------------------------------
    // info
    //----------------------------------

    @Override
    public final ComponentInfo getInfo() {
        return info;
    }

    //----------------------------------
    // causticFile
    //----------------------------------

    /**
     * Returns the absolute location of a <code>.caustic</code> song file if the
     * scene is meant to be initialized from the song file.
     */
    public File getCausticFile() {
        return causticFile;
    }

    //----------------------------------
    // isInternal
    //----------------------------------

    /**
     * Sets the {@link RackSet} as an internal scene(not saved to disk), meaning
     * it is treated as a application state scene loaded when the application is
     * loaded with the application's state.
     */
    public final void setInternal() {
        isInternal = true;
    }

    /**
     * Returns whether the {@link RackSet} is an internal scene. (not saved to
     * disk)
     */
    public boolean isInternal() {
        return isInternal;
    }

    //----------------------------------
    // MasterMixer
    //----------------------------------

    public final MasterDelay getDelay() {
        return masterMixer.getDelay();
    }

    public final MasterReverb getReverb() {
        return masterMixer.getReverb();
    }

    public final MasterEqualizer getEqualizer() {
        return masterMixer.getEqualizer();
    }

    public final MasterLimiter getLimiter() {
        return masterMixer.getLimiter();
    }

    public final float getVolume() {
        return masterMixer.getVolume().getOut();
    }

    public final void setVolume(float value) {
        masterMixer.getVolume().setOut(value);
    }

    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------

    /*
     * Serialization.
     */
    RackSet() {
    }

    RackSet(ComponentInfo info, ICaustkFactory factory) {
        this.info = info;
        this.factory = factory;
        this.rack = factory.getRack();
    }

    RackSet(ComponentInfo info, ICaustkFactory factory, File absoluteCausticFile) {
        this.info = info;
        this.factory = factory;
        this.rack = factory.getRack();
        this.causticFile = absoluteCausticFile;
        this.info.setName(absoluteCausticFile.getName().replace(".caustic", ""));
    }

    //--------------------------------------------------------------------------
    // Public API :: Methods
    //--------------------------------------------------------------------------

    public void addMachine(int index, Machine caustkMachine) {
        // XXX This is going to be complex but just try adding to empty
        // if the index is right, should be able to call update()
        // and have the majic happen
        caustkMachine.setMachineIndex(index);
        machines.put(index, caustkMachine);
        caustkMachine.update(factory.createContext());
        masterSequencer.updateMachine(caustkMachine);
    }

    public boolean removeMachine(Machine caustkMachine) {
        if (machines.remove(caustkMachine.getMachineIndex()) == null)
            return false;
        RackMessage.REMOVE.send(rack, caustkMachine.getMachineIndex());
        return true;
    }

    public int getMachineCount() {
        return machines.size();
    }

    public boolean hasMachine(int index) {
        return machines.containsKey(index);
    }

    public Collection<Machine> getMachines() {
        return Collections.unmodifiableCollection(machines.values());
    }

    public Collection<RackTone> getRackTones() {
        ArrayList<RackTone> result = new ArrayList<RackTone>();
        for (Machine machine : machines.values()) {
            result.add(machine.getRackTone());
        }
        return result;
    }

    /**
     * Returns the {@link Machine} at the specified index, <code>null</code> if
     * does not exist.
     * 
     * @param index The machine index.
     */
    public Machine getMachine(int index) {
        return machines.get(index);
    }

    public Machine getMachineByName(String value) {
        for (Machine caustkMachine : machines.values()) {
            if (caustkMachine.getMachineName().equals(value))
                return caustkMachine;
        }
        return null;
    }

    public List<Machine> findMachineStartsWith(String name) {
        List<Machine> result = new ArrayList<Machine>();
        for (Machine tone : machines.values()) {
            if (tone.getMachineName().startsWith(name))
                result.add(tone);
        }
        return result;
    }

    public void rackChanged(ICaustkFactory factory) throws CausticException {
        // since the is a restoration of deserialized components, all sub
        // components are guaranteed to be created, setRack() recurses and sets
        // all components rack
        this.factory = factory;
        this.rack = factory.getRack();

        if (!isInternal) {
            // if this set is internal, the rack state is already in the correct state
            // no need to update the native rack with the scene's serialized properties
            ICaustkApplicationContext context = factory.createContext();
            update(context);
        }
    }

    @Override
    public void create(ICaustkApplicationContext context) throws CausticException {
        masterMixer = factory.createMasterMixer(this);
        masterSequencer = factory.createMasterSequencer(this);
        masterMixer.create(context);
        masterSequencer.create(context);
    }

    public void clearMachines() throws CausticException {
        ArrayList<Machine> list = new ArrayList<Machine>(getMachines());
        for (Machine machine : list) {
            removeMachine(machine);
        }
        rack.clearRack();
    }

    @Override
    public void update(ICaustkApplicationContext context) {
        rack = context.getRack();

        RackMessage.BLANKRACK.send(rack);

        masterMixer.update(context);

        for (Machine machine : machines.values()) {
            machine.update(context);
        }

        masterSequencer.update(context);
    }

    /**
     * Loads the {@link RackSet} using the {@link #getCausticFile()} passed
     * during scene construction.
     * <p>
     * Calling this method will issue a <code>BLANKRACK</code> command and
     * <code>LOAD_SONG</code>, all song state is reset to default before
     * loading.
     * <p>
     * So; any client calling this needs to do it in an initialize phase or save
     * the state of the rack into a temp <code>.caustic</code> file to reload
     * after this method returns.
     * 
     * @param context
     * @throws IOException
     * @throws CausticException
     */
    @Override
    public void load(ICaustkApplicationContext context) throws CausticException {
        if (causticFile == null || !causticFile.exists())
            throw new IllegalStateException("Caustic song file null or not found on file system: "
                    + causticFile);

        if (rack == null)
            throw new IllegalStateException("Rack must not be null");

        // reset the rack and sound source to empty
        RackMessage.BLANKRACK.send(rack);

        // load the song raw, don not create tones
        RackMessage.LOAD_SONG.send(rack, causticFile.getAbsolutePath());

        try {
            // create the scene sub components
            createComponents(context);
            // load the current song rack state into the sub components
            loadComponents(context);
        } catch (IOException e) {
            throw new CausticException(e);
        }
    }

    @Override
    public void restore() {
        rack.restore();
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onSave() {
        masterMixer.onSave();
        masterSequencer.onSave();
        for (Machine machine : machines.values()) {
            machine.onSave();
        }
    }

    private void createComponents(ICaustkApplicationContext context) throws IOException,
            CausticException {
        masterMixer = context.getFactory().createMasterMixer(this);
        masterMixer.create(null);
        for (int i = 0; i < 14; i++) {
            createMachine(i, context);
        }
        masterSequencer = context.getFactory().createMasterSequencer(this);
        masterSequencer.create(null);
    }

    private void loadComponents(ICaustkApplicationContext context) throws IOException,
            CausticException {
        masterMixer.load(context);
        for (int i = 0; i < 14; i++) {
            Machine caustkMachine = getMachine(i);
            if (caustkMachine != null) {
                loadMachine(caustkMachine, context);
            }
        }
        masterSequencer.load(context);
    }

    public Machine createMachine(int machineIndex, String machineName, MachineType machineType)
            throws CausticException {
        Machine machine = factory.createMachine(this, machineIndex, machineType, machineName);
        machines.put(machineIndex, machine);
        machine.create(factory.createContext());
        return machine;
    }

    private void createMachine(int index, ICaustkApplicationContext context) throws IOException,
            CausticException {
        String machineName = RackMessage.QUERY_MACHINE_NAME.queryString(rack, index);
        if (machineName == null)
            return;

        MachineType machineType = MachineType.fromString(RackMessage.QUERY_MACHINE_TYPE
                .queryString(rack, index));
        Machine caustkMachine = context.getFactory().createMachine(this, index, machineType,
                machineName);
        machines.put(index, caustkMachine);
    }

    private void loadMachine(Machine caustkMachine, ICaustkApplicationContext context)
            throws IOException, CausticException {
        // loads CaustkPatch (MachinePreset, MixerPreset, CaustkEffects), CaustkPhrases
        caustkMachine.load(context);
    }

    public void dispose() {
        // TODO Auto-generated method stub

    }

}
