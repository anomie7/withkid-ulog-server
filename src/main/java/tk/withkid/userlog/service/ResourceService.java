package tk.withkid.userlog.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import tk.withkid.userlog.dto.EventDto;
import tk.withkid.userlog.dto.Quration;

import java.util.Arrays;
import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class ResourceService {
    private RestTemplate restTemplate;
    private Environment env;

    @Autowired
    public ResourceService(RestTemplate restTemplate, Environment env) {
        this.restTemplate = restTemplate;
        this.env = env;
    }

    @HystrixCommand
    public Quration getEventsOf(List<Long> eventIds) {
        String apiuri = env.getProperty("withkid.api.uri");
        String apiPort = env.getProperty("withkid.api.event.port");

        String pathVar = eventIds.toString().replace("[", "").replace("]", "");
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(apiuri).path("/event/".concat(pathVar)).port(apiPort).build();
        String uriString = uriComponents.toUriString();
        EventDto[] eventDtos = restTemplate.getForObject(uriString, EventDto[].class);
        return new Quration("최근 조회한 이벤트", Arrays.asList(eventDtos));
    }
}
