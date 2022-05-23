package org.example;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientTCPHandler {

    private Socket clientSocket;
    private static BufferedReader in = null;
    private static PrintWriter out = null;

    private static String indications =
            "Insert: \r\n" +
                    ">> 'red' -> List of red wines: \r\n" +
                    ">> 'white' -> List of white wines \r\n" +
                    ">> 'sorted_by_name' -> List of all wines sorted by name: \r\n" +
                    ">> 'sorted_by_price' -> List of all wines sorted by price: ";

    public ClientTCPHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void handle() {
        in = allocateReader(clientSocket);
        out = allocateWriter(clientSocket);
        out.println(indications);
        buildWineList();
        handleInput();
    }

    public void handleInput() {
        String userInput;
        try {
            while ((userInput = in.readLine()) != null) {
                System.out.println("Client: " + (userInput));
                out.println("You: '" + userInput + "'");
                out.println(process(userInput));
            }
        } catch (IOException e) {
            System.out.println("Client disconnected");
        }
    }

    static ArrayList<Wine> wines = new ArrayList<>();
    static void buildWineList() {
        wines.add(new Wine(13,"Dom Perignon",225.94, "white"));
        wines.add(new Wine(14,"Pignoli",133.0, "red"));
        wines.add(new Wine(124,"APinot",6.94, "red"));
    }

    public String process(String input) {

        Gson gson = new Gson();

        String intro;
        String result;

        if (input.equals("red")) {
            intro = "List of red wines: ";

            ArrayList<Wine> results = new ArrayList<>();

            for(Wine wine : wines) {
                if(wine.getType() == "red") {
                    results.add(wine);
                }
            }
            result = gson.toJson(results);
            return intro + result;

        }  else if (input.equals("white")) {
            intro = "List of white wines: ";

            ArrayList<Wine> results = new ArrayList<>();

            for(Wine wine : wines) {
                if(wine.getType() == "white") {
                    results.add(wine);
                }
            }
            result = gson.toJson(results);
            return intro + result;

        } else if (input.equals("sorted_by_name")) {
            intro = "List of all wines sorted by name: ";

            sortName();

            result = gson.toJson(wines);
            return intro + result;

        }  else if (input.equals("sorted_by_price")) {
            intro = "List of all wines sorted by price: ";

            sortPrice();

            result = gson.toJson(wines);
            return intro + result;

        } else {
            result = indications;
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

    //Server functions.

    private BufferedReader allocateReader(Socket clientSocket) {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Reader failed" + e);
            return null;
        } return in;
    }

    private PrintWriter allocateWriter(Socket clientSocket) {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } return out;
    }

    // END: Server functions.

}
