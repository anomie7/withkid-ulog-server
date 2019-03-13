package tk.withkid.userlog.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.withkid.userlog.domain.SearchLog;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class SearchLogServiceTest {
    @Autowired
    private SearchLogService searchLogService;
    private String accessTkn = "eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNTUyMzkwOTQ3NTE5LCJ0eXBlIjoiYWNjZXNzLXRva2VuIiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VyLWlkIjo1LCJleHAiOjE1ODQwMTY5NDcsImVtYWlsIjoiZGVwcm9tZWV0QHRyYWN0NC5jb20ifQ.J2DqpOYDEXfM-d49X2-G3qSdROZLKWmEgUxQOZMXp6w";

    @Test
    public void saveSearchLog() throws ExecutionException, InterruptedException {
        SearchLog searchLog = SearchLog.builder().userId(1L).category("Mu").region("서울").build();

        String res = searchLogService.saveSearchLog(accessTkn, searchLog);

        assertNotNull(res);
    }

    @Test
    public void getUserId() {
        Long userId = searchLogService.getUserId(accessTkn);

        assertTrue(userId.equals(5L));
    }
}