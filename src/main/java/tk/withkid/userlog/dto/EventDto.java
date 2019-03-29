package tk.withkid.userlog.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@JsonTypeName("EventDto")
@Getter
@NoArgsConstructor
public class EventDto extends AbstractEventDto {
	private LocalDate startDate;
	private LocalDate endDate;

	@Builder
	public EventDto(Long eventId, String interparkCode,String name, String location, InterparkType kindOf, String imageFilePath,
			List<PriceDto> price, LocalDate startDate, LocalDate endDate) {
		super(eventId, interparkCode,name, location, kindOf, imageFilePath, price);
		this.startDate = startDate;
		this.endDate = endDate;
	}



}
