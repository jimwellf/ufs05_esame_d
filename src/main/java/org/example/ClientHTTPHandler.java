package org.example;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;

public class ClientHTTPHandler implements HttpHandler {

    public void handle(HttpExchange t) throws IOException {
        InputStream is = t.getRequestBody();
        URI uri = t.getRequestURI();
        String query = uri.getQuery();
        String result = process(query);

        buildWineList();

        String response = "<!doctype html>\n" +
                "<html lang=en>\n" +
                "<head>\n" +
                "<meta charset=utf-8>\n" +
                "<title>UFS 05 - Exam Traccia D</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h3>Query:</h3>" +
                query +
                "<h3>Risultato:</h3>\n" +
                result +
                "</body>\n" +
                "</html>\n";

        t.sendResponseHeaders(200, response.length());

        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public String process(String msg) {

        Gson gson = new Gson();

        String query = cleanQuery(msg);
        String intro;
        String result;

        if (query.equals("red")) {
            intro = "List of red wines: ";

            ArrayList<Wine> results = new ArrayList<>();

            for(Wine wine : wines) {
                if(wine.getType().equals("red")) {
                    results.add(wine);
                }
            }
            result = gson.toJson(results);
            return intro + result;

        }  else if (query.equals("white")) {
            intro = "List of white wines: ";

            ArrayList<Wine> results = new ArrayList<>();

            for(Wine wine : wines) {
                if(wine.getType().equals("white")) {
                    results.add(wine);
                }
            }
            result = gson.toJson(results);
            return intro + result;

        } else if (query.equals("sorted_by_name")) {
            intro = "List of all wines sorted by name: ";

            sortName();

            result = gson.toJson(wines);
            return intro + result;

        }  else if (query.equals("sorted_by_price")) {
            intro = "List of all wines sorted by price: ";

            sortPrice();

            result = gson.toJson(wines);
            return intro + result;

        } else {
            result = "Command not found.";
            return result;
        }
    }

    private void sortPrice() {
        wines.sort((Wine wine1, Wine wine2) -> {
            if (wine1.getPrice() > wine2.getPrice())
                return 1;
            if (wine1.getPrice() < wine2.getPrice())
                return -1;
            return 0;
        });
    }

    private void sortName() {
        wines.sort((Wine wine1, Wine wine2) -> {
            return wine1.getName().compareTo(wine2.getName());
        });
    }

    static ArrayList<Wine> wines = new ArrayList<>();
    static void buildWineList() {
        wines.add(new Wine(13,"Dom Perignon",225.94, "white"));
        wines.add(new Wine(14,"Pignoli",133.0, "red"));
        wines.add(new Wine(124,"Pinot",6.94, "red"));
    }

    String cleanQuery(String query) {
        return query.split("=")[1];
    }
}