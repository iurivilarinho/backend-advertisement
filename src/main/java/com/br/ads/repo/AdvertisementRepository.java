package com.br.ads.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.br.ads.models.Advertisement;

@Repository
public interface AdvertisementRepository
		extends JpaRepository<Advertisement, Long>, JpaSpecificationExecutor<Advertisement> {
	List<Advertisement> findByCustomerId(Long clientId);
}