package br.dev.farmes.modules.pedidos;

import java.math.BigDecimal;

import br.dev.farmes.modules.pedidos.dto.PedidoResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PedidoEntity {
	@Id
	public Integer numped;
	public Integer numnota;
	public String data;
	public BigDecimal valor;
	public String posicao;
	public String cobranca;
	public String planoPagamento;
	public String cliente;
	
	public PedidoEntity(
			Integer numped, 
			Integer numnota, 
			String data, 
			BigDecimal valor, 
			String posicao,
			String cobranca, 
			String planoPagamento, 
			String cliente) {
		this.numped = numped;
		this.numnota = numnota;
		this.data = data;
		this.valor = valor;
		this.posicao = posicao;
		this.cobranca = cobranca;
		this.planoPagamento = planoPagamento;
		this.cliente = cliente;
	}
	
	public PedidoResponse toResponse() {
		return new PedidoResponse(
				this.numped,
				this.numnota,
				this.data,
				this.valor,
				this.posicao,
				this.cobranca,
				this.planoPagamento,
				this.cliente
				);
	}
}
