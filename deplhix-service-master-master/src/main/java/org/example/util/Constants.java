package org.example.util;

public class Constants {

    public final static String PREDICTION_URL = "https://api-v3.mbta.com/predictions?filter[stop]=place-pktrm&sort=departure_time";
    public final static String ROUTE_URL = "https://api-v3.mbta.com/routes?filter[stop]={0}";
    public final static String FILTERED_PREDICTION_URL = "https://api-v3.mbta.com/predictions?sort=departure_time&include={0}&filter[route]={1}";
    public final static String DEFAULT_PREDICTIONS_DATA_LOCATION = "src/main/resources/prediction.json";
    public final static String DEFAULT_TS = "2021-05-11 17:15:00";

}
