package Handlers;

import Request.RegisterRequest;
import Result.RegisterResult;
import Services.RegisterService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.HttpURLConnection;

/**
 * A class that handles the http exchange for the register api call.
 */
public class RegisterHandler extends BaseHandler {

    /**
     * Handle the exchange.
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
                RegisterRequest request = gson.fromJson(requestJson, RegisterRequest.class);

                // Do service
                RegisterService service = new RegisterService();
                RegisterResult result = service.register(request);

                if (result.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                // Send back Json from result object
                Writer resultBody = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(result, resultBody);
                resultBody.close();
                success = true;
            }

        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }

    }
}
