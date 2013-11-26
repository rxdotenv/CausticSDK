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

package com.teotigraphix.caustk.rack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.teotigraphix.caustk.controller.ICaustkApplication;
import com.teotigraphix.caustk.controller.ICaustkController;
import com.teotigraphix.caustk.controller.ICaustkLogger;
import com.teotigraphix.caustk.controller.IDispatcher;
import com.teotigraphix.caustk.controller.core.Dispatcher;
import com.teotigraphix.caustk.core.CausticException;
import com.teotigraphix.caustk.core.osc.RackMessage;
import com.teotigraphix.caustk.utils.RuntimeUtils;
import com.teotigraphix.caustk.workstation.RackSet;

/**
 * The {@link Rack} is a fully serializable state instance.
 */
public class Rack implements IRack {

    //--------------------------------------------------------------------------
    // Private :: Variables
    //--------------------------------------------------------------------------

    private transient ISoundGenerator soundGenerator;

    private transient ICaustkController controller;

    private transient IDispatcher dispatcher;

    // - The Rack is the dispatcher for all events coming from a Scene, 
    // so listeners in apps can safely listen to the Rack without getting coupled 
    // with a Scene reference, the Rack is ONLY CREATED ONCE

    @Override
    public IDispatcher getDispatcher() {
        return dispatcher;
    }

    @Override
    public IDispatcher getComponentDispatcher() {
        return controller;
    }

    @Override
    public ICaustkLogger getLogger() {
        return application.getLogger();
    }

    //----------------------------------
    // application
    //----------------------------------

    private ICaustkApplication application;

    public ICaustkApplication getApplication() {
        return application;
    }

    void setApplication(ICaustkApplication application) {
        this.application = application;
        this.dispatcher = new Dispatcher();
        this.controller = application.getController();

        soundGenerator = application.getConfiguration().getSoundGenerator();

        controller.addComponent(IRack.class, this);
    }

    //----------------------------------
    // controller
    //----------------------------------

    @Override
    public ICaustkController getController() {
        return application.getController();
    }

    //----------------------------------
    // scene
    //----------------------------------

    private RackSet rackSet;

    @Override
    public final RackSet getRackSet() {
        return rackSet;
    }

    @Override
    public void setRackSet(RackSet value) {
        if (value == rackSet)
            return;
        getLogger().log("Rack", "Setting new RackSet");
        RackSet oldSet = rackSet;
        rackSet = value;
        rackSetChanged(rackSet, oldSet);
    }

    private void rackSetChanged(RackSet newSet, RackSet oldSet) {
        if (oldSet != null) {
            try {
                oldSet.dispose();
                getLogger().log("RackSet", "Disposed old RackSet");
            } catch (CausticException e) {
                e.printStackTrace();
            }
        }
        // when a rackSet is assigned, the Rack does not care or want to care
        // how the rackSet was loaded, unserialized etc., it will just call update()
        // and restore whatever is there.

        // recursively create OR updates all scene components based on their previous 
        // saved state
        try {
            newSet.rackChanged(application.getFactory());
        } catch (CausticException e) {
            getController().getLogger().err("Rack", "Error assigning RackSet to Rack", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------

    public Rack(ICaustkApplication application) {
        setApplication(application);
    }

    //--------------------------------------------------------------------------
    // Public API :: Methods
    //--------------------------------------------------------------------------

    private final List<OnRackListener> listeners = new ArrayList<OnRackListener>();

    @Override
    public void addListener(OnRackListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListener(OnRackListener l) {
        listeners.remove(l);
    }

    /**
     * Listener API for the {@link Rack}.
     * 
     * @author Michael Schmalle
     */
    public interface OnRackListener {

        void frameChange(float delta, int measure, float beat);

        void beatChange(int measure, float beat);

        void stepChange(int sixteenthStep);
    }

    @Override
    public void frameChanged(float delta) {
        final int measure = (int)getCurrentSongMeasure();
        final float beat = getCurrentBeat();
        for (OnRackListener listener : listeners) {
            listener.frameChange(delta, measure, beat);
        }

        boolean changed = rackSet.updatePosition(measure, beat);
        if (changed) {
            for (OnRackListener listener : listeners) {
                listener.beatChange(measure, beat);
            }
        }

        changed = rackSet.updateStep(measure, beat);
        if (changed) {
            for (OnRackListener listener : listeners) {
                listener.stepChange(rackSet.getSequencer().getCurrentSixteenthStep());
            }
        }
    }

    @Override
    public void clearRack() throws CausticException {
        RackMessage.BLANKRACK.send(this);
    }

    @Override
    public void loadSong(File causticFile) throws IOException {
        if (!causticFile.exists())
            throw new IOException(".caustic File not found: " + causticFile);

        RackMessage.LOAD_SONG.send(this, causticFile.getAbsolutePath());
    }

    @Override
    public File saveSong(String name) {
        RackMessage.SAVE_SONG.send(this, name);
        return RuntimeUtils.getCausticSongFile(name);
    }

    @Override
    public File saveSongAs(File file) throws IOException {
        File song = saveSong(file.getName().replace(".caustic", ""));
        FileUtils.copyFileToDirectory(song, file.getParentFile());
        song.delete();
        return file;
    }

    @Override
    public void restore() {
        if (rackSet != null)
            rackSet.restore();
    }

    //--------------------------------------------------------------------------
    // IRack API
    //--------------------------------------------------------------------------

    @Override
    public final float getCurrentSongMeasure() {
        return soundGenerator.getCurrentSongMeasure();
    }

    @Override
    public final float getCurrentBeat() {
        return soundGenerator.getCurrentBeat();
    }

    //----------------------------------
    // IActivityCycle API
    //----------------------------------

    @Override
    public void onStart() {
        soundGenerator.onStart();
        soundGenerator.onResume();
    }

    @Override
    public void onResume() {
        soundGenerator.onResume();
    }

    @Override
    public void onPause() {
        soundGenerator.onPause();
    }

    @Override
    public void onStop() {
        soundGenerator.onStop();
    }

    @Override
    public void onDestroy() {
        soundGenerator.onDestroy();
    }

    @Override
    public void onRestart() {
        soundGenerator.onRestart();
    }

    @Override
    public void dispose() {
        RackMessage.BLANKRACK.send(this);

        controller.removeComponent(IRack.class);
        controller.removeComponent(ISystemSequencer.class);

        application = null;
        controller = null;
        soundGenerator = null;
    }

    //--------------------------------------------------------------------------
    // ICausticEngine API
    //--------------------------------------------------------------------------

    // we proxy the actual OSC impl so we can stop, or reroute
    @Override
    public final float sendMessage(String message) {
        return soundGenerator.sendMessage(message);
    }

    @Override
    public final String queryMessage(String message) {
        return soundGenerator.queryMessage(message);
    }
}
