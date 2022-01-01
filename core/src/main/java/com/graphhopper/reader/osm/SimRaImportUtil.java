package com.graphhopper.reader.osm;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.graphhopper.routing.util.parsers.SafetyScoreEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class SimRaImportUtil {
    private final File safetyScoresFile;
    private static final Logger LOGGER = LoggerFactory.getLogger(SimRaImportUtil.class);

    public SimRaImportUtil(String filePath) {
        this.safetyScoresFile = new File(filePath);
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

    /**
     * Finds the corresponding safety score for a given OSM way ID
     *
     * @param osmWayId the osm way id
     * @return SimRa safety score
     */
    public double findSafetyScore(long osmWayId) {
        try {
            List<SafetyScoreEntry> scoreEntries = readScoresFile(); // TODO(DK): optimize performance
            Optional<SafetyScoreEntry> foundEntry = scoreEntries
                    .stream()
                    .parallel()
                    .filter(safetyScore -> safetyScore.id == osmWayId)
                    .findAny();
            if (foundEntry.isPresent()) {
                LOGGER.info("Found score for way with ID " + osmWayId);
                return foundEntry.get().safetyScore;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
