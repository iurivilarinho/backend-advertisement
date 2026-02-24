// src/main/java/com/cantodeminas/ads/service/CustomerService.java
package com.br.ads.services;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.ads.models.Customer;
import com.br.ads.models.SocialLink;
import com.br.ads.repo.AdvertisementRepository;
import com.br.ads.repo.CustomerRepository;
import com.br.ads.repo.SocialLinkRepository;
import com.br.ads.specification.CustomerSpecification;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import request.CustomerRequest;
import request.SocialLinkRequest;
import response.CustomerIndicatorsResponse;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final SocialLinkRepository socialLinkRepository;
	private final AdvertisementRepository advertisementRepository;
	private final EntityManager entityManager;

	public CustomerService(CustomerRepository customerRepository, SocialLinkRepository socialLinkRepository,
			AdvertisementRepository advertisementRepository, EntityManager entityManager) {
		this.customerRepository = customerRepository;
		this.socialLinkRepository = socialLinkRepository;
		this.advertisementRepository = advertisementRepository;
		this.entityManager = entityManager;
	}

	@Transactional
	public Customer create(CustomerRequest request) {
		Customer customer = new Customer(request.getName(), request.getPhone());
		return customerRepository.save(customer);
	}

	@Transactional(readOnly = true)
	public Customer findById(Long id) {
		return customerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Customere não encontrado: id=" + id));
	}

	@Transactional(readOnly = true)
	public Page<Customer> findAll(String search, Pageable page) {
		return customerRepository.findAll(CustomerSpecification.searchAllFields(search, entityManager), page);

	}

	@Transactional
	public Customer update(Long id, CustomerRequest request) {
		Customer customer = findById(id);
		customer.setName(request.getName());
		customer.setPhone(request.getPhone());
		return customerRepository.save(customer);
	}

	@Transactional
	public void enableDisable(Long id, Boolean status) {
		Customer customer = findById(id);
		customer.setActive(status);
		customerRepository.save(customer);
	}

	@Transactional
	public SocialLink addSocialLink(Long customerId, SocialLinkRequest request) {
		Customer customer = findById(customerId);
		SocialLink link = new SocialLink(customer, request.getUrl(), request.getLabel());
		customer.getSocialLinks().add(link);
		return socialLinkRepository.save(link);
	}

	@Transactional
	public void removeSocialLink(Long customerId, Long linkId) {
		findById(customerId);
		SocialLink link = socialLinkRepository.findById(linkId)
				.orElseThrow(() -> new EntityNotFoundException("Link de rede social não encontrado: id=" + linkId));
		if (!link.getCustomer().getId().equals(customerId)) {
			throw new EntityNotFoundException("Link não pertence ao customere informado.");
		}
		socialLinkRepository.delete(link);
	}

	@Transactional(readOnly = true)
	public CustomerIndicatorsResponse getIndicators(Long customerId) {
		findById(customerId);

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