package com.graphhopper.reader.osm;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.graphhopper.routing.util.parsers.ScoreEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class SimRaImportUtil {
    private final File scoresFile;
    private static final Logger LOGGER = LoggerFactory.getLogger(SimRaImportUtil.class);
    private List<ScoreEntry> scoreEntryList;

    public SimRaImportUtil(String filePath) {
        this.scoresFile = new File(filePath);
        try {
            this.scoreEntryList = readScoresFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the SimRa safety scores file and returns a list of osm way id-score pairs (in form of SafetyScore objects)
     *
     * @return a List of Safety Score objects
     * @throws IOException if the file was not readable
     */
    public List<ScoreEntry> readScoresFile() throws IOException {
        MappingIterator<ScoreEntry> iterator = new CsvMapper()
                .readerFor(ScoreEntry.class)
                .with(CsvSchema.emptySchema().withHeader())
                .readValues(scoresFile);
        return iterator.readAll();
    }

    /**
     * Finds the corresponding safety/surface quality score for a given OSM way ID
     *
     * @param osmWayId the osm way id
     * @return SimRa safety score
     */
    public double findSafetyScore(long osmWayId) {
        if (scoreEntryList != null) {
            OptionalInt entryIndex = IntStream.range(0, scoreEntryList.size())
                    .filter(i -> osmWayId == scoreEntryList.get(i).id)
                    .findAny();
            if (entryIndex.isPresent()) {
                int i = entryIndex.getAsInt();
                LOGGER.debug("Found score for way with ID " + osmWayId);
                ScoreEntry foundEntry = scoreEntryList.get(i);
                scoreEntryList.remove(i);
                return foundEntry.safetyScore;
            }
        }
        return 0.0;
    }

    /**
     * Fetches the number of (remaining) elements in the score entry list.
     * Useful to see how many elements were not found
     *
     * @return size of the list
     */
    public int getEntryListSize() {
        return this.scoreEntryList.size();
    }
}
