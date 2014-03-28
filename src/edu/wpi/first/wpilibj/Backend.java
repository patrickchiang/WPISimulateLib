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

            Map<String, String> params = mapRequestParameters(t.getRequestURI().getQuery());
            String response = "Done.";

            if (params == null) {   // Give it info
                response = RobotStatus.devicesToJson();
            } else if (params.containsKey("status")) {
                // Get, parse, and set state
                int status = Integer.parseInt(params.get("status"));
                RobotStatus.state = status;
            }

            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            t.sendResponseHeaders(200, response.length());

            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.flush();
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Map<String, String> mapRequestParameters(String params) {
        if (params == null) {
            return null;
        }

        Map<String, String> result = new HashMap<String, String>();
        for (String param : params.split("&")) {
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
