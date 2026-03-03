package com.br.ads.specification;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.br.ads.models.Advertisement;

import jakarta.persistence.criteria.Predicate;

public class AdvertisementSpecification {

	public static Specification<Advertisement> customerIdEquals(Long customerId) {
		if (customerId == null) {
			return Specification.unrestricted();
		}
		return (root, query, builder) -> builder.equal(root.get("customer").get("id"), customerId);
	}

	public static Specification<Advertisement> isValid(LocalDate date, Boolean active) {
		if (active == null || Boolean.FALSE.equals(active)) {
			return Specification.unrestricted();
		}
		final LocalDate effectiveDate = (date != null) ? date : LocalDate.now();

		return (root, query, builder) -> {
			Predicate isActive = builder.isTrue(root.get("active"));

			Predicate fromOk = builder.or(builder.isNull(root.get("recurrence").get("startDate")),
					builder.lessThanOrEqualTo(root.get("recurrence").get("startDate"), effectiveDate));

			Predicate toOk = builder.or(builder.isNull(root.get("recurrence").get("endDate")),
					builder.greaterThanOrEqualTo(root.get("recurrence").get("endDate"), effectiveDate));

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
			return builder.isMember(day, root.get("recurrence").get("allowedDays"));
		};
	}

}
