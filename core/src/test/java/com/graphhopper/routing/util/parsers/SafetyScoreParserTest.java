package com.graphhopper.routing.util.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SafetyScoreParserTest {
    private SafetyScoreParser parser;

    @BeforeEach
    void setUp() {
        parser = new SafetyScoreParser();
    }

    @Test
    void readScoresFile() {
        List<SafetyScoreEntry> scoreEntries = null;
        try {
            scoreEntries = parser.readScoresFile();
            System.out.println(scoreEntries.get(0).safetyScore);
            System.out.println(scoreEntries.get(0).id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(scoreEntries);
        assertTrue(scoreEntries.size() > 100);
    }


}