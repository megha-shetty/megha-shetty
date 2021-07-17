package org.example.service;

import org.example.exceptions.SystemException;
import org.example.models.RouteInfoCollection;

public interface RouteService {

    /**
     * Returns all routes passing through the provided stop
     *
     * @param stop
     * @return {@link RouteInfoCollection}
     * @throws SystemException
     */
    public RouteInfoCollection getAllRoutesHavingAStopAt(String stop) throws SystemException;

}
