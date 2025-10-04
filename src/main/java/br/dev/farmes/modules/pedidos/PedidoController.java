package br.dev.farmes.modules.pedidos;

import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import br.dev.farmes.modules.pedidos.dto.PedidoResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

@Path("/api/pedidos")
public class PedidoController {

	@Inject
	PedidoService pedidoService;
	
	@Inject
	@RestClient
	PedidoRestClient pedidoRestClient;
	
	@GET
	@Produces("application/json")
	public List<PedidoResponse> getAllPedidos(@HeaderParam("Authorization") String authorization) {
		try {
			List<PedidoResponse> pedidos = pedidoService.findAll();
			return pedidos;
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar pedidos: " + e.getMessage());
		}
	}	
	
	@GET
	@Path("/{pedidoId}")
	@Produces("application/json")
	public PedidoResponse getPedido(@HeaderParam("Authorization") String authorization, @PathParam("pedidoId") Integer pedidoId) {
		try {
			PedidoResponse pedido = pedidoService.findByNumPed(pedidoId);
			return pedido;
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar pedido: " + e.getMessage());
		}
	}
	
	@DELETE
	@Path("/{pedidoId}")
	public void deletePedido(@HeaderParam("Authorization") String authorization, @PathParam("pedidoId") String orderId) {
		try {
			pedidoRestClient.deletePedido(
					authorization, 
					orderId, 
					"false", 
					"Cancelamento via API");
		} catch (Exception e) {
			throw new RuntimeException("Erro ao deletar pedido: " + e.getMessage());
		}
	}
}
