package com.graphhopper.routing.ev;

/**
 * Denotes whether an accident occurred on the way.
 */
public class Accident {
    public static final String KEY = "accident";

    public static BooleanEncodedValue create() {
        return new SimpleBooleanEncodedValue(KEY, false);
    }
}
