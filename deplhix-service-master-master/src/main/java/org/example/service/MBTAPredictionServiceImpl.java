package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.example.exceptions.BusinessException;
import org.example.exceptions.SystemException;
import org.example.models.PredictionInfoCollection;
import org.example.models.PredictionResult;
import org.example.util.CommonUtils;

import java.io.*;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static org.example.exceptions.ErrorCodes.JSON_PARSE_EXCEPTION;
import static org.example.exceptions.ErrorCodes.JSON_PARSE_EXCEPTION_MSG;
import static org.example.exceptions.ErrorCodes.NO_ROUTES;
import static org.example.exceptions.ErrorCodes.NO_ROUTES_MSG;
import static org.example.util.Constants.DEFAULT_PREDICTIONS_DATA_LOCATION;
import static org.example.util.Constants.DEFAULT_TS;
import static org.example.util.Constants.FILTERED_PREDICTION_URL;

public class MBTAPredictionServiceImpl implements PredictionService {

    private static Logger log = Logger.getLogger(MBTAPredictionServiceImpl.class);

    /**
     * Returns Top N Prediction data for the provided routes groupedByRoute
     *
     * @param topN
     * @param routes
     * @return {@link Map<String, Set<PredictionResult>>}
     * @throws SystemException,BusinessException
     */
    public Map<String, List<PredictionResult>> getTopNPredictionForRoutesGroupedByRoute(int topN, List<String> routes) throws SystemException, BusinessException {

        log.info("Top " + topN + " results to predict");

        PredictionInfoCollection predictionInfoCollection = getPredictionsForRoutes(routes);

        Timestamp currentTime = predictionInfoCollection.getCurrentTime();

        Supplier<Stream<PredictionResult>> predictionResultSupplier = () -> predictionInfoCollection.getData().stream()
          .limit(topN)
          .filter(predictionInfo -> predictionInfo.getAttributes().getDeparture_time() != null)
          .map(predictionInfo -> predictionInfo.toResult(predictionInfoCollection.getIncluded(), currentTime))
          .filter(predictionResult -> predictionResult.getDepartureTime().after(currentTime))
          .sorted(comparing(PredictionResult::getDepartureTime));

        log.info("Number of Predicted Routes satisfying the conditions are : " + predictionResultSupplier.get().count());

        Map<String, List<PredictionResult>> result = predictionResultSupplier.get()
          .collect(Collectors.groupingBy(PredictionResult::getRoute, Collectors.toList()));

        log.info("Results are");
        System.out.println("Current Time : " + currentTime);
        result.forEach((route, predictions) -> {
            System.out.println("----" + route + "----");
            predictions.forEach(prediction -> {
                System.out.println(prediction.getDestination() + ": Departing in " + prediction.getTimeLeftForDeparture() + " minutes");
            });
        });

        return result;
    }


    /**
     * Returns Prediction data for the provided routes
     *
     * @param routes
     * @return {@link PredictionInfoCollection}
     * @throws SystemException,BusinessException
     */
    @Override
    public PredictionInfoCollection getPredictionsForRoutes(List<String> routes) throws SystemException, BusinessException {

        log.info("Routes to predict for are : " + routes);
        if (routes.isEmpty()) {
            log.error(NO_ROUTES + ":" + NO_ROUTES_MSG);
            throw new BusinessException(NO_ROUTES, NO_ROUTES_MSG);
        }

        String concatenatedRouteInfo = routes.stream().collect(Collectors.joining(","));
        ObjectMapper objectMapper = new ObjectMapper();
        String predictioninfo = CommonUtils.getAPIResult(MessageFormat.format(FILTERED_PREDICTION_URL, "route", concatenatedRouteInfo));
        PredictionInfoCollection allPredictionData;

        try {
            allPredictionData = objectMapper.readValue(predictioninfo, PredictionInfoCollection.class);
            allPredictionData.setCurrentTime(CommonUtils.getUTCdate());
        } catch (IOException ex) {
            log.error(JSON_PARSE_EXCEPTION + ":" + JSON_PARSE_EXCEPTION_MSG);
            throw new SystemException(JSON_PARSE_EXCEPTION + ":" + JSON_PARSE_EXCEPTION_MSG, ex, true, true);
        }

        if (allPredictionData.getData().isEmpty()) {
            log.error("Prediciton API returned Zero results");
            log.info("As the api returned zero results retrieving default Data for stop place-pktrm");
            allPredictionData = getDefaultPredictions();
            allPredictionData.setCurrentTime(Timestamp.valueOf(DEFAULT_TS));
        }

        log.info("Number of Predictions retrieved : " + allPredictionData.getData().size());
        return allPredictionData;

    }

    private PredictionInfoCollection getDefaultPredictions() throws SystemException {
        ObjectMapper objectMapper = new ObjectMapper();
        PredictionInfoCollection defaultPredictionData;
        try {
            defaultPredictionData = objectMapper.readValue(new File(DEFAULT_PREDICTIONS_DATA_LOCATION), PredictionInfoCollection.class);
        } catch (Exception ex) {
            log.error(JSON_PARSE_EXCEPTION + ":" + JSON_PARSE_EXCEPTION_MSG);
            throw new SystemException(JSON_PARSE_EXCEPTION + ":" + JSON_PARSE_EXCEPTION_MSG, ex, true, true);
        }
        return defaultPredictionData;
    }

}
