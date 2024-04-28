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

package com.teotigraphix.caustk.core;

import com.teotigraphix.caustk.gdx.app.ICaustkApplication;
import com.teotigraphix.caustk.node.ICaustkNode;

/**
 * @author Michael Schmalle
 * @since 1.0
 */
public interface ICaustkRuntime {

    /**
     * The main caustk application.
     */
    ICaustkApplication getApplication();

    /**
     * Returns the session {@link ICaustkLogger} instance.
     */
    ICaustkLogger getLogger();

    /**
     * Returns the session {@link ICaustkRack} instance.
     */
    ICaustkRack getRack();

    /**
     * Returns the session {@link ICaustkFactory} for created
     * {@link ICaustkNode} s.
     */
    ICaustkFactory getFactory();

    /**
     * Dispatches the event through the {@link ICaustkRack#post(Object)}..
     * 
     * @param event The event object.
     */
    void post(Object post);
}
