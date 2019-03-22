package tk.withkid.userlog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import tk.withkid.userlog.dto.ErrorResponse;

import java.net.ConnectException;
import java.util.concurrent.TimeoutException;

@Slf4j
@ControllerAdvice
@RestController
public class ExceptionController {

    @ExceptionHandler({HttpStatusCodeException.class})
    public ResponseEntity<ErrorResponse> requestFailHandler(Exception e) {
        ErrorResponse body = ErrorResponse.builder().name(HttpStatusCodeException.class.getSimpleName())
                .msg("잘못된 요청으로 인한 실패").status(HttpStatus.NOT_FOUND).build();
        log.error("restTemplate의 요청이 실패함: msg : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler({ConnectException.class})
    public ResponseEntity<ErrorResponse> connectFailHandler(Exception e) {
        ErrorResponse body = ErrorResponse.builder().name(ConnectException.class.getSimpleName())
                .msg("의존하는 서비스 컨포넌트의 연결이 실패했습니다.").status(HttpStatus.NOT_FOUND).build();
        log.error("restTemplate의 요청이 실패함: msg : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler({TimeoutException.class})
    public ResponseEntity<ErrorResponse> timeOutHandler(Exception e) {
        ErrorResponse body = ErrorResponse.builder().name(TimeoutException.class.getSimpleName())
                .msg("타임 아웃이 발생했습니다.").status(HttpStatus.REQUEST_TIMEOUT).build();
        log.error("restTemplate의 요청이 실패함: msg : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(body);
    }
}
