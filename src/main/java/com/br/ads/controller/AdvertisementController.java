package com.br.ads.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.ads.services.AdvertisementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import request.AdvertisementRequest;
import response.AdvertisementResponse;

@RestController
@RequestMapping("/api/advertisements")
public class AdvertisementController {

	private final AdvertisementService service;

	public AdvertisementController(AdvertisementService service) {
		this.service = service;
	}

	@Operation(summary = "Cadastrar anúncio", description = "Cria um novo anúncio do tipo imagem/carrossel ou vídeo.")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Anúncio criado com sucesso."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida.") })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<AdvertisementResponse> create(@Valid @RequestBody AdvertisementRequest request) {
		return ResponseEntity.ok(new AdvertisementResponse(service.create(request)));
	}

	@Operation(summary = "Consultar anúncio", description = "Consulta um anúncio pelo identificador.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Anúncio encontrado."),
			@ApiResponse(responseCode = "404", description = "Anúncio não encontrado.") })
	@GetMapping("/{id}")
	public ResponseEntity<AdvertisementResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(new AdvertisementResponse(service.getById(id)));
	}

	@Operation(summary = "Atualizar anúncio", description = "Atualiza um anúncio existente.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Anúncio atualizado com sucesso."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida."),
			@ApiResponse(responseCode = "404", description = "Anúncio não encontrado.") })
	@PutMapping("/{id}")
	public ResponseEntity<AdvertisementResponse> update(@PathVariable Long id,
			@Valid @RequestBody AdvertisementRequest request) {
		return ResponseEntity.ok(new AdvertisementResponse(service.update(id, request)));
	}

	@Operation(summary = "Excluir anúncio", description = "Exclui um anúncio existente.")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Anúncio excluído com sucesso."),
			@ApiResponse(responseCode = "404", description = "Anúncio não encontrado.") })
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}

	@Operation(summary = "Listar anúncios por cliente", description = "Retorna todos os anúncios associados a um cliente.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.") })
	@GetMapping("/by-client/{clientId}")
	public ResponseEntity<List<AdvertisementResponse>> listByClient(@PathVariable Long clientId) {
		return ResponseEntity.ok(service.listByCustomer(clientId).stream().map(AdvertisementResponse::new).toList());
	}

	@Operation(summary = "Playlist ativa para execução", description = "Retorna a lista de anúncios aptos para reprodução na data informada (ou hoje).")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Playlist retornada com sucesso.") })
	@GetMapping("/active")
	public ResponseEntity<List<AdvertisementResponse>> activeForPlayback(
			@RequestParam(required = false) LocalDate date) {
		return ResponseEntity.ok(service.listActiveForPlayback(date).stream().map(AdvertisementResponse::new).toList());
	}
}