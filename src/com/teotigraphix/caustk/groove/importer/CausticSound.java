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

package com.teotigraphix.caustk.groove.importer;

import com.teotigraphix.caustk.groove.library.LibrarySound;
import com.teotigraphix.caustk.groove.manifest.LibrarySoundManifest;

public class CausticSound extends CausticItem {

    private int index;

    private CausticEffect effect;

    private CausticInstrument instrument;

    private CausticPatternBank patternBank;

    @Override
    public LibrarySoundManifest getManifest() {
        return (LibrarySoundManifest)super.getManifest();
    }

    public int getIndex() {
        return index;
    }

    public CausticEffect getEffect() {
        return effect;
    }

    public CausticInstrument getInstrument() {
        return instrument;
    }

    public CausticPatternBank getPatternBank() {
        return patternBank;
    }

    public CausticSound(LibrarySound item) {
        super(item);
        this.index = item.getIndex();
        this.instrument = new CausticInstrument(item.getInstrument());
        this.effect = new CausticEffect(item.getEffect());
        this.patternBank = new CausticPatternBank(item.getPatternBank());
    }

}
