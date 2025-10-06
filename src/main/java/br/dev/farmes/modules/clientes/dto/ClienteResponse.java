package br.dev.farmes.modules.clientes.dto;

import java.math.BigDecimal;
import java.util.List;

public record ClienteResponse(
		Integer totalClientes,
		BigDecimal titulosTotal,
		BigDecimal titulosAVencer,
		BigDecimal titulosPagos,
		BigDecimal titulosVencidos,
		Integer totalClientesBloqueados,
		List<ClienteItemResponse> clientes) {}
