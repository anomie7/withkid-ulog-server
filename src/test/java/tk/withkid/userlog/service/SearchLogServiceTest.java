package tk.withkid.userlog.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import tk.withkid.userlog.domain.SearchLog;
import tk.withkid.userlog.exception.AuthorizationUnavailableException;
import tk.withkid.userlog.repository.SearchLogRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchLogServiceTest {
    private SearchLogService searchLogService;
    @MockBean
    private AuthService authService;

    @MockBean
    private SearchLogRepository mockSearchLogRepository;

    private String accessTkn = "eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNTUyMzkwOTQ3NTE5LCJ0eXBlIjoiYWNjZXNzLXRva2VuIiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VyLWlkIjo1LCJleHAiOjE1ODQwMTY5NDcsImVtYWlsIjoiZGVwcm9tZWV0QHRyYWN0NC5jb20ifQ.J2DqpOYDEXfM-d49X2-G3qSdROZLKWmEgUxQOZMXp6w";

    @Before
    public void provisioning(){
        searchLogService = new SearchLogService(mockSearchLogRepository, authService);
    }

    @Test
    public void getMaxSearchLogKeysTest() throws ExecutionException, InterruptedException, AuthorizationUnavailableException {
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
        given(this.mockSearchLogRepository.findRecentSearchLog(5L)).willReturn(Arrays.asList(arr));

        Map<String, String> maxKeys = searchLogService.getMaxSearchLogKeys(accessTkn);
        assertEquals("서울" ,maxKeys.get("region"));
        assertEquals("Pl" ,maxKeys.get("kindOf"));
    }

    @Test
    public void getMaxSearchLogKeysWhenNotFoundTest() throws ExecutionException, InterruptedException, AuthorizationUnavailableException {
        given(this.authService.getUserId(accessTkn)).willReturn(5L);
        given(this.mockSearchLogRepository.findRecentSearchLog(5L)).willReturn(Collections.singletonList(SearchLog.builder().region("전체").kindOf("전체").build()));

        Map<String, String> maxKeys = searchLogService.getMaxSearchLogKeys(accessTkn);

        assertEquals("전체" ,maxKeys.get("region"));
        assertEquals("전체" ,maxKeys.get("kindOf"));
    }

    @Test
    public void getMaxSearchLogKeysWhenNullPointExceptionTest() throws ExecutionException, InterruptedException, AuthorizationUnavailableException {
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
        given(this.mockSearchLogRepository.findRecentSearchLog(5L)).willReturn(Arrays.asList(arr));

        Map<String, String> maxKeys = searchLogService.getMaxSearchLogKeys(accessTkn);

        assertEquals("서울" ,maxKeys.get("region"));
        assertEquals("Mu" ,maxKeys.get("kindOf"));
    }
}