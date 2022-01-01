package com.graphhopper.routing.util.parsers;

import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.ev.EncodedValue;
import com.graphhopper.routing.ev.EncodedValueLookup;
import com.graphhopper.routing.ev.IntEncodedValue;
import com.graphhopper.routing.ev.SafetyScore;
import com.graphhopper.storage.IntsRef;

import java.util.List;

/**
 * SimRa safety score parser for safety score values
 */
public class SafetyScoreParser implements TagParser {
    private final IntEncodedValue safetyScoreEnc;

    public SafetyScoreParser() {
        // initialize encoded value, file and csv schema
        this.safetyScoreEnc = SafetyScore.create();
    }

    @Override
    public void createEncodedValues(EncodedValueLookup lookup, List<EncodedValue> registerNewEncodedValue) {
        registerNewEncodedValue.add(safetyScoreEnc);
    }



    @Override
    public IntsRef handleWayTags(IntsRef edgeFlags, ReaderWay way, IntsRef relationFlags) {
        Double score = way.getTag("safety_score", 0.0);
        safetyScoreEnc.setInt(false, edgeFlags, (int) (score * 100));
        return edgeFlags;
    }
}
