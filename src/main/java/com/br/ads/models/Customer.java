package com.br.ads.models;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbCustomer")
@Schema(description = "Entidade que representa um cliente anunciante.")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Identificador do cliente.", example = "1")
	private Long id;

	@Column(nullable = false, length = 200)
	@Schema(description = "Nome do cliente.", example = "Padaria Exemplo LTDA")
	private String name;

	@Column(nullable = true, length = 30)
	@Schema(description = "Telefone do cliente.", example = "+55 31 99999-9999")
	private String phone;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	@Schema(description = "Lista de anúncios vinculados ao cliente.")
	private List<Advertisement> advertisements = new ArrayList<>();

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	@Schema(description = "Lista de links de redes sociais do cliente.")
	private List<SocialLink> socialLinks = new ArrayList<>();

	protected Customer() {
	}

	public Customer(String name, String phone) {
		this.name = name;
		this.phone = phone;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public List<Advertisement> getAdvertisements() {
		return advertisements;
	}

	public List<SocialLink> getSocialLinks() {
		return socialLinks;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}