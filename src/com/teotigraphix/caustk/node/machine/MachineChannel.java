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

package com.teotigraphix.caustk.node.machine;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;
import com.teotigraphix.caustk.node.NodeBase;

/**
 * The {@link MachineChannel} is the base class for all {@link Machine}
 * composite components.
 * 
 * @author Michael Schmalle
 * @since 1.0
 */
public abstract class MachineChannel extends NodeBase {

    //--------------------------------------------------------------------------
    // Serialized API
    //--------------------------------------------------------------------------

    @Tag(50)
    private Machine machineNode;

    //--------------------------------------------------------------------------
    // Public Property API
    //--------------------------------------------------------------------------

    public Machine getMachineNode() {
        return machineNode;
    }

    public void setMachineNode(Machine machineNode) {
        this.machineNode = machineNode;
    }

    /**
     * Returns the machine index this component decorates (0..13).
     */
    public int getMachineIndex() {
        return machineNode.getIndex();
    }

    /**
     * Returns the machine type of the parent, may be null if not set.
     */
    public MachineType getMachineType() {
        return machineNode.getType();
    }

    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------

    /**
     * Serialization
     */
    protected MachineChannel() {
    }

    public MachineChannel(Machine machineNode) {
        this.machineNode = machineNode;
    }
}
