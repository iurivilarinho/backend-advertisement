package request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO de requisição para cadastro/atualização de link de rede social.")
public class SocialLinkRequest {

	@NotBlank
	@Size(max = 500)
	@Schema(description = "URL da rede social.", example = "https://instagram.com/minhaempresa", requiredMode = Schema.RequiredMode.REQUIRED)
	private String url;

	@Size(max = 100)
	@Schema(description = "Label opcional do link.", example = "Instagram")
	private String label;

	public SocialLinkRequest() {
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