package org.example.util;

import org.apache.log4j.Logger;
import org.example.exceptions.SystemException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.example.exceptions.ErrorCodes.REST_CALL_EXCEPTION_FAILURE;
import static org.example.exceptions.ErrorCodes.REST_CALL_EXCEPTION_FAILURE_MSG;
import static org.example.exceptions.ErrorCodes.REST_CALL_FAILURE;
import static org.example.exceptions.ErrorCodes.REST_CALL_FAILURE_MSG;


public class CommonUtils {

    private static Logger log = Logger.getLogger(CommonUtils.class);

    public static Timestamp getUTCdate() {
        return Timestamp.valueOf(OffsetDateTime.now(ZoneOffset.UTC).minusHours(4).toLocalDateTime());
    }


    public static String getAPIResult(String url) throws SystemException {

        StringBuffer response = new StringBuffer();
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                  con.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } else {
                log.error(REST_CALL_FAILURE + ":" + MessageFormat.format(REST_CALL_FAILURE_MSG, responseCode, con.getResponseMessage()));
                throw new SystemException(REST_CALL_FAILURE + ":" + MessageFormat.format(REST_CALL_FAILURE_MSG, responseCode, con.getResponseMessage()));
            }
        } catch (IOException ex) {
            log.error(REST_CALL_EXCEPTION_FAILURE + ":" + REST_CALL_EXCEPTION_FAILURE_MSG);
            throw new SystemException(REST_CALL_EXCEPTION_FAILURE + ":" + REST_CALL_EXCEPTION_FAILURE_MSG, ex);
        }
        return response.toString();
    }

}
