/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.json_validator;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import java.io.IOException;
import java.net.URI;

public class Main {

    // curl --form "file=@filename" http://localhost:80/validator/resource/upload/
    public static final String BASE_URI = "http://localhost:8080";

    public static HttpServer startServer() {
        ResourceConfig rc = new ResourceConfig().packages("com.mycompany.json_validator");
        rc.register(MultiPartFeature.class);
        rc.register(Resource.class);

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Server available at "
                + "%s\n", BASE_URI));
    }
}
