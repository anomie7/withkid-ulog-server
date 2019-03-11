package tk.witkhid.ulog.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import lombok.extern.slf4j.Slf4j;
import tk.witkhid.ulog.domain.SearchLog;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Slf4j
public class FIrestoreRepository {
    private Firestore fIrestore;

    public FIrestoreRepository(Firestore fIrestore) {
        this.fIrestore = fIrestore;
    }

    public String saveSearchLog(SearchLog searchLog) throws ExecutionException, InterruptedException {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        String random = String.valueOf(new Random().nextInt(100000));
        String docId = String.valueOf(now) + "." + random;
        DocumentReference docRef = fIrestore.collection("search-ulog").document(docId);

        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        searchLog.setTimestamp(timestamp);
        ApiFuture<WriteResult> set = docRef.set(searchLog);
        String updateTIme = String.valueOf(set.get().getUpdateTime());
        log.info("search user log update time: {}", updateTIme);
        return updateTIme;
    }
}
