package tk.withkid.userlog.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor @EqualsAndHashCode
@AllArgsConstructor @Getter
public class Quration {
    private String title;
    private List<EventDto> events;

}
