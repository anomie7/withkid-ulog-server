package tk.withkid.userlog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

@Slf4j
@ControllerAdvice
@RestController
public class ExceptionController {

    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<ErrorResponse> requestFailHandler(Exception e) {
        ErrorResponse body = ErrorResponse.builder().name(HttpStatusCodeException.class.getSimpleName())
                .msg("요청이 실패했습니다.").status(HttpStatus.NOT_FOUND).build();
        log.error("restTemplate의 요청이 실패함: msg : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
