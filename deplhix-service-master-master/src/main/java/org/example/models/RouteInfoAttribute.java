package org.example.models;

import lombok.Data;

import java.util.List;

@Data
public class RouteInfoAttribute {

    private String color;
    private String description;
    private List<String> direction_destinations;
    private List<String> direction_names;
    private String fare_class;
    private String long_name;
    private String short_name;
    private int sort_order;
    private String text_color;
    private int type;

}
