package tk.withkid.userlog.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.withkid.userlog.domain.SearchLog;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FIrestoreRepositoryTest {

    @Autowired
    private FIrestoreRepository fIrestoreRepository;

    @Test
    public void saveSearchLog() throws ExecutionException, InterruptedException {
        SearchLog searchLog = SearchLog.builder().userId(1L).kindOf("Mu").region("서울").timestamp("201902120211").build();

        String result = fIrestoreRepository.saveSearchLog("test", searchLog);

        Assert.assertNotNull(result);
        }

    @Test
    public void findRecentSearchLog() throws ExecutionException, InterruptedException {
        Long userId = 12L;
        List<SearchLog> searchLogs = this.fIrestoreRepository.findRecentSearchLog(userId);
        Assert.assertNotNull(searchLogs);
    }
}
