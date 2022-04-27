package com.graphhopper.routing.ev;

/**
 * The SafetyScore stores the SimRa safety score values for bicycles.
 * A higher score means the path/node is LESS safe.
 */
public class SafetyScore {
    public static final String KEY = "safety_score";

    /**
     * Creates the safety score encoded value.
     * Currently, the encoded value is able to score an integer value up to 128 (2^7 bits).
     * The score is scaled on a range from 0 to 100.
     *
     * @return the Int encoded value implementation with the bit range
     */
    public static IntEncodedValue create() {
        return new IntEncodedValueImpl(KEY, 7, false);
    }
}
