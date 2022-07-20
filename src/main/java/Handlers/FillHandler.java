package Handlers;

import Result.FillResult;
import Services.FillService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

/**
 * A class to handle the http exchange for the Fill api call.
 */
public class FillHandler extends BaseHandler {

    /**
     * Handle the exhange for the fill api.
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                String urlPath = exchange.getRequestURI().toString().toLowerCase();
                String[] str = urlPath.split("/");
                int numGenerations = 0;
                String username = str[2];

                if (str.length >= 4) {
                    numGenerations = Integer.parseInt(str[3]);
                }

                // Do service
                FillService service = new FillService();
                FillResult result = service.fill(username, numGenerations);

                // Send back Json from result object
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                Writer resultBody = new OutputStreamWriter(exchange.getResponseBody());
                Gson gson = new Gson();
                gson.toJson(result, resultBody);
                resultBody.close();
                success = true;
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
