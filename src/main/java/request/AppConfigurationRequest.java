package request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Schema(description = "DTO de requisição para configuração do modo de execução do player.")
public class AppConfigurationRequest {

	@Schema(description = "Se verdadeiro, habilita overlay periódico.", example = "true")
	private boolean overlayEnabled;

	@Min(1)
	@Schema(description = "Intervalo em segundos entre sobreposições.", example = "600")
	private int overlayIntervalSeconds = 600;

	@Schema(description = "Se verdadeiro, habilita modo contínuo.", example = "false")
	private boolean continuousModeEnabled;

	@Schema(description = "Se verdadeiro, permite execução paralela com players externos.", example = "true")
	private boolean allowParallelWithExternalPlayers;

	@Schema(description = "Prioridade de exibição do overlay (maior = mais agressivo).", example = "10")
	private int displayPriority = 10;

	public AppConfigurationRequest() {
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