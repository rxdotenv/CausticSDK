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

package com.teotigraphix.caustk.core.internal;

import android.app.Activity;
import android.os.Bundle;

import com.teotigraphix.caustk.core.CausticException;
import com.teotigraphix.caustk.core.ISoundGenerator;
import com.teotigraphix.caustk.core.internal.generator.AndroidSoundGenerator;

/*

Open Application

- onCreate()
- onStart()
- onResume()

Back button to Home

- onPause()
- onStop()
- onDestroy()

Go back into application

- onCreate()
- onStart()
- onResume()

Go to another Activity

- onPause()
- onStop()

Back into running App

- onRestart()
- onStart() [Threads are still alive in the core]
- onResume() [Threads are told to run again to process audio data]

*/

/**
 * The {@link CaustkActivity} is a simple base class for a Caustk application
 * that creates the
 * {@link com.teotigraphix.caustk.core.generator.AndroidSoundGenerator} and
 * proxys it's life cycle events for the internal sound engine.
 * <p>
 * Subclasses must override the {@link #getActivationKey()} with the key
 * registered against the application's Android package name. Or use a demo key
 * that expires after a provided time length.
 * 
 * @author Michael Schmalle
 */
public abstract class CaustkActivity extends Activity {

    private ISoundGenerator generator;

    /**
     * Returns the single instance of the CausticCore sound generator.
     */
    public ISoundGenerator getGenerator() {
        return generator;
    }

    /**
     * Override in subclasses to return the CausticCore activation key for the
     * registered application.
     */
    protected abstract int getActivationKey();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            generator = new AndroidSoundGenerator(this, getActivationKey());
        } catch (CausticException e) {
            // TODO Handle AndroidSoundGenerator exception for link
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        generator.onStart();
        super.onStart();
    }

    @Override
    protected void onPause() {
        generator.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        generator.onResume();
        super.onResume();
    }

    @Override
    protected void onStop() {
        generator.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        generator.onDestroy();
        super.onDestroy();
    }
}
