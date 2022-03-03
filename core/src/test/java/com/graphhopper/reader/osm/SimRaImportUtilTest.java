package com.graphhopper.reader.osm;

import com.graphhopper.routing.util.parsers.ScoreEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimRaImportUtilTest {
    private SimRaImportUtil importUtil;

    @BeforeEach
    void setUp() {
        Path tempFile;
        try {
            tempFile = Files.createTempFile("way_score_mapping-", ".csv");
            List<String> content = Arrays.asList("way_id,score\n", "4441941,0.04950495049504951\n");
            Files.write(tempFile, content, StandardOpenOption.APPEND);
            importUtil = new SimRaImportUtil(tempFile.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void readScoresFile() {
        List<ScoreEntry> scoreEntries = null;
        try {
            scoreEntries = importUtil.readScoresFile();
            System.out.println(scoreEntries.get(0).safetyScore);
            System.out.println(scoreEntries.get(0).id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(scoreEntries);
        assertTrue(scoreEntries.size() > 1);
    }

    @Test
    void findSafetyScore() {
        // first osm ID in the .csv file
        long firstId = 4441941;
        double safetyScore = importUtil.findScore(firstId, 0.0);
        assertEquals(0.04950495049504951, safetyScore);
    }
}