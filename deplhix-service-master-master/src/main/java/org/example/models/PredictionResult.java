package org.example.models;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class PredictionResult {
    private String route;
    private String destination;
    private Timestamp departureTime;
    private Integer timeLeftForDeparture;
}
