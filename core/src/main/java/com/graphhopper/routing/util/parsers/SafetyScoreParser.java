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
import java.util.List;
import java.util.Optional;

/**
 * SimRa safety score parser for safety score values
 */
public class SafetyScoreParser implements TagParser {
    private final IntEncodedValue safetyScoreEnc;
    private final File safetyScoresFile;

    public SafetyScoreParser() {
        // initialize encoded value, file and csv schema
        String filepath = "/Users/dk/uniprojects/graphhopper/way_score_mapping.csv"; //TODO(DK): not hardcoded
        this.safetyScoreEnc = SafetyScore.create();
        this.safetyScoresFile = new File(filepath);
    }

    @Override
    public void createEncodedValues(EncodedValueLookup lookup, List<EncodedValue> registerNewEncodedValue) {
        registerNewEncodedValue.add(safetyScoreEnc);
    }

    /**
     * Reads the SimRa safety scores file and returns a list of osm way id-score pairs (in form of SafetyScore objects)
     *
     * @return a List of Safety Score objects
     * @throws IOException if the file was not readable
     */
    public List<SafetyScoreEntry> readScoresFile() throws IOException {
        MappingIterator<SafetyScoreEntry> iterator = new CsvMapper()
                .readerFor(SafetyScoreEntry.class)
                .with(CsvSchema.emptySchema().withHeader())
                .readValues(safetyScoresFile);
        return iterator.readAll();
    }

    @Override
    public IntsRef handleWayTags(IntsRef edgeFlags, ReaderWay way, IntsRef relationFlags) {
        try {
            // filter scores list and find any entry that matches way id
            List<SafetyScoreEntry> scoresList = readScoresFile(); // TODO(DK): move this to constructor for performance
            Optional<SafetyScoreEntry> foundEntry = scoresList
                    .stream()
                    .parallel()
                    .filter(safetyScore -> safetyScore.id == way.getId())
                    .findAny();
            foundEntry.ifPresent(entry -> {
                System.out.printf("Safety score entry found with OSM ID: %d", entry.id);
                safetyScoreEnc.setInt(false, edgeFlags, (int) (entry.safetyScore * 100));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return edgeFlags;
    }
}
