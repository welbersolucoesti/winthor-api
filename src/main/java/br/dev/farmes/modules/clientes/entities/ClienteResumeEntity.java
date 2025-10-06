package br.dev.farmes.modules.clientes.entities;

import java.math.BigDecimal;

public class ClienteResumeEntity {
	public Integer totalClientes;
	public BigDecimal titulosTotal;
	public BigDecimal titulosAVencer;
	public BigDecimal titulosPagos;
	public BigDecimal titulosVencidos;
	public Integer totalClientesBloqueados;
	
	public ClienteResumeEntity() {
		this.totalClientes = 0;
		this.titulosTotal = BigDecimal.ZERO;
		this.titulosAVencer = BigDecimal.ZERO;
		this.titulosPagos = BigDecimal.ZERO;
		this.titulosVencidos = BigDecimal.ZERO;
		this.totalClientesBloqueados = 0;
	}
	
	public ClienteResumeEntity(
			Integer totalClientes, 
			BigDecimal titulosTotal,
			BigDecimal titulosAVencer,
			BigDecimal titulosPagos,
			BigDecimal titulosVencidos,
			Integer totalClientesBloqueados) {
		this.totalClientes = totalClientes;
		this.titulosTotal = titulosTotal;
		this.titulosAVencer = titulosAVencer;
		this.titulosPagos = titulosPagos;
		this.titulosVencidos = titulosVencidos;
		this.totalClientesBloqueados = totalClientesBloqueados;
	}
}
