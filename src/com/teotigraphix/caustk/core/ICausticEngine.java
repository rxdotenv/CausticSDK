////////////////////////////////////////////////////////////////////////////////
// Copyright 2011 Michael Schmalle - Teoti Graphix, LLC
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

package com.teotigraphix.caustk.core;

import com.singlecellsoftware.causticcore.CausticCore;

/**
 * The {@link ICausticEngine} API is the basic interface that communicates with
 * the {@link CausticCore} using OSC String messages.
 * <p>
 * Any core application must call the lifecycle event methods on the core
 * instance to enable, disable the audio thread.
 * 
 * @author Michael Schmalle
 * @copyright Teoti Graphix, LLC
 * @since 1.0
 */
public interface ICausticEngine {

    /**
     * Sends an OSC message to the {@link CausticCore}.
     * <p>
     * This method is the fastest way to send a message to the Caustic core.
     * 
     * @param message The String OSC message.
     * @return A float value if the message returns a value, otherwise NaN.
     * @since 1.0
     */
    float sendMessage(String message);

    /**
     * Queries an OSC message from the Caustic core.
     * 
     * @param message The String OSC message.
     * @return A String message or <code>null</code> if the message returned and
     *         <strong>never</strong> an <em>empty</em> String <code>""</code>.
     * @since 1.0
     */
    String queryMessage(String message);

    // Lifesycle

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onRestart();

    void onDispose();
}
