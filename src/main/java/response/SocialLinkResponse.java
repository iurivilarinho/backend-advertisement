package response;

import com.br.ads.models.SocialLink;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta de link de rede social.")
public class SocialLinkResponse {

	@Schema(description = "Identificador do link.", example = "10")
	private Long id;

	@Schema(description = "URL da rede social.", example = "https://instagram.com/minhaempresa")
	private String url;

	@Schema(description = "Label opcional do link.", example = "Instagram")
	private String label;

	public SocialLinkResponse() {
	}

	public SocialLinkResponse(SocialLink socialLink) {
		this.id = socialLink.getId();
		this.url = socialLink.getUrl();
		this.label = socialLink.getLabel();
	}

	public Long getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public String getLabel() {
		return label;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}