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

public class Saturator extends ModularComponentBase {

    //--------------------------------------------------------------------------
    // Serialized API
    //--------------------------------------------------------------------------

    @Tag(100)
    private float amount;

    @Tag(101)
    private float inGain;

    @Tag(102)
    private float outGain;

    //--------------------------------------------------------------------------
    // Public API :: Properties
    //--------------------------------------------------------------------------

    //----------------------------------
    // amount
    //----------------------------------

    public float getAmount() {
        return amount;
    }

    float getAmount(boolean restore) {
        return getValue("amount");
    }

    /**
     * @param value (0..1)
     */
    public void setAmount(float value) {
        if (value == amount)
            return;
        amount = value;
        if (value < 0f || value > 1f)
            newRangeException("amount", "0..1", value);
        setValue("amount", value);
    }

    //----------------------------------
    // inGain
    //----------------------------------

    public float getInGain() {
        return inGain;
    }

    float getInGain(boolean restore) {
        return getValue("in_gain");
    }

    /**
     * @param value (0..1)
     */
    public void setInGain(float value) {
        if (value == inGain)
            return;
        inGain = value;
        if (value < 0f || value > 1f)
            newRangeException("in_gain", "0..1", value);
        setValue("in_gain", value);
    }

    //----------------------------------
    // outGain
    //----------------------------------

    public float getOutGain() {
        return outGain;
    }

    float getOutGain(boolean restore) {
        return getValue("out_gain");
    }

    /**
     * @param value (0..1)
     */
    public void setOutGain(float value) {
        if (value == outGain)
            return;
        outGain = value;
        if (value < 0f || value > 1f)
            newRangeException("out_gain", "0..1", value);
        setValue("out_gain", value);
    }

    public Saturator() {
    }

    public Saturator(Machine machineNode, int bay) {
        super(machineNode, bay);
        setLabel("Saturator");
    }

    @Override
    protected int getNumBays() {
        return 1;
    }

    @Override
    protected void restoreComponents() {
        setAmount(getAmount(true));
        setInGain(getInGain(true));
        setOutGain(getOutGain(true));
    }

    public enum SaturatorJack implements IModularJack {

        InInput(0),

        OutOutput(0);

        private int value;

        @Override
        public final int getValue() {
            return value;
        }

        SaturatorJack(int value) {
            this.value = value;
        }
    }
}
