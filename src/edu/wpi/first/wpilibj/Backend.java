package edu.wpi.first.wpilibj;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Patrick
 */
public class Backend implements HttpHandler {

    @Override
    public void handle(HttpExchange t) {
        try {
            System.out.println(queryToMap(t.getRequestURI().getQuery()));
            String response = "This is the response";
            t.sendResponseHeaders(200, response.length());
            try (OutputStream os = t.getResponseBody()) {
                os.write(response.getBytes());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Map<String, String> queryToMap(String query) {
        if (query == null) {
            return null;
        }

        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }

        return result;
    }
}
