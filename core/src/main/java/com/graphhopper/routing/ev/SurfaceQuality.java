package com.graphhopper.routing.ev;

/**
 * The SurfaceQuality stores the SimRa surface quality score values for bicycles.
 * A higher score means the path/node is LESS safe.
 */
public class SurfaceQuality {
    public static final String KEY = "simra_surface_quality";

    /**
     * creates the surface quality score encoded value
     * currently is able to score an integer value up to 8 (2^3 bits)
     * the score is scaled on a range from 1 to 5
     *
     * @return the Int encoded value implementation with the bit range
     */
    public static IntEncodedValue create() {
        return new IntEncodedValueImpl(KEY, 3, 1, false, false);
    }
}
