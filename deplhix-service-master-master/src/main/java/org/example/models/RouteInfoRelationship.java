package org.example.models;

import lombok.Data;

@Data
public class RouteInfoRelationship {
    private Line line;
    RoutePattern route_patterns;
}
