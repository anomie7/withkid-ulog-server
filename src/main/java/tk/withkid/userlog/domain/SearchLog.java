package tk.withkid.userlog.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class SearchLog {
    private Long userId;
    private String kindOf;
    private String region;
    private String startDate;
    private String endDate;
    private String timestamp;

    public void setStorableLog(Long userId, String timestamp) {
        if(this.region == null) {
            this.region = "전체";
        }

        if(this.kindOf == null) {
            this.kindOf = "전체";
        }

        this.userId = userId;
        this.timestamp = timestamp;
    }
}
