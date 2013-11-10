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

package com.teotigraphix.caustk.workstation;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;
import com.teotigraphix.caustk.controller.ICaustkApplicationContext;
import com.teotigraphix.caustk.core.CausticException;
import com.teotigraphix.caustk.utils.PatternUtils;

/**
 * @author Michael Schmalle
 */
public class Pattern extends CaustkComponent {

    private transient Song song;

    //--------------------------------------------------------------------------
    // Serialized API
    //--------------------------------------------------------------------------

    @Tag(100)
    private PatternSet patternSet;

    // the index the PatternSet assigns
    @Tag(101)
    private int index;

    @Tag(102)
    private int selectedPartIndex;

    @Tag(103)
    private int octave;

    @Tag(104)
    private float tempo = 120f;

    @Tag(105)
    private int length;

    //--------------------------------------------------------------------------
    // Public API :: Properties
    //--------------------------------------------------------------------------

    //----------------------------------
    // defaultName
    //----------------------------------

    @Override
    public String getDefaultName() {
        return PatternUtils.toString(getBankIndex(), getPatternIndex());
    }

    //---------------------------------- 
    // index
    //----------------------------------

    /**
     * The index of the pattern within the owning {@link PatternSet} (0..63).
     */
    public final int getIndex() {
        return index;
    }

    //---------------------------------- 
    // selectedPartIndex
    //----------------------------------

    public int getSelectedPartIndex() {
        return selectedPartIndex;
    }

    /**
     * @param value
     * @see OnPatternChange
     * @see PatternChangeKind#SelectedPartIndex
     */
    public void setSelectedPartIndex(int value) {
        if (value == selectedPartIndex)
            return;
        selectedPartIndex = value;
        int oldIndex = selectedPartIndex;
        selectedPartIndex = value;
        trigger(new OnPatternChange(this, PatternChangeKind.SelectedPartIndex, selectedPartIndex,
                oldIndex));
    }

    //----------------------------------
    // octave
    //----------------------------------

    /**
     * Returns the transpose octave for all {@link Part}s
     * 
     * @see #transpose(int)
     */
    public int getOctave() {
        return octave;
    }

    //----------------------------------
    // tempo
    //----------------------------------

    public float getTempo() {
        return tempo;
    }

    /**
     * Sets the pattern tempo, will also set the native output panel bpm.
     * 
     * @param value (60..250)
     * @see OnPatternChange
     * @see PatternChangeKind#Tempo
     */
    public void setTempo(float value) {
        if (value == tempo)
            return;
        tempo = value;
        patternSet.getRackSet().getSequencer().setBPM(tempo);
        trigger(new OnPatternChange(this, PatternChangeKind.Tempo));
    }

    //----------------------------------
    // length
    //----------------------------------

    public int getLength() {
        return length;
    }

    /**
     * Sets the pattern length, updates all {@link Part}s
     * {@link Phrase#getLength()}.
     * 
     * @param value (1, 2, 4, 8)
     * @see OnPatternChange
     * @see PatternChangeKind#Length
     */
    public void setLength(int value) {
        if (value == length)
            return;
        length = value;
        for (Part part : patternSet.getParts()) {
            Phrase phrase = getPhrase(part.getMachineIndex());
            phrase.setLength(length);
        }
        trigger(new OnPatternChange(this, PatternChangeKind.Length));
    }

    //----------------------------------
    // bankIndex
    //----------------------------------

    /**
     * Returns the native pattern_sequencer bank index calculated from the
     * {@link #getIndex()}.
     */
    public final int getBankIndex() {
        return PatternUtils.getBank(index);
    }

    //----------------------------------
    // patternIndex
    //----------------------------------

    /**
     * Returns the native pattern_sequencer pattern index calculated from the
     * {@link #getIndex()}.
     */
    public final int getPatternIndex() {
        return PatternUtils.getPattern(index);
    }

    //----------------------------------
    // song
    //----------------------------------

    public final Song getSong() {
        return song;
    }

    //----------------------------------
    // part
    //----------------------------------

    /**
     * Returns the pattern's selected {@link Part} using the
     * {@link #getSelectedPartIndex()}.
     */
    public Part getSelectedPart() {
        return getPart(selectedPartIndex);
    }

