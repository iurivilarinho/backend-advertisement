package com.br.ads.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.ads.services.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import request.CustomerRequest;
import request.SocialLinkRequest;
import response.CustomerIndicatorsResponse;
import response.CustomerResponse;
import response.SocialLinkResponse;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private final CustomerService service;

	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@Operation(summary = "Cadastrar customere", description = "Cria um novo customere anunciante.")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Customere criado com sucesso."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida.") })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest request) {
		return ResponseEntity.ok(new CustomerResponse(service.create(request)));
	}

	@Operation(summary = "Consultar customere", description = "Consulta os dados de um customere pelo identificador.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Customere encontrado."),
			@ApiResponse(responseCode = "404", description = "Customere não encontrado.") })
	@GetMapping("/{id}")
	public ResponseEntity<CustomerResponse> findById(@PathVariable Long id) {
		return ResponseEntity.ok(new CustomerResponse(service.findById(id)));
	}

	@Operation(summary = "Consultar customers", description = "Buscar todos os clientes.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Customere encontrado."),
			@ApiResponse(responseCode = "404", description = "Customere não encontrado.") })
	@GetMapping
	public ResponseEntity<Page<CustomerResponse>> findAll(@RequestParam(required = false) String search,
			Pageable page) {
		return ResponseEntity.ok(service.findAll(search, page).map(CustomerResponse::new));
	}

	@Operation(summary = "Atualizar customer", description = "Atualiza os dados de um customere existente.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Customere atualizado com sucesso."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida."),
			@ApiResponse(responseCode = "404", description = "Customere não encontrado.") })
	@PutMapping("/{id}")
	public ResponseEntity<CustomerResponse> update(@PathVariable Long id, @Valid @RequestBody CustomerRequest request) {
		return ResponseEntity.ok(new CustomerResponse(service.update(id, request)));
	}

	@Operation(summary = "Atualiza active de customer", description = "Ativa/Desativa um customere existente.")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Customere atualizado com sucesso."),
			@ApiResponse(responseCode = "404", description = "Customere não encontrado.") })
	@PatchMapping("/{id}/enable-disable/{active}")
	public ResponseEntity<?> enableDisable(@PathVariable Long id, @PathVariable Boolean active) {
		service.enableDisable(id, active);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Adicionar rede social ao customere", description = "Cadastra um link de rede social para o customere.")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Link cadastrado com sucesso."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida."),
			@ApiResponse(responseCode = "404", description = "Customere não encontrado.") })
	@PostMapping("/{customerId}/social-links")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<SocialLinkResponse> addSocialLink(@PathVariable Long customerId,
			@Valid @RequestBody SocialLinkRequest request) {
		return ResponseEntity.ok(new SocialLinkResponse(service.addSocialLink(customerId, request)));
	}

	@Operation(summary = "Remover rede social do customere", description = "Remove um link de rede social do customere.")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Link removido com sucesso."),
			@ApiResponse(responseCode = "404", description = "Customere ou link não encontrado.") })
	@DeleteMapping("/{customerId}/social-links/{linkId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeSocialLink(@PathVariable Long customerId, @PathVariable Long linkId) {
		service.removeSocialLink(customerId, linkId);
	}

	@Operation(summary = "Indicadores do customere", description = "Retorna indicadores agregados do customere, incluindo tempo total de anúncios ativos.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Indicadores retornados com sucesso."),
			@ApiResponse(responseCode = "404", description = "Customere não encontrado.") })
	@GetMapping("/{customerId}/indicators")
	public CustomerIndicatorsResponse indicators(@PathVariable Long customerId) {
		return service.getIndicators(customerId);
	}
}