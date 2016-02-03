package com.aplicacaodamonografia.webconnection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pablo on 31/01/16.
 */
public class Connection {

    private static final Integer READ_TIMEOUT = 10000; /* milliseconds */
    private static final Integer CONNECT_TIMEOUT = 15000; /* milliseconds */
    private static final String METHOD = "GET";

    public static HttpURLConnection createConnection(String end) throws IOException {
        URL url = new URL(end);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setRequestMethod(METHOD);
        connection.setDoInput(true);
        connection.connect();
        return connection;
    }

}
