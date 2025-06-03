package com.blackjack.java.blackjack.exceptions;

import com.blackjack.java.blackjack.exceptions.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Mono<ErrorDetails> handleGeneralExceptions(Exception ex) {
        log.debug("Unhandled exception caught in GlobalExceptionHandler", ex);
        return Mono.just(new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ERROR! An unexpected error occurred: ", ex.getMessage()));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ErrorDetails> handleValidationExceptions(WebExchangeBindException ex) {
        return Mono.just(new ErrorDetails(HttpStatus.BAD_REQUEST.value(), "ERROR! Invalid request: ", ex.getMessage()));
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public Mono<ErrorDetails> handlePlayerNotFoundException(PlayerNotFoundException ex) {
        return Mono.just(new ErrorDetails(HttpStatus.NOT_FOUND.value(), "ERROR! Player not found: ", ex.getMessage()));
    }

    @ExceptionHandler(PlayerAlreadyExistsException.class)
    public Mono<ErrorDetails> handlePlayerAlreadyExistsException(Exception ex) {
        return Mono.just(new ErrorDetails(HttpStatus.CONFLICT.value(), "ERROR! Player already exists: ", ex.getMessage()));
    }

    @ExceptionHandler(GameNotFoundException.class)
    public Mono<ErrorDetails> handleGameNotFoundException(GameNotFoundException ex) {
        return Mono.just(new ErrorDetails(HttpStatus.NOT_FOUND.value(), "ERROR! Game not found: ", ex.getMessage()));
    }

    @ExceptionHandler(RankingIsEmptyException.class)
    public Mono<ErrorDetails> handleRankingIsEmptyException(RankingIsEmptyException ex) {
        return Mono.just(new ErrorDetails(HttpStatus.NOT_FOUND.value(), "ERROR! Ranking is empty: ", ex.getMessage()));
    }

    @ExceptionHandler(InvalidPlayException.class)
    public Mono<ErrorDetails> handleInvalidPlayException(InvalidPlayException ex) {
        log.debug("InvalidPlayException caught in GlobalExceptionHandler", ex);
        return Mono.just(new ErrorDetails(HttpStatus.BAD_REQUEST.value(), "ERROR! Invalid play: ", ex.getMessage()));
    }

    @ExceptionHandler(InvalidActionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorDetails> handleInvalidActionException(InvalidActionException ex) {
        return Mono.just(new ErrorDetails(HttpStatus.BAD_REQUEST.value(),"ERROR! Invalid action: ", ex.getMessage()));
    }

}




