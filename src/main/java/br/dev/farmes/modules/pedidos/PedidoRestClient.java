package br.dev.farmes.modules.pedidos;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import br.dev.farmes.modules.pedidos.dto.PedidoResponse;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@RegisterRestClient(configKey = "auth-api")
public interface PedidoRestClient {

	@GET
	@Path("/api/wholesale/v1/orders/")
	PedidoResponse getPedido(@HeaderParam("Authorization") String authorization, @QueryParam("orderId") String orderId);
	
	@DELETE
	@Path("/api/wholesale/v1/orders/")
	void deletePedido(
			@HeaderParam("Authorization") String authorization, 
			@QueryParam("id") String orderId,
			@QueryParam("sendMessageRca") String sendMessageRca,
			@QueryParam("reasonCancellation") String reasonCancellation);
}
