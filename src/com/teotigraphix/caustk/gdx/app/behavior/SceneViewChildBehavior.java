////////////////////////////////////////////////////////////////////////////////
//Copyright 2014 Michael Schmalle - Teoti Graphix, LLC
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License
//
//Author: Michael Schmalle, Principal Architect
//mschmalle at teotigraphix dot com
////////////////////////////////////////////////////////////////////////////////

package com.teotigraphix.caustk.gdx.app.behavior;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.teotigraphix.caustk.gdx.app.ui.CaustkBehavior;

/**
 * A child of the Main scene's viewstack.
 */
public abstract class SceneViewChildBehavior extends CaustkBehavior {
    private SceneViewChildData data;

    public SceneViewChildData getData() {
        return data;
    }

    public void setData(SceneViewChildData data) {
        this.data = data;
    }

    public SceneViewChildBehavior() {
    }

    public abstract Table create();
}
