package tk.withkid.userlog.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class ResourceServiceTest {
    @Autowired
    private ResourceService resourceService;

    @Test
    public void getEventsOf() throws IOException {
        Long[] arr = {1L, 2L, 3L, 4L, 5L};
        List<Long> eventIds = Arrays.asList(arr);
        JsonNode events = resourceService.getEventsOf(eventIds);
        Assert.assertNotNull(events);
    }
}