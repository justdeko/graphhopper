package com.graphhopper.routing.util.parsers;

import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.ev.BooleanEncodedValue;
import com.graphhopper.routing.ev.EncodedValue;
import com.graphhopper.routing.ev.EncodedValueLookup;
import com.graphhopper.routing.ev.Lit;
import com.graphhopper.storage.IntsRef;

import java.util.List;

public class OSMLitParser implements TagParser {

    private final BooleanEncodedValue litEnc;

    public OSMLitParser() {
        this.litEnc = Lit.create();
    }

    @Override
    public void createEncodedValues(EncodedValueLookup lookup, List<EncodedValue> registerNewEncodedValue) {
        registerNewEncodedValue.add(litEnc);
    }

    @Override
    public IntsRef handleWayTags(IntsRef edgeFlags, ReaderWay way, IntsRef relationFlags) {
        String lit = way.getTag("lit");
        return null;
    }
}
