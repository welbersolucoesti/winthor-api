package br.dev.farmes.modules.auth;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
@ApplicationScoped
public class AuthFilter implements ContainerRequestFilter {

	@Inject
	@RestClient
	AuthRestClient authRestClient;
	
	private static final Response UNAUTHORIZED = Response.status(Response.Status.UNAUTHORIZED).build();
	
	@Override
	public void filter(ContainerRequestContext requestContext) {
		String authorization = requestContext.getHeaderString("Authorization");
		
		String path = requestContext.getUriInfo().getPath();
		
		if (path.equals("/api/auth") && requestContext.getMethod().equals("POST"))
			return;
		
		if (authorization == null || authorization.isEmpty()) {
			requestContext.abortWith(UNAUTHORIZED);
			return;
		}
		
		try {
			Response response = authRestClient.validate(authorization);
			if (response.getStatus() != 200) {
				requestContext.abortWith(UNAUTHORIZED);
			}
		} catch (Exception e) {
			requestContext.abortWith(UNAUTHORIZED);
		}
	}

}
