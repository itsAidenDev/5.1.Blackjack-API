package exceptions;

import exceptions.InvalidActionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<String>> handleValidationExceptions(WebExchangeBindException ex) {
        return Mono.just(ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Validation error: " + ex.getMessage()));
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public Mono<ErrorResponse> handlePlayerNotFoundException(PlayerNotFoundException ex) {
        return Mono.just((ErrorResponse) ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("ERROR: " + ex.getMessage()));
    }

    @ExceptionHandler(GameNotFoundException.class)
    public Mono<ErrorResponse> handleGameNotFoundException(GameNotFoundException ex) {
        return Mono.just((ErrorResponse) ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("ERROR: " + ex.getMessage()));
    }

    @ExceptionHandler(InvalidPlayException.class)
    public Mono<ErrorDetails> handleInvalidPlayException(InvalidPlayException ex) {
        log.debug("InvalidPlayException caught in GlobalExceptionHandler", ex);
        return Mono.just(new ErrorDetails(HttpStatus.BAD_REQUEST.value(), "ERROR: ", ex.getMessage()));
    }

    @ExceptionHandler(InvalidActionException.cLass)
    public Mono<ErrorDetails> handleInvalidActionException(InvalidActionException ex) {
        log.debug("InvalidActionException caught in GlobalExceptionHandler", ex);
        return Mono.just(new ErrorDetails(HttpStatus.BAD_REQUEST.value(), "ERROR: ", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ErrorDetails> handleGeneralExceptions(Exception ex) {
        log.debug("Unhandled exception caught in GlobalExceptionHandler", ex);
        return Mono.just(new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ERROR: ", ex.getMessage()));
    }
}




