package org.example.models;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PredictionInfoAttribute {
    private Timestamp arrival_time;
    private Timestamp departure_time;
    private int direction_id;
    private String schedule_relationship;
    private String status;
    private int stop_sequence;
}
