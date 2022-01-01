package com.graphhopper.reader.osm;

import com.graphhopper.routing.util.parsers.SafetyScoreEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimRaImportUtilTest {
    private SimRaImportUtil importUtil;

    @BeforeEach
    void setUp() {
        String filepath = "/Users/dk/uniprojects/graphhopper/way_score_mapping.csv"; //TODO(DK): not hardcoded
        importUtil = new SimRaImportUtil(filepath);
    }

    @Test
    void readScoresFile() {
        List<SafetyScoreEntry> scoreEntries = null;
        try {
            scoreEntries = importUtil.readScoresFile();
            System.out.println(scoreEntries.get(0).safetyScore);
            System.out.println(scoreEntries.get(0).id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(scoreEntries);
        assertTrue(scoreEntries.size() > 100);
    }

    @Test
    void findSafetyScore() {
        // first osm ID in the .csv file
        long firstId = 4441941;
        double safetyScore = importUtil.findSafetyScore(firstId);
        assertEquals(0.04950495049504951, safetyScore);
    }
}