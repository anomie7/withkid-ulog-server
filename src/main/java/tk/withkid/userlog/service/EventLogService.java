package tk.withkid.userlog.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.withkid.userlog.domain.EventLog;
import tk.withkid.userlog.repository.EventLogRepository;
import tk.withkid.userlog.util.DateTimeUtill;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EventLogService {

    private EventLogRepository eventLogRepository;
    private AuthService authService;
    private ResourceService resourceService;

    @Autowired
    public EventLogService(EventLogRepository eventLogRepository, AuthService authService, ResourceService resourceService) {
        this.eventLogRepository = eventLogRepository;
        this.authService = authService;
        this.resourceService = resourceService;
    }

    public String saveEventLog(String accessToken, EventLog eventLog) throws ExecutionException, InterruptedException {
        LocalDateTime now = DateTimeUtill.nowOfUTC();
        Long userId = this.authService.getUserId(accessToken);
        eventLog.setStorableLog(userId, now);
        String docId = getDocId(now);
        String updateTIme = eventLogRepository.saveEventLog(docId, eventLog);
        log.info("search user log update time: {}", updateTIme);
        return updateTIme;
    }

    public List<Long> getRecentEventIds(String accessToken) {
        List<Long> eventIds = null;
        try {
            Long userId = this.authService.getUserId(accessToken);
            List<EventLog> recentEventLog = this.eventLogRepository.findRecentEventLog(userId);
            eventIds = recentEventLog.stream().map(EventLog::getEventId).distinct().collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return eventIds;
    }

    public String getDocId(LocalDateTime now){
        String random = String.valueOf(new Random().nextInt(100000));
        String docId = now + "." + random;
        return docId;
    }
}
