
package com.teotigraphix.gdx.groove.ui.components;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.teotigraphix.gdx.groove.ui.components.PatternSelectionListener.PatternSelectionEvent;
import com.teotigraphix.gdx.groove.ui.components.PatternSelectionListener.PatternSelectionEventKind;
import com.teotigraphix.gdx.scene2d.ui.ButtonBar;
import com.teotigraphix.gdx.scene2d.ui.ButtonBar.ButtonBarItem;
import com.teotigraphix.gdx.scene2d.ui.ButtonBarListener;

public class PatternPane extends UITable {

    //--------------------------------------------------------------------------
    // Private :: Variables
    //--------------------------------------------------------------------------

    private ButtonBar bankBar;

    private ButtonGroup pattternGridGroup;

    private ButtonBar lengthBar;

    private HashMap<Integer, Integer> lengthMap;

    private boolean updating;

    private boolean disabled;

    //--------------------------------------------------------------------------
    // Constructor
    //--------------------------------------------------------------------------

    public PatternPane(Skin skin) {
        super(skin);
        setStyleClass(PatternPaneStyle.class);
        lengthMap = new HashMap<Integer, Integer>();
        lengthMap.put(0, 1);
        lengthMap.put(1, 2);
        lengthMap.put(2, 4);
        lengthMap.put(3, 8);
    }

    //--------------------------------------------------------------------------
    // Overridden Protected :: Methods
    //--------------------------------------------------------------------------

    @Override
    protected void createChildren() {
        debug();

        pattternGridGroup = new ButtonGroup();

        Table topRow = new Table();
        topRow.pad(4f);
        Table centerRow = new Table();
        Table bottomRow = new Table();
        bottomRow.pad(4f);

        add(topRow).height(40f).expandX().fillX();
        row();
        add(centerRow).expand().fill();
        row();
        add(bottomRow).height(40f).expandX().fillX();

        bankBar = createBankBar();
        topRow.add(bankBar).expand().fill();

        createPatternGrid(centerRow);

        lengthBar = createLengthBar();
        bottomRow.add(lengthBar).expand().fill();
    }

    //--------------------------------------------------------------------------
    // Public Method :: API
    //--------------------------------------------------------------------------

    public void setDisabled(boolean disabled) {
        if (disabled == this.disabled)
            return;
        this.disabled = disabled;
        bankBar.setDisabled(disabled);
        for (Button button : pattternGridGroup.getButtons()) {
            button.setDisabled(disabled);
        }
        lengthBar.setDisabled(disabled);
    }

    public void selectBank(int bank) {
        bankBar.setSelectedIndex(bank);
    }

    public void selectPattern(int pattern) {
        updating = true;
        pattternGridGroup.getButtons().get(pattern).setChecked(true);
        updating = false;
    }

    public void selectLength(int length) {
        lengthBar.setSelectedIndex(getLengthIndex(length));
    }

    //--------------------------------------------------------------------------
    // Private :: Methods
    //--------------------------------------------------------------------------

    private ButtonBar createBankBar() {
        PatternPaneStyle style = getStyle();
        Array<ButtonBarItem> banks = new Array<ButtonBarItem>();
        banks.add(new ButtonBarItem("a", "A", "", ""));
        banks.add(new ButtonBarItem("b", "B", "", ""));
        banks.add(new ButtonBarItem("c", "C", "", ""));
        banks.add(new ButtonBarItem("d", "D", "", ""));
        ButtonBar buttonBar = new ButtonBar(getSkin(), banks, false, style.bankButtonStyle);
        buttonBar.setGap(4f);
        buttonBar.addListener(new ButtonBarListener() {
            @Override
            public void selectedIndexChange(int selectedIndex) {
                PatternSelectionEvent e = new PatternSelectionEvent(
                        PatternSelectionEventKind.bankChange);
                e.setIndex(selectedIndex);
                fire(e);
            }
        });

        buttonBar.create("default");
        return buttonBar;
    }

    private ButtonBar createLengthBar() {
        PatternPaneStyle style = getStyle();
        Array<ButtonBarItem> lengths = new Array<ButtonBarItem>();
        lengths.add(new ButtonBarItem("one", "1", "", ""));
        lengths.add(new ButtonBarItem("two", "2", "", ""));
        lengths.add(new ButtonBarItem("three", "4", "", ""));
        lengths.add(new ButtonBarItem("four", "8", "", ""));
        ButtonBar buttonBar = new ButtonBar(getSkin(), lengths, false, style.lengthButtonStyle);
        buttonBar.setGap(4f);
        buttonBar.addListener(new ButtonBarListener() {
            @Override
            public void selectedIndexChange(int selectedIndex) {
                PatternSelectionEvent e = new PatternSelectionEvent(
                        PatternSelectionEventKind.lengthChange);
                e.setLength(lengthMap.get(selectedIndex));
                fire(e);
            }
        });
        buttonBar.create("default");
        return buttonBar;
    }

    private ButtonGroup createPatternGrid(Table parent) {
        PatternPaneStyle style = getStyle();
        parent.pad(4f);
        // XXX until you get a new ToggleButton impl, buttonGroup is trying to check in add()
        updating = true;
        for (int i = 0; i < 16; i++) {
            final int index = i;
            TextButton button = new TextButton("P " + (i + 1), style.padButtonStyle);
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (updating)
                        return;
                    PatternSelectionEvent e = new PatternSelectionEvent(
                            PatternSelectionEventKind.patternChange);
                    e.setIndex(index);
                    fire(e);
                }
            });
            parent.add(button).expand().fill().space(4f);
            pattternGridGroup.add(button);
            if (i % 4 == 3)
                parent.row();
        }
        updating = false;
        return null;
    }

    private int getLengthIndex(int value) {
        for (Entry<Integer, Integer> entry : lengthMap.entrySet()) {
            if (entry.getValue() == value)
                return entry.getKey();
        }
        return -1;
    }

    public static class PatternPaneStyle {

        public Drawable background;

        public TextButtonStyle bankButtonStyle;

        public TextButtonStyle padButtonStyle;

        public TextButtonStyle lengthButtonStyle;

        public PatternPaneStyle(TextButtonStyle bankButtonStyle, TextButtonStyle padButtonStyle,
                TextButtonStyle lengthButtonStyle) {
            this.bankButtonStyle = bankButtonStyle;
            this.padButtonStyle = padButtonStyle;
            this.lengthButtonStyle = lengthButtonStyle;
        }

    }
}