package tk.withkid.userlog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import tk.withkid.userlog.dto.ErrorResponse;
import tk.withkid.userlog.exception.AuthorizationUnavailableException;

@Slf4j
@ControllerAdvice
@RestController
public class ExceptionController {

    @ExceptionHandler({AuthorizationUnavailableException.class})
    public ResponseEntity<ErrorResponse> authorizationFailHandler(Exception e) {
        ErrorResponse body = ErrorResponse.builder().name(AuthorizationUnavailableException.class.getSimpleName())
                .msg(e.getMessage()).status(HttpStatus.FORBIDDEN).build();
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

}
