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

package com.teotigraphix.caustk.gdx.scene2d.ui.app;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.teotigraphix.caustk.gdx.app.ui.StylesDefault;
import com.teotigraphix.caustk.gdx.scene2d.ui.UITable;

public class ViewStackPane extends UITable {

    private ViewStack viewStack;

    private Array<Actor> views;

    public void select(int index) {
        viewStack.setSelectedIndex(index);
    }

    public ViewStackPane(Skin skin, Array<Actor> views) {
        super(skin);
        this.views = views;
    }

    @Override
    protected void createChildren() {
        viewStack = new ViewStack(getSkin());
        for (Actor actor : views) {
            viewStack.addView(actor); // Mute
        }

        viewStack.create(StylesDefault.ViewStack);
        add(viewStack).expand().fill();
    }

}
