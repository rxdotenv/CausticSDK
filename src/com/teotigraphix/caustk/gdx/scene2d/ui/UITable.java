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

package com.teotigraphix.caustk.gdx.scene2d.ui;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class UITable extends Table { // IHelpManagerAware, IValueAware

    //--------------------------------------------------------------------------
    // Private :: Variables
    //--------------------------------------------------------------------------

    private Map<String, Object> properties;

    private String styleName;

    private Class<? extends Object> styleClass;

    private Skin skin;

    private float originalValue = Float.MIN_VALUE;

    //--------------------------------------------------------------------------
    // Public Property :: API
    //--------------------------------------------------------------------------

    //----------------------------------
    // properties
    //----------------------------------

    /**
     * Lazy property map.
     */
    public final Map<String, Object> getProperties() {
        if (properties == null)
            properties = new HashMap<String, Object>();
        return properties;
    }

    //----------------------------------
    // helpText
    //----------------------------------

    public String getHelpText() {
        return null;
    };

    //----------------------------------
    // styleName
    //----------------------------------

    /**
     * Returns the unique styleName for style lookup of this component.
     */
    public String getStyleName() {
        if (styleName == null)
            return "default";
        return styleName;
    }

    /**
     * Sets the unique styleName for style lookup.
     * 
     * @param value The String styleName.
     */
    public void setStyleName(String value) {
        styleName = value;
    }

    /**
     * Returns the component's style based on {@link #getStyleName()}.
     */
    @SuppressWarnings("unchecked")
    public <T> T getStyle() {
        return (T)getStyle(styleClass);
    }

    /**
     * Subclasses set the specific styleClass.
     * 
     * @param styleClass The class used for styling.
     */
    protected void setStyleClass(Class<? extends Object> styleClass) {
        this.styleClass = styleClass;
    }

    //----------------------------------
    // skin
    //----------------------------------

    /**
     * Returns the skin.
     */
    public Skin getSkin() {
        return skin;
    }

    /**
     * Override original behavior, superclass will never create text, buttons
     * etc.
     */
    @Override
    public void setSkin(Skin skin) {
        super.setSkin(skin);
        this.skin = skin;
    }

    //----------------------------------
    // originalValue
    //----------------------------------

    //@Override
    public Actor getActor() {
        return this;
    }

    //@Override
    public float getValue() {
        return Float.MIN_VALUE;
    }

    public boolean setValue(float value) {
        return false;
    }

    //@Override
    public float getOriginalValue() {
        return originalValue;
    }

    //@Override
    public void setOriginalValue(float value) {
        originalValue = value;
    }

    //@Override
    public void resetValue() {
        if (originalValue == Float.MIN_VALUE)
            return;
        setValue(originalValue);
    }

    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------

    public UITable(Skin skin) {
        setSkin(skin);
    }

    //--------------------------------------------------------------------------
    // Public :: Methods
    //--------------------------------------------------------------------------

    /**
     * Creates the componet's children.
     * 
     * @param styleName The styleName to use for child construction.
     */
    public final void create(String styleName) {
        setStyleName(styleName);
        createChildren();
    }

    public final void create() {
        create("default");
    }

    /**
     * Create all component children using current {@link #getStyle()}.
     */
    protected abstract void createChildren();

    /**
     * Returns the component style based on styleName.
     * 
     * @param styleClass The styleClass to lookup it's styleName instance.
     */
    public <T> T getStyle(Class<T> styleClass) {
        return styleClass.cast(skin.get(styleName, styleClass));
    }
}
