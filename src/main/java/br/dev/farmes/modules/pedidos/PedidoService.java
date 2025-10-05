package br.dev.farmes.modules.pedidos;

import java.util.List;
import java.util.Optional;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import br.dev.farmes.modules.pedidos.dto.PedidoResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PedidoService {
	
	@Inject
	PedidoRepository repository;
	
	@Inject
	@RestClient
	PedidoRestClient pedidoRestClient;
	
	public PedidoResponse findByNumPed(Integer numPed) {
		
		if (numPed == null) {
			throw new IllegalArgumentException("Número do pedido é obrigatório");
		}
		
		PedidoEntity pedido = repository.findByNumPed(numPed)
				.orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
		
		return pedido.toResponse();
	}
	
	public List<PedidoResponse> findAll() {
		
		Optional<List<PedidoEntity>> pedidos = repository.findAllPedidos();
		
		if (pedidos.isEmpty()) {
			return List.of();
		}
		
		return pedidos.get().stream()
				.map(PedidoEntity::toResponse)
				.toList();
	}
	
	public void deletePedido(String authorization, String orderId) {
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
 