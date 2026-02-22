package com.br.ads.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.br.ads.enums.AdvertisementType;
import com.br.ads.models.Advertisement;
import com.br.ads.models.AdvertisementImage;
import com.br.ads.models.Customer;
import com.br.ads.repo.AdvertisementRepository;
import com.br.ads.repo.CustomerRepository;
import com.br.ads.specification.AdvertisementSpecification;

import jakarta.persistence.EntityNotFoundException;
import request.AdvertisementImageRequest;
import request.AdvertisementRequest;

@Service
public class AdvertisementService {

	private final AdvertisementRepository advertisementRepository;
	private final CustomerRepository customerRepository;
	@Value("${ads.media.base-path}")
	private String mediaBasePath;

	public AdvertisementService(AdvertisementRepository advertisementRepository,
			CustomerRepository customerRepository) {
		this.advertisementRepository = advertisementRepository;
		this.customerRepository = customerRepository;
	}

	@Transactional
	public Advertisement create(AdvertisementRequest request) {
		Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(
				() -> new EntityNotFoundException("Cliente não encontrado: id=" + request.getCustomerId()));

		Advertisement ad = new Advertisement(customer, request);

		// 1) salva para gerar ID
		ad = advertisementRepository.save(ad);

		// 2) aplica mídia (pode gravar em disco usando ad.getId())
		applyMedia(ad, request);

		// 3) salva refs (videoUrl/imageUrl)
		return advertisementRepository.save(ad);
	}

	@Transactional(readOnly = true)
	public Advertisement getById(Long id) {
		return advertisementRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Anúncio não encontrado: id=" + id));
	}

	@Transactional
	public Advertisement update(Long id, AdvertisementRequest request) {
		Advertisement ad = getById(id);

		if (!ad.getCustomer().getId().equals(request.getCustomerId())) {
			Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(
					() -> new EntityNotFoundException("Customere não encontrado: id=" + request.getCustomerId()));
			ad.setCustomer(customer);

		}

		ad.setName(request.getName());
		ad.setType(request.getType());
		ad.setActive(request.getActive());
		ad.setValidFrom(request.getValidFrom());
		ad.setValidTo(request.getValidTo());
		ad.setMaxShowsPerDay(request.getMaxShowsPerDay());
		ad.setAllowedDays(request.getAllowedDays());
		ad.setShowSocialAtEnd(request.getShowSocialAtEnd());

		// limpa mídia anterior e aplica nova
		ad.getImages().clear();
		ad.setVideoUrl(null);
		ad.setVideoDurationSeconds(null);

		applyMedia(ad, request);

		return advertisementRepository.save(ad);
	}

	@Transactional
	public void delete(Long id) {
		Advertisement ad = getById(id);
		advertisementRepository.delete(ad);
	}

	@Transactional(readOnly = true)
	public List<Advertisement> listByCustomer(Long customerId) {
		return advertisementRepository.findByCustomerId(customerId);
	}

	@Transactional(readOnly = true)
	public List<Advertisement> listActiveForPlayback(LocalDate date) {
		return advertisementRepository.findAll(AdvertisementSpecification.allowedOnDay(date.getDayOfWeek())
				.and(AdvertisementSpecification.isValid(date)));
	}

	private void applyMedia(Advertisement ad, AdvertisementRequest request) {
		if (request.getType() == AdvertisementType.IMAGE) {

			if (request.getImages() == null || request.getImages().isEmpty()) {
				throw new IllegalArgumentException("Para tipo IMAGE, a lista de imagens é obrigatória.");
			}

			for (int i = 0; i < request.getImages().size(); i++) {
				AdvertisementImageRequest imgReq = request.getImages().get(i);

				boolean hasFile = imgReq.getImage() != null && !imgReq.getImage().isEmpty();
				boolean hasUrl = imgReq.getImageUrl() != null && !imgReq.getImageUrl().isBlank();

				if (!hasFile && !hasUrl) {
					throw new IllegalArgumentException("images[" + i + "]: informe 'image' (arquivo) ou 'imageUrl'.");
				}
				if (hasFile && hasUrl) {
					throw new IllegalArgumentException("images[" + i + "]: informe apenas um: 'image' OU 'imageUrl'.");
				}

				// Se veio arquivo, salva e substitui imageUrl por referência local
				if (hasFile) {
					String storedRef = saveImageFile(ad.getId(), imgReq.getOrderIndex(), imgReq.getImage());
					imgReq.setImageUrl(storedRef); // mantém seu model: AdvertisementImage lê do request
				}

				AdvertisementImage img = new AdvertisementImage(ad, imgReq);
				ad.getImages().add(img);
			}

			ad.getImages().sort(Comparator.comparingInt(AdvertisementImage::getOrderIndex));
			return;
		}

		if (request.getType() == AdvertisementType.VIDEO) {

			boolean hasFile = request.getVideo() != null && !request.getVideo().isEmpty();
			boolean hasUrl = request.getVideoUrl() != null && !request.getVideoUrl().isBlank();

			if (!hasFile && !hasUrl) {
				throw new IllegalArgumentException("Para tipo VIDEO, informe 'video' (arquivo) ou 'videoUrl'.");
			}
			if (hasFile && hasUrl) {
				throw new IllegalArgumentException("Para tipo VIDEO, informe apenas um: 'video' OU 'videoUrl'.");
			}

			if (hasFile) {
				String storedRef = saveVideoFile(ad.getId(), request.getVideo());
				ad.setVideoUrl(storedRef); // referência local
			} else {
				ad.setVideoUrl(request.getVideoUrl().trim());
			}

			ad.setVideoDurationSeconds(request.getVideoDurationSeconds());
			return;
		}

		throw new IllegalArgumentException("Tipo de anúncio inválido.");
	}

	private String saveVideoFile(Long adId, MultipartFile video) {
		Path base = Path.of(requiredNonBlank(mediaBasePath, "ads.media.base-path"));
		String filename = safeFilename(video.getOriginalFilename(), "video.mp4");

		Path adDir = base.resolve(String.valueOf(adId));
		Path target = adDir.resolve(filename);

		try {
			Files.createDirectories(adDir);
			Files.copy(video.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
			return filename; // referência no banco
		} catch (IOException e) {
			throw new IllegalStateException("Falha ao salvar vídeo em disco: " + target, e);
		}
	}

	private String saveImageFile(Long adId, int orderIndex, MultipartFile image) {
		Path base = Path.of(requiredNonBlank(mediaBasePath, "ads.media.base-path"));
		String filename = safeFilename(image.getOriginalFilename(), "image");

		Path imagesDir = base.resolve(String.valueOf(adId)).resolve("images");
		String storedName = orderIndex + "-" + filename;
		Path target = imagesDir.resolve(storedName);

		try {
			Files.createDirectories(imagesDir);
			Files.copy(image.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
			return "images/" + storedName; // referência no banco
		} catch (IOException e) {
			throw new IllegalStateException("Falha ao salvar imagem em disco: " + target, e);
		}
	}

	private String safeFilename(String original, String fallback) {
		String name = (original == null || original.isBlank()) ? fallback : original.trim();
		name = name.replaceAll("[\\r\\n\\t]", "_");
		name = name.replace("..", "_");
		name = name.replace("/", "_").replace("\\", "_");
		return name.isBlank() ? fallback : name;
	}

	private String requiredNonBlank(String value, String propertyName) {
		if (value == null || value.isBlank()) {
			throw new IllegalStateException("Propriedade obrigatória não configurada: " + propertyName);
		}
		return value.trim();
	}

}