package org.example.models;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class PredictionInfoCollection {

    private Timestamp currentTime;
    private List<PredictionInfo> data;
    private List<RouteInfo> included;
    private Jsonapi jsonapi;

}
