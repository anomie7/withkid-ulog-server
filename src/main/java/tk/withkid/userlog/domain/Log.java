package tk.withkid.userlog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Log {
    private Long userId;
    private String timestamp;

    public void setStorableLog(Long userId, LocalDateTime now){
        this.setUserId(userId);
        this.convertNowToTimestamp(now);
    }

    private void convertNowToTimestamp(LocalDateTime now){
        this.timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
