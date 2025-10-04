package br.dev.farmes.modules.pedidos;

import java.util.List;

import br.dev.farmes.modules.pedidos.dto.PedidoResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PedidoService {
	
	@Inject
	PedidoRepository em;
	
	public PedidoResponse findByNumPed(Integer numPed) {
		 PedidoEntity pedido = em.findByNumPed(numPed);
		if (pedido == null) {
			throw new RuntimeException("Pedido não encontrado: " + numPed);
		}
		return pedido.toResponse();
	}
	
	public List<PedidoResponse> findAll() {
		return em.findAllPedidos()
				.stream()
				.map(PedidoEntity::toResponse)
				.toList();
	}
}
