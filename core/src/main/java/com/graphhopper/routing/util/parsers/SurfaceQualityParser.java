package com.graphhopper.routing.util.parsers;

import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.ev.EncodedValue;
import com.graphhopper.routing.ev.EncodedValueLookup;
import com.graphhopper.routing.ev.IntEncodedValue;
import com.graphhopper.routing.ev.SurfaceQuality;
import com.graphhopper.storage.IntsRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * SimRa surface quality score parser for surface quality score values
 */
public class SurfaceQualityParser implements TagParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(SurfaceQualityParser.class);
    private final IntEncodedValue surfaceQualityEnc;

    public SurfaceQualityParser() {
        // initialize the encoded value
        this.surfaceQualityEnc = SurfaceQuality.create();
    }

    @Override
    public void createEncodedValues(EncodedValueLookup lookup, List<EncodedValue> registerNewEncodedValue) {
        registerNewEncodedValue.add(surfaceQualityEnc);
    }


    @Override
    public IntsRef handleWayTags(IntsRef edgeFlags, ReaderWay way, IntsRef relationFlags) {
        Double score = way.getTag(SurfaceQuality.KEY, 0.0);
        LOGGER.debug("Setting surface quality score with value: " + score);
        surfaceQualityEnc.setInt(false, edgeFlags, (int) (score * 100));
        return edgeFlags;
    }
}
