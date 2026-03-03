package com.br.ads.controller.filters;

import io.swagger.v3.oas.annotations.media.Schema;

public class DisplayFilters {

	@Schema(description = "Identificador do cliente.", example = "123")
	private Long customerId;

	@Schema(description = "Indica se o anúncio está ativo (true) ou inativo (false).", example = "true")
	private Boolean active;

	@Schema(description = "Campo de busca aberto.", example = "Tela 1.")
	private String search;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

}
