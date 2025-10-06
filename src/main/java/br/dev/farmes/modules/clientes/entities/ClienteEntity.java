package br.dev.farmes.modules.clientes.entities;

import java.math.BigDecimal;

import br.dev.farmes.modules.clientes.dto.ClienteItemResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ClienteEntity {
	@Id
	public Integer codCliente;
	public String cnpj;
	public String cliente;
	public BigDecimal titulosTotal;
	public BigDecimal titulosAVencer;
	public BigDecimal titulosPagos;
	public BigDecimal titulosVencidos;
	public Boolean bloqueado;
	
	public ClienteEntity() {
		this.codCliente = 0;
		this.cnpj = "";
		this.cliente = "";
		this.titulosTotal = BigDecimal.ZERO;
		this.titulosAVencer = BigDecimal.ZERO;
		this.titulosPagos = BigDecimal.ZERO;
		this.titulosVencidos = BigDecimal.ZERO;
		this.bloqueado = true;
	}
	
	public ClienteEntity(
			Integer codCliente,
			String cnpj,
			String cliente,
			BigDecimal titulosTotal,
			BigDecimal titulosAVencer,
			BigDecimal titulosPagos,
			BigDecimal titulosVencidos,
			Boolean bloqueado) {
		this.codCliente = codCliente;
		this.cnpj = cnpj;
		this.cliente = cliente;
		this.titulosTotal = titulosTotal;
		this.titulosAVencer = titulosAVencer;
		this.titulosPagos = titulosPagos;
		this.titulosVencidos = titulosVencidos;
		this.bloqueado = bloqueado;
	}
	
	public ClienteItemResponse toResponse() {
		return new ClienteItemResponse(
				this.codCliente,
				this.cnpj,
				this.cliente,
				this.titulosTotal,
				this.titulosAVencer,
				this.titulosPagos,
				this.titulosVencidos,
				this.bloqueado);
	}
}
