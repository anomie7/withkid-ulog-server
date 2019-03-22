package tk.withkid.userlog.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import tk.withkid.userlog.domain.EventLog;
import tk.withkid.userlog.repository.FIrestoreRepository;
import tk.withkid.userlog.util.DateTimeUtill;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventLogServiceTest {
    private EventLogService eventLogService;
    @MockBean
    private AuthService authService;

    @MockBean
    private FIrestoreRepository fIrestoreRepository;

    private String accessTkn = "eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNTUyMzkwOTQ3NTE5LCJ0eXBlIjoiYWNjZXNzLXRva2VuIiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VyLWlkIjo1LCJleHAiOjE1ODQwMTY5NDcsImVtYWlsIjoiZGVwcm9tZWV0QHRyYWN0NC5jb20ifQ.J2DqpOYDEXfM-d49X2-G3qSdROZLKWmEgUxQOZMXp6w";

    @Test
    @Ignore
    public void saveEventLog() throws ExecutionException, InterruptedException {
        EventLog eventLog = EventLog.builder().eventId(1L).build();
        eventLog.setStorableLog(1L, DateTimeUtill.nowOfUTC());
        given(this.authService.getUserId(accessTkn)).willReturn(5L);
        given(this.fIrestoreRepository.saveEventLog(eventLogService.getDocId(LocalDateTime.now()), eventLog)).willReturn("2019-03-18T12:27:19.206629000Z");

        eventLogService = new EventLogService(fIrestoreRepository, authService);
        String result = eventLogService.saveEventLog(accessTkn, eventLog);
        Assert.assertNotNull(result);
    }
}