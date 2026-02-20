package com.br.ads.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.ads.models.SocialLink;

public interface SocialLinkRepository extends JpaRepository<SocialLink, Long> {
	List<SocialLink> findByClientId(Long clientId);
}