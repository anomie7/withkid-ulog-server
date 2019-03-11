package tk.withkid.userlog.service;

import com.google.cloud.firestore.DocumentReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.withkid.userlog.domain.SearchLog;
import tk.withkid.userlog.repository.FIrestoreRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class SearchLogService {
    private FIrestoreRepository fIrestoreRepository;

    @Autowired
    public SearchLogService(FIrestoreRepository fIrestoreRepository) {
        this.fIrestoreRepository = fIrestoreRepository;
    }

    public String saveSearchLog(SearchLog searchLog) throws ExecutionException, InterruptedException {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        String random = String.valueOf(new Random().nextInt(100000));
        String docId = now + "." + random;

        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        searchLog.setTimestamp(timestamp);

        String updateTIme = fIrestoreRepository.saveSearchLog(docId, searchLog);
        log.info("search user log update time: {}", updateTIme);
        return updateTIme;
    }
}
