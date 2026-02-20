// src/main/java/com/cantodeminas/ads/api/dto/response/ClientResponse.java
package response;

import java.util.List;

import com.br.ads.models.Customer;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta de cliente.")
public class CustomerResponse {

	@Schema(description = "Identificador do cliente.", example = "1")
	private Long id;

	@Schema(description = "Nome do cliente.", example = "Padaria Exemplo LTDA")
	private String name;

	@Schema(description = "Telefone do cliente.", example = "+55 31 99999-9999")
	private String phone;

	@Schema(description = "Links de redes sociais cadastrados para o cliente.")
	private List<SocialLinkResponse> socialLinks;

	public CustomerResponse() {
	}

	public CustomerResponse(Customer customer) {
		this.id = customer.getId();
		this.name = customer.getName();
		this.phone = customer.getPhone();
		this.socialLinks = customer.getSocialLinks() != null
				? customer.getSocialLinks().stream().map(SocialLinkResponse::new).toList()
				: null;
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

	public List<SocialLinkResponse> getSocialLinks() {
		return socialLinks;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setSocialLinks(List<SocialLinkResponse> socialLinks) {
		this.socialLinks = socialLinks;
	}
}