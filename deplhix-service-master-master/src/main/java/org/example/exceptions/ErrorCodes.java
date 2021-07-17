package org.example.exceptions;

public class ErrorCodes {

    //Business Exception starts from 1000
    public static final int NO_ROUTES = 1000;
    public static final String NO_ROUTES_MSG = "No routes are present";

    public static final int NO_STOP_INFO_PROVIDED = 1001;
    public static final String NO_STOP_INFO_PROVIDED_MSG = "Please provide a stop for which routes can be identified";

    //System Exception starts from 4000
    public static final int JSON_PARSE_EXCEPTION = 4000;
    public static final String JSON_PARSE_EXCEPTION_MSG = "Unable to convert to response to the mapped Object";

    public static final int REST_CALL_FAILURE = 4001;
    public static final String REST_CALL_FAILURE_MSG = "Rest call failed with status {0} due to {1}";

    public static final int REST_CALL_EXCEPTION_FAILURE = 4002;
    public static final String REST_CALL_EXCEPTION_FAILURE_MSG = "Rest call failed with exception";

}
