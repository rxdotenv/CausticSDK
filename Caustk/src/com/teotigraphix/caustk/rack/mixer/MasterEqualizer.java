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

package com.teotigraphix.caustk.rack.mixer;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;
import com.teotigraphix.caustk.core.osc.MasterMixerMessage;
import com.teotigraphix.caustk.machine.CaustkLibraryFactory;

/**
 * @author Michael Schmalle
 */
public class MasterEqualizer extends MasterComponent {

    //--------------------------------------------------------------------------
    // Serialized API
    //--------------------------------------------------------------------------

    @Tag(100)
    private float bass = 1.1f;

    @Tag(110)
    private float bassMidFreq = 0.5f;

    @Tag(120)
    private float mid = 1f;

    @Tag(130)
    private float midHighFreq = 0.5f;

    @Tag(140)
    private float high = 1.1f;

    //--------------------------------------------------------------------------
    // Public API :: Properties
    //--------------------------------------------------------------------------

    //----------------------------------
    // bass
    //----------------------------------

    public float getBass() {
        return bass;
    }

    float getBass(boolean restore) {
        return MasterMixerMessage.EQ_BASS.query(rack);
    }

    public void setBass(float value) {
        if (bass == value)
            return;
        if (value < 0f || value > 2f)
            throw newRangeException("bass", "0..2", value);
        bass = value;
        MasterMixerMessage.EQ_BASS.send(rack, value);
    }

    //----------------------------------
    // bassMidFreq
    //----------------------------------

    public float getBassMidFreq() {
        return bassMidFreq;
    }

    float getBassMidFreq(boolean restore) {
        return MasterMixerMessage.EQ_BASSMID_FREQ.query(rack);
    }

    public void setBassMidFreq(float value) {
        if (bassMidFreq == value)
            return;
        if (value < 0f || value > 1f)
            throw newRangeException("bassmid_freq", "0..1", value);
        bassMidFreq = value;
        MasterMixerMessage.EQ_BASSMID_FREQ.send(rack, value);
    }

    //----------------------------------
    // mid
    //----------------------------------

    public float getMid() {
        return mid;
    }

    float getMid(boolean restore) {
        return MasterMixerMessage.EQ_MID.query(rack);
    }

    public void setMid(float value) {
        if (mid == value)
            return;
        if (value < 0f || value > 2f)
            throw newRangeException("mid ", "0..2", value);
        mid = value;
        MasterMixerMessage.EQ_MID.send(rack, value);
    }

    //----------------------------------
    // midHighFreq
    //----------------------------------

    public float getMidHighFreq() {
        return midHighFreq;
    }

    float getMidHighFreq(boolean restore) {
        return MasterMixerMessage.EQ_MIDHIGH_FREQ.query(rack);
    }

    public void setMidHighFreq(float value) {
        if (midHighFreq == value)
            return;
        if (value < 0f || value > 1f)
            throw newRangeException("midhigh_freq ", "0..1", value);
        midHighFreq = value;
        MasterMixerMessage.EQ_MIDHIGH_FREQ.send(rack, value);
    }

    //----------------------------------
    // high
    //----------------------------------

    public float getHigh() {
        return high;
    }

    float getHigh(boolean restore) {
        return MasterMixerMessage.EQ_HIGH.query(rack);
    }

    public void setHigh(float value) {
        if (high == value)
            return;
        if (value < 0f || value > 2f)
            throw newRangeException("high ", "0..2", value);
        high = value;
        MasterMixerMessage.EQ_HIGH.send(rack, value);
    }

    //--------------------------------------------------------------------------
    // Constructor
    //--------------------------------------------------------------------------

    public MasterEqualizer() {
        bypassMessage = MasterMixerMessage.EQ_BYPASS;
    }

    //--------------------------------------------------------------------------
    // IRackSerializer API :: Methods
    //--------------------------------------------------------------------------

    @Override
    public void load(CaustkLibraryFactory factory) {
        super.load(factory);
    }

    @Override
    public void restore() {
        super.restore();
        setBass(getBass(true));
        setBassMidFreq(getBassMidFreq(true));
        setHigh(getHigh(true));
        setMid(getMid(true));
        setMidHighFreq(getMidHighFreq(true));
    }

    @Override
    public void update() {
        super.update();
        MasterMixerMessage.EQ_BASS.send(rack, bass);
        MasterMixerMessage.EQ_BASSMID_FREQ.send(rack, bassMidFreq);
        MasterMixerMessage.EQ_HIGH.send(rack, high);
        MasterMixerMessage.EQ_MID.send(rack, mid);
        MasterMixerMessage.EQ_MIDHIGH_FREQ.send(rack, midHighFreq);
    }
}
