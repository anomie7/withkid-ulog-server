package tk.withkid.userlog.domain;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class SearchLog extends Log{
    private String kindOf;
    private String region;
    private String startDate;
    private String endDate;

    @Override
    public void setStorableLog(Long userId, LocalDateTime now) {
        if(this.region == null) {
            this.region = "전체";
        }

        if(this.kindOf == null) {
            this.kindOf = "전체";
        }

        super.setStorableLog(userId, now);
    }
}
