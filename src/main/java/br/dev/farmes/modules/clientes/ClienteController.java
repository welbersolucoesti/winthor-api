package br.dev.farmes.modules.clientes;

import br.dev.farmes.modules.clientes.dto.ClienteResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

@Path("/api/clientes")
public class ClienteController {

	@Inject
	ClienteService clienteService;
	
	@GET
	@Produces("application/json")
	public ClienteResponse getAllClientes(
			@HeaderParam("Authorization") String authorization,
			@QueryParam("bloqueados") @DefaultValue("A") String bloqueados,
			@QueryParam("page") @DefaultValue("1") Integer page,
			@QueryParam("size") @DefaultValue("20") Integer size) {
		
		// Garantir que os bloqueados sejam "S" ou "N"
		if (!bloqueados.equalsIgnoreCase("S") && !bloqueados.equalsIgnoreCase("N"))
			bloqueados = "A";
		
		// Converter para maiúsculo
		bloqueados = bloqueados.toUpperCase();
		
		
		return clienteService.findAllClientes(bloqueados, page, size);
	}
}
