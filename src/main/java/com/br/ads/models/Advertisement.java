package com.br.ads.models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import com.br.ads.enums.AdvertisementType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import request.AdvertisementRequest;

@Entity
@Table(name = "tbAdvertisement")
@Schema(description = "Entidade que representa um anúncio (imagem/carrossel ou vídeo) associado a um customere.")
public class Advertisement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Identificador do anúncio.", example = "100")
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_Id_Customer", nullable = false, foreignKey = @ForeignKey(name = "FK_FROM_tbCustomer_FOR_tbAdvertisement"))
	@Schema(description = "Customere associado ao anúncio.")
	private Customer customer;

	@Column(nullable = false, length = 200)
	@Schema(description = "Nome do anúncio (referência interna).", example = "Promoção de Queijos - Fevereiro")
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	@Schema(description = "Tipo do anúncio.", example = "IMAGE")
	private AdvertisementType type;

	@Column(nullable = false)
	@Schema(description = "Indica se o anúncio está vigente (ativo).", example = "true")
	private Boolean active;

	@Column(nullable = false)
	@Schema(description = "Data de início da vigência.", example = "2026-02-20")
	private LocalDate validFrom;

	@Column(nullable = false)
	@Schema(description = "Data de fim da vigência.", example = "2026-03-20")
	private LocalDate validTo;

	@Column(nullable = false)
	@Schema(description = "Quantidade máxima de exibições por dia.", example = "10")
	private int maxShowsPerDay;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "tbAdvertisementAllowedDay", joinColumns = @JoinColumn(name = "fk_Id_Advertisement", foreignKey = @ForeignKey(name = "FK_FROM_tbAdvertisement_FOR_tbAdvertisementAllowedDay")))
	@Column(name = "allowedDay", nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	@Schema(description = "Dias da semana em que o anúncio pode ser exibido.")
	private Set<DayOfWeek> allowedDays = EnumSet.noneOf(DayOfWeek.class);

	@Column(nullable = false)
	@Schema(description = "Se verdadeiro, exibe redes sociais ao final do anúncio antes de seguir para o próximo.", example = "true")
	private Boolean showSocialAtEnd;

	// IMAGE fields: always a list (even for single image)
	@OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("orderIndex ASC")
	@Schema(description = "Lista de imagens do anúncio (sempre lista, mesmo que tenha apenas uma imagem).")
	private List<AdvertisementImage> images = new ArrayList<>();

	@Column(nullable = true, length = 1000)
	@Schema(description = "URL do vídeo (quando tipo=VIDEO).", example = "https://cdn.exemplo.com/video.mp4")
	private String videoUrl;

	@Column(nullable = true)
	@Schema(description = "Duração do vídeo em segundos (capturada automaticamente).", example = "30")
	private Integer videoDurationSeconds;

	protected Advertisement() {
	}

	public Advertisement(Customer customer, AdvertisementRequest payload) {
		this.customer = customer;
		this.name = payload.getName();
		this.type = payload.getType();
		this.active = payload.getActive();
		this.validFrom = payload.getValidFrom();
		this.validTo = payload.getValidTo();
		this.maxShowsPerDay = payload.getMaxShowsPerDay();
		this.allowedDays = (payload.getAllowedDays() == null) ? EnumSet.noneOf(DayOfWeek.class)
				: EnumSet.copyOf(payload.getAllowedDays());
		this.showSocialAtEnd = payload.getShowSocialAtEnd();
	}

	public Long getId() {
		return id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public String getName() {
		return name;
	}

	public AdvertisementType getType() {
		return type;
	}

	public Boolean isActive() {
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

	public Boolean isShowSocialAtEnd() {
		return showSocialAtEnd;
	}

	public List<AdvertisementImage> getImages() {
		return images;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public Integer getVideoDurationSeconds() {
		return videoDurationSeconds;
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
		this.allowedDays = (allowedDays == null) ? EnumSet.noneOf(DayOfWeek.class) : EnumSet.copyOf(allowedDays);
	}

	public void setShowSocialAtEnd(Boolean showSocialAtEnd) {
		this.showSocialAtEnd = showSocialAtEnd;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public void setVideoDurationSeconds(Integer videoDurationSeconds) {
		this.videoDurationSeconds = videoDurationSeconds;
	}

	public Boolean getActive() {
		return active;
	}

	public Boolean getShowSocialAtEnd() {
		return showSocialAtEnd;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setImages(List<AdvertisementImage> images) {
		this.images = images;
	}

	public Boolean isCurrentlyValid(LocalDate today) {
		if (!active)
			return false;
		if (today == null)
			today = LocalDate.now();
		return (today.isEqual(validFrom) || today.isAfter(validFrom))
				&& (today.isEqual(validTo) || today.isBefore(validTo));
	}
}