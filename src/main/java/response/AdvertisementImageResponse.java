// src/main/java/com/cantodeminas/ads/api/dto/response/AdvertisementImageResponse.java
package response;

import com.br.ads.models.AdvertisementImage;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta de uma imagem do anúncio.")
public class AdvertisementImageResponse {

	@Schema(description = "Identificador da imagem.", example = "1000")
	private Long id;

	@Schema(description = "URL/arquivo da imagem.", example = "https://cdn.exemplo.com/img1.jpg")
	private String imageUrl;

	@Schema(description = "Tempo de exibição desta imagem, em segundos.", example = "5")
	private int displaySeconds;

	@Schema(description = "Ordem da imagem no carrossel.", example = "0")
	private int orderIndex;

	public AdvertisementImageResponse() {
	}

	public AdvertisementImageResponse(AdvertisementImage advertisementImage) {
		this.id = advertisementImage.getId();
		this.imageUrl = advertisementImage.getImageUrl();
		this.displaySeconds = advertisementImage.getDisplaySeconds();
		this.orderIndex = advertisementImage.getOrderIndex();
	}

	public Long getId() {
		return id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public int getDisplaySeconds() {
		return displaySeconds;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setDisplaySeconds(int displaySeconds) {
		this.displaySeconds = displaySeconds;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}
}