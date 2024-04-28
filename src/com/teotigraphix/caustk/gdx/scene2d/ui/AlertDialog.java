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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class AlertDialog extends DialogBase {

    private Skin skin;

    private TextButton okButton;

    private TextButton cancelButton;

    private OnAlertDialogListener listener;

    private String buttonStyleName;

    private String title;

    public Skin getSkin() {
        return skin;
    }

    public TextButton getOkButton() {
        return okButton;
    }

    public TextButton getCancelButton() {
        return cancelButton;
    }

    public AlertDialog(String title, Skin skin, String windowStyleName, String buttonStyleName) {
        super("", skin, windowStyleName);
        this.title = title;
        this.skin = skin;
        this.buttonStyleName = buttonStyleName;
        createChildren();
    }

    public AlertDialog(String title, Skin skin) {
        super("", skin);
        this.title = title;
        this.skin = skin;
        createChildren();
    }

    public AlertDialog(String title, Skin skin, WindowStyle windowStyle) {
        super("", skin, windowStyle);
        setSkin(skin);
        this.skin = skin;
        this.title = title;
        createChildren();
    }

    protected void createChildren() {
        Label label = new Label(title, new LabelStyle(getStyle().titleFont,
                getStyle().titleFontColor));
        getContentTable().add(label).pad(4f);
        getContentTable().row();

        TextButtonStyle buttonStyle = null;
        if (buttonStyleName != null)
            buttonStyle = getSkin().get(buttonStyleName, TextButtonStyle.class);
        else
            buttonStyle = ((AlertDialogStyle)getStyle()).buttonStyle;

        okButton = new TextButton("OK", buttonStyle);
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (okButton.isDisabled())
                    return;
                listener.onOk();
                hide();
            }
        });
        cancelButton = new TextButton("Cancel", buttonStyle);
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                listener.onCancel();
                hide();
            }
        });

        button(okButton, true);
        button(cancelButton, false);
        getButtonTable().getCell(okButton).size(100f, 50f);
        getButtonTable().getCell(cancelButton).size(100f, 50f);
    }

    @Override
    protected void result(Object object) {
        boolean result = (Boolean)object;
        if (result) {
            listener.onOk();
        } else {
            listener.onCancel();
        }
        hide();
    }

    public void setOnAlertDialogListener(OnAlertDialogListener l) {
        listener = l;
    }

    public interface OnAlertDialogListener {
        void onCancel();

        void onOk();
    }

    @SuppressWarnings("rawtypes")
    public Cell setContent(Actor actor) {
        Cell cell = getContentTable().add(actor);//.size(getWidth(), getHeight());
        //((WidgetGroup)actor).validate();
        cell.size(actor.getWidth(), actor.getHeight());
        return cell;
    }

    /*
       public void changed (ChangeEvent event, Actor actor) {
    new Dialog("Some Dialog", skin, "dialog") {
    protected void result (Object object) {
    System.out.println("Chosen: " + object);
    }
    }.text("Are you enjoying this demo?").button("Yes", true).button("No", false).key(Keys.ENTER, true)
    .key(Keys.ESCAPE, false).show(stage);
    } 
       
    */

    public static class AlertDialogStyle extends WindowStyle {
        public TextButtonStyle buttonStyle;

        public AlertDialogStyle(BitmapFont titleFont, Color titleFontColor, Drawable background) {
            super(titleFont, titleFontColor, background);
        }

    }

}
