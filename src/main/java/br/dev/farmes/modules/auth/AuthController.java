package br.dev.farmes.modules.auth;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import br.dev.farmes.modules.auth.dto.LoginRequest;
import br.dev.farmes.modules.auth.dto.LoginResponse;
import br.dev.farmes.modules.auth.util.MD5;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/auth")
public class AuthController {

	@Inject
	@RestClient
	AuthRestClient authRestClient;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public LoginResponse login(@Valid LoginRequest loginRequest) {

		String login = loginRequest.login().toUpperCase();

		String senha = loginRequest.senha();
		senha = MD5.generateMD5(senha);
		senha = senha.toUpperCase();

		loginRequest = new LoginRequest(login, senha);

		try {
			LoginResponse loginResponse = authRestClient.login(loginRequest);
			return loginResponse;
		} catch (Exception e) {
			throw new RuntimeException("Erro ao autenticar: " + e.getMessage());
		}
	}
	
	@GET
	@Path("/validate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response validate(@HeaderParam("Authorization") String authorization) {
		try {
			authRestClient.validate(authorization);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	@GET
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(@HeaderParam("Authorization") String authorization) {
		try {
			authRestClient.logout(authorization);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
}
