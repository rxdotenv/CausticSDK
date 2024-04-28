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

package com.teotigraphix.caustk.node.machine.patch.fmsynth;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;
import com.teotigraphix.caustk.core.osc.FMSynthMessage;
import com.teotigraphix.caustk.core.osc.FMSynthMessage.FMAlgorithm;
import com.teotigraphix.caustk.node.machine.MachineChannel;
import com.teotigraphix.caustk.node.machine.Machine;

/**
 * The fm controls component.
 * 
 * @author Michael Schmalle
 * @since 1.0
 */
public class FMControlsComponent extends MachineChannel {

    //--------------------------------------------------------------------------
    // Serialized API
    //--------------------------------------------------------------------------

    @Tag(100)
    private FMAlgorithm algorithm = FMAlgorithm.ThreeTwoOne;

    @Tag(101)
    private float feedback = 0f;

    @Tag(102)
    private boolean feedbackVelocity = false;

    @Tag(103)
    private boolean volumeVelocity = false;

    //--------------------------------------------------------------------------
    // Public API :: Properties
    //--------------------------------------------------------------------------

    //----------------------------------
    // algorithm
    //----------------------------------

    /**
     * @see com.teotigraphix.caustk.core.osc.FMSynthMessage#ALGORITHM
     */
    public FMAlgorithm getAlgorithm() {
        return algorithm;
    }

    public FMAlgorithm queryAlgorithm() {
        return FMAlgorithm.toType(FMSynthMessage.ALGORITHM.query(getRack(), getMachineIndex()));
    }

    /**
     * @param algorithm FMAlgorithm
     * @see com.teotigraphix.caustk.core.osc.FMSynthMessage#ALGORITHM
     */
    public void setAlgorithm(FMAlgorithm algorithm) {
        if (algorithm == this.algorithm)
            return;
        this.algorithm = algorithm;
        FMSynthMessage.ALGORITHM.send(getRack(), getMachineIndex(), algorithm.getValue());
    }

    //----------------------------------
    // feedback
    //----------------------------------

    /**
     * @see com.teotigraphix.caustk.core.osc.FMSynthMessage#FEEDBACK
     */
    public float getFeedback() {
        return feedback;
    }

    public float queryFeedback() {
        return FMSynthMessage.FEEDBACK.query(getRack(), getMachineIndex());
    }

    /**
     * @param feedback (0.0..0.25)
     * @see com.teotigraphix.caustk.core.osc.FMSynthMessage#FEEDBACK
     */
    public void setFeedback(float feedback) {
        if (feedback == this.feedback)
            return;
        if (feedback < 0 || feedback > 0.25f)
            throw newRangeException(FMSynthMessage.FEEDBACK, "0..0.25", feedback);
        this.feedback = feedback;
        FMSynthMessage.FEEDBACK.send(getRack(), getMachineIndex(), feedback);
    }

    //----------------------------------
    // feedbackVelocity
    //----------------------------------

    /**
     * @see com.teotigraphix.caustk.core.osc.FMSynthMessage#FEEDBACK_VEL
     */
    public boolean isFeedbackVelocity() {
        return feedbackVelocity;
    }

    public boolean queryFeedbackVelocity() {
        return FMSynthMessage.FEEDBACK_VEL.query(getRack(), getMachineIndex()) == 0f ? false : true;
    }

    /**
     * @param feedbackVelocity (true|false)
     * @see com.teotigraphix.caustk.core.osc.FMSynthMessage#FEEDBACK_VEL
     */
    public void setFeedbackVelocity(boolean feedbackVelocity) {
        if (feedbackVelocity == this.feedbackVelocity)
            return;
        this.feedbackVelocity = feedbackVelocity;
        FMSynthMessage.FEEDBACK_VEL.send(getRack(), getMachineIndex(), feedbackVelocity ? 1 : 0);
    }

    //----------------------------------
    // feedbackVelocity
    //----------------------------------

    /**
     * @see com.teotigraphix.caustk.core.osc.FMSynthMessage#FEEDBACK_VEL
     */
    public boolean isVolumeVelocity() {
        return volumeVelocity;
    }

    public boolean queryVolumeVelocity() {
        return FMSynthMessage.VOLUME_VEL.query(getRack(), getMachineIndex()) == 0f ? false : true;
    }

    /**
     * @param volumeVelocity (true|false)
     * @see com.teotigraphix.caustk.core.osc.FMSynthMessage#VOLUME_VEL
     */
    public void setVolumeVelocity(boolean volumeVelocity) {
        if (volumeVelocity == this.volumeVelocity)
            return;
        this.volumeVelocity = volumeVelocity;
        FMSynthMessage.VOLUME_VEL.send(getRack(), getMachineIndex(), volumeVelocity ? 1 : 0);
    }

    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------

    /**
     * Serialization
     */
    public FMControlsComponent() {
    }

    public FMControlsComponent(Machine machineNode) {
        super(machineNode);
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

    @Override
    protected void updateComponents() {
        FMSynthMessage.ALGORITHM.send(getRack(), getMachineIndex(), algorithm.getValue());
        FMSynthMessage.FEEDBACK.send(getRack(), getMachineIndex(), feedback);
        FMSynthMessage.FEEDBACK_VEL.send(getRack(), getMachineIndex(), feedbackVelocity ? 1 : 0);
        FMSynthMessage.VOLUME_VEL.send(getRack(), getMachineIndex(), volumeVelocity ? 1 : 0);
    }

    @Override
    protected void restoreComponents() {
        setAlgorithm(queryAlgorithm());
        setFeedback(queryFeedback());
        setFeedbackVelocity(queryFeedbackVelocity());
        setVolumeVelocity(queryVolumeVelocity());
    }
}
