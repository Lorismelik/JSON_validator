/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.json_validator;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.util.Map;
import java.util.HashMap;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.stream.Collectors;

/* Root resource */
@Path("/resource")
public class Resource {

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)

    public Response checkFile(@FormDataParam("file") InputStream stream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) throws Exception {
        final String json = new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.joining());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, String> result = new HashMap<>();
        try {
            result = gson.fromJson(json, new TypeToken<Map<String, String>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            return Response.status(200).entity(gson.toJson(makeError(e, fileDetail))).build();
        }
        return Response.status(200).entity(gson.toJson(result)).build();
    }

    private Map<String, String> makeError(JsonSyntaxException e, FormDataContentDisposition fileDetail) {
        String messageDetail = e.getCause().getMessage();
        Map<String, String> error = new HashMap<>();
        String[] arrayMessage = messageDetail.split("at", 2);
        error.put("resource", fileDetail.getFileName());
        error.put("errorPlace", arrayMessage[1]);
        error.put("errorMessage", arrayMessage[0]);
        return error;
    }
}
