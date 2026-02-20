// src/main/java/com/cantodeminas/ads/api/dto/response/AdvertisementResponse.java
package response;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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

	@Schema(description = "Data de início da vigência.", example = "2026-02-20")
	private LocalDate validFrom;

	@Schema(description = "Data de fim da vigência.", example = "2026-03-20")
	private LocalDate validTo;

	@Schema(description = "Quantidade máxima de exibições por dia.", example = "10")
	private int maxShowsPerDay;

	@Schema(description = "Dias da semana permitidos para exibição.")
	private Set<DayOfWeek> allowedDays;

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
		this.validFrom = advertisement.getValidFrom();
		this.validTo = advertisement.getValidTo();
		this.maxShowsPerDay = advertisement.getMaxShowsPerDay();
		this.allowedDays = advertisement.getAllowedDays();
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

	public Long getClientId() {
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

	public LocalDate getValidFrom() {
		return validFrom;
	}

	public LocalDate getValidTo() {
		return validTo;
	}

	public int getMaxShowsPerDay() {
		return maxShowsPerDay;
	}

	public Set<DayOfWeek> getAllowedDays() {
		return allowedDays;
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

	public void setId(Long id) {
		this.id = id;
	}

	public void setClientId(Long clientId) {
		this.customerId = clientId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(AdvertisementType type) {
		this.type = type;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setValidFrom(LocalDate validFrom) {
		this.validFrom = validFrom;
	}

	public void setValidTo(LocalDate validTo) {
		this.validTo = validTo;
	}

	public void setMaxShowsPerDay(int maxShowsPerDay) {
		this.maxShowsPerDay = maxShowsPerDay;
	}

	public void setAllowedDays(Set<DayOfWeek> allowedDays) {
		this.allowedDays = allowedDays;
	}

	public void setShowSocialAtEnd(boolean showSocialAtEnd) {
		this.showSocialAtEnd = showSocialAtEnd;
	}

	public void setImages(List<AdvertisementImageResponse> images) {
		this.images = images;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public void setVideoDurationSeconds(Integer videoDurationSeconds) {
		this.videoDurationSeconds = videoDurationSeconds;
	}
}