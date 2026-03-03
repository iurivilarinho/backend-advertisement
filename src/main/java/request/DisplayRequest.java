// src/main/java/com/cantodeminas/ads/api/dto/request/DisplayRequest.java
package request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de requisição para criação de Display (telão).")
public class DisplayRequest {

	@Schema(description = "Identificador do cliente ao qual o display pertence.", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
	private Long customerId;

	@Schema(description = "Nome do display.", example = "Telão - Loja Centro - Entrada", requiredMode = Schema.RequiredMode.REQUIRED)
	private String name;

	@Schema(description = "Código interno/identificador do display.", example = "DL-000123")
	private String code;

	@Schema(description = "Descrição do display.", example = "Telão da vitrine esquerda")
	private String description;

	@Schema(description = "Largura da resolução em pixels.", example = "1920")
	private Integer resolutionWidth;

	@Schema(description = "Altura da resolução em pixels.", example = "1080")
	private Integer resolutionHeight;

	@Schema(description = "Fuso horário do display (IANA).", example = "America/Sao_Paulo")
	private String timezone;

	public DisplayRequest() {
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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
}