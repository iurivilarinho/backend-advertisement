// src/main/java/com/cantodeminas/ads/api/dto/request/AdvertisementRequest.java
package request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.br.ads.enums.AdvertisementType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO de requisição para cadastro/atualização de anúncio.")
public class AdvertisementRequest {

	@NotNull(message = "O identificador do cliente associado é obrigatório.")
	@Schema(description = "Identificador do cliente associado.", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
	private Long customerId;

	@NotBlank(message = "O nome do anúncio é obrigatório.")
	@Size(max = 200, message = "O nome do anúncio deve ter no máximo 200 caracteres.")
	@Schema(description = "Nome do anúncio (referência interna).", example = "Promoção de Queijos - Fevereiro", requiredMode = Schema.RequiredMode.REQUIRED)
	private String name;

	@NotNull(message = "O tipo do anúncio é obrigatório.")
	@Schema(description = "Tipo do anúncio.", example = "IMAGE", requiredMode = Schema.RequiredMode.REQUIRED)
	private AdvertisementType type;

	@Schema(description = "Indica se o anúncio está vigente (ativo).", example = "true") 
	private Boolean active;

	@Valid
	@Schema(description = "Reccorencia de execução do anuncio.")
	private RecurrenceRequest recurrence;

	@Schema(description = "Se verdadeiro, exibe redes sociais ao final do anúncio.", example = "true")
	private Boolean showSocialAtEnd;

	@Valid
	@Schema(description = "Lista de imagens do anúncio (sempre lista, mesmo para imagem única). Obrigatório quando tipo=IMAGE.")
	private List<AdvertisementImageRequest> images;

	@Size(max = 1000, message = "A URL do vídeo deve ter no máximo 1000 caracteres.")
	@Schema(description = "URL do vídeo. Obrigatório quando tipo=VIDEO.", example = "https://cdn.exemplo.com/video.mp4")
	private String videoUrl;

	@Schema(description = "Duração do vídeo em segundos (opcional; se informado será validado/armazenado).", example = "30")
	private Integer videoDurationSeconds;

	private MultipartFile video;

	public AdvertisementRequest() {
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

	public Boolean getActive() {
		return active;
	}

	public RecurrenceRequest getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(RecurrenceRequest recurrence) {
		this.recurrence = recurrence;
	}

	public Boolean getShowSocialAtEnd() {
		return showSocialAtEnd;
	}

	public void setShowSocialAtEnd(Boolean showSocialAtEnd) {
		this.showSocialAtEnd = showSocialAtEnd;
	}

	public List<AdvertisementImageRequest> getImages() {
		return images;
	}

	public void setImages(List<AdvertisementImageRequest> images) {
		this.images = images;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Integer getVideoDurationSeconds() {
		return videoDurationSeconds;
	}

	public void setVideoDurationSeconds(Integer videoDurationSeconds) {
		this.videoDurationSeconds = videoDurationSeconds;
	}

	public MultipartFile getVideo() {
		return video;
	}

	public void setVideo(MultipartFile video) {
		this.video = video;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(AdvertisementType type) {
		this.type = type;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}