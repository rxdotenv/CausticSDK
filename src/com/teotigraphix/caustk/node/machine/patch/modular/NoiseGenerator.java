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

package com.teotigraphix.caustk.node.machine.patch.modular;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;
import com.teotigraphix.caustk.node.machine.Machine;

public class NoiseGenerator extends ModularComponentBase {

    //--------------------------------------------------------------------------
    // Serialized API
    //--------------------------------------------------------------------------

    @Tag(100)
    private float lfGain;

    @Tag(101)
    private float pinkGain;

    @Tag(102)
    private float whiteGain;

    //--------------------------------------------------------------------------
    // Public API :: Properties
    //--------------------------------------------------------------------------

    //----------------------------------
    // lfGain
    //----------------------------------

    public float getLFGain() {
        return lfGain;
    }

    float getLFGain(boolean restore) {
        return getValue("lf_gain");
    }

    /**
     * @param value (0..1)
     */
    public void setLFGain(float value) {
        if (value == lfGain)
            return;
        lfGain = value;
        if (value < 0f || value > 1f)
            newRangeException("lf_gain", "0..1", value);
        setValue("lf_gain", value);
    }

    //----------------------------------
    // pinkGain
    //----------------------------------

    public float getPinkGain() {
        return pinkGain;
    }

    float getPinkGain(boolean restore) {
        return getValue("pink_gain");
    }

    /**
     * @param value (0..1)
     */
    public void setPinkGain(float value) {
        if (value == pinkGain)
            return;
        pinkGain = value;
        if (value < 0f || value > 1f)
            newRangeException("pink_gain", "0..1", value);
        setValue("pink_gain", value);
    }

    //----------------------------------
    // whiteGain
    //----------------------------------

    public float getWhiteGain() {
        return whiteGain;
    }

    float getWhiteGain(boolean restore) {
        return getValue("white_gain");
    }

    /**
     * @param value (0..1)
     */
    public void setWhiteGain(float value) {
        if (value == whiteGain)
            return;
        whiteGain = value;
        if (value < 0f || value > 1f)
            newRangeException("white_gain", "0..1", value);
        setValue("white_gain", value);
    }

    public NoiseGenerator() {
    }

    public NoiseGenerator(Machine machineNode, int bay) {
        super(machineNode, bay);
        setLabel("NoiseGenerator");
    }

    @Override
    protected int getNumBays() {
        return 1;
    }

    @Override
    protected void restoreComponents() {
        setLFGain(getLFGain(true));
        setPinkGain(getPinkGain(true));
        setWhiteGain(getWhiteGain(true));
    }

    public enum NoiseGeneratorJack implements IModularJack {

        InModulation(0),

        OutLF(0),

        OutPink(1),

        OutWhite(2);

        private int value;

        @Override
        public final int getValue() {
            return value;
        }

        NoiseGeneratorJack(int value) {
            this.value = value;
        }
    }
}
