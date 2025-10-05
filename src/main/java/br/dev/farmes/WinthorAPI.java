package br.dev.farmes;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api")
public class WinthorAPI {
	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Bem-vindo ao Winthor API";
    }
}
