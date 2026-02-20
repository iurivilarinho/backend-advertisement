// src/main/java/com/cantodeminas/ads/service/CustomerService.java
package com.br.ads.services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.ads.models.Customer;
import com.br.ads.models.SocialLink;
import com.br.ads.repo.AdvertisementRepository;
import com.br.ads.repo.CustomerRepository;
import com.br.ads.repo.SocialLinkRepository;

import jakarta.persistence.EntityNotFoundException;
import request.CustomerRequest;
import request.SocialLinkRequest;
import response.CustomerIndicatorsResponse;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final SocialLinkRepository socialLinkRepository;
	private final AdvertisementRepository advertisementRepository;

	public CustomerService(CustomerRepository customerRepository, SocialLinkRepository socialLinkRepository,
			AdvertisementRepository advertisementRepository) {
		this.customerRepository = customerRepository;
		this.socialLinkRepository = socialLinkRepository;
		this.advertisementRepository = advertisementRepository;
	}

	@Transactional
	public Customer create(CustomerRequest request) {
		Customer customer = new Customer(request.getName(), request.getPhone());
		return customerRepository.save(customer);
	}

	@Transactional(readOnly = true)
	public Customer getById(Long id) {
		return customerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Customere não encontrado: id=" + id));
	}

	@Transactional
	public Customer update(Long id, CustomerRequest request) {
		Customer customer = getById(id);
		customer.setName(request.getName());
		customer.setPhone(request.getPhone());
		return customerRepository.save(customer);
	}

	@Transactional
	public void delete(Long id) {
		Customer customer = getById(id);
		customerRepository.delete(customer);
	}

	@Transactional
	public SocialLink addSocialLink(Long customerId, SocialLinkRequest request) {
		Customer customer = getById(customerId);
		SocialLink link = new SocialLink(customer, request.getUrl(), request.getLabel());
		customer.getSocialLinks().add(link);
		return socialLinkRepository.save(link);
	}

	@Transactional
	public void removeSocialLink(Long customerId, Long linkId) {
		getById(customerId);
		SocialLink link = socialLinkRepository.findById(linkId)
				.orElseThrow(() -> new EntityNotFoundException("Link de rede social não encontrado: id=" + linkId));
		if (!link.getCustomer().getId().equals(customerId)) {
			throw new EntityNotFoundException("Link não pertence ao customere informado.");
		}
		socialLinkRepository.delete(link);
	}

	@Transactional(readOnly = true)
	public CustomerIndicatorsResponse getIndicators(Long customerId) {
		getById(customerId);

		LocalDate today = LocalDate.now();
		long activeCount = advertisementRepository.findByCustomerId(customerId).stream()
				.filter(ad -> ad.isCurrentlyValid(today)).count();

		// Regra prática para "tempo total": soma de duração do vídeo ou soma do
		// carrossel (somatório das imagens).
		long totalSeconds = advertisementRepository.findByCustomerId(customerId).stream()
				.filter(ad -> ad.isCurrentlyValid(today)).mapToLong(ad -> {
					if (ad.getType().name().equals("VIDEO")) {
						return ad.getVideoDurationSeconds() == null ? 0 : ad.getVideoDurationSeconds();
					}
					return ad.getImages().stream().mapToLong(img -> img.getDisplaySeconds()).sum();
				}).sum();

		return new CustomerIndicatorsResponse(customerId, totalSeconds, activeCount);
	}
}