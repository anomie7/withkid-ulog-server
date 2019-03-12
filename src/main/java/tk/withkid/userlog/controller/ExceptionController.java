package tk.withkid.userlog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

@ControllerAdvice
@RestController
public class ExceptionController {

    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<ErrorResponse> requestFailHandler() {
        ErrorResponse body = ErrorResponse.builder().name(HttpStatusCodeException.class.getSimpleName())
                .msg("요청이 실패했습니다.").status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
