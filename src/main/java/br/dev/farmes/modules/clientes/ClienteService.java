package br.dev.farmes.modules.clientes;

import java.util.List;

import br.dev.farmes.modules.clientes.dto.ClienteResponse;
import br.dev.farmes.modules.clientes.entities.ClienteEntity;
import br.dev.farmes.modules.clientes.entities.ClienteResumeEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ClienteService {

	@Inject
	ClienteRepository repository;
	
	public ClienteResponse findAllClientes(String bloqueados, Integer page, Integer size) {
		
		ClienteResumeEntity resume = repository.findResumeClientes()
				.orElse(new ClienteResumeEntity());
		
		List<ClienteEntity> clientes = repository.findAllClientes(bloqueados, page, size)
				.orElse(List.of());
		
		return new ClienteResponse(
				resume.totalClientes,
				resume.titulosTotal,
				resume.titulosAVencer,
				resume.titulosPagos,
				resume.titulosVencidos,
				resume.totalClientesBloqueados,
				clientes.stream()
					.map(ClienteEntity::toResponse)
					.toList()
		);
		
	}
}
