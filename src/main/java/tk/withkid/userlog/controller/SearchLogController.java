package tk.withkid.userlog.controller;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import tk.withkid.userlog.domain.SearchLog;
import tk.withkid.userlog.service.SearchLogService;

import java.util.concurrent.ExecutionException;


@NoArgsConstructor
@RestController
public class SearchLogController {
    private SearchLogService searchLogService;

    @Autowired
    public SearchLogController(SearchLogService searchLogService) {
        this.searchLogService = searchLogService;
    }

    @PostMapping(path = "/searchLog",headers="Accept=text/plain", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> setSearchLog(@RequestHeader(name = "Authorization") String accessToken, @RequestBody SearchLog searchLog) throws ExecutionException, InterruptedException {
        String updateTIme = null;
        updateTIme = searchLogService.saveSearchLog(accessToken, searchLog);
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(updateTIme);
    }
}
