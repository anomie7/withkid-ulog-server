package tk.withkid.userlog.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import tk.withkid.userlog.domain.EventLog;
import tk.withkid.userlog.domain.SearchLog;
import tk.withkid.userlog.repository.FIrestoreRepository;
import tk.withkid.userlog.util.DateTimeUtill;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchLogServiceTest {
    private SearchLogService searchLogService;
    @MockBean
    private AuthService authService;

    @MockBean
    private FIrestoreRepository fIrestoreRepository;

    private String accessTkn = "eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNTUyMzkwOTQ3NTE5LCJ0eXBlIjoiYWNjZXNzLXRva2VuIiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VyLWlkIjo1LCJleHAiOjE1ODQwMTY5NDcsImVtYWlsIjoiZGVwcm9tZWV0QHRyYWN0NC5jb20ifQ.J2DqpOYDEXfM-d49X2-G3qSdROZLKWmEgUxQOZMXp6w";

    @Test
    @Ignore
    public void saveSearchLogTest() throws ExecutionException, InterruptedException {
        SearchLog searchLog = SearchLog.builder().kindOf("Mu").region("서울").build();
        given(this.authService.getUserId(accessTkn)).willReturn(5L);
        given(this.fIrestoreRepository.saveSearchLog(searchLogService.getDocId(LocalDateTime.now()), searchLog)).willReturn("2019-03-18T12:27:19.206629000Z");
        searchLogService = new SearchLogService(fIrestoreRepository, authService);

        String res = searchLogService.saveSearchLog(accessTkn, searchLog);

        assertNotNull(res);
    }

    @Test
    public void getMaxSearchLogKeysTest() throws ExecutionException, InterruptedException {
        SearchLog[] arr = { SearchLog.builder().region("전체").kindOf("전체").build(),
                SearchLog.builder().region("전체").kindOf("전체").build(),
                SearchLog.builder().region("전체").kindOf("전체").build(),
                SearchLog.builder().region("전체").kindOf("전체").build(),
                SearchLog.builder().region("전체").kindOf("전체").build(),
                SearchLog.builder().region("서울").kindOf("Mu").build(),
                SearchLog.builder().region("서울").kindOf("Pl").build(),
                SearchLog.builder().region("대구").kindOf("Pl").build(),
        };

        given(this.authService.getUserId(accessTkn)).willReturn(5L);
        given(this.fIrestoreRepository.findRecentSearchLog(5L)).willReturn(Arrays.asList(arr));
        searchLogService = new SearchLogService(fIrestoreRepository, authService);

        Map<String, String> maxKeys = searchLogService.getMaxSearchLogKeys(accessTkn);
        assertEquals("서울" ,maxKeys.get("region"));
        assertEquals("Pl" ,maxKeys.get("kindOf"));
    }

    @Test
    public void getMaxSearchLogKeysWhenNotFoundTest() throws ExecutionException, InterruptedException {
        given(this.authService.getUserId(accessTkn)).willReturn(5L);
        given(this.fIrestoreRepository.findRecentSearchLog(5L)).willReturn(Collections.singletonList(SearchLog.builder().region("전체").kindOf("전체").build()));
        searchLogService = new SearchLogService(fIrestoreRepository, authService);

        Map<String, String> maxKeys = searchLogService.getMaxSearchLogKeys(accessTkn);

        assertEquals("전체" ,maxKeys.get("region"));
        assertEquals("전체" ,maxKeys.get("kindOf"));
    }

    @Test
    public void getMaxSearchLogKeysWhenNullPointExceptionTest() throws ExecutionException, InterruptedException {
        SearchLog[] arr = {
                SearchLog.builder().region("대구").kindOf("Mu").build(),
                SearchLog.builder().region("대구").kindOf("Mu").build(),
                SearchLog.builder().region(null).kindOf("Cl").build(),
                SearchLog.builder().region("서울").kindOf(null).build(),
                SearchLog.builder().region("서울").kindOf("Mu").build(),
                SearchLog.builder().region("서울").kindOf("Mu").build(),
                SearchLog.builder().region("서울").kindOf("Pl").build(),
                SearchLog.builder().region("대구").kindOf("Pl").build(),
        };

        given(this.authService.getUserId(accessTkn)).willReturn(5L);
        given(this.fIrestoreRepository.findRecentSearchLog(5L)).willReturn(Arrays.asList(arr));
        searchLogService = new SearchLogService(fIrestoreRepository, authService);

        Map<String, String> maxKeys = searchLogService.getMaxSearchLogKeys(accessTkn);

        assertEquals("서울" ,maxKeys.get("region"));
        assertEquals("Mu" ,maxKeys.get("kindOf"));
    }
}