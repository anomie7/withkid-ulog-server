package tk.withkid.userlog.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import tk.withkid.userlog.domain.SearchLog;
import tk.withkid.userlog.repository.FIrestoreRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class SearchLogService {
    private FIrestoreRepository fIrestoreRepository;
    private Environment env;

    @Autowired
    public SearchLogService(FIrestoreRepository fIrestoreRepository, Environment env) {
        this.fIrestoreRepository = fIrestoreRepository;
        this.env = env;
    }

    public String saveSearchLog(String accessToken, SearchLog searchLog) throws ExecutionException, InterruptedException {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        String random = String.valueOf(new Random().nextInt(100000));
        String docId = now + "." + random;

        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        Long userId = this.getUserId(accessToken);
        searchLog.setStorableLog(userId, timestamp);

        String updateTIme = fIrestoreRepository.saveSearchLog(docId, searchLog);
        log.info("search user log update time: {}", updateTIme);
        return updateTIme;
    }

    public Long getUserId(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        String apiuri = env.getProperty("withkid.api.userId.uri");
        String apiPort = env.getProperty("withkid.api.userId.port");

        UriComponents uri = UriComponentsBuilder.fromUriString(apiuri).path("/userId").port(apiPort).build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<String> entity = new HttpEntity<String>("param", headers);
        ResponseEntity<Long> res = restTemplate.exchange(uri.toUriString(),HttpMethod.GET ,entity, Long.class);
        return res.getBody();
    }
}
