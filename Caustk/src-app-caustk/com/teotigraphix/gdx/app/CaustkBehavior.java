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

package com.teotigraphix.gdx.app;

import com.google.inject.Inject;
import com.teotigraphix.caustk.core.ICaustkRack;
import com.teotigraphix.caustk.node.RackNode;
import com.teotigraphix.gdx.controller.IDialogManager;
import com.teotigraphix.gdx.controller.IFileManager;
import com.teotigraphix.gdx.controller.IFileModel;
import com.teotigraphix.gdx.controller.IViewManager;
import com.teotigraphix.gdx.controller.command.CommandExecutionException;
import com.teotigraphix.gdx.controller.command.ICommandManager;
import com.teotigraphix.gdx.controller.view.AbstractDisplay;
import com.teotigraphix.gdx.groove.ui.IContainerMap;
import com.teotigraphix.gdx.groove.ui.factory.UIFactory;

public abstract class CaustkBehavior extends Behavior {

    @Inject
    private IApplicationModel applicationModel;

    @Inject
    private IApplicationState applicationStateInternal;

    @Inject
    private IFileManager fileManager;

    @Inject
    private IFileModel fileModel;

    @Inject
    private IViewManager viewManager;

    @Inject
    private IDialogManager dialogManager;

    @Override
    public final ICaustkApplication getApplication() {
        return (ICaustkApplication)super.getApplication();
    }

    public final IApplicationModel getApplicationModel() {
        return applicationModel;
    }

    public IProjectModel getProjectModel() {
        return getScene().getProjectModel();
    }

    @Override
    public ICaustkScene getScene() {
        return (ICaustkScene)super.getScene();
    }

    public IApplicationState getApplicationState() {
        return applicationStateInternal;
    }

    protected final IContainerMap getContainerMap() {
        return ((CaustkScene)getScene()).getContainerMap();
    }

    public final ICaustkRack getRack() {
        return getApplication().getRack();
    }

    protected final RackNode getRackNode() {
        return getApplication().getRack().getRackNode();
    }

    protected UIFactory getFactory() {
        return getScene().getFactory();
    }

    /**
     * Used by Behavior proxy classes.
     */
    public IViewManager getViewManager() {
        return viewManager;
    }

    protected final IDialogManager getDialogManager() {
        return dialogManager;
    }

    protected final AbstractDisplay getDisplay() {
        return viewManager.getDisplay();
    }

    protected final AbstractDisplay getSubDisplay() {
        return viewManager.getSubDisplay();
    }

    protected final IFileModel getFileModel() {
        return fileModel;
    }

    protected final IFileManager getFileManager() {
        return fileManager;
    }

    public CaustkBehavior() {
    }

    @Inject
    private ICommandManager commandManager;

    public void execute(String message, Object... args) {

        try {
            commandManager.execute(message, args);
        } catch (CommandExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onAwake() {
        super.onAwake();
        getRack().getEventBus().register(this);
        getApplicationState().getEventBus().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getRack().getEventBus().unregister(this);
        getApplicationState().getEventBus().unregister(this);
    }

    /**
     * Fires every 32nd step before any beat, sixteenth or thritysecond
     * callbacks.
     * 
     * @param measure
     * @param beat
     * @param sixteenth
     * @param thirtysecond
     */
    public void onPreCalculate(int measure, float beat, int sixteenth, int thirtysecond) {
        for (ISceneBehavior child : getChildren()) {
            ((CaustkBehavior)child).onPreCalculate(measure, beat, sixteenth, thirtysecond);
        }
    }

    /**
     * Only called when {@link IRackSequencerListener} is implemented.
     * 
     * @param measure
     * @param beat
     * @param sixteenth
     * @param thirtysecond
     */
    public void onBeatChange(int measure, float beat, int sixteenth, int thirtysecond) {
        for (ISceneBehavior child : getChildren()) {
            ((CaustkBehavior)child).onBeatChange(measure, beat, sixteenth, thirtysecond);
        }
    }

    /**
     * Called before {@link #onSixteenthChange(int, float, int, int)} for global
     * calculations that need to be accessed in the onSixteenthChange().
     * 
     * @param measure
     * @param beat
     * @param sixteenth
     * @param thirtysecond
     */
    public void onPreSixteenthChange(int measure, float beat, int sixteenth, int thirtysecond) {
        for (ISceneBehavior child : getChildren()) {
            ((CaustkBehavior)child).onPreSixteenthChange(measure, beat, sixteenth, thirtysecond);
        }
    }

    /**
     * Only called when {@link IRackSequencerListener} is implemented.
     * 
     * @param measure
     * @param beat
     * @param sixteenth
     * @param thirtysecond
     */
    public void onSixteenthChange(int measure, float beat, int sixteenth, int thirtysecond) {
        for (ISceneBehavior child : getChildren()) {
            ((CaustkBehavior)child).onSixteenthChange(measure, beat, sixteenth, thirtysecond);
        }
    }

    /**
     * Only called when {@link IRackSequencerListener} is implemented.
     * 
     * @param measure
     * @param beat
     * @param sixteenth
     * @param thirtysecond
     */
    public void onThirtysecondChange(int measure, float beat, int sixteenth, int thirtysecond) {
        for (ISceneBehavior child : getChildren()) {
            ((CaustkBehavior)child).onThirtysecondChange(measure, beat, sixteenth, thirtysecond);
        }
    }

}
