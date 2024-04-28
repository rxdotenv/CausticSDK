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

package com.teotigraphix.caustk.gdx.scene2d.ui;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

public abstract class PaneStackListener implements EventListener {

    public PaneStackListener() {
    }

    @Override
    public boolean handle(Event event) {
        if (event instanceof PaneStackEvent) {
            PaneStackEvent e = (PaneStackEvent)event;
            selecteIndexChange(e, e.getIndex());
        }
        return false;
    }

    public abstract void selecteIndexChange(PaneStackEvent event, int index);

    public enum PaneStackEventKind {
        SelectedIndexChange
    }

    public static class PaneStackEvent extends Event {

        private PaneStackEventKind kind;

        private int index;

        @Override
        public PaneStack getTarget() {
            return (PaneStack)super.getTarget();
        }

        public PaneStackEventKind getKind() {
            return kind;
        }

        public int getIndex() {
            return index;
        }

        public PaneStackEvent(PaneStackEventKind kind, int index) {
            this.kind = kind;
            this.index = index;
        }

        @Override
        public void reset() {
            super.reset();
            kind = null;
        }
    }
}
