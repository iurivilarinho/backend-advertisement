// src/main/java/com/cantodeminas/ads/api/dto/response/DisplayResponse.java
package response;

import com.br.ads.models.Display;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta de Display (telão).")
public class DisplayResponse {

	@Schema(description = "Identificador do display.", example = "1")
	private Long id;

	@Schema(description = "Identificador do cliente ao qual o display pertence.", example = "1")
	private Long customerId;

	@Schema(description = "Nome do display.", example = "Telão - Loja Centro - Entrada")
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

	@Schema(description = "Indica se o display está ativo (true) ou inativo (false).", example = "true")
	private Boolean active;

	public DisplayResponse() {
	}

	public DisplayResponse(Display display) {
		this.id = display.getId();
		this.customerId = display.getCustomer() != null ? display.getCustomer().getId() : null;
		this.name = display.getName();
		this.code = display.getCode();
		this.description = display.getDescription();
		this.resolutionWidth = display.getResolutionWidth();
		this.resolutionHeight = display.getResolutionHeight();
		this.timezone = display.getTimezone();
		this.active = display.getActive();
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

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public Integer getResolutionWidth() {
		return resolutionWidth;
	}

	public Integer getResolutionHeight() {
		return resolutionHeight;
	}

	public String getTimezone() {
		return timezone;
	}

	public Boolean getActive() {
		return active;
	}
}