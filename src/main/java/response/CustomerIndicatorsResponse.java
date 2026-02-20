package response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Indicadores agregados do cliente.")
public class CustomerIndicatorsResponse {

	@Schema(description = "Identificador do cliente.", example = "1")
	private Long clientId;

	@Schema(description = "Tempo total (em segundos) de anúncios ativos do cliente.", example = "120")
	private long totalActiveAdvertisementSeconds;

	@Schema(description = "Quantidade de anúncios vigentes do cliente.", example = "3")
	private long activeAdvertisementsCount;

	public CustomerIndicatorsResponse() {
	}

	public CustomerIndicatorsResponse(Long clientId, long totalActiveAdvertisementSeconds,
			long activeAdvertisementsCount) {
		this.clientId = clientId;
		this.totalActiveAdvertisementSeconds = totalActiveAdvertisementSeconds;
		this.activeAdvertisementsCount = activeAdvertisementsCount;
	}

	public Long getClientId() {
		return clientId;
	}

	public long getTotalActiveAdvertisementSeconds() {
		return totalActiveAdvertisementSeconds;
	}

	public long getActiveAdvertisementsCount() {
		return activeAdvertisementsCount;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public void setTotalActiveAdvertisementSeconds(long totalActiveAdvertisementSeconds) {
		this.totalActiveAdvertisementSeconds = totalActiveAdvertisementSeconds;
	}

	public void setActiveAdvertisementsCount(long activeAdvertisementsCount) {
		this.activeAdvertisementsCount = activeAdvertisementsCount;
	}
}