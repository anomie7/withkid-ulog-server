package tk.withkid.userlog.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.withkid.userlog.domain.EventLog;
import tk.withkid.userlog.dto.Quration;
import tk.withkid.userlog.exception.AuthorizationUnavailableException;
import tk.withkid.userlog.repository.EventLogRepository;
import tk.withkid.userlog.util.DateTimeUtill;

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

    public String saveEventLog(String accessToken, EventLog eventLog) throws ExecutionException, InterruptedException, AuthorizationUnavailableException {
        LocalDateTime now = DateTimeUtill.nowOfUTC();
        Long userId = this.authService.getUserId(accessToken);
        eventLog.setStorableLog(userId, now);
        String docId = getDocId(now);
        String updateTIme = eventLogRepository.saveEventLog(docId, eventLog);
        log.info("search user log update time: {}", updateTIme);
        return updateTIme;
    }

    public Quration getEvents(String accessToken) throws AuthorizationUnavailableException {
        Quration events = null;
        List<Long> eventIds = null;
        try {
            eventIds = this.getRecentEventIds(accessToken);
            events = resourceService.getEventsOf(eventIds);
        } catch (ExecutionException e) {
            log.error(e.getMessage());
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        return events;
    }

    public List<Long> getRecentEventIds(String accessToken) throws ExecutionException, InterruptedException, AuthorizationUnavailableException {
        List<Long> eventIds = null;
        Long userId = this.authService.getUserId(accessToken);
        List<EventLog> recentEventLog = this.eventLogRepository.findRecentEventLog(userId);
        eventIds = recentEventLog.stream().map(EventLog::getEventId).distinct().collect(Collectors.toList());
        return eventIds;
    }

    public String getDocId(LocalDateTime now){
        String random = String.valueOf(new Random().nextInt(100000));
        String docId = now + "." + random;
        return docId;
    }
}
