package br.dev.farmes.modules.pedidos.dto;

import java.math.BigDecimal;

public record PedidoResponse(
		Integer numped,
		Integer numnota,
		String data,
		BigDecimal valor,
		String posicao,
		String cobranca,
		String planoPagamento,
		String cliente
		) {}
