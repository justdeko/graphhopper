package com.graphhopper.routing.ev;

/**
 * Denotes whether a way has lighting.
 */
public class Lit {
    public static final String KEY = "lit";

    public static BooleanEncodedValue create() {
        return new SimpleBooleanEncodedValue(KEY, false);
    }
}
