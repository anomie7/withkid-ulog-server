package tk.withkid.userlog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractEventDto {
	private Long eventId;
	private String interparkCode;
	private String name;
	private String location;
	private InterparkType kindOf;
	private String imageFilePath;
	private List<PriceDto> price = new ArrayList<>();

}
