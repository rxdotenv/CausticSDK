
package com.teotigraphix.caustk.tone;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.teotigraphix.caustk.CaustkTestBase;
import com.teotigraphix.caustk.core.CausticException;
import com.teotigraphix.caustk.sound.SoundSource;
import com.teotigraphix.caustk.tone.SubSynthTone;
import com.teotigraphix.caustk.tone.Tone;
import com.teotigraphix.caustk.tone.ToneType;

public class SoundSerializationTest extends CaustkTestBase {

    // XXX Test createScene()

    private SoundSource soundSource;

    @Override
    protected void start() {
        soundSource = (SoundSource)controller.getSoundSource();
    }

    @Override
    protected void end() {
        soundSource = null;
    }

    @Test
    public void test_serialize_SubSynth() throws CausticException, IOException {
        Tone tone = soundSource.createTone(10, "part1", ToneType.SubSynth);

        String data = tone.serialize();

        SubSynthTone subsynth = soundSource.createTone(data);

        // check the index is 1, not what was saved, 'tone' is already at 0 index
        assertEquals(1, subsynth.getIndex());
        assertEquals(tone.getId(), subsynth.getId());
        assertEquals("part1", subsynth.getName());
        // check all components were created
        assertEquals(tone.getComponentCount(), subsynth.getComponentCount());
    }

}
