package com.br.ads.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.ads.models.AppConfiguration;

@Repository
public interface AppConfigurationRepository extends JpaRepository<AppConfiguration, Long> {
}