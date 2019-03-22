package tk.withkid.userlog.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.withkid.userlog.domain.SearchLog;
import tk.withkid.userlog.util.DateTimeUtill;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class SearchLogRepository {
    private Firestore firestore;
    private final String ulogCollection = "search-ulog";
    private final String eventLogCollection = "event-ulog";

    @Autowired
    public SearchLogRepository(Firestore fIrestore) {
        this.firestore = fIrestore;
    }

    public String saveSearchLog(String docId, SearchLog searchLog) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(ulogCollection).document(docId);
        ApiFuture<WriteResult> set = docRef.set(searchLog);

        String updateTIme = String.valueOf(set.get().getUpdateTime());
        return updateTIme;
    }

    public List<SearchLog> findRecentSearchLog(Long userId) throws ExecutionException, InterruptedException {
        CollectionReference ulogRef = firestore.collection(ulogCollection);
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
