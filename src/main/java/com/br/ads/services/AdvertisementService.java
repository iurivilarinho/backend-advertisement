// src/main/java/com/cantodeminas/ads/service/AdvertisementService.java
package com.br.ads.services;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.ads.enums.AdvertisementType;
import com.br.ads.models.Advertisement;
import com.br.ads.models.AdvertisementImage;
import com.br.ads.models.Customer;
import com.br.ads.repo.AdvertisementRepository;
import com.br.ads.repo.CustomerRepository;

import jakarta.persistence.EntityNotFoundException;
import request.AdvertisementImageRequest;
import request.AdvertisementRequest;

@Service
public class AdvertisementService {

	private final AdvertisementRepository advertisementRepository;
	private final CustomerRepository customerRepository;

	public AdvertisementService(AdvertisementRepository advertisementRepository,
			CustomerRepository customerRepository) {
		this.advertisementRepository = advertisementRepository;
		this.customerRepository = customerRepository;
	}

	@Transactional
	public Advertisement create(AdvertisementRequest request) {
		Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(
				() -> new EntityNotFoundException("Customere não encontrado: id=" + request.getCustomerId()));

		Advertisement ad = new Advertisement(customer, request);

		applyMedia(ad, request);

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
		LocalDate target = (date == null) ? LocalDate.now() : date;
		return advertisementRepository.findAll().stream().filter(ad -> ad.isCurrentlyValid(target))
				.filter(ad -> ad.getAllowedDays() != null && ad.getAllowedDays().contains(target.getDayOfWeek()))
				.sorted(Comparator.comparing(Advertisement::getId)).toList();
	}

	private void applyMedia(Advertisement ad, AdvertisementRequest request) {
		if (request.getType() == AdvertisementType.IMAGE) {
			if (request.getImages() == null || request.getImages().isEmpty()) {
				throw new IllegalArgumentException("Para tipo IMAGE, a lista de imagens é obrigatória.");
			}
			for (AdvertisementImageRequest imgReq : request.getImages()) {
				AdvertisementImage img = new AdvertisementImage(ad, imgReq);
				ad.getImages().add(img);
			}
			// garante ordenação consistente
			ad.getImages().sort(Comparator.comparingInt(AdvertisementImage::getOrderIndex));
		} else if (request.getType() == AdvertisementType.VIDEO) {
			if (request.getVideoUrl() == null || request.getVideoUrl().isBlank()) {
				throw new IllegalArgumentException("Para tipo VIDEO, videoUrl é obrigatório.");
			}
			ad.setVideoUrl(request.getVideoUrl());
			ad.setVideoDurationSeconds(request.getVideoDurationSeconds());
		} else {
			throw new IllegalArgumentException("Tipo de anúncio inválido.");
		}
	}

}