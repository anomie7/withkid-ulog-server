package tk.withkid.userlog.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import tk.withkid.userlog.domain.EventLog;
import tk.withkid.userlog.exception.AuthorizationUnavailableException;
import tk.withkid.userlog.repository.EventLogRepository;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventLogServiceTest {
    private EventLogService eventLogService;
    @MockBean
    private AuthService authService;

    @MockBean
    private ResourceService resourceService;

    @MockBean
    private EventLogRepository eventLogRepository;

    private String accessTkn = "eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNTU5MjMxOTE1NzYxLCJ0eXBlIjoiYWNjZXNzLXRva2VuIiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VyLWlkIjo1LCJleHAiOjE1NTkyMzU1MTUsImVtYWlsIjoiZGVwcm9tZWV0QHRyYWN0NC5jb20ifQ.WwRqgGxtfVKdG0HMBsZN5k_zqPnpWN49eG2o6qYgMUg";

    @Before
    public void provisioning(){
        eventLogService = new EventLogService(eventLogRepository, authService, resourceService);
    }

    @Test
    public void getRecentEventIds() throws ExecutionException, InterruptedException, AuthorizationUnavailableException {
        given(this.authService.getUserId(accessTkn)).willReturn(5L);
        EventLog[] arr = {
                EventLog.builder().eventId(1L).build(),
                EventLog.builder().eventId(2L).build(),
                EventLog.builder().eventId(3L).build(),
                EventLog.builder().eventId(4L).build(),
                EventLog.builder().eventId(4L).build(),
                EventLog.builder().eventId(5L).build(),
                EventLog.builder().eventId(5L).build(),
                EventLog.builder().eventId(7L).build(),
                EventLog.builder().eventId(8L).build(),
        };
        given(eventLogRepository.findRecentEventLog(5L)).willReturn(Arrays.asList(arr));

        List<Long> recentEventLog = this.eventLogService.getRecentEventIds(accessTkn);
        Assert.assertEquals(7, recentEventLog.size());
        Assert.assertNotNull(recentEventLog);
    }

}