package com.br.ads.specification;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.br.ads.models.Advertisement;

import jakarta.persistence.criteria.Predicate;

public class AdvertisementSpecification {

	public static Specification<Advertisement> isValid(LocalDate date) {
		final LocalDate effectiveDate = (date != null) ? date : LocalDate.now();

		return (root, query, builder) -> {
			Predicate isActive = builder.isTrue(root.get("active"));

			Predicate fromOk = builder.or(builder.isNull(root.get("validFrom")),
					builder.lessThanOrEqualTo(root.get("validFrom"), effectiveDate));

			Predicate toOk = builder.or(builder.isNull(root.get("validTo")),
					builder.greaterThanOrEqualTo(root.get("validTo"), effectiveDate));

			return builder.and(isActive, fromOk, toOk);
		};
	}

	/**
	 * Filtra anúncios cujo campo allowedDays contém o dia informado. Equivalente a:
	 * ad.getAllowedDays() != null && ad.getAllowedDays().contains(day)
	 */
	public static Specification<Advertisement> allowedOnDay(DayOfWeek day) {
		return (root, query, builder) -> {
			if (day == null) {
				return builder.conjunction();
			}
			return builder.isMember(day, root.get("allowedDays"));
		};
	}

}
