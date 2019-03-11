package tk.witkhid.ulog.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.witkhid.ulog.domain.SearchLog;

import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FIrestoreRepositoryTest {

    @Autowired
    private FIrestoreRepository fIrestoreRepository;

    @Test
    public void saveSearchLog() throws ExecutionException, InterruptedException {
        SearchLog searchLog = SearchLog.builder().userId(1L).Category("Mu").region("서울").build();
        String result = fIrestoreRepository.saveSearchLog(searchLog);

        Assert.assertNotNull(result);
        }
    }
