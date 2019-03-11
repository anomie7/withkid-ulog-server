package tk.witkhid.ulog.repository;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.*;

@Slf4j
public class FIrestoreRepository {
    private Firestore fIrestore;

    public FIrestoreRepository(Firestore fIrestore) {
        this.fIrestore = fIrestore;
    }

    public void saveSearchLog(){

    }
}
