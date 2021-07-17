package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.example.exceptions.SystemException;
import org.example.models.RouteInfoCollection;
import org.example.util.CommonUtils;
import  java.io.*;


import java.text.MessageFormat;

import static org.example.exceptions.ErrorCodes.JSON_PARSE_EXCEPTION;
import static org.example.exceptions.ErrorCodes.JSON_PARSE_EXCEPTION_MSG;
import static org.example.util.Constants.ROUTE_URL;

public class MBTARouteServiceImpl implements RouteService {

    private static Logger log = Logger.getLogger(MBTARouteServiceImpl.class);

    /**
     * Returns all routes passing through the provided stop
     *
     * @param stop
     * @return {@link RouteInfoCollection}
     * @throws SystemException
     */
    @Override
    public RouteInfoCollection getAllRoutesHavingAStopAt(String stop) throws SystemException {

        log.info("Finding routes for the stop : " + stop);
        ObjectMapper objectMapper = new ObjectMapper();
        String routeinfo = CommonUtils.getAPIResult(MessageFormat.format(ROUTE_URL, stop));
        RouteInfoCollection routesHavingAStop;
        try {
            routesHavingAStop = objectMapper.readValue(routeinfo, RouteInfoCollection.class);
        } catch (IOException ex) {
            log.error(JSON_PARSE_EXCEPTION + ":" + JSON_PARSE_EXCEPTION_MSG);
            throw new SystemException(JSON_PARSE_EXCEPTION + ":" + JSON_PARSE_EXCEPTION_MSG, ex, true, true);
        }
        log.info("Number of routes retrieved are " + routesHavingAStop.getData().size());
        return routesHavingAStop;
    }

}
