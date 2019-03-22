package tk.withkid.userlog.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import tk.withkid.userlog.domain.EventLog;
import tk.withkid.userlog.domain.SearchLog;
import tk.withkid.userlog.util.DateTimeUtill;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FIrestoreRepository {
    private Firestore fIrestore;
    private final String ulogCollection = "search-ulog";
    private final String eventLogCollection = "event-ulog";

    public FIrestoreRepository(Firestore fIrestore) {
        this.fIrestore = fIrestore;
    }

    public String saveSearchLog(String docId, SearchLog searchLog) throws ExecutionException, InterruptedException {
        DocumentReference docRef = fIrestore.collection(ulogCollection).document(docId);
        ApiFuture<WriteResult> set = docRef.set(searchLog);

        String updateTIme = String.valueOf(set.get().getUpdateTime());
        return updateTIme;
    }

    public String saveEventLog(String docId, EventLog eventLog) throws ExecutionException, InterruptedException {
        DocumentReference docRef = fIrestore.collection(eventLogCollection).document(docId);
        ApiFuture<WriteResult> set = docRef.set(eventLog);

        String updateTIme = String.valueOf(set.get().getUpdateTime());
        return updateTIme;
    }

    public List<SearchLog> findRecentSearchLog(Long userId) throws ExecutionException, InterruptedException {
        CollectionReference ulogRef = fIrestore.collection(ulogCollection);
        Query query = ulogRef.whereEqualTo("userId", userId)
                            .whereGreaterThanOrEqualTo("timestamp", DateTimeUtill.oneWeekAgo())
                            .whereLessThanOrEqualTo("timestamp",DateTimeUtill.tomorrow());
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        List<SearchLog> searchLogs = querySnapshot.get().toObjects(SearchLog.class);
        if(searchLogs.size() == 0) {
            return Collections.singletonList(SearchLog.builder().kindOf("전체").region("전체").build());
        }
        return searchLogs;
    }
}
