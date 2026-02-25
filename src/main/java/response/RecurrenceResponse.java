package response;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Set;

import com.br.ads.models.Recurrence;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta com os dados de recorrência para exibição de anúncios.")
public class RecurrenceResponse {

	@Schema(description = "Identificador único da recorrência.", example = "10")
	private Long id;

	@Schema(description = "Data de início da recorrência.", example = "2026-02-25")
	private LocalDate startDate;

	@Schema(description = "Data de término da recorrência.", example = "2026-03-25")
	private LocalDate endDate;

	@Schema(description = "Dias da semana em que o anúncio pode ser exibido.", example = "[\"MONDAY\",\"WEDNESDAY\",\"FRIDAY\"]")
	private Set<DayOfWeek> allowedDays;

	@Schema(description = "Valor do intervalo da recorrência (ex.: 2 para a cada 2 dias).", example = "2")
	private Integer intervalValue;

	@Schema(description = "Número de vezes por dia que o anúncio será exibido.", example = "5")
	private Integer dailyDisplayCount;

	public RecurrenceResponse(Recurrence recurrence) {
		this.id = recurrence.getId();
		this.startDate = recurrence.getStartDate();
		this.endDate = recurrence.getEndDate();
		this.allowedDays = recurrence.getAllowedDays() == null ? EnumSet.noneOf(DayOfWeek.class)
				: EnumSet.copyOf(recurrence.getAllowedDays());
		this.intervalValue = recurrence.getIntervalValue();
		this.dailyDisplayCount = recurrence.getDailyDisplayCount();
	}

	public Long getId() {
		return id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public Set<DayOfWeek> getAllowedDays() {
		return allowedDays;
	}

	public Integer getIntervalValue() {
		return intervalValue;
	}

	public Integer getDailyDisplayCount() {
		return dailyDisplayCount;
	}
}