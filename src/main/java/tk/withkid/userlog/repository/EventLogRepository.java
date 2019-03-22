package tk.withkid.userlog.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.withkid.userlog.domain.EventLog;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class EventLogRepository {
    private Firestore firestore;
    private final String eventLogCollection = "event-ulog";

    @Autowired

    public EventLogRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public String saveEventLog(String docId, EventLog eventLog) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(eventLogCollection).document(docId);
        ApiFuture<WriteResult> set = docRef.set(eventLog);

        String updateTIme = String.valueOf(set.get().getUpdateTime());
        return updateTIme;
    }

    public List<EventLog> findRecentEventLog(Long userId) throws ExecutionException, InterruptedException {
        CollectionReference eventUlog = firestore.collection(eventLogCollection);
        Query query = eventUlog.whereEqualTo("userId", userId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(30);
        ApiFuture<QuerySnapshot> snapshotApiFuture = query.get();
        List<EventLog> eventLogs = snapshotApiFuture.get().toObjects(EventLog.class);
        if(eventLogs.size() == 0){
            throw new NullPointerException();
        }
        return eventLogs;
    }
}
