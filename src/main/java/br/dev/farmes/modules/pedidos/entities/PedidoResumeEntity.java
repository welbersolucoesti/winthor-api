package br.dev.farmes.modules.pedidos.entities;

import java.math.BigDecimal;

public class PedidoResumeEntity {
	public Integer qtdPedidos;
	public Integer clientes;
	public BigDecimal valorTotal;
	
	public PedidoResumeEntity() {}
	
	public PedidoResumeEntity(
			Integer qtdPedidos, 
			Integer clientes, 
			BigDecimal valorTotal) {
		this.qtdPedidos = qtdPedidos;
		this.clientes = clientes;
		this.valorTotal = valorTotal;
	}
}
