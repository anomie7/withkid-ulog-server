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
    private String category;
    private String region;
    private String timestamp;

    public void setStorableLog(Long userId, String timestamp) {
        this.userId = userId;
        this.timestamp = timestamp;
    }
}
