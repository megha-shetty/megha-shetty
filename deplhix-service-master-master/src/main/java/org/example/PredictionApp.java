package org.example;

import org.apache.log4j.Logger;
import org.example.exceptions.BusinessException;
import org.example.exceptions.SystemException;
import org.example.models.PredictionResult;
import org.example.models.RouteInfoCollection;
import org.example.service.MBTAPredictionServiceImpl;
import org.example.service.MBTARouteServiceImpl;
import org.example.service.PredictionService;
import org.example.service.RouteService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.example.exceptions.ErrorCodes.NO_STOP_INFO_PROVIDED;
import static org.example.exceptions.ErrorCodes.NO_STOP_INFO_PROVIDED_MSG;

public class PredictionApp {

    private static Logger log = Logger.getLogger(PredictionApp.class);

    public static void main(String[] args) throws SystemException, IOException {

        BufferedReader reader = new BufferedReader(
          new InputStreamReader(System.in));

        System.out.println("Provide the stop for which the prediction has to be done , eg:) place-pktrm");
        String stop = reader.readLine();

        if(stop.trim().isEmpty()){
            throw new BusinessException(NO_STOP_INFO_PROVIDED,NO_STOP_INFO_PROVIDED_MSG);
        }

        System.out.println("Provide the top N results that has to retrieved");
        String limit = reader.readLine();
        if(limit.trim().isEmpty()){
            limit="10";
        }

        int limitN = Integer.parseInt(limit);

        log.info("Provided stop is : " + stop);
        log.info("Top " + limitN + " predictions to be retrieved");

        RouteService routeService = new MBTARouteServiceImpl();
        RouteInfoCollection routeInfo = routeService.getAllRoutesHavingAStopAt(stop);

        List<String> routes = routeInfo.getData().stream().map(x -> {
            return x.getId();
        }).collect(Collectors.toList());

        PredictionService predictionService = new MBTAPredictionServiceImpl();
        Map<String, List<PredictionResult>> result = predictionService.getTopNPredictionForRoutesGroupedByRoute(limitN, routes);

    }

}
