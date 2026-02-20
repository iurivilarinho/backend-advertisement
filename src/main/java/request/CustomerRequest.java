// src/main/java/com/cantodeminas/ads/api/dto/request/ClientRequest.java
package request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO de requisição para cadastro/atualização de cliente.")
public class CustomerRequest {

	@NotBlank
	@Size(max = 200)
	@Schema(description = "Nome do cliente.", example = "Padaria Exemplo LTDA", requiredMode = Schema.RequiredMode.REQUIRED)
	private String name;

	@Size(max = 30)
	@Schema(description = "Telefone do cliente.", example = "+55 31 99999-9999")
	private String phone;

	public CustomerRequest() {
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}