package tk.withkid.userlog.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import tk.withkid.userlog.domain.SearchLog;

import java.util.concurrent.ExecutionException;

public class FIrestoreRepository {
    private Firestore fIrestore;

    public FIrestoreRepository(Firestore fIrestore) {
        this.fIrestore = fIrestore;
    }

    public String saveSearchLog(String docId, SearchLog searchLog) throws ExecutionException, InterruptedException {
        DocumentReference docRef = fIrestore.collection("search-ulog").document(docId);
        ApiFuture<WriteResult> set = docRef.set(searchLog);

        String updateTIme = String.valueOf(set.get().getUpdateTime());
        return updateTIme;
    }
}
