package tk.withkid.userlog.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.withkid.userlog.domain.EventLog;
import tk.withkid.userlog.util.DateTimeUtill;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventLogRepositoryTest {
    @Autowired
    private EventLogRepository eventLogRepository;

    @Test
    public void saveEventLog() throws ExecutionException, InterruptedException {
        EventLog eventLog = EventLog.builder().eventId(1L).build();
        eventLog.setStorableLog(5L, DateTimeUtill.nowOfUTC());

        String result = eventLogRepository.saveEventLog("test", eventLog);
        Assert.assertNotNull(result);
    }

    @Test
    public void findRecentEventLog() throws ExecutionException, InterruptedException {
        Long userId = 5L;
        List<EventLog> eventLogs = this.eventLogRepository.findRecentEventLog(userId);
        Assert.assertNotNull(eventLogs);
    }
}