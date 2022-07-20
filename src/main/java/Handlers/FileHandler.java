package Handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;

/**
 * A class to handle the http exchange for the filehandler api call.
 */
public class FileHandler extends BaseHandler {

    /**
     * Handle the file handler api call.
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {
            // check to make sure it is a GET request
                // return 405 if not
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                // get request URL out of http exchange
                String urlPath = exchange.getRequestURI().toString().toLowerCase();

                if (urlPath.equals("/")) {
                    urlPath = "/index.html";
                }

                /*
                URL PATH        FILE PATH
                /              -> web/index.html
                /index.html    -> web/index.html
                /favicon.ico   -> web/favicon.ico
                /css/main.css  -> web/css/main.css
                 */

                String filePath = "web" + urlPath;
                File file = new File(filePath);

                if (!file.exists()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);

                    filePath = "web/HTML/404.html";
                    File fourohfourfile = new File(filePath);
                    OutputStream responseBody = exchange.getResponseBody();
                    Files.copy(fourohfourfile.toPath(), responseBody);

                    exchange.getResponseBody().close();
                    return;
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }

                // if file does exist, read the file and write it to the exchange output stream
                OutputStream responseBody = exchange.getResponseBody();
                Files.copy(file.toPath(), responseBody);
                success = true;
                exchange.getResponseBody().close();

            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                exchange.getResponseBody().close();
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }

        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
