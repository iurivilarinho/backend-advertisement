// src/main/java/com/cantodeminas/ads/service/AppConfigurationService.java
package com.br.ads.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.ads.models.AppConfiguration;
import com.br.ads.repo.AppConfigurationRepository;

import jakarta.persistence.EntityNotFoundException;
import request.AppConfigurationRequest;

@Service
public class AppConfigurationService {

	private final AppConfigurationRepository repository;

	public AppConfigurationService(AppConfigurationRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public AppConfiguration upsert(Long id, AppConfigurationRequest request) {
		AppConfiguration cfg;
		if (id == null) {
			cfg = new AppConfiguration(request.isOverlayEnabled(), request.getOverlayIntervalSeconds(),
					request.isContinuousModeEnabled(), request.isAllowParallelWithExternalPlayers(),
					request.getDisplayPriority());
			return repository.save(cfg);
		}

		cfg = repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Configuração não encontrada: id=" + id));
		cfg.setOverlayEnabled(request.isOverlayEnabled());
		cfg.setOverlayIntervalSeconds(request.getOverlayIntervalSeconds());
		cfg.setContinuousModeEnabled(request.isContinuousModeEnabled());
		cfg.setAllowParallelWithExternalPlayers(request.isAllowParallelWithExternalPlayers());
		cfg.setDisplayPriority(request.getDisplayPriority());
		return repository.save(cfg);
	}

	@Transactional(readOnly = true)
	public AppConfiguration getById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Configuração não encontrada: id=" + id));
	}
}