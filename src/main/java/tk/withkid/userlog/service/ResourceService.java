package tk.withkid.userlog.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
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

    public JsonNode getEventsOf(List<Long> eventIds) throws IOException {
        String pathVar = eventIds.toString().replace("[", "").replace("]", "");
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://localhost").path("/event/".concat(pathVar)).port(8081).build();
        String uriString = uriComponents.toUriString();
        String res = restTemplate.getForObject(uriString, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(res);
        return jsonNode;
    }
}
