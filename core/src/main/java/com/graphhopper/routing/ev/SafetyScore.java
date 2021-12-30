package com.graphhopper.routing.ev;

/**
 * The SafetyScore stores the SimRa safety score values for bicycles.
 */
public class SafetyScore {
    public static final String KEY = "safety_score";

    /**
     * creates the safety score encoded value
     * currently is able to score an integer value up to 128 (2^7 bits)
     * the score is scaled on a range from 0 to 100
     *
     * @return the Int encoded value implementation with the bit range
     */
    public static IntEncodedValue create() {
        return new IntEncodedValueImpl(KEY, 7, false);
    }
}
