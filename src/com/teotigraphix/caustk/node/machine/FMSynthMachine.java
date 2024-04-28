////////////////////////////////////////////////////////////////////////////////
//Copyright 2014 Michael Schmalle - Teoti Graphix, LLC
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0 
//
//Unless required by applicable law or agreed to in writing, software 
//distributed under the License is distributed on an "AS IS" BASIS, 
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and 
//limitations under the License
//
//Author: Michael Schmalle, Principal Architect
//mschmalle at teotigraphix dot com
////////////////////////////////////////////////////////////////////////////////

package com.teotigraphix.caustk.node.machine;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;
import com.teotigraphix.caustk.node.RackInstance;
import com.teotigraphix.caustk.node.machine.patch.fmsynth.FMControlsComponent;
import com.teotigraphix.caustk.node.machine.patch.fmsynth.FMOperatorComponent;
import com.teotigraphix.caustk.node.machine.patch.fmsynth.LFOComponent;

/**
 * The Caustic <strong>FMSynth</strong> OSC decorator.
 * 
 * @author Michael Schmalle
 * @since 1.0
 */
public class FMSynthMachine extends Machine {

    //--------------------------------------------------------------------------
    // Serialized API
    //--------------------------------------------------------------------------

    @Tag(100)
    private FMControlsComponent controls;

    @Tag(101)
    private LFOComponent lfo;

    @Tag(102)
    private FMOperatorComponent operator;

    //--------------------------------------------------------------------------
    // Components
    //--------------------------------------------------------------------------

    //----------------------------------
    // controls
    //----------------------------------

    public FMControlsComponent getControls() {
        return controls;
    }

    //----------------------------------
    // lfo
    //----------------------------------

    public LFOComponent getLFO() {
        return lfo;
    }

    //----------------------------------
    // operators
    //----------------------------------

    public FMOperatorComponent getOperators() {
        return operator;
    }

    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------

    /**
     * Serialization
     */
    public FMSynthMachine() {
    }

    public FMSynthMachine(RackInstance rackNode, int index, String name) {
        super(rackNode, index, MachineType.FMSynth, name);
    }

    //--------------------------------------------------------------------------
    // Overridden Protected :: Methods
    //--------------------------------------------------------------------------

    @Override
    protected void intialize() {
        super.intialize();
        controls = new FMControlsComponent(this);
        lfo = new LFOComponent(this);
        operator = new FMOperatorComponent(this);
    }

    @Override
    protected void createComponents() {
        super.createComponents();
        controls.create();
        lfo.create();
        operator.create();
    }

    @Override
    protected void destroyComponents() {
        super.destroyComponents();
        controls.destroy();
        lfo.destroy();
        operator.destroy();
    }

    @Override
    protected void updateComponents() {
        super.updateComponents();
        controls.update();
        lfo.update();
        operator.update();
    }

    @Override
    protected void restorePresetProperties() {
        controls.restore();
        lfo.restore();
        operator.restore();
    }
}
