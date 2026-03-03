// src/main/java/com/cantodeminas/ads/api/dto/response/AdvertisementBasicResponse.java
package response;

import com.br.ads.enums.AdvertisementType;
import com.br.ads.models.Advertisement;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta básica de anúncio (campos essenciais).")
public class AdvertisementBasicResponse {

	@Schema(description = "Identificador do anúncio.", example = "100")
	private Long id;

	@Schema(description = "Identificador do cliente associado.", example = "1")
	private Long customerId;

	@Schema(description = "Nome do anúncio.", example = "Promoção de Queijos - Fevereiro")
	private String name;

	@Schema(description = "Tipo do anúncio.", example = "IMAGE")
	private AdvertisementType type;

	@Schema(description = "Indica se o anúncio está vigente (ativo).", example = "true")
	private boolean active;

	public AdvertisementBasicResponse(Advertisement advertisement) {
		this.id = advertisement.getId();
		this.customerId = advertisement.getCustomer() != null ? advertisement.getCustomer().getId() : null;
		this.name = advertisement.getName();
		this.type = advertisement.getType();
		this.active = Boolean.TRUE.equals(advertisement.getActive());
	}

	public Long getId() {
		return id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public String getName() {
		return name;
	}

	public AdvertisementType getType() {
		return type;
	}

	public boolean isActive() {
		return active;
	}
}