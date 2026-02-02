package org.eclipse.jakarta.hello;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Map;

@Path("/translate")
public class TranslatorResource {

    @Inject
    private GeminiService geminiService; 

    @OPTIONS
    public Response options() {
        return Response.ok().build();
    }

    @POST
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response translate(TranslateRequest request) { 
        if (request == null || request.getText() == null || request.getText().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(Map.of("error", "Text is required"))
                           .build();
        }

       
        String translatedText = geminiService.translateToDarija(request.getText());
        
  
        return Response.ok(Map.of("translatedText", translatedText)).build();
    }
}