////////////////////////////////////////////////////////////////////////////////
// Copyright 2012 Michael Schmalle - Teoti Graphix, LLC
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

package com.teotigraphix.caustk.core.osc;

public class ModularMessage extends CausticMessage {

    /**
     * Message: <code>/caustic/[machine_index]/create [bay] [type]</code>
     * <p>
     * <strong>Default</strong>: <code>N/A</code>
     * <p>
     * <strong>Parameters</strong>:
     * <ul>
     * <li><strong>machine_index</strong>: The machine index in the rack.
     * <li><strong>bay</strong>:
     * <li><strong>type</strong>:
     * </ul>
     * <p>
     * <strong>Returns</strong>: <code>N/A</code>
     */
    public static final ModularMessage CREATE = new ModularMessage("/caustic/${0}/create ${1} ${2}");

    // /caustic/[machine]/connect [src_bay] [src_jack] [dest_bay] [dest_jack]

    /**
     * Message:
     * <code>/caustic/[machine_index]/connect [src_bay] [src_jack] [dest_bay] [dest_jack]</code>
     * <p>
     * <strong>Default</strong>: <code>120.0</code>
     * <p>
     * <strong>Parameters</strong>:
     * <ul>
     * <li><strong>machine_index</strong>: The machine index in the rack.
     * <li><strong>src_bay</strong>:
     * <li><strong>src_jack</strong>:
     * <li><strong>dest_bay</strong>:
     * <li><strong>dest_jack</strong>:</li>
     * </ul>
     * <p>
     * <strong>Returns</strong>: <code>N/A</code>
     */
    public static final ModularMessage CONNECT = new ModularMessage(
            "/caustic/${0}/connect ${1} ${2} ${3} ${4}");

    /**
     * Message:
     * <code>/caustic/[machine_index]/[bay]/[control_name] [value]</code>
     * <p>
     * <strong>Default</strong>:
     * <code>Will return the value if the value is empty.</code>
     * <p>
     * <strong>Parameters</strong>:
     * <ul>
     * <li><strong>machine_index</strong>: The machine index in the rack.
     * <li><strong>bay</strong>:
     * <li><strong>control_name</strong>:
     * <li><strong>value</strong>:
     * </ul>
     * <p>
     * <strong>Returns</strong>: <code>float</code>
     */
    public static final ModularMessage SET = new ModularMessage("/caustic/${0}/${1}/${2} ${3}");

    // 

    /**
     * /caustic/[machine_index]/type <component bay#> returns the type# from the
     * list above
     */
    public static final ModularMessage TYPE = new ModularMessage("/caustic/${0}/type/${1}");

    public ModularMessage(String message) {
        super(message);
    }

    public enum ModularComponentType {

        Empty(0),

        TwoToOneMixerModulator(1),

        ThreeToOneMixer(2),

        SixToOneMixer(3),

        WaveformGenerator(4),

        SubOscillator(5),

        PulseGenerator(6),

        DADSREnvelope(7),

        AREnvelope(8),

        DecayEnvelope(9),

        SVFilter(10),

        ResonantLP(11),

        FormantFilter(12),

        MiniLFO(13),

        NoiseGenerator(14),

        PanModule(15),

        CrossFade(16),

        LagProcessor(17),

        Delay(18),

        SampleAndHold(19),

        CrossOver(20),

        // stand-alone effects ommited

        Saturator(24),

        FMPair(25),

        Arpeggiator(26);

        private int value;

        public int getValue() {
            return value;
        }

        ModularComponentType(int value) {
            this.value = value;
        }

        public static ModularComponentType fromInt(int type) {
            for (ModularComponentType item : values()) {
                if (item.getValue() == type)
                    return item;
            }
            return null;
        }
    }
}
