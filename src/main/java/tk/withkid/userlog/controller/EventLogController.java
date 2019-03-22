package tk.withkid.userlog.controller;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import tk.withkid.userlog.domain.EventLog;
import tk.withkid.userlog.service.EventLogService;

import java.util.concurrent.ExecutionException;

@NoArgsConstructor
@RestController
public class EventLogController {
    private EventLogService eventLogService;

    @Autowired
    public EventLogController(EventLogService eventLogService) {
        this.eventLogService = eventLogService;
    }

    @PostMapping(path = "/eventLog",headers="Accept=text/plain", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> setEventhLog(@RequestHeader(name = "Authorization") String accessToken, @RequestBody EventLog eventLog) throws ExecutionException, InterruptedException {
        String updateTIme = null;
        updateTIme = eventLogService.saveEventLog(accessToken, eventLog);
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(updateTIme);
    }
}
