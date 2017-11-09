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
import java.io.IOException;

/* Root resource */
 @Path("/resource")
public class Resource {
    
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    
    public Response checkFile(@FormDataParam("file") InputStream stream,
    @FormDataParam("file") FormDataContentDisposition fileDetail) throws Exception {
        StringBuilder json = new StringBuilder();
        try {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        String line;
	while ((line = br.readLine()) != null) {
			json.append(line);
	}
        } catch (IOException e) {
		System.out.print(e.getLocalizedMessage());
	}
        Gson gson =  new GsonBuilder().setPrettyPrinting().create();
        Map<String, String> result = null;
        try {
        result = gson.fromJson(json.toString(),  new TypeToken<Map<String, String>>() {}.getType());
        } catch (JsonSyntaxException e) {
            return  Response.status(200).entity(gson.toJson(makeError(e.getCause().getMessage(),fileDetail))).build();
        } 
        return   Response.status(200).entity(gson.toJson(result)).build();
    }   
    
    private  Map<String, String> makeError (String messageDetail, FormDataContentDisposition fileDetail){
         Map<String, String> error = new HashMap<String, String>();
         String[] arrayMessage =  messageDetail.split("at", 2);
         error.put("resource", fileDetail.getFileName());
         error.put("errorPlace", arrayMessage[1]);
         error.put("errorMessage", arrayMessage[0]);
        
         return error;
    }
}
