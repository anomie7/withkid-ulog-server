package tk.withkid.userlog.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import tk.withkid.userlog.domain.EventLog;
import tk.withkid.userlog.exception.EventIdNotFoundException;
import tk.withkid.userlog.repository.EventLogRepository;
import tk.withkid.userlog.util.DateTimeUtill;

import java.time.LocalDateTime;
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

    private String accessTkn = "eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNTUyMzkwOTQ3NTE5LCJ0eXBlIjoiYWNjZXNzLXRva2VuIiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VyLWlkIjo1LCJleHAiOjE1ODQwMTY5NDcsImVtYWlsIjoiZGVwcm9tZWV0QHRyYWN0NC5jb20ifQ.J2DqpOYDEXfM-d49X2-G3qSdROZLKWmEgUxQOZMXp6w";

    @Before
    public void provisioning(){
        eventLogService = new EventLogService(eventLogRepository, authService, resourceService);
    }

    @Test
    @Ignore
    public void saveEventLog() throws ExecutionException, InterruptedException {
        EventLog eventLog = EventLog.builder().eventId(1L).build();
        eventLog.setStorableLog(1L, DateTimeUtill.nowOfUTC());
        given(this.authService.getUserId(accessTkn)).willReturn(5L);
        given(this.eventLogRepository.saveEventLog(eventLogService.getDocId(LocalDateTime.now()), eventLog)).willReturn("2019-03-18T12:27:19.206629000Z");

        String result = eventLogService.saveEventLog(accessTkn, eventLog);
        Assert.assertNotNull(result);
    }

    @Test
    public void getRecentEventIds() throws ExecutionException, InterruptedException, EventIdNotFoundException {
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