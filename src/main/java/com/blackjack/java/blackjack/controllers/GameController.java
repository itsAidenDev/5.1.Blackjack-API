package com.blackjack.java.blackjack.controllers;

import com.blackjack.java.blackjack.models.Game;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.blackjack.java.blackjack.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(summary = "Create new game", description = "Create new game with a player and a bet")
    @ApiResponse(responseCode = "201", description = "NEW GAME CREATED")
    @PostMapping("/new/{playerId}")
    public Mono<ResponseEntity<Game>> createNewGame(@RequestBody String playerName) {
        return gameService.createNewGame(playerName)
                .map(game -> ResponseEntity.status(HttpStatus.CREATED).body(game));
    }

    @Operation(summary = "Get game by ID")
    @GetMapping("/{gameId}")
    public Mono<ResponseEntity<Object>> getGameDetails(@PathVariable Long id) {
        return Mono.justOrEmpty(gameService.getPlayerById(id))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Play game by ID")
    @PostMapping("/{id}/play")
    public Mono<ResponseEntity<Game>> playGame(
            @PathVariable String id,
            @RequestBody String action) {
        return gameService.playGame(Long.valueOf(id), action)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete game by ID")
    @DeleteMapping("/{id}/delete")
    public Mono<ResponseEntity<Object>> deleteGame(@PathVariable String id) {
        return gameService.deleteGame(Long.valueOf(id))
                .then(Mono.just(ResponseEntity.noContent().build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }
}