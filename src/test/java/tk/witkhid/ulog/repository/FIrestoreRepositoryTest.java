package tk.witkhid.ulog.repository;

import com.google.cloud.firestore.DocumentReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FIrestoreRepositoryTest {

    @Autowired
    private FIrestoreRepository fIrestoreRepository;

    @Test
    public void saveSearchLog() {
        fIrestoreRepository.saveSearchLog();
    }
}