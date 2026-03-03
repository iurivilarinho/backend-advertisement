package com.br.ads.controller.filters;

import java.time.DayOfWeek;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Filtros para consulta de anúncios.")
public class AdvertisementFilters {

	@Schema(description = "Identificador do cliente.", example = "123")
	private Long customerId;

	@Schema(description = "Indica se o anúncio está ativo (true) ou inativo (false).", example = "true")
	private Boolean active;

	@Schema(description = "Dia da semana para filtrar anúncios.", example = "MONDAY")
	private DayOfWeek day;

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

	public DayOfWeek getDay() {
		return day;
	}

	public void setDay(DayOfWeek day) {
		this.day = day;
	}

}