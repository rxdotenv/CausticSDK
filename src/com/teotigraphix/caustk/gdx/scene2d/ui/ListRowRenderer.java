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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pools;
import com.teotigraphix.caustk.gdx.scene2d.ui.AdvancedListListener.AdvancedListEvent;
import com.teotigraphix.caustk.gdx.scene2d.ui.AdvancedListListener.AdvancedListEventKind;

public abstract class ListRowRenderer extends Table {

    //--------------------------------------------------------------------------
    // Private :: Variables
    //--------------------------------------------------------------------------

    private boolean selected;

    private ListRowRendererStyle style;

    private Label label;

    private String text = "";

    private Skin skin;

    protected Image background;

    protected Table content;

    private boolean over;

    protected boolean down;

    //--------------------------------------------------------------------------
    // Public Property :: API
    //--------------------------------------------------------------------------

    //----------------------------------
    // skin
    //----------------------------------

    public Skin getSkin() {
        return skin;
    }

    //----------------------------------
    // style
    //----------------------------------

    public ListRowRendererStyle getStyle() {
        return style;
    }

    public void setStyle(ListRowRendererStyle style) {
        this.style = style;

        //        if (style != null)
        //            setBackground(style.background);
    }

    //----------------------------------
    // text
    //----------------------------------

    public String getText() {
        return text;
    }

    public void setText(String value) {
        text = value;
        invalidate();
    }

    //----------------------------------
    // selected
    //----------------------------------

    public void setSelected(boolean selected) {
        if (selected == this.selected)
            return;

        this.selected = selected;
        invalidate();
    }

    public boolean isSelected() {
        return selected;
    }

    //----------------------------------
    // over
    //----------------------------------

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
        invalidate();
    }

    //----------------------------------
    // down
    //----------------------------------

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
        invalidate();
    }

    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------

    public ListRowRenderer(Skin skin) {
        super(skin);
        this.skin = skin;
    }

    public ListRowRenderer(Skin skin, ListRowRendererStyle style) {
        super(skin);
        this.skin = skin;
        align(Align.left);
        setTouchable(Touchable.enabled);
        setStyle(style);
        addCaptureListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (count == 2) {
                    AdvancedListEvent e = Pools.obtain(AdvancedListEvent.class);
                    e.setKind(AdvancedListEventKind.DoubleTap);
                    fire(e);
                    Pools.free(e);
                }
            }

            @Override
            public boolean longPress(Actor actor, float x, float y) {
                AdvancedListEvent e = Pools.obtain(AdvancedListEvent.class);
                e.setKind(AdvancedListEventKind.LongPress);
                fire(e);
                Pools.free(e);
                return true;
            }
        });
    }

    public void createChildren() {
        content = new Table();

        background = new Image(style.background);

        LabelStyle labelStyle = new LabelStyle(style.font, style.fontColor);
        label = new Label(text, labelStyle);
        label.setAlignment(Align.left);
        content.add(label).expand().fill().pad(style.padding);

        stack(background, content).expand().fill();
    }

    @Override
    public void layout() {
        super.layout();

        if (label != null)
            label.setText(text);

        if (background != null && style != null) {
            if (selected)
                background.setDrawable(style.selection);
            //else if (over && style.over != null)
            //    background.setDrawable(style.over);
            else if (down && style.down != null)
                background.setDrawable(style.down);
            else
                background.setDrawable(style.background);
        }
    }

    public static class ListRowRendererStyle {

        public Drawable background;

        public Drawable selection;

        public Drawable over;

        public Drawable down;

        public BitmapFont font;

        public Color fontColor;

        public Color fontSelectedColor;

        public Color fontOverColor;

        public float padding;

        public ListRowRendererStyle() {
        }
    }
}
