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

/**
 * The {@link SequencerMessage} holds all OSC messages associated with the
 * {@link ISequencer} API.
 * 
 * @author Michael Schmalle
 * @since 1.0
 */
public class SequencerMessage extends CausticMessage {
    /**
     * Message: <code>/caustic/sequencer/song_end_mode [mode]</code>
     * <p>
     * Sequences a machine pattern into the song sequencer.
     * <p>
     * <strong>Default</strong>: <code>2</code>
     * <p>
     * <strong>Parameters</strong>:
     * <ul>
     * <li><strong>mode</strong>: The mode(0,1,2) keep playing, stop, loop to
     * start.</li>
     * </ul>
     * <p>
     * <strong>Returns</strong>: <code>int</code>
     */
    public static final SequencerMessage SONG_END_MODE = new SequencerMessage(
            "/caustic/sequencer/song_end_mode ${0}");

    /**
     * Message:
     * <code>/caustic/sequencer/pattern_event [machin_index] [start_measure] [bank] [pattern] [end_measure]</code>
     * <p>
     * Sequences a machine pattern into the song sequencer.
     * <p>
     * <strong>Default</strong>: <code>N/A</code>
     * <p>
     * <strong>Parameters</strong>:
     * <ul>
     * <li><strong>machine_index</strong>: The machine index.</li>
     * <li><strong>start_measure</strong>: The start measure at insert.</li>
     * <li><strong>bank</strong>: The bank index of the machine for insertion.</li>
     * <li><strong>pattern</strong>: The pattern index of the machine for
     * insertion.</li>
     * <li><strong>end_measure</strong>: The end measure at insert.</li>
     * </ul>
     * <p>
     * <strong>Returns</strong>: <code>N/A</code>
     */
    public static final SequencerMessage PATTERN_EVENT = new SequencerMessage(
            "/caustic/sequencer/pattern_event ${0} ${1} ${2} ${3} ${4}");

    public static final SequencerMessage PATTERN_EVENT_REMOVE = new SequencerMessage(
            "/caustic/sequencer/pattern_event ${0} ${1} -1 -1 ${2}");

    /**
     * Query: <code>/caustic/sequencer/pattern_event</code>
     * <p>
     * Returns a token string separated by <code>|</code> of all patterns in the
     * sequencer.
     * <p>
     * Example token
     * <code>[machin_index] [start_measure] [bank] [pattern] [end_measure]</code>.
     * <p>
     * <strong>Default</strong>: <code>N/A</code>
     * <p>
     * <strong>Returns</strong>: <code>Token string</code>
     */
    public static final SequencerMessage QUERY_PATTERN_EVENT = new SequencerMessage(
            "/caustic/sequencer/pattern_event");

    /**
     * Message:
     * <code>/caustic/sequencer/loop_points [start_measure] [end_measure]</code>
     * <p>
     * Sets the song sequencers start and end loop points.
     * <p>
     * <strong>Default</strong>: <code>N/A</code>
     * <p>
     * <strong>Parameters</strong>:
     * <ul>
     * <li><strong>start_measure</strong>: The start measure at loop.</li>
     * <li><strong>end_measure</strong>: The end measure at loop.</li>
     * </ul>
     * <p>
     * <strong>Returns</strong>: <code>N/A</code>
     */
    public static final SequencerMessage LOOP_POINTS = new SequencerMessage(
            "/caustic/sequencer/loop_points ${0} ${1}");

    /**
     * Message: <code>/caustic/sequencer/play_position [beat]</code>
     * <p>
     * Sets the playhead of the song sequencer to a specific beat.
     * <p>
     * <strong>Default</strong>: <code>N/A</code>
     * <p>
     * <strong>Parameters</strong>:
     * <ul>
     * <li><strong>beat</strong>: The start beat to begin play.</li>
     * </ul>
     * <p>
     * <strong>Returns</strong>: <code>N/A</code>
     */
    public static final SequencerMessage PLAY_POSITION = new SequencerMessage(
            "/caustic/sequencer/play_position ${0}");

