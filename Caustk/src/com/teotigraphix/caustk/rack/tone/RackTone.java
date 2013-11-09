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

package com.teotigraphix.caustk.rack.tone;

import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;
import com.teotigraphix.caustk.controller.ICaustkApplicationContext;
import com.teotigraphix.caustk.core.CausticException;
import com.teotigraphix.caustk.core.ICausticEngine;
import com.teotigraphix.caustk.core.osc.RackMessage;
import com.teotigraphix.caustk.rack.IRack;
import com.teotigraphix.caustk.rack.tone.components.PatternSequencerComponent;
import com.teotigraphix.caustk.rack.tone.components.SynthComponent;
import com.teotigraphix.caustk.workstation.CaustkComponent;
import com.teotigraphix.caustk.workstation.Machine;
import com.teotigraphix.caustk.workstation.MachineMixer;
import com.teotigraphix.caustk.workstation.MachineType;

/**
 * The base class for all tone's that wrap a native Caustic machine.
 * 
 * @author Michael Schmalle
 */
public abstract class RackTone extends CaustkComponent {

    private transient IRack rack;

    /**
     * Only public to allow the {@link Machine} to connect this tone back up to
     * a {@link IRack}.
     * 
     * @param value The current session rack.
     */
    public void setRack(IRack value) {
        rack = value;
    }

    //--------------------------------------------------------------------------
    // Serialized API
    //--------------------------------------------------------------------------

    @Tag(100)
    private Machine machine;

    @Tag(101)
    private int machineIndex;

    @Tag(102)
    private MachineType machineType;

    @Tag(103)
    private String machineName;

    @Tag(104)
    private Map<Class<? extends RackToneComponent>, RackToneComponent> components = new HashMap<Class<? extends RackToneComponent>, RackToneComponent>();

    //--------------------------------------------------------------------------
    // Public API :: Properties
    //--------------------------------------------------------------------------

    //----------------------------------
    // defaultName
    //----------------------------------

    @Override
    public String getDefaultName() {
        return machineName;
    }

    //----------------------------------
    // machine
    //----------------------------------

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine value) {
        this.machine = value;
    }

    //----------------------------------
    // machineIndex
    //----------------------------------

    /**
     * The index location of the tone loaded into/from the core rack.
     */
    public final int getMachineIndex() {
        return machineIndex;
    }

    //----------------------------------
    // machineType
    //----------------------------------

    public final MachineType getMachineType() {
        return machineType;
    }

    //----------------------------------
    // machineName
    //----------------------------------

    /**
     * The name loaded into/from the core rack.
     */
    public final String getMachineName() {
        return machineName;
    }

    /**
     * Returns the native machine name in the caustic rack.
     * 
     * @param restore Retrieve the native machine name.
     */
    public final String getMachineName(boolean restore) {
        return RackMessage.QUERY_MACHINE_NAME.queryString(getEngine(), machineIndex);
    }

    /**
     * Sets the new name of the tone, will send the
     * {@link RackMessage#MACHINE_NAME} message to the core.
     * 
     * @param value The new name of the tone, 10 character limit, cannot be
     *            <code>null</code>.
     */
    public final void setMachineName(String value) {
        setMachineName(value, false);
    }

    public void setMachineName(String value, boolean noUpdate) {
        if (value.equals(machineName))
            return;
        machineName = value;
        if (!noUpdate)
            RackMessage.MACHINE_NAME.send(getEngine(), machineIndex, machineName);
    }

    //--------------------------------------------------------------------------
    // Public Component API
    //--------------------------------------------------------------------------

    public int getComponentCount() {
        return components.size();
    }

    /**
     * Adds a {@link RackToneComponent} to the tone's component map and sets the
     * component's tone reference.
     * 
     * @param clazz The component API class.
     * @param instance The component instance.
     */
    void addComponent(Class<? extends RackToneComponent> clazz, RackToneComponent instance) {
        components.put(clazz, instance);
        instance.setTone(this);
    }

    /**
     * Returns a {@link RackToneComponent} by class type.
     * 
     * @param clazz The component API class.
     */
    public <T extends RackToneComponent> T getComponent(Class<T> clazz) {
        return clazz.cast(components.get(clazz));
    }

    /**
     * Returns the core audio engine interface.
     */
    public final ICausticEngine getEngine() {
        return rack;
    }

    public MachineMixer getMixer() {
        return getMachine().getMixer();
    }

    public SynthComponent getSynth() {
        return getComponent(SynthComponent.class);
    }

    public PatternSequencerComponent getPatternSequencer() {
        return getComponent(PatternSequencerComponent.class);
    }

    //--------------------------------------------------------------------------
    // Constructor
    //--------------------------------------------------------------------------

    RackTone() {
    }

    RackTone(MachineType machineType, String machineName, int machineIndex) {
        this.machineType = machineType;
        this.machineName = machineName;
        this.machineIndex = machineIndex;
    }

    //--------------------------------------------------------------------------
    // IRackSerializer API :: Methods
    //--------------------------------------------------------------------------

    @Override
    protected void componentPhaseChange(ICaustkApplicationContext context, ComponentPhase phase)
            throws CausticException {
        switch (phase) {
            case Create:
                rack = context.getRack();
                createComponents();
                break;

            case Load:
                rack = context.getRack();
                loadComponents();
                break;

            case Update:
                rack = context.getRack();
                // look and see if this Tone exists on the rack before updating tone components
                String name = RackMessage.QUERY_MACHINE_NAME.queryString(rack, machineIndex);
                if (name == null) {
                    // since we are updating and this tone dosn't exist, create it native
                    RackMessage.CREATE.send(rack, machineType.getType(), machineName, machineIndex);
                }
                updateComponents(context);
                break;

            case Restore:
                setMachineName(getMachineName(true), true);
                for (RackToneComponent component : components.values()) {
                    component.restore();
                }
                break;

            case Connect:
                rack = context.getRack();
                break;

            case Disconnect:
                break;
        }
    }

    @Override
    public void onSave() {
    }

    @Override
    public void onLoad() {
    }

    /**
     * Create/add sub components to the tone using
     * {@link #addComponent(Class, RackToneComponent)}.
     */
    protected abstract void createComponents();

    /**
     * Calls {@link #restore()}, the {@link IRack} is guaranteed to be non null.
     */
    protected void loadComponents() {
        restore();
    }

    protected void updateComponents(ICaustkApplicationContext context) {
        // XXX this is wrong, what if the machine is not created yet?
        RackMessage.MACHINE_NAME.send(getEngine(), machineIndex, machineName);
        for (RackToneComponent component : components.values()) {
            component.update(context);
        }
    }

}
