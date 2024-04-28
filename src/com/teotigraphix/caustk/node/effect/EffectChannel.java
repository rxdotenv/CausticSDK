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

package com.teotigraphix.caustk.node.effect;

import java.util.HashMap;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;
import com.teotigraphix.caustk.core.CausticException;
import com.teotigraphix.caustk.core.osc.EffectsRackMessage;
import com.teotigraphix.caustk.core.osc.EffectsRackMessage.EffectsRackControl;
import com.teotigraphix.caustk.core.osc.IOSCControl;
import com.teotigraphix.caustk.node.NodeBase;
import com.teotigraphix.caustk.node.NodeBaseEvents.NodeEvent;
import com.teotigraphix.caustk.node.machine.Machine;
import com.teotigraphix.caustk.node.machine.MachineChannel;
import com.teotigraphix.caustk.node.master.MasterChannel;

/**
 * The effect channel node, currently holds 2 slots.
 * 
 * @author Michael Schmalle
 * @since 1.0
 * @see Machine#getEffects()
 */
public class EffectChannel extends MachineChannel {

    // If machineIndex is -1, this is the master effect channel

    private static final int NUM_SLOTS = 2;

    //--------------------------------------------------------------------------
    // Serialized API
    //--------------------------------------------------------------------------

    @Tag(100)
    private HashMap<Integer, EffectNode> slots = new HashMap<Integer, EffectNode>();

    @Tag(101)
    private MasterChannel masterNode;

    //--------------------------------------------------------------------------
    // Public API :: Properties
    //--------------------------------------------------------------------------

    //----------------------------------
    // slots
    //----------------------------------

    /**
     * Returns whether the effects channel contains en {@link EffectNode} at the
     * specified slot.
     * 
     * @param slot The slot index (0,1).
     */
    public boolean containsEffect(int slot) {
        return slots.containsKey(slot);
    }

    /**
     * Returns an {@link EffectNode} at the specified slot or <code>null</code>.
     * 
     * @param slot The slot index (0,1).
     */
    public EffectNode getEfffect(int slot) {
        return slots.get(slot);
    }

    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------

    /**
     * Serialization
     */
    public EffectChannel() {
    }

    public EffectChannel(Machine machineNode) {
        super(machineNode);
    }

    public EffectChannel(MasterChannel masterNode) {
        super(null);
        this.masterNode = masterNode;
    }

    //--------------------------------------------------------------------------
    // Public API :: Methods
    //--------------------------------------------------------------------------

    /**
     * Creates a {@link EffectNode} in the {@link #getEffects()} channel and
     * returns the new instance.
     * 
     * @param slot The effect slot (0,1).
     * @param effectType The {@link EffectType}.
     * @return The new {@link EffectNode}.
     * @throws com.teotigraphix.caustk.core.CausticException Effect channel
     *             contains effect at slot
     * @see com.teotigraphix.caustk.node.effect.EffectChannel.EffectsChannelNodeCreateEvent
     */
    @SuppressWarnings("unchecked")
    public <T extends EffectNode> T createEffect(int slot, EffectType effectType)
            throws CausticException {
        if (containsEffect(slot))
            throw new CausticException("Effect channel contains effect at slot: " + slot);

        EffectNode effectNode = getFactory().getNodeFactory().createEffect(getMachineNode(), slot,
                effectType);
        EffectsRackMessage.CREATE.send(getRack(), effectNode.getMachineIndex(),
                effectNode.getSlot(), effectNode.getType().getValue());

        set(effectNode);
        post(new EffectsChannelNodeCreateEvent(this, EffectsRackControl.Create, effectNode));

        return (T)effectNode;
    }

    public void updateEffects(Machine machineNode, EffectNode effect1, EffectNode effect2) {
        if (effect1 != null) {
            slots.put(0, effect1);
            effect1.setMachineNode(getMachineNode());
            EffectsRackMessage.CREATE.send(getRack(), machineNode.getIndex(), effect1.getSlot(),
                    effect1.getType().getValue());
            effect1.update();
        }
        if (effect2 != null) {
            slots.put(1, effect2);
            effect2.setMachineNode(getMachineNode());
            EffectsRackMessage.CREATE.send(getRack(), machineNode.getIndex(), effect2.getSlot(),
                    effect2.getType().getValue());
            effect2.update();
        }
    }

    //--------------------------------------------------------------------------
    // Overridden Protected :: Methods
    //--------------------------------------------------------------------------

    @Override
    protected void createComponents() {
    }

    @Override
    protected void destroyComponents() {
    }

    /**
     * Updates this effect channel with the source effect channel calls the
     * native OSC to create the effects, then updates to core with the source
     * effect properties.
     * 
     * @param effect
     * @throws CausticException
     */
    public void update(EffectChannel effect) throws CausticException {
        if (effect.containsEffect(0)) {
            EffectNode effect1 = effect.getEfffect(0);
            EffectsRackMessage.CREATE.send(getRack(), getMachineIndex(), 0, effect1.getType()
                    .getValue());
            slots.put(0, effect1);
            effect1.update();
        }
        if (effect.containsEffect(1)) {
            EffectNode effect2 = effect.getEfffect(1);
            EffectsRackMessage.CREATE.send(getRack(), getMachineIndex(), 1, effect2.getType()
                    .getValue());
            slots.put(1, effect2);
            effect2.update();
        }
    }

    @Override
    protected void updateComponents() {
        for (int i = 0; i < NUM_SLOTS; i++) {
            if (containsEffect(i)) {
                EffectNode effectNode = getEfffect(i);
                EffectsRackMessage.CREATE.send(getRack(), effectNode.getMachineIndex(),
                        effectNode.getSlot(), effectNode.getType().getValue());
                effectNode.update();
            }
        }
    }

    @Override
    protected void restoreComponents() {
        if (getMachineNode() != null) {
            for (int i = 0; i < NUM_SLOTS; i++) {
                EffectType type = EffectType.fromInt((int)EffectsRackMessage.TYPE.send(getRack(),
                        getMachineIndex(), i));
                if (type != null) {
                    EffectNode effect;
                    try {
                        effect = createEffect(i, type);
                        effect.restore();
                    } catch (CausticException e) {
                        getLogger().err("EffectsChannelNode", e.getMessage());
                    }
                }
            }
        } else if (masterNode != null) {
            // XXX Implement MasterNode effects restore
        }
    }

    //--------------------------------------------------------------------------
    // Private :: Methods
    //--------------------------------------------------------------------------

    private void set(EffectNode effectNode) {
        slots.put(effectNode.getSlot(), effectNode);
    }

    //--------------------------------------------------------------------------
    // Events
    //--------------------------------------------------------------------------

    /**
     * Base event for the {@link EffectChannel}.
     * 
     * @author Michael Schmalle
     * @since 1.0
     */
    public static class EffectsChannelNodeEvent extends NodeEvent {
        public EffectsChannelNodeEvent(NodeBase target, IOSCControl control) {
            super(target, control);
        }
    }

    /**
     * @author Michael Schmalle
     * @since 1.0
     * @see EffectChannel#createEffect(int, EffectType)
     */
    public static class EffectsChannelNodeCreateEvent extends NodeEvent {
        private EffectNode effectNode;

        public EffectNode getEffectNode() {
            return effectNode;
        }

        public EffectsChannelNodeCreateEvent(NodeBase target, IOSCControl control,
                EffectNode effectNode) {
            super(target, control);
            this.effectNode = effectNode;
        }
    }

}
