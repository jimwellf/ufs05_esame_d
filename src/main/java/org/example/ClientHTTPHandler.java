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
        String result = getCommand(query);

        buildWineList();

        String s = read(is);

        String response = "<!doctype html>\n" +
                "<html lang=en>\n" +
                "<head>\n" +
                "<meta charset=utf-8>\n" +
                "<title>UFS 05 - Exam Traccia D</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h3>Query:</h3>" +
                "</br>\n" +
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

    private String read(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        System.out.println("\n");

        String received = null;
        while (true) {
            String s = null;
            try {
                if ((s = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(s);
            received += s;
        }
        return received;
    }

    String getCommand(String query) {

        Gson gson = new Gson();
        query = cleanQuery(query);

        String intro;
        String result = "";

        switch (query) {
            default:
                result = query + " is not a command";
                break;
            case "red":
                intro = "List of red wines: ";

                ArrayList<Wine> redwines = new ArrayList<>();
                for(Wine wine : wines) {
                    if(wine.getType().equals("red")) {
                        redwines.add(wine);
                    }
                }
                result = intro + gson.toJson(redwines);
                break;

            case "white":
                intro = "List of white wines: ";

                ArrayList<Wine> whitewines = new ArrayList<>();

                for(Wine wine : wines) {
                    if(wine.getType().equals("white")) {
                        whitewines.add(wine);
                    }
                }
                result = intro + gson.toJson(whitewines);
                break;

            case "sorted_by_name":
                intro = "List of all wines sorted by name: ";
                sortName();
                result = intro + gson.toJson(wines);

                break;

            case "sorted_by_price":
                intro = "List of all wines sorted by price: ";
                sortPrice();
                result = intro + gson.toJson(wines);

                break;
        }

        return result;
    }

    static ArrayList<Wine> wines = new ArrayList<>();
    static void buildWineList() {
        wines.add(new Wine(13,"Dom Perignon",225.94, "white"));
        wines.add(new Wine(14,"Pignoli",133.0, "red"));
        wines.add(new Wine(124,"APinot",6.94, "red"));
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

    String cleanQuery(String query) {
        return query.split("=")[1];
    }
}