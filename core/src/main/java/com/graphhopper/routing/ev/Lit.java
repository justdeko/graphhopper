package com.graphhopper.routing.ev;

/**
 * Denotes whether a way has lighting.
 */
public class Lit {
    public static final String KEY = "lit";

    /**
     * Creates the lit encoded value. A "true" represents that this OSM way has street lighting.
     *
     * @return the boolean encoded value
     */
    public static BooleanEncodedValue create() {
        return new SimpleBooleanEncodedValue(KEY, false);
    }
}
