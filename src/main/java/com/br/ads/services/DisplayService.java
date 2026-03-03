package com.br.ads.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.ads.controller.filters.DisplayFilters;
import com.br.ads.models.Customer;
import com.br.ads.models.Display;
import com.br.ads.repo.DisplayRepository;
import com.br.ads.specification.DisplaySpecification;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import request.DisplayRequest;

@Service
public class DisplayService {

	private final DisplayRepository displayRepository;
	private final CustomerService customerService;
	private final EntityManager entityManager;

	public DisplayService(DisplayRepository displayRepository, CustomerService customerService,
			EntityManager entityManager) {
		this.displayRepository = displayRepository;
		this.customerService = customerService;
		this.entityManager = entityManager;
	}

	@Transactional
	public Display create(DisplayRequest request) {
		Customer customer = customerService.findById(request.getCustomerId());

		Display display = new Display(request, customer);
		return displayRepository.save(display);
	}

	@Transactional
	public Display update(Long id, DisplayRequest request) {

		Display display = findById(id);

		if (request.getCustomerId() != null) {
			Customer customer = customerService.findById(request.getCustomerId());
			display.setCustomer(customer);
		}

		display.applyUpdate(request);
		return displayRepository.save(display);
	}

	@Transactional(readOnly = true)
	public Page<Display> findAll(DisplayFilters filters, Pageable page) {
		return displayRepository.findAll(DisplaySpecification.activeEquals(filters.getActive())
				.and(DisplaySpecification.customerIdEquals(filters.getCustomerId()))
				.and(DisplaySpecification.searchAllFields(filters.getSearch(), entityManager)), page);
	}

	@Transactional(readOnly = true)
	public Display findById(Long id) {
		return displayRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Display não encontrado. id=" + id));
	}

	@Transactional
	public Display enableDisable(Long id, Boolean active) {
		Display display = displayRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Display não encontrado. id=" + id));

		display.setActive(active);
		return displayRepository.save(display);
	}
}