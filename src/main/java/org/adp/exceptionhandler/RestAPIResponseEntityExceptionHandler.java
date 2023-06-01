package org.adp.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestAPIResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {IllegalArgumentException.class})
    public ResponseEntity<String> handleIllegalArgumentException(Exception ex) {
        log.error("Exception occurred: ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("Illegal Argument specified : %s", ex.getMessage()));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        log.error("Exception occurred: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(String.format("Internal Server Error : %s", ex.getMessage()));
    }
}

