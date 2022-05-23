package org.example;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerHTTP
{
    public static void main( String[] args ) {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //server.createContext("/applications/myapp", new MyHandler());
        server.createContext("/", new ClientHTTPHandler());
        server.setExecutor(null); // creates a default executor
        server.start();

        System.out.println("Server HTTP started!");
    }

}
