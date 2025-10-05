package br.dev.farmes.modules.pedidos;

import java.util.List;

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
	
	@GET
	@Produces("application/json")
	public List<PedidoResponse> getAllPedidos(
			@HeaderParam("Authorization") String authorization) {
		return pedidoService.findAll();
	}	
	
	@GET
	@Path("/{pedidoId}")
	@Produces("application/json")
	public PedidoResponse getPedido(
			@HeaderParam("Authorization") String authorization, 
			@PathParam("pedidoId") Integer pedidoId) {
		return pedidoService.findByNumPed(pedidoId);
	}
	
	@DELETE
	@Path("/{pedidoId}")
	public void deletePedido(
			@HeaderParam("Authorization") String authorization, 
			@PathParam("pedidoId") String orderId) {
		pedidoService.deletePedido(authorization, orderId);
	}
}
