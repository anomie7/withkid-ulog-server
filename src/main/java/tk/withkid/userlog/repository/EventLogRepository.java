package tk.withkid.userlog.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.withkid.userlog.domain.EventLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class EventLogRepository {
    private Firestore firestore;
    private final String userLogCollection = "event-ulog";

    @Autowired

    public EventLogRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public String saveEventLog(String docId, EventLog eventLog) throws ExecutionException, InterruptedException {
        DocumentReference ulogRef = firestore.collection(userLogCollection).document(docId);
        DocumentReference countRef = firestore.collection("event-count").document(String.valueOf(eventLog.getEventId()));
        ApiFuture<Timestamp> transaction = firestore.runTransaction(new Transaction.Function<Timestamp>() {
            @Override
            public Timestamp updateCallback(Transaction transaction) throws Exception {
                DocumentSnapshot snapshot = transaction.get(countRef).get();
                if (snapshot.exists()) {
                    Long oldCount = snapshot.getLong("count");
                    transaction.update(countRef, "count", oldCount + 1);
                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("eventId", eventLog.getEventId());
                    map.put("count", 1L);
                    transaction.set(countRef, map);
                }
                transaction.set(ulogRef, eventLog);
                return snapshot.getUpdateTime();
            }
        });
        return transaction.get().toString();
    }

    public List<EventLog> findRecentEventLog(Long userId) throws ExecutionException, InterruptedException {
        CollectionReference eventUlog = firestore.collection(userLogCollection);
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
