package tk.withkid.userlog.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.withkid.userlog.dto.Quration;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Ignore
public class ResourceServiceTest {
    @Autowired
    private ResourceService resourceService;

    @Test
    public void getEventsOf() {
        Long[] arr = {1L, 2L, 3L, 4L, 5L};
        List<Long> eventIds = Arrays.asList(arr);
        Quration events = resourceService.getEventsOf(eventIds);

        Assert.assertEquals(events.getClass(), Quration.class);
        Assert.assertNotNull(events);
    }
}