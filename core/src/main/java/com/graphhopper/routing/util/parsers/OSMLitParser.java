package com.graphhopper.routing.util.parsers;

import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.ev.BooleanEncodedValue;
import com.graphhopper.routing.ev.EncodedValue;
import com.graphhopper.routing.ev.EncodedValueLookup;
import com.graphhopper.routing.ev.Lit;
import com.graphhopper.storage.IntsRef;

import java.util.List;

/**
 * OSM lit tag parser for lit booleans
 */
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
        String lit = way.getTag(Lit.KEY, "no");
        // a way is considered lit if lighting is not unavailable or disused
        boolean litBool = !(lit.equals("no") || lit.equals("disused"));
        litEnc.setBool(false, edgeFlags, litBool);
        return edgeFlags;
    }
}
