package com.br.ads.models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import request.RecurrenceRequest;

@Entity
@Table(name = "tbRecurrence")
@Schema(description = "Entidade que representa as regras de recorrência para exibição de anúncios.")
public class Recurrence {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Identificador único da recorrência.")
	private Long id;

	@Schema(description = "Data de início da recorrência.")
	private LocalDate startDate;

	@Schema(description = "Data de término da recorrência.")
	private LocalDate endDate;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "tbRecurrenceAllowedDay", joinColumns = @JoinColumn(name = "fk_Id_Recurrence", foreignKey = @ForeignKey(name = "FK_FROM_tbRECURRENCE_FOR_tbRECURRENCEAllowedDay")))
	@Column(name = "allowedDay", nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	@Schema(description = "Dias da semana em que o anúncio pode ser exibido.")
	private Set<DayOfWeek> allowedDays = EnumSet.noneOf(DayOfWeek.class);

	@Schema(description = "Valor do intervalo da recorrência (ex.: 2 para a cada 2 dias).")
	private Integer intervalValue;

	@Schema(description = "Número de vezes por dia que o anúncio será exibido.")
	private Integer dailyDisplayCount;

	public Recurrence() {
	}

	public Recurrence(RecurrenceRequest payload) {
		this.startDate = payload.getStartDate();
		this.endDate = payload.getEndDate();
		this.allowedDays = payload.getAllowedDays() == null ? EnumSet.noneOf(DayOfWeek.class)
				: EnumSet.copyOf(payload.getAllowedDays());
		this.intervalValue = payload.getIntervalValue();
		this.dailyDisplayCount = payload.getDailyDisplayCount();
	}

	public Long getId() {
		return id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Set<DayOfWeek> getAllowedDays() {
		return allowedDays;
	}

	public void setAllowedDays(Set<DayOfWeek> allowedDays) {
		this.allowedDays = allowedDays == null ? EnumSet.noneOf(DayOfWeek.class) : EnumSet.copyOf(allowedDays);
	}

	public Integer getIntervalValue() {
		return intervalValue;
	}

	public void setIntervalValue(Integer intervalValue) {
		this.intervalValue = intervalValue;
	}

	public Integer getDailyDisplayCount() {
		return dailyDisplayCount;
	}

	public void setDailyDisplayCount(Integer dailyDisplayCount) {
		this.dailyDisplayCount = dailyDisplayCount;
	}
}