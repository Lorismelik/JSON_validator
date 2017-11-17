/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.json_validator;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.google.gson.*;
import javax.ws.rs.core.Response;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.stream.Collectors;

/* Root resource */
@Path("")
public class Resource {
    @POST
    @Produces(MediaType.TEXT_PLAIN)

    public Response checkFile(InputStream stream){
        final String json = new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.joining());  
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Object result;
        try {
            result = gson.fromJson(json, Object.class);
        } catch (JsonSyntaxException e) {
            return Response.status(200).entity(e.getCause().getMessage()).build();
        }
        return Response.status(200).entity(gson.toJson(result)).build();
    }
}
