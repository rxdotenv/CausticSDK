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

import com.teotigraphix.caustk.core.MachineType;
import com.teotigraphix.caustk.node.machine.patch.beatbox.WavSamplerComponent;

/**
 * The Caustic <strong>BeatBox</strong> OSC decorator.
 * 
 * @author Michael Schmalle
 * @since 1.0
 */
public class BeatBoxMachine extends MachineNode {

    //--------------------------------------------------------------------------
    // Serialized API
    //--------------------------------------------------------------------------

    private WavSamplerComponent sampler;

    //--------------------------------------------------------------------------
    // Components
    //--------------------------------------------------------------------------

    //----------------------------------
    // sampler
    //----------------------------------

    public WavSamplerComponent getSampler() {
        return sampler;
    }

    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------

    /**
     * Serialization
     */
    public BeatBoxMachine() {
    }

    public BeatBoxMachine(int index, String name) {
        super(index, MachineType.BeatBox, name);
    }

    //--------------------------------------------------------------------------
    // Overridden Protected :: Methods
    //--------------------------------------------------------------------------

    @Override
    protected void intialize() {
        super.intialize();
        sampler = new WavSamplerComponent(this);
    }

    @Override
    protected void createComponents() {
        super.createComponents();
        sampler.create();
    }

    @Override
    protected void updateComponents() {
        super.updateComponents();
        sampler.update();
    }

    @Override
    protected void restorePresetProperties() {
        sampler.restore();
    }
}