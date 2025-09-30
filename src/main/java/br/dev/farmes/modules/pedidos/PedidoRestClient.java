package br.dev.farmes.modules.pedidos;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import br.dev.farmes.modules.pedidos.dto.PedidoResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@RegisterRestClient(baseUri = "https://cooperativade192848.winthor.cloudtotvs.com.br")
public interface PedidoRestClient {

	@GET
	@Path("/api/wholesale/v1/orders/")
	PedidoResponse getPedido(@HeaderParam("Authorization") String authorization, @QueryParam("orderId") String orderId);
}
