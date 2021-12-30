package com.graphhopper.routing.util.parsers;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.ev.EncodedValue;
import com.graphhopper.routing.ev.EncodedValueLookup;
import com.graphhopper.routing.ev.IntEncodedValue;
import com.graphhopper.routing.ev.SafetyScore;
import com.graphhopper.storage.IntsRef;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * SimRa safety score parser for safety score values
 */
public class SafetyScoreParser implements TagParser {
    private final IntEncodedValue safetyScoreEnc;

    public SafetyScoreParser() {
        this.safetyScoreEnc = SafetyScore.create();
    }

    @Override
    public void createEncodedValues(EncodedValueLookup lookup, List<EncodedValue> registerNewEncodedValue) {
        registerNewEncodedValue.add(safetyScoreEnc);
    }

    /**
     * Reads the SimRa safety scores file and returns a list of osm way id-score pairs
     *
     * @param file the safety scores file
     * @return a List of osm way id-score key-value maps
     * @throws IOException if the file was not readable
     */
    public static List<Map<Long, Long>> readScoresFile(File file) throws IOException {
        List<Map<Long, Long>> scoresList = new LinkedList<>();
        MappingIterator<Map<Long, Long>> iterator = new CsvMapper().readerFor(Map.class)
                .with(CsvSchema.emptySchema().withHeader())
                .readValues(file);
        while (iterator.hasNext()) {
            scoresList.add(iterator.next());
        }
        return scoresList;
    }

    @Override
    public IntsRef handleWayTags(IntsRef edgeFlags, ReaderWay way, IntsRef relationFlags) {
        String filepath = "../sample_data/scores.csv";
        File scoresFile = new File(filepath);
        try {
            // iterate over scores list
            List<Map<Long, Long>> scoresList = readScoresFile(scoresFile);
            for (Map<Long, Long> scorePair : scoresList) {
                Long id = way.getId();
                Long score = scorePair.get(id);
                // if score is found, set encoded value
                if (score != null) {
                    safetyScoreEnc.setInt(false, edgeFlags, Math.toIntExact(score * 100));
                    return null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
