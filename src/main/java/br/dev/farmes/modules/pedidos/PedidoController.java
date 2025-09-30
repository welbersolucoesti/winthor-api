package br.dev.farmes.modules.pedidos;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import br.dev.farmes.modules.pedidos.dto.PedidoResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/api/pedidos")
public class PedidoController {

	@Inject
	@RestClient
	PedidoRestClient pedidoRestClient;
	
	@GET
	@Path("/{pedidoId}")
	public PedidoResponse getPedido(@HeaderParam("Authorization") String authorization, @PathParam("pedidoId") String orderId) {
		try {
			PedidoResponse pedidoResponse = pedidoRestClient.getPedido(authorization, orderId);
			return pedidoResponse;
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar pedido: " + e.getMessage());
		}
	}
}
