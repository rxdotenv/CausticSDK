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

package com.teotigraphix.caustic.internal.actvity;

import android.os.Bundle;

import com.google.inject.Inject;
import com.teotigraphix.caustic.activity.ICausticEngineCore;
import com.teotigraphix.caustic.song.IWorkspace;

public class CausticEngineCore implements ICausticEngineCore {

    @Inject
    IWorkspace workspace;

    public CausticEngineCore() {
    }

    @Override
    public void onCreate(Bundle state) {
    }

    @Override
    public void onStart() {
        workspace.getRack().getEngine().onStart();
    }

    @Override
    public void onStop() {
        workspace.getRack().getEngine().onStop();
    }

    @Override
    public void onPause() {
        workspace.getRack().getEngine().onPause();
    }

    @Override
    public void onResume() {
        workspace.getRack().getEngine().onResume();
    }

    @Override
    public void onDestroy() {
        workspace.getRack().getEngine().onDestroy();
    }

    @Override
    public void onRestart() {
        workspace.getRack().getEngine().onRestart();
    }
}
