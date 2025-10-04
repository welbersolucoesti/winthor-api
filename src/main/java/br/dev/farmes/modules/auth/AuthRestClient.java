package br.dev.farmes.modules.auth;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import br.dev.farmes.modules.auth.dto.LoginRequest;
import br.dev.farmes.modules.auth.dto.LoginResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@RegisterRestClient(configKey = "auth-api")
public interface AuthRestClient {

	@POST
	@Path("/winthor/autenticacao/v1/login")
	LoginResponse login(LoginRequest loginRequest);
	
	@GET
	@Path("/api/purchases/v1/products/1" )
	Response validate(@HeaderParam("Authorization") String authorization);
	
	@GET
	@Path("/winthor/autenticacao/v1/logout")
	Response logout(@HeaderParam("Authorization") String authorization);
}
