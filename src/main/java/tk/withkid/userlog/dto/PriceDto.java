package tk.withkid.userlog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class PriceDto {
	private Long id;
	private String name;
	private int price;
	private boolean defaultPrice;
	private String ticketInfo;
	private String extraInfo;
}
