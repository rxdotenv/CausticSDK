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

package com.teotigraphix.caustk.gdx.app.controller.view;

import com.badlogic.gdx.graphics.Color;

public enum GrooveColor {

    DarkGrey(0.3294117748737335, 0.3294117748737335, 0.3294117748737335, 1),

    Gray(.47843137383461, 0.47843137383461, 0.47843137383461, 2),

    LightGray(0.7882353067398071, 0.7882353067398071, 0.7882353067398071, 3),

    Silver(0.5254902243614197, 0.5372549295425415, 0.6745098233222961, 40),

    DarkBrown(0.6392157077789307, 0.4745098054409027, 0.26274511218070984, 11),

    Brown(0.7764706015586853, 0.6235294342041016, 0.43921568989753723, 12),

    DarkBlue(0.34117648005485535, 0.3803921639919281, 0.7764706015586853, 42),

    Lavendar(0.5176470875740051, 0.5411764979362488, 0.8784313797950745, 44),

    Purple(0.5843137502670288, 0.2862745225429535, 0.7960784435272217, 58),

    Pink(0.8509804010391235, 0.21960784494876862, 0.4431372582912445, 57),

    Red(0.8509804010391235, 0.18039216101169586, 0.1411764770746231, 6),

    Orange(1, 0.34117648005485535, 0.0235294122248888, 60),

    LightOrange(0.8509804010391235, 0.615686297416687, 0.062745101749897, 62),

    Green(0.45098039507865906, 0.5960784554481506, 0.0784313753247261, 18),

    DarkGreen(0, 0.615686297416687, 0.27843138575553894, 26),

    Teal(0, 0.6509804129600525, 0.5803921818733215, 30),

    ElectricBlue(0, 0.6000000238418579, 0.8509804010391235, 37),

    LightPurple(0.7372549176216125, 0.4627451002597809, 0.9411764740943909, 48),

    LightPink(0.8823529481887817, 0.4000000059604645, 0.5686274766921997, 56),

    Skin(0.9254902005195618, 0.3803921639919281, 0.34117648005485535, 4),

    Peach(1, 0.5137255191802979, 0.24313725531101227, 10),

    BurntOrange(0.8941176533699036, 0.7176470756530762, 0.30588236451148987, 61),

    OliveGreen(0.6274510025978088, 0.7529411911964417, 0.2980392277240753, 18),

    MintGreen(0.24313725531101227, 0.7333333492279053, 0.3843137323856354, 25),

    Aqua(0.26274511218070984, 0.8235294222831726, 0.7254902124404907, 32),

    Cyan(0.2666666805744171, 0.7843137383460999, 1, 41),

    White(1f, 1f, 1f, 120);

    private Color color;

    private int id;

    public int getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    GrooveColor(double red, double green, double blue, int id) {
        this.id = id;
        color = new Color((float)red, (float)green, (float)blue, 1f);
    }

    public static GrooveColor fromId(int id) {
        for (GrooveColor colors : values()) {
            if (colors.getId() == id)
                return colors;
        }
        return null;
    }

    public static boolean hasColor(int colorId) {
        return fromId(colorId) != null;
    }
}
