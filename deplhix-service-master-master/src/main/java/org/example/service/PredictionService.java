package org.example.service;

import org.example.exceptions.BusinessException;
import org.example.exceptions.SystemException;
import org.example.models.PredictionInfoCollection;
import org.example.models.PredictionResult;

import java.util.List;
import java.util.Map;

public interface PredictionService {

    /**
     * Returns Top N Prediction data for the provided routes
     *
     * @param topN
     * @param routes
     * @return {@link PredictionInfoCollection}
     * @throws SystemException,BusinessException
     */
    public Map<String, List<PredictionResult>> getTopNPredictionForRoutesGroupedByRoute(int topN, List<String> routes,String stop) throws SystemException, BusinessException;

    /**
     * Returns Prediction data for the provided routes
     *
     * @param routes
     * @return {@link PredictionInfoCollection}
     * @throws SystemException,BusinessException
     */
    public PredictionInfoCollection getPredictionsForRoutes(List<String> routes,String stop) throws SystemException, BusinessException;
}
