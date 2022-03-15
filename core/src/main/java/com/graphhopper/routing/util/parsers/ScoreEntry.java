package com.graphhopper.routing.util.parsers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"way_id", "score"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreEntry {
    @JsonProperty("way_id")
    public long id;
    @JsonProperty("score")
    public double safetyScore;
}
