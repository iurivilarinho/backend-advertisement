package com.br.ads.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "tbAppConfiguration")
@Schema(description = "Configuração do modo de execução do player (overlay periódico ou contínuo) e comportamento.")
public class AppConfiguration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Identificador da configuração.", example = "1")
	private Long id;

	@Column(nullable = false)
	@Schema(description = "Se verdadeiro, o modo overlay periódico fica habilitado.", example = "true")
	private Boolean overlayEnabled;

	@Column(nullable = false)
	@Schema(description = "Intervalo (em segundos) entre sobreposições no modo overlay.", example = "600")
	private int overlayIntervalSeconds;

	@Column(nullable = false)
	@Schema(description = "Se verdadeiro, o modo contínuo fica habilitado.", example = "false")
	private Boolean continuousModeEnabled;

	@Column(nullable = false)
	@Schema(description = "Se verdadeiro, permite execução paralela com players externos.", example = "true")
	private Boolean allowParallelWithExternalPlayers;

	@Column(nullable = false)
	@Schema(description = "Prioridade de exibição do overlay (quanto maior, mais agressivo em trazer para frente).", example = "10")
	private int displayPriority;

	protected AppConfiguration() {
	}

	public AppConfiguration(Boolean overlayEnabled, int overlayIntervalSeconds, Boolean continuousModeEnabled,
			Boolean allowParallelWithExternalPlayers, int displayPriority) {
		this.overlayEnabled = overlayEnabled;
		this.overlayIntervalSeconds = overlayIntervalSeconds;
		this.continuousModeEnabled = continuousModeEnabled;
		this.allowParallelWithExternalPlayers = allowParallelWithExternalPlayers;
		this.displayPriority = displayPriority;
	}

	public Boolean isOverlayEnabled() {
		return overlayEnabled;
	}

	public void setOverlayEnabled(Boolean overlayEnabled) {
		this.overlayEnabled = overlayEnabled;
	}

	public int getOverlayIntervalSeconds() {
		return overlayIntervalSeconds;
	}

	public void setOverlayIntervalSeconds(int overlayIntervalSeconds) {
		this.overlayIntervalSeconds = overlayIntervalSeconds;
	}

	public Boolean isContinuousModeEnabled() {
		return continuousModeEnabled;
	}

	public void setContinuousModeEnabled(Boolean continuousModeEnabled) {
		this.continuousModeEnabled = continuousModeEnabled;
	}

	public Boolean isAllowParallelWithExternalPlayers() {
		return allowParallelWithExternalPlayers;
	}

	public void setAllowParallelWithExternalPlayers(Boolean allowParallelWithExternalPlayers) {
		this.allowParallelWithExternalPlayers = allowParallelWithExternalPlayers;
	}

	public int getDisplayPriority() {
		return displayPriority;
	}

	public void setDisplayPriority(int displayPriority) {
		this.displayPriority = displayPriority;
	}

	public Long getId() {
		return id;
	}

	public Boolean getOverlayEnabled() {
		return overlayEnabled;
	}

	public Boolean getContinuousModeEnabled() {
		return continuousModeEnabled;
	}

	public Boolean getAllowParallelWithExternalPlayers() {
		return allowParallelWithExternalPlayers;
	}

}