package com.br.ads.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import request.AdvertisementImageRequest;

@Entity
@Table(name = "tbAdvertisementImage")
@Schema(description = "Entidade que representa uma imagem de um anúncio (carrossel).")
public class AdvertisementImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Identificador da imagem.", example = "1000")
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_Id_Advertisement", nullable = false, foreignKey = @ForeignKey(name = "FK_FROM_tbAdvertisement_FOR_tbAdvertisementImage"))
	@Schema(description = "Anúncio ao qual a imagem pertence.")
	private Advertisement advertisement;

	@Column(nullable = false, length = 1000)
	@Schema(description = "URL/arquivo da imagem.", example = "https://cdn.exemplo.com/img1.jpg")
	private String imageUrl;

	@Column(nullable = false)
	@Schema(description = "Tempo de exibição desta imagem, em segundos.", example = "5")
	private int displaySeconds;

	@Column(nullable = false)
	@Schema(description = "Ordem da imagem no carrossel.", example = "0")
	private int orderIndex;

	protected AdvertisementImage() {
	}

	public AdvertisementImage(Advertisement advertisement, AdvertisementImageRequest payload) {
		this.advertisement = advertisement;
		this.imageUrl = payload.getImageUrl();
		this.displaySeconds = payload.getDisplaySeconds();
		this.orderIndex = payload.getOrderIndex();
	}

	public Long getId() {
		return id;
	}

	public Advertisement getAdvertisement() {
		return advertisement;
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