    /**
     * Returns the {@link Part} at the specified index.
     * 
     * @param partIndex The part index (0..13).
     */
    public final Part getPart(int partIndex) {
        return patternSet.getPart(partIndex);
    }

    //----------------------------------
    // phrase
    //----------------------------------

    /**
     * Returns the pattern's selected {@link Part}'s {@link Phrase} using the
     * {@link #getSelectedPartIndex()}.
     * <p>
     * When the {@link PatternChangeKind#SelectedPartIndex} is fired, this will
     * reflect the new {@link Phrase} of the selected {@link Part}.
     */
    public Phrase getSelectedPhrase() {
        return getPhrase(selectedPartIndex);
    }

    /**
     * Returns the {@link Phrase} of the machine at the {@link Pattern}'s bank
     * and pattern index.
     * <p>
     * Uses the {@link Part#getMachine()} to retrieve the {@link Machine}'s
     * {@link Phrase}.
     */
    public final Phrase getPhrase(int partIndex) {
        Part part = getPart(partIndex);
        if (part == null)
            throw new IllegalStateException("Part is null at index: " + partIndex);
        Machine machine = part.getMachine();
        return machine.getPhrase(getBankIndex(), getPatternIndex());
    }

    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------

    /*
     * Serialization.
     */
    Pattern() {
    }

    Pattern(ComponentInfo info, PatternSet patternSet, int index) {
        setInfo(info);
        this.patternSet = patternSet;
        this.index = index;
    }

    //--------------------------------------------------------------------------
    // Public API :: Methods
    //--------------------------------------------------------------------------

    /**
     * Transposes all {@link Part}'s {@link Machine}s
     * 
     * @param octave
     * @see OnPatternChange
     * @see PatternChangeKind#Octave
     */
    public void transpose(int octave) {
        if (octave == this.octave)
            return;
        this.octave = octave;
        for (Part part : patternSet.getParts()) {
            Phrase phrase = getPhrase(part.getMachineIndex());
            phrase.transpose(octave);
        }
        trigger(new OnPatternChange(this, PatternChangeKind.Octave));
    }

    @Override
    protected void componentPhaseChange(ICaustkApplicationContext context, ComponentPhase phase)
            throws CausticException {
        switch (phase) {
            case Connect:
                break;
            case Create:
                break;
            case Disconnect:
                break;
            case Load:
                break;
            case Restore:
                break;
            case Update:
                patternSet.getRackSet().getSequencer().setBPM(tempo);
                break;
        }
    }

    //--------------------------------------------------------------------------
    // Event API
    //--------------------------------------------------------------------------

    private void trigger(Object event) {
        patternSet.getRackSet().getComponentDispatcher().trigger(event);
    }

    public enum PatternChangeKind {

        SelectedPartIndex,

        Octave,

        Tempo,

        Length
    }

    /**
     * @author Michael Schmalle
     * @see RackSet#getComponentDispatcher()
     */
    public static class OnPatternChange {

        private Pattern pattern;

        private PatternChangeKind kind;

        private int index;

        private int oldIndex;

        private Part part;

        public Pattern getPattern() {
            return pattern;
        }

        public PatternChangeKind getKind() {
            return kind;
        }

        /**
         * @see PatternChangeKind#SelectedPartIndex
         */
        public int getIndex() {
            return index;
        }

        /**
         * @see PatternChangeKind#SelectedPartIndex
         */
        public int getOldIndex() {
            return oldIndex;
        }

        /**
         * @see PatternChangeKind#PartAdd
         */
        public Part getPart() {
            return part;
        }

        public OnPatternChange(Pattern pattern, PatternChangeKind kind) {
            this.pattern = pattern;
            this.kind = kind;
        }

        public OnPatternChange(Pattern pattern, PatternChangeKind kind, int index, int oldIndex) {
            this.pattern = pattern;
            this.kind = kind;
            this.index = index;
            this.oldIndex = oldIndex;
        }

        public OnPatternChange(Pattern pattern, PatternChangeKind kind, Part part) {
            this.pattern = pattern;
            this.kind = kind;
            this.part = part;
        }
    }

}
