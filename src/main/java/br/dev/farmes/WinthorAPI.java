package br.dev.farmes;

import java.util.Set;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import br.dev.farmes.MyRemoteService.Extension;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api")
public class WinthorAPI {
	
	@Inject
	@RestClient
	MyRemoteService myRemoteService;
	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Bem-vindo ao Winthor API";
    }
    
    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Extension> quarkusStatus() {
    	Set<MyRemoteService.Extension> restClientExtensions = myRemoteService.getExtensionsById("io.quarkus:quarkus-hibernate-validator");
		return restClientExtensions;
	}
}
