package com.br.ads.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.br.ads.models.Display;

@Repository
public interface DisplayRepository extends JpaRepository<Display, Long>, JpaSpecificationExecutor<Display> {
}