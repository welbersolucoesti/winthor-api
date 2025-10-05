package br.dev.farmes.modules.pedidos.dto;

import java.math.BigDecimal;
import java.util.List;

public record PedidoItemResponse(
			Integer numped, 
			Integer numnota, 
			String data, 
			BigDecimal valor, 
			String posicao,
			String cobranca, 
			String planoPagamento, 
			String cliente) {}
