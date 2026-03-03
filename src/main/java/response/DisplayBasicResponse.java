// src/main/java/com/cantodeminas/ads/api/dto/response/DisplayBasicResponse.java
package response;

import com.br.ads.models.Display;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta básica de Display (telão).")
public class DisplayBasicResponse {

	@Schema(description = "Identificador do display.", example = "1")
	private Long id;

	@Schema(description = "Identificador do cliente ao qual o display pertence.", example = "1")
	private Long customerId;

	@Schema(description = "Nome do display.", example = "Telão - Loja Centro - Entrada")
	private String name;

	@Schema(description = "Indica se o display está ativo (true) ou inativo (false).", example = "true")
	private Boolean active;

	public DisplayBasicResponse() {
	}

	public DisplayBasicResponse(Display display) {
		this.id = display.getId();
		this.customerId = display.getCustomer() != null ? display.getCustomer().getId() : null;
		this.name = display.getName();
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

	public Boolean getActive() {
		return active;
	}
}