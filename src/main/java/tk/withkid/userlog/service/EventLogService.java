package tk.withkid.userlog.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.withkid.userlog.domain.EventLog;
import tk.withkid.userlog.repository.FIrestoreRepository;
import tk.withkid.userlog.util.DateTimeUtill;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class EventLogService {

    private FIrestoreRepository fIrestoreRepository;
    private AuthService authService;

    @Autowired
    public EventLogService(FIrestoreRepository fIrestoreRepository, AuthService authService) {
        this.fIrestoreRepository = fIrestoreRepository;
        this.authService = authService;
    }

    public String saveEventLog(String accessToken, EventLog eventLog) throws ExecutionException, InterruptedException {
        LocalDateTime now = DateTimeUtill.nowOfUTC();
        Long userId = this.authService.getUserId(accessToken);
        eventLog.setStorableLog(userId, now);
        String docId = getDocId(now);
        String updateTIme = fIrestoreRepository.saveEventLog(docId, eventLog);
        log.info("search user log update time: {}", updateTIme);
        return updateTIme;
    }

    public String getDocId(LocalDateTime now){
        String random = String.valueOf(new Random().nextInt(100000));
        String docId = now + "." + random;
        return docId;
    }
}
