package com.graphhopper.routing.util.parsers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"way_id", "score"})
public class SafetyScoreEntry {
    @JsonProperty("way_id")
    public long id;
    @JsonProperty("score")
    public double safetyScore;
}
