package com.graphhopper.routing.util.parsers;

import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.ev.*;
import com.graphhopper.storage.IntsRef;

import java.util.List;

/**
 * Accident boolean parser for accident values
 */
public class AccidentParser implements TagParser {

    private final BooleanEncodedValue accidentEnc;

    public AccidentParser() {
        this.accidentEnc = Accident.create();
    }

    @Override
    public void createEncodedValues(EncodedValueLookup lookup, List<EncodedValue> registerNewEncodedValue) {
        registerNewEncodedValue.add(accidentEnc);
    }

    @Override
    public IntsRef handleWayTags(IntsRef edgeFlags, ReaderWay way, IntsRef relationFlags) {
        boolean accident = way.getTag(Accident.KEY, false);
        accidentEnc.setBool(false, edgeFlags, accident);
        return edgeFlags;
    }
}
