package org.example.models;

import lombok.Data;

@Data
public class RouteInfo {
    private String id;
    private String type;
    private RouteInfoAttribute attributes;
    private Link links;
    private RouteInfoRelationship relationships;
}
