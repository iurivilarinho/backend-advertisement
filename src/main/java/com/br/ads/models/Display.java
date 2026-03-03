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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import request.DisplayRequest;

@Schema(description = "Display (telão) cadastrado para exibição de anúncios.")
@Entity
@Table(name = "tbDisplay")
public class Display {

	@Schema(description = "Identificador do display.", example = "1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Schema(description = "Nome do display.", example = "Telão - Loja Centro - Entrada")
	@Column(nullable = false, length = 200)
	private String name;

	@Schema(description = "Código interno/identificador do display.", example = "DL-000123")
	@Column(nullable = true, length = 60, unique = true)
	private String code;

	@Schema(description = "Descrição do display.", example = "Telão da vitrine esquerda")
	@Column(nullable = true, length = 255)
	private String description;

	@Schema(description = "Largura da resolução em pixels.", example = "1920")
	@Column(nullable = true)
	private Integer resolutionWidth;

	@Schema(description = "Altura da resolução em pixels.", example = "1080")
	@Column(nullable = true)
	private Integer resolutionHeight;

	@Schema(description = "Fuso horário do display (IANA).", example = "America/Sao_Paulo")
	@Column(nullable = true, length = 60)
	private String timezone;

	@Schema(description = "Indica se o display está ativo (true) ou inativo (false).", example = "true")
	@Column(nullable = false)
	private Boolean active;

	@Schema(description = "Cliente ao qual o display pertence.")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_Id_Customer", nullable = false, foreignKey = @ForeignKey(name = "FK_FROM_TBCUSTOMER_FOR_TBDISPLAY"))
	private Customer customer;

	protected Display() {
	}

	public Display(DisplayRequest request, Customer customer) {
		this.customer = customer;
		applyCreate(request);
		this.active = Boolean.TRUE;
	}

	public void applyUpdate(DisplayRequest request) {
		this.name = request.getName();
		this.code = request.getCode();
		this.description = request.getDescription();
		this.resolutionWidth = request.getResolutionWidth();
		this.resolutionHeight = request.getResolutionHeight();
		this.timezone = request.getTimezone();
	}

	private void applyCreate(DisplayRequest request) {
		this.name = request.getName();
		this.code = request.getCode();
		this.description = request.getDescription();
		this.resolutionWidth = request.getResolutionWidth();
		this.resolutionHeight = request.getResolutionHeight();
		this.timezone = request.getTimezone();
	}

	@PrePersist
	private void prePersist() {
		if (this.active == null) {
			this.active = Boolean.TRUE;
		}
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getResolutionWidth() {
		return resolutionWidth;
	}

	public void setResolutionWidth(Integer resolutionWidth) {
		this.resolutionWidth = resolutionWidth;
	}

	public Integer getResolutionHeight() {
		return resolutionHeight;
	}

	public void setResolutionHeight(Integer resolutionHeight) {
		this.resolutionHeight = resolutionHeight;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}