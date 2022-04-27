package com.graphhopper.routing.ev;

/**
 * Denotes whether an accident occurred on the way.
 */
public class Accident {
    public static final String KEY = "accident";

    /**
     * Creates the accident encoded value. A "true" represents that an accident has occurred on this OSM way.
     *
     * @return the boolean encoded value
     */
    public static BooleanEncodedValue create() {
        return new SimpleBooleanEncodedValue(KEY, false);
    }
}
