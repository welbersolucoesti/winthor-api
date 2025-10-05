package br.dev.farmes.modules.pedidos.entities;

import java.math.BigDecimal;

import br.dev.farmes.modules.pedidos.dto.PedidoItemResponse;
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
	public Integer idCliente;
	public String cliente;
	
	public PedidoEntity() {
		this.numped = 0;
		this.numnota = 0;
		this.data = "";
		this.valor = BigDecimal.ZERO;
		this.posicao = "";
		this.cobranca = "";
		this.planoPagamento = "";
		this.idCliente = 0;
		this.cliente = "";
	}
	
	public PedidoEntity(
			Integer numped, 
			Integer numnota, 
			String data, 
			BigDecimal valor, 
			String posicao,
			String cobranca, 
			String planoPagamento, 
			Integer idCliente,
			String cliente) {
		this.numped = numped;
		this.numnota = numnota;
		this.data = data;
		this.valor = valor;
		this.posicao = posicao;
		this.cobranca = cobranca;
		this.planoPagamento = planoPagamento;
		this.idCliente = idCliente;
		this.cliente = cliente;
	}
	
	public PedidoItemResponse toResponse() {
		return new PedidoItemResponse(
				this.numped,
				this.numnota,
				this.data,
				this.valor,
				this.posicao,
				this.cobranca,
				this.planoPagamento,
				this.cliente);
	}
}
