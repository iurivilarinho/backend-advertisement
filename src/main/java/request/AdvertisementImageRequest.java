package request;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO de requisição para uma imagem do anúncio.")
public class AdvertisementImageRequest {

	
	@Size(max = 1000)
	@Schema(description = "URL/arquivo da imagem.", example = "https://cdn.exemplo.com/img1.jpg", requiredMode = Schema.RequiredMode.REQUIRED)
	private String imageUrl;

	@Min(1)
	@Schema(description = "Tempo de exibição desta imagem, em segundos.", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
	private int displaySeconds;

	@Min(0)
	@Schema(description = "Ordem da imagem no carrossel.", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
	private int orderIndex;

	private MultipartFile image;

	public AdvertisementImageRequest() {
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public int getDisplaySeconds() {
		return displaySeconds;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setDisplaySeconds(int displaySeconds) {
		this.displaySeconds = displaySeconds;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

}