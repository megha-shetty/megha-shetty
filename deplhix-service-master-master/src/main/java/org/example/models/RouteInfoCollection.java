package org.example.models;

import lombok.Data;

import java.util.List;

@Data
public class RouteInfoCollection {
    private List<RouteInfo> data;
    private Jsonapi jsonapi;
}
