package com.ymonnier.restful.littleapp;

import com.ymonnier.restful.littleapp.utilities.CORSFilter;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/littleapp/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     *
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.ymonnier.restful.littleapp package
        final ResourceConfig rc = new ResourceConfig()
                .register(CORSFilter.class)
                .packages("com.ymonnier.restful.littleapp");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        final HttpServer server = GrizzlyHttpServerFactory
                .createHttpServer(URI.create(BASE_URI), rc);

        return server;
    }

    /**
     * Main method.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();

        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));

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
