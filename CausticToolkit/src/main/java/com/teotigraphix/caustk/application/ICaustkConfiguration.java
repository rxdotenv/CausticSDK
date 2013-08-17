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

package com.teotigraphix.caustk.application;

import java.io.File;

import com.teotigraphix.caustk.controller.CaustkController;
import com.teotigraphix.caustk.controller.ICaustkController;
import com.teotigraphix.caustk.core.ICausticEngine;
import com.teotigraphix.caustk.sound.ISoundGenerator;

/**
 * @author Michael Schmalle
 */
public interface ICaustkConfiguration {

    /**
     * Returns the machine id.
     */
    String getApplicationId();

    /**
     * Returns the native root of the application's directory.
     */
    File getApplicationRoot();

    /**
     * Set the application directory root.
     * <p>
     * This root uses the application name.
     * 
     * @param value A directory that will be created for the root of the
     *            application.
     */
    void setApplicationRoot(File value);

    /**
     * Returns the storage directory that holds the <code>caustic</code>
     * directory.
     */
    File getCausticStorage();

    /**
     * Sets the caustic storage root.
     * 
     * @param value A directory holding the <code>caustic</code> folder.
     */
    void setCausticStorage(File value);

    //--------------------------------------------------------------------------
    // Factory Methods
    //--------------------------------------------------------------------------

    /**
     * The main {@link CaustkController} instance that instrumentates the whole
     * application sequencing from patterns, parts, presets, memory and all
     * other things needing controlling.
     * <p>
     * If the device framework was a hierarchy which it kind of is, the
     * {@link CaustkController} is the top device, other than a
     * GrooveBoxApplication.
     * 
     * @param application
     */
    ICaustkController createController(ICaustkApplication application);

    /**
     * Creates the core {@link ICausticEngine} implementation for the desktop or
     * android device.
     * 
     * @param controller The main controller.
     * @return The single instance of the {@link ISoundGenerator}.
     */
    ISoundGenerator createSoundGenerator(ICaustkController controller);

}
