package tk.withkid.userlog.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.withkid.userlog.domain.SearchLog;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchLogServiceTest {
    @Autowired
    private SearchLogService searchLogService;

    @Test
    public void saveSearchLog() throws ExecutionException, InterruptedException {
        SearchLog searchLog = SearchLog.builder().userId(1L).category("Mu").region("서울").build();

        String res = searchLogService.saveSearchLog(searchLog);

        assertNotNull(res);
    }
}