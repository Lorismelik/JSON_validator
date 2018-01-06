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
import java.util.HashMap;
import java.util.Map;
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
            return Response.status(200).entity(gson.toJson(makeError(e))).build();
        }
        return Response.status(200).entity(gson.toJson(result)).build();
    }
    
     private Map<String, String> makeError(JsonSyntaxException e) {
        String messageDetail = e.getCause().getMessage();
        Map<String, String> error = new HashMap<>();
        String[] arrayMessage = messageDetail.split(" at", 2);
        error.put("resource", "filename");
        error.put("request-id", "12345");
        error.put("errorCode", "12345");
        error.put("errorPlace", arrayMessage[1]);
        error.put("errorMessage", arrayMessage[0]);
        return error;
    }
}
