package br.dev.farmes.modules.auth.dto;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
		@NotEmpty(message = "O campo 'login' é obrigatório")
		String login,
		
		@NotEmpty(message = "O campo 'senha' é obrigatório")
		String senha) {}