    //    /**
    //     * Message:
    //     * <code>/caustic/sequencer/export_song [path] [type] [quality]</code>
    //     * <p>
    //     * Exports a song file to an external media file.
    //     * <p>
    //     * <strong>Default</strong>: <code>N/A</code>
    //     * <p>
    //     * <strong>Parameters</strong>:
    //     * <ul>
    //     * <li><strong>path</strong>: The location of export.</li>
    //     * <li><strong>type</strong>: The export type (WAV, OGG, MID).</li>
    //     * <li><strong>quality</strong>: The quality of export (0-100) OGG only.</li>
    //     * </ul>
    //     * <p>
    //     * <strong>Returns</strong>: <code>N/A</code>
    //     */
    //    public static final SequencerMessage EXPORT_SONG = new SequencerMessage(
    //            "/caustic/sequencer/export_song ${0} ${1} ${2}");

    //    /**
    //     * Message: <code>/caustic/sequencer/export_song [path] [type]</code>
    //     * <p>
    //     * Exports a song file to an external media file.
    //     * <p>
    //     * <strong>Default</strong>: <code>N/A</code>
    //     * <p>
    //     * <strong>Parameters</strong>:
    //     * <ul>
    //     * <li><strong>path</strong>: The location of export.</li>
    //     * <li><strong>type</strong>: The export type (WAV, OGG, MID).</li>
    //     * </ul>
    //     * <p>
    //     * <strong>Returns</strong>: <code>N/A</code>
    //     */
    //    public static final SequencerMessage EXPORT_SONG_DEFAULT = new SequencerMessage(
    //            "/caustic/sequencer/export_song ${0} ${1}");

    /**
     * Message:
     * <code>/caustic/export [loop_mode] [format] [quality] [path]</code>
     * <p>
     * <strong>Default</strong>: <code>N/A</code>
     * <p>
     * <strong>Parameters</strong>:
     * <ul>
     * <li><strong>loop_mode</strong>: (loop, song)</li>
     * <li><strong>format</strong>: The export format (WAV, OGG, MID).</li>
     * <li><strong>quality</strong>: The quality of export (0-100) OGG only.</li>
     * <li><strong>path</strong>: The full path to the exported file.</li>
     * </ul>
     * <p>
     * <strong>Returns</strong>: <code>N/A</code>
     * 
     * @since 1.0
     */
    public static final RackMessage EXPORT = new RackMessage(
            "/caustic/sequencer/export ${0} ${1} ${2} ${3}");

    /**
     * Message: <code>/caustic/sequencer/export_progress</code>
     * <p>
     * Returns the current progress of the song export from <code>0..100</code>.
     * <p>
     * <strong>Default</strong>: <code>N/A</code>
     * <p>
     * <strong>Parameters</strong>:
     * <ul>
     * <li><strong>N/A</strong></li>
     * </ul>
     * <p>
     * <strong>Returns</strong>: <code>float</code> (0-100) The progress of
     * exported samples.
     */
    public static final SequencerMessage EXPORT_PROGRESS = new SequencerMessage(
            "/caustic/sequencer/export_progress");

    /**
     * Message: <code>/caustic/sequencer/clear_patterns</code>
     * <p>
     * Clears all the patterns from the song sequencer.
     * <p>
     * <strong>Default</strong>: <code>N/A</code>
     * <p>
     * <strong>Parameters</strong>:
     * <ul>
     * <li><strong>N/A</strong></li>
     * </ul>
     * <p>
     * <strong>Returns</strong>: <code>N/A</code>
     */
    public static final SequencerMessage CLEAR_PATTERNS = new SequencerMessage(
            "/caustic/sequencer/clear_patterns");

    /**
     * Message: <code>/caustic/sequencer/clear_automation [machine_index]</code>
     * <p>
     * <strong>Default</strong>: <code>N/A</code>
     * <p>
     * <strong>Parameters</strong>:
     * <ul>
     * <li><strong>machine_index</strong>: The index of the machine to clear
     * automation.</li>
     * </ul>
     * <p>
     * <strong>Returns</strong>: <code>N/A</code>
     */
    public static final SequencerMessage CLEAR_MACHINE_AUTOMATION = new SequencerMessage(
            "/caustic/sequencer/clear_automation ${0}");

    /**
     * Message: <code>/caustic/sequencer/clear_automation</code>
     * <p>
     * Clears all automation from all machines in the song sequencer.
     * <p>
     * <strong>Default</strong>: <code>N/A</code>
     * <p>
     * <strong>Parameters</strong>:
     * <ul>
     * <li><strong>N/A</strong></li>
     * <p>
     * <strong>Returns</strong>: <code>N/A</code>
     */
    public static final SequencerMessage CLEAR_AUTOMATION = new SequencerMessage(
            "/caustic/sequencer/clear_automation");

    SequencerMessage(String message) {
        super(message);
    }
}
