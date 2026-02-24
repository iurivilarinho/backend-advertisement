package request;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;

public class RecurrenceRequest {

	@Schema(description = "Data de início da recorrência.")
	private LocalDate startDate;

	@Schema(description = "Data de término da recorrência.")
	private LocalDate endDate;

	@Schema(description = "Dias da semana em que o anúncio pode ser exibido.")
	private Set<DayOfWeek> allowedDays;

	@Schema(description = "Valor do intervalo da recorrência (ex.: 2 para a cada 2 dias).")
	private Integer intervalValue;

	@Schema(description = "Número de vezes por dia que o anúncio será exibido.")
	private Integer dailyDisplayCount;

	public RecurrenceRequest() {
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Set<DayOfWeek> getAllowedDays() {
		return allowedDays;
	}

	public void setAllowedDays(Set<DayOfWeek> allowedDays) {
		this.allowedDays = allowedDays;
	}

	public Integer getIntervalValue() {
		return intervalValue;
	}

	public void setIntervalValue(Integer intervalValue) {
		this.intervalValue = intervalValue;
	}

	public Integer getDailyDisplayCount() {
		return dailyDisplayCount;
	}

	public void setDailyDisplayCount(Integer dailyDisplayCount) {
		this.dailyDisplayCount = dailyDisplayCount;
	}
}