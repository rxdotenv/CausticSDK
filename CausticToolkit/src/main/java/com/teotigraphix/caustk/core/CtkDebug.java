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

package com.teotigraphix.caustk.core;

public final class CtkDebug {

    public static void model(String message) {
        System.out.println(message);
    }

    public static void view(String message) {
        System.out.println(message);
    }

    public static void err(String message) {
        System.err.println(message);
    }

    public static void log(String message) {
        System.out.println(message);
    }

    public static void osc(String message) {
        System.out.println("OSC: " + message);
    }

    public static void ui(String message) {
        log(message);
    }

    public static void warn(String message) {
        System.err.println("WARNING: " + message);
    }

}
