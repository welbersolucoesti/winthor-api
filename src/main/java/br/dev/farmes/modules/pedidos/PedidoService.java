package br.dev.farmes.modules.pedidos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import br.dev.farmes.modules.pedidos.dto.PedidoItemResponse;
import br.dev.farmes.modules.pedidos.dto.PedidoResponse;
import br.dev.farmes.modules.pedidos.entities.PedidoEntity;
import br.dev.farmes.modules.pedidos.entities.PedidoResumeEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PedidoService {
	
	@Inject
	PedidoRepository repository;
	
	@Inject
	@RestClient
	PedidoRestClient pedidoRestClient;
	
	public PedidoItemResponse findByNumPed(Integer numPed) {
		
		if (numPed == null) {
			throw new IllegalArgumentException("Número do pedido é obrigatório");
		}
		
		PedidoEntity pedido = repository.findByNumPed(numPed)
				.orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
		
		return pedido.toResponse();
	}
	
	public List<PedidoResponse> findAll(LocalDate dataInicial, LocalDate dataFinal, Integer page, Integer size) {
		
		Optional<List<PedidoEntity>> pedidos = repository.findAllPedidos(dataInicial, dataFinal, page, size);
		
		if (pedidos.isEmpty()) {
			return List.of();
		}
		
		PedidoResumeEntity resume = repository.findResumePedidos(dataInicial, dataFinal)
				.orElse(new PedidoResumeEntity(0, 0, BigDecimal.ZERO));
		
		return List.of(
				new PedidoResponse(
						dataInicial.toString(),
						dataFinal.toString(),
						resume.qtdPedidos,
						resume.clientes,
						resume.valorTotal,
						pedidos.get().stream()
							.map(PedidoEntity::toResponse)
							.toList()
				)
		);
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
 