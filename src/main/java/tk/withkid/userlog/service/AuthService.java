package tk.withkid.userlog.service;

        import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
        import lombok.NoArgsConstructor;
        import lombok.extern.slf4j.Slf4j;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.core.env.Environment;
        import org.springframework.http.HttpEntity;
        import org.springframework.http.HttpHeaders;
        import org.springframework.http.HttpMethod;
        import org.springframework.http.ResponseEntity;
        import org.springframework.stereotype.Service;
        import org.springframework.web.client.RestTemplate;
        import org.springframework.web.util.UriComponents;
        import org.springframework.web.util.UriComponentsBuilder;

@Service
@NoArgsConstructor
@Slf4j
public class AuthService {
    private RestTemplate restTemplate;
    private Environment env;

    @Autowired
    public AuthService(RestTemplate restTemplate, Environment env) {
        this.restTemplate = restTemplate;
        this.env = env;
    }

    @HystrixCommand(fallbackMethod = "getInvalidID")
    public Long getUserId(String accessToken) {
        String apiuri = env.getProperty("withkid.api.uri");
        String apiPort = env.getProperty("withkid.api.userId.port");

        UriComponents uri = UriComponentsBuilder.fromUriString(apiuri).path("/userId").port(apiPort).build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<String> entity = new HttpEntity<String>("param", headers);
        ResponseEntity<Long> res = restTemplate.exchange(uri.toUriString(), HttpMethod.GET, entity, Long.class);
        return res.getBody();
    }

    public Long getInvalidID(String accessToken) {
        return -1L;
    }
}