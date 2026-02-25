package com.br.ads.models;

import java.util.ArrayList;
import java.util.List;

import com.br.ads.enums.AdvertisementType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import jakarta.persistence.OneToOne;
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

	@Schema(description = "Recorrência vinculada ao anuncio.", example = "true")
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "fk_Id_Recurrence")
	private Recurrence recurrence;

	@Column(nullable = false)
	@Schema(description = "Se verdadeiro, exibe redes sociais ao final do anúncio antes de seguir para o próximo.", example = "true")
	private Boolean showSocialAtEnd;

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
		this.recurrence = payload.getRecurrence() != null ? new Recurrence(payload.getRecurrence()) : null;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Recurrence getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(Recurrence recurrence) {
		this.recurrence = recurrence;
	}

	public Boolean getShowSocialAtEnd() {
		return showSocialAtEnd;
	}

	public void setShowSocialAtEnd(Boolean showSocialAtEnd) {
		this.showSocialAtEnd = showSocialAtEnd;
	}

	public List<AdvertisementImage> getImages() {
		return images;
	}

	public void setImages(List<AdvertisementImage> images) {
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

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(AdvertisementType type) {
		this.type = type;
	}

}