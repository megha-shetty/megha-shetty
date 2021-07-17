package org.example.models;

import lombok.Data;

@Data
public class PredictionInfoRelationship {
    private Route route;
    private Stop stop;
    private Trip trip;
    private Vehicle vehicle;
}
