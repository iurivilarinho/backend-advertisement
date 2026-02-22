package com.br.ads.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "tbSocialLink")
@Schema(description = "Entidade que representa um link de rede social associado a um customere.")
public class SocialLink {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Identificador do link.", example = "10")
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_Id_Customer", nullable = false, foreignKey = @ForeignKey(name = "FK_FROM_tbCustomer_FOR_tbSocialLink"))
	@Schema(description = "Customere ao qual o link pertence.")
	private Customer customer;

	@Column(nullable = false, length = 500)
	@Schema(description = "URL da rede social.", example = "https://instagram.com/minhaempresa")
	private String url;

	@Column(nullable = true, length = 100)
	@Schema(description = "Nome/label opcional do link.", example = "Instagram")
	private String label;

	protected SocialLink() {
	}

	public SocialLink(Customer customer, String url, String label) {
		this.customer = customer;
		this.url = url;
		this.label = label;
	}

	public Long getId() {
		return id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public String getUrl() {
		return url;
	}

	public String getLabel() {
		return label;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}