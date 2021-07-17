package org.example.models;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Data
public class PredictionInfo {

    private PredictionInfoAttribute attributes;
    private String id;
    private PredictionInfoRelationship relationships;
    private String type;


    public PredictionResult toResult(List<RouteInfo> routeInfo, Timestamp currentTime) {

        String routeName = relationships.getRoute().getData().getId();

        Optional<RouteInfo> destinationRoute = routeInfo.stream()
          .filter(route -> route.getId().equalsIgnoreCase(routeName))
          .findFirst();
        String destination = "";

        if (destinationRoute.isPresent()) {
            destination = destinationRoute.get().getAttributes().getDirection_destinations().get(attributes.getDirection_id());
        }

        return PredictionResult.builder()
          .route(relationships.getRoute().getData().getId())
          .departureTime(attributes.getDeparture_time())
          .destination(destination)
          .timeLeftForDeparture(attributes.getDeparture_time().getMinutes() - currentTime.getMinutes())
          .build();
    }

}
