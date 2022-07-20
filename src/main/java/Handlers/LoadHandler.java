package Handlers;

import Request.LoadRequest;
import Result.LoadResult;
import Services.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

/**
 * A class to handle the http exchange for the load api call.
 */
public class LoadHandler extends BaseHandler {

    /**
     * Handle the excahnge for the load api call.
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

                // Get Json string
                InputStream requestBody = exchange.getRequestBody();
                String requestJson = readString(requestBody);
                System.out.println(requestJson);

                // Turn it into a request object
                Gson gson = new Gson();
                LoadRequest request = gson.fromJson(requestJson, LoadRequest.class);

                // Do service
                LoadService service = new LoadService();
                LoadResult result = service.load(request);

                // Send back Json from result object
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                Writer resultBody = new OutputStreamWriter(exchange.getResponseBody());
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
