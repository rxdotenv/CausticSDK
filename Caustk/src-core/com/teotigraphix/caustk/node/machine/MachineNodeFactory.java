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

package com.teotigraphix.caustk.node.machine;

import com.teotigraphix.caustk.core.CaustkFactory;
import com.teotigraphix.caustk.core.MachineType;
import com.teotigraphix.caustk.core.factory.CaustkFactoryChildBase;
import com.teotigraphix.caustk.node.RackNode;

/**
 * Factory to create {@link MachineNode}s.
 * 
 * @author Michael Schmalle
 * @since 1.0
 */
public class MachineNodeFactory extends CaustkFactoryChildBase {

    public MachineNodeFactory(CaustkFactory factory) {
        super(factory);
    }

    @SuppressWarnings("unchecked")
    public <T extends MachineNode> T createMachine(RackNode rackNode, int index, MachineType type,
            String name) {
        MachineNode machineNode = null;
        switch (type) {
            case SubSynth:
                machineNode = new SubSynthMachine(rackNode, index, name);
                break;
            case Bassline:
                machineNode = new BasslineMachine(rackNode, index, name);
                break;
            case BeatBox:
                machineNode = new BeatBoxMachine(rackNode, index, name);
                break;
            case EightBitSynth:
                machineNode = new EightBitSynthMachine(rackNode, index, name);
                break;
            case FMSynth:
                machineNode = new FMSynthMachine(rackNode, index, name);
                break;
            case Modular:
                machineNode = new ModularMachine(rackNode, index, name);
                break;
            case Organ:
                machineNode = new OrganMachine(rackNode, index, name);
                break;
            case PCMSynth:
                machineNode = new PCMSynthMachine(rackNode, index, name);
                break;
            case PadSynth:
                machineNode = new PadSynthMachine(rackNode, index, name);
                break;
            case Vocoder:
                machineNode = new VocoderMachine(rackNode, index, name);
                break;
            case KSSynth:
                machineNode = new KSSynthMachine(rackNode, index, name);
                break;
        }
        return (T)machineNode;
    }
}