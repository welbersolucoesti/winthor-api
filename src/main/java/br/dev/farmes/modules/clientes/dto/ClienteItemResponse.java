package br.dev.farmes.modules.clientes.dto;

import java.math.BigDecimal;

public record ClienteItemResponse(
		Integer codCliente,
		String cnpj,
		String cliente,
		BigDecimal titulosTotal,
		BigDecimal titulosAVencer,
		BigDecimal titulosPagos,
		BigDecimal titulosVencidos,
		Boolean bloqueado) {}
