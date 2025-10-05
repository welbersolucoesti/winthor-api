package br.dev.farmes.modules.pedidos.dto;

import java.math.BigDecimal;
import java.util.List;

public record PedidoResponse(
		String dataInicio,
		String dataFim,
		Integer qtdPedidos,
		Integer clientes,
		BigDecimal valorTotal,
		List<PedidoItemResponse> items) {}