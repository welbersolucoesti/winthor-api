package br.dev.farmes.modules.pedidos;

import java.time.LocalDate;
import java.util.List;

import br.dev.farmes.modules.pedidos.dto.PedidoItemResponse;
import br.dev.farmes.modules.pedidos.dto.PedidoResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

@Path("/api/pedidos")
public class PedidoController {

	@Inject
	PedidoService pedidoService;
	
	@GET
	@Produces("application/json")
	public List<PedidoResponse> getAllPedidos(
			@HeaderParam("Authorization") String authorization,
			@QueryParam("dataInicio") @DefaultValue("") String dataInicio,
			@QueryParam("dataFim") @DefaultValue("") String dataFim,
			@QueryParam("page") @DefaultValue("1") Integer page,
			@QueryParam("size") @DefaultValue("20") Integer size) {
		
		LocalDate dtDataInicio = (dataInicio.isBlank()) ? null : LocalDate.parse(dataInicio);
		LocalDate dtDdataFim = (dataFim.isBlank()) ? null : LocalDate.parse(dataFim);
		
		
		// Limitar tamanho máximo da página
		if (size > 100) {
			size = 100;
		}
		
		// Default dataInicio para o começo do mês atual
		if (dtDataInicio == null) {
			dtDataInicio = LocalDate.now().withDayOfMonth(1);
		}
		
		// Default dataFim para hoje
		if (dtDdataFim == null) {
			dtDdataFim = LocalDate.now();
		}
		
		// Garantir que dataFim não seja anterior a dataInicio
		if (dtDdataFim.isBefore(dtDataInicio)) {
			dtDdataFim = dtDataInicio;
		}
		
		return pedidoService.findAll(dtDataInicio, dtDdataFim, page, size);
	}	
	
	@GET
	@Path("/{pedidoId}")
	@Produces("application/json")
	public PedidoItemResponse getPedido(
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
