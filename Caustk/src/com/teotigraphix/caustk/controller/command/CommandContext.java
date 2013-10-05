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

package com.teotigraphix.caustk.controller.command;

import com.teotigraphix.caustk.controller.ICaustkController;
import com.teotigraphix.caustk.controller.IDispatcher;
import com.teotigraphix.caustk.controller.IRack;

public class CommandContext {

    private final ICaustkController controller;

    public final IRack getRack() {
        return controller.getRack();
    }

    public final <T> T getComponent(Class<T> clazz) {
        return controller.getComponent(clazz);
    }

    private final OSCMessage message;

    public final OSCMessage getMessage() {
        return message;
    }

    /**
     * The {@link ICaustkController} as dispatcher.
     */
    protected final IDispatcher getDispatcher() {
        return controller;
    }

    public CommandContext(ICaustkController controller, OSCMessage message) {
        this.controller = controller;
        this.message = message;
    }

}