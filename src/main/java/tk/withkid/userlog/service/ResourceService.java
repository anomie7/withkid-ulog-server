package tk.withkid.userlog.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public ResponseEntity<?> getEventsOf(List<Long> eventIds){
        return null;
    }
}
