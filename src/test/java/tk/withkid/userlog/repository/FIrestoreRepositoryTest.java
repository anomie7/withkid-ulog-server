package tk.withkid.userlog.repository;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.withkid.userlog.domain.SearchLog;

import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class FIrestoreRepositoryTest {

    @Autowired
    private FIrestoreRepository fIrestoreRepository;

    @Test
    public void saveSearchLog() throws ExecutionException, InterruptedException {
        SearchLog searchLog = SearchLog.builder().userId(1L).kindOf("Mu").region("서울").timestamp("201902120211").build();

        String result = fIrestoreRepository.saveSearchLog("test", searchLog);

        Assert.assertNotNull(result);
        }
    }
