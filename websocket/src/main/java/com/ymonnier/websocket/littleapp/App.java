package com.ymonnier.websocket.littleapp;

import com.ymonnier.websocket.littleapp.ws.ChannelEndPoint;
import org.glassfish.tyrus.server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Hello world!
 *
 */
public class App
{
    public static final String BASE_URI = "0.0.0.0";

    private static Server startServer() {
        return new Server(BASE_URI, 8025, "/littleapp/ws", null, ChannelEndPoint.class);
    }


    public static void main( String[] args )
    {
        Server server = startServer();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Stopping server..");
            server.stop();
        }, "shutdownHook"));

        // run
        try {
            server.start();
            System.out.println("Press CTRL^C to exit..");
            Thread.currentThread().join();
        } catch (Exception e) {
            System.out.println(String.format("There was an error while starting Grizzly HTTP server.\n%s", e.getLocalizedMessage()));
        }
    }
}
