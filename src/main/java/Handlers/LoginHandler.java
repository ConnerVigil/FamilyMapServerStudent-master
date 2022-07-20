package Handlers;

import Request.LoginRequest;
import Result.LoginResult;
import Services.LoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.HttpURLConnection;

/**
 * A class to handle the http exchange for the login api call.
 */
public class LoginHandler extends BaseHandler {

    /**
     * Handles the exchange for the login service api call.
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                InputStream requestBody = exchange.getRequestBody();
                String requestJson = readString(requestBody);
                System.out.println(requestJson);

                Gson gson = new Gson();
                LoginRequest request = gson.fromJson(requestJson, LoginRequest.class);

				LoginService service = new LoginService();
				LoginResult result = service.login(request);

                if (result.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    Writer resultBody = new OutputStreamWriter(exchange.getResponseBody());
                    gson.toJson(result, resultBody);
                    resultBody.close();
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    Writer resultBody = new OutputStreamWriter(exchange.getResponseBody());
                    gson.toJson(result, resultBody);
                    resultBody.close();
                }
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
