package response;

import com.br.ads.models.AppConfiguration;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta para configuração do modo de execução do player.")
public class AppConfigurationResponse {

	@Schema(description = "Identificador da configuração.", example = "1")
	private Long id;

	@Schema(description = "Overlay periódico habilitado.", example = "true")
	private boolean overlayEnabled;

	@Schema(description = "Intervalo em segundos entre sobreposições.", example = "600")
	private int overlayIntervalSeconds;

	@Schema(description = "Modo contínuo habilitado.", example = "false")
	private boolean continuousModeEnabled;

	@Schema(description = "Permite execução paralela com players externos.", example = "true")
	private boolean allowParallelWithExternalPlayers;

	@Schema(description = "Prioridade de exibição.", example = "10")
	private int displayPriority;

	public AppConfigurationResponse() {
	}

	public AppConfigurationResponse(AppConfiguration configuration) {
		this.id = configuration.getId();
		this.overlayEnabled = configuration.getOverlayEnabled();
		this.overlayIntervalSeconds = configuration.getOverlayIntervalSeconds();
		this.continuousModeEnabled = configuration.getContinuousModeEnabled();
		this.allowParallelWithExternalPlayers = configuration.getAllowParallelWithExternalPlayers();
		this.displayPriority = configuration.getDisplayPriority();
	}

	public Long getId() {
		return id;
	}

	public boolean isOverlayEnabled() {
		return overlayEnabled;
	}

	public int getOverlayIntervalSeconds() {
		return overlayIntervalSeconds;
	}

	public boolean isContinuousModeEnabled() {
		return continuousModeEnabled;
	}

	public boolean isAllowParallelWithExternalPlayers() {
		return allowParallelWithExternalPlayers;
	}

	public int getDisplayPriority() {
		return displayPriority;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setOverlayEnabled(boolean overlayEnabled) {
		this.overlayEnabled = overlayEnabled;
	}

	public void setOverlayIntervalSeconds(int overlayIntervalSeconds) {
		this.overlayIntervalSeconds = overlayIntervalSeconds;
	}

	public void setContinuousModeEnabled(boolean continuousModeEnabled) {
		this.continuousModeEnabled = continuousModeEnabled;
	}

	public void setAllowParallelWithExternalPlayers(boolean allowParallelWithExternalPlayers) {
		this.allowParallelWithExternalPlayers = allowParallelWithExternalPlayers;
	}

	public void setDisplayPriority(int displayPriority) {
		this.displayPriority = displayPriority;
	}
}