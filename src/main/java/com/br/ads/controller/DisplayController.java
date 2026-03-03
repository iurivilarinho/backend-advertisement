// src/main/java/com/br/ads/controller/DisplayController.java
package com.br.ads.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.ads.controller.filters.DisplayFilters;
import com.br.ads.models.Display;
import com.br.ads.services.DisplayService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import request.DisplayRequest;
import response.DisplayResponse;

@Tag(name = "Displays", description = "Endpoints para gerenciamento de displays (telões).")
@RestController
@RequestMapping("/displays")
public class DisplayController {

	private final DisplayService displayService;

	public DisplayController(DisplayService displayService) {
		this.displayService = displayService;
	}

	@Operation(summary = "Criar display", description = "Cria um novo display vinculando ao cliente informado.")
	@ApiResponse(responseCode = "201", description = "Display criado com sucesso.")
	@ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
	@PostMapping
	public ResponseEntity<DisplayResponse> create(@RequestBody DisplayRequest request) {
		Display created = displayService.create(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(new DisplayResponse(created));
	}

	@Operation(summary = "Atualizar display", description = "Atualiza os dados de um display pelo identificador.")
	@ApiResponse(responseCode = "200", description = "Display atualizado com sucesso.")
	@ApiResponse(responseCode = "404", description = "Display não encontrado.")
	@PutMapping("/{id}")
	public ResponseEntity<DisplayResponse> update(@PathVariable Long id, @RequestBody DisplayRequest request) {
		Display updated = displayService.update(id, request);
		return ResponseEntity.ok(new DisplayResponse(updated));
	}

	@Operation(summary = "Listar displays", description = "Lista displays com filtros e paginação.")
	@ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.")
	@GetMapping
	public ResponseEntity<Page<DisplayResponse>> findAll(DisplayFilters filters, Pageable pageable) {
		Page<DisplayResponse> result = displayService.findAll(filters, pageable).map(DisplayResponse::new);
		return ResponseEntity.ok(result);
	}

	@Operation(summary = "Buscar display por id", description = "Retorna um display pelo seu identificador.")
	@ApiResponse(responseCode = "200", description = "Display retornado com sucesso.")
	@ApiResponse(responseCode = "404", description = "Display não encontrado.")
	@GetMapping("/{id}")
	public ResponseEntity<DisplayResponse> findById(@PathVariable Long id) {
		return ResponseEntity.ok(new DisplayResponse(displayService.findById(id)));
	}

	@Operation(summary = "Ativar/desativar display", description = "Ativa ou desativa um display.")
	@ApiResponse(responseCode = "200", description = "Display atualizado com sucesso.")
	@ApiResponse(responseCode = "404", description = "Display não encontrado.")
	@PatchMapping("/{id}/enable-disable/{active}")
	public ResponseEntity<DisplayResponse> enableDisable(@PathVariable Long id, @PathVariable Boolean active) {
		Display updated = displayService.enableDisable(id, active);
		return ResponseEntity.ok(new DisplayResponse(updated));
	}
}