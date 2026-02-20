package com.br.ads.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.ads.services.AppConfigurationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import request.AppConfigurationRequest;
import response.AppConfigurationResponse;

@RestController
@RequestMapping("/api/configuration")
public class AppConfigurationController {

	private final AppConfigurationService service;

	public AppConfigurationController(AppConfigurationService service) {
		this.service = service;
	}

	@Operation(summary = "Criar configuração", description = "Cria uma configuração inicial do player.")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Configuração criada com sucesso."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida.") })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<AppConfigurationResponse> create(@Valid @RequestBody AppConfigurationRequest request) {
		return ResponseEntity.ok(new AppConfigurationResponse(service.upsert(null, request)));
	}

	@Operation(summary = "Atualizar configuração", description = "Atualiza uma configuração existente do player.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Configuração atualizada com sucesso."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida."),
			@ApiResponse(responseCode = "404", description = "Configuração não encontrada.") })
	@PutMapping("/{id}")
	public ResponseEntity<AppConfigurationResponse> update(@PathVariable Long id,
			@Valid @RequestBody AppConfigurationRequest request) {
		return ResponseEntity.ok(new AppConfigurationResponse(service.upsert(id, request)));
	}

	@Operation(summary = "Consultar configuração", description = "Consulta a configuração do player pelo identificador.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Configuração encontrada."),
			@ApiResponse(responseCode = "404", description = "Configuração não encontrada.") })
	@GetMapping("/{id}")
	public ResponseEntity<AppConfigurationResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(new AppConfigurationResponse(service.getById(id)));
	}
}