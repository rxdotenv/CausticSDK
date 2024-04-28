////////////////////////////////////////////////////////////////////////////////
// Copyright 2014 Michael Schmalle - Teoti Graphix, LLC
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

package com.teotigraphix.caustk.utils.gdx;

import com.teotigraphix.caustk.core.ICaustkRack;
import com.teotigraphix.caustk.core.osc.MixerControls;

public final class BehaviorUtils {

    /**
     * Sends mixer channel control messages to the machine's
     * {@link com.teotigraphix.caustk.node.machine.patch.MixerChannel}.
     * 
     * @param rack The rack.
     * @param index The machine index.
     * @param control The {@link com.teotigraphix.caustk.core.osc.MixerControls}
     *            .
     * @param value The float value for the mixer control.
     */
    public static void send(ICaustkRack rack, int index, MixerControls control, float value) {
        if (!rack.contains(index))
            return;

        if (control == MixerControls.Solo) {
            rack.getRackInstance().setSolo(index, value == 0f ? false : true);
            return;
        } else if (control == MixerControls.Mute) {
            rack.getRackInstance().setMute(index, value == 0f ? false : true);
            return;
        }

        if (index == -1) {
            switch (control) {
                case Volume:
                    rack.getRackInstance().getMaster().getVolume().setOut(value);
                    break;
                case EqHigh:
                    rack.getRackInstance().getMaster().getEqualizer().setHigh(value);
                    break;
                case EqMid:
                    rack.getRackInstance().getMaster().getEqualizer().setMid(value);
                    break;
                case EqBass:
                    rack.getRackInstance().getMaster().getEqualizer().setBass(value);
                    break;
                default:
                    break;
            }
        } else {
            rack.get(index).getMixer().invoke(control, value);
        }
    }
}
