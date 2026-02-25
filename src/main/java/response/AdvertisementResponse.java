// src/main/java/com/cantodeminas/ads/api/dto/response/AdvertisementResponse.java
package response;

import java.util.List;

import com.br.ads.enums.AdvertisementType;
import com.br.ads.models.Advertisement;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta de anúncio.")
public class AdvertisementResponse {

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

	@Schema(description = "Recorrência vinculada ao anuncio.", example = "true")
	private RecurrenceResponse recurrence;

	@Schema(description = "Se verdadeiro, exibe redes sociais ao final do anúncio.", example = "true")
	private boolean showSocialAtEnd;

	@Schema(description = "Lista de imagens (sempre lista para tipo IMAGE).")
	private List<AdvertisementImageResponse> images;

	@Schema(description = "URL do vídeo (quando tipo=VIDEO).", example = "https://cdn.exemplo.com/video.mp4")
	private String videoUrl;

	@Schema(description = "Duração do vídeo em segundos.", example = "30")
	private Integer videoDurationSeconds;

	public AdvertisementResponse() {
	}

	public AdvertisementResponse(Advertisement advertisement) {
		this.id = advertisement.getId();
		this.customerId = advertisement.getCustomer().getId();
		this.name = advertisement.getName();
		this.type = advertisement.getType();
		this.active = advertisement.getActive();
		this.recurrence = advertisement.getRecurrence() != null ? new RecurrenceResponse(advertisement.getRecurrence())
				: null;
		this.showSocialAtEnd = advertisement.getShowSocialAtEnd();
		this.images = advertisement.getImages() != null
				? advertisement.getImages().stream().map(AdvertisementImageResponse::new).toList()
				: null;
		this.videoUrl = advertisement.getVideoUrl();
		this.videoDurationSeconds = advertisement.getVideoDurationSeconds();
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

	public RecurrenceResponse getRecurrence() {
		return recurrence;
	}

	public boolean isShowSocialAtEnd() {
		return showSocialAtEnd;
	}

	public List<AdvertisementImageResponse> getImages() {
		return images;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public Integer getVideoDurationSeconds() {
		return videoDurationSeconds;
	}

}