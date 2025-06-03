package com.blackjack.java.blackjack.controllers;

import com.blackjack.java.blackjack.dto.GameResponseDTO;
import com.blackjack.java.blackjack.dto.RankingDTO;
import com.blackjack.java.blackjack.model.Game;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.blackjack.java.blackjack.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
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
    public Mono<ResponseEntity<GameResponseDTO>> createNewGame(@PathVariable Long playerId, @RequestBody int bet) {
        return gameService.createGame(playerId, bet)
                .map(game -> ResponseEntity.status(HttpStatus.CREATED).body(game));
    }

    @Operation(summary = "Get game by ID")
    @GetMapping("/{gameId}")
    public Mono<ResponseEntity<Object>> getGameById(@PathVariable Long id) {
        return Mono.justOrEmpty(gameService.getGameById(id))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Play game by ID")
    @PostMapping("/{id}/play")
    public Mono<ResponseEntity<Game>> playGame(
            @PathVariable Long gameId,
            @RequestBody String action) {
        return gameService.playGame(gameId, action)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete game by ID")
    @DeleteMapping("/{id}/delete")
    public Mono<ResponseEntity<Object>> deleteGame(@PathVariable Long gameId) {
        return gameService.deleteGame(gameId)
                .then(Mono.just(ResponseEntity.noContent().build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @Operation(summary = "Get player ranking")
    @GetMapping("/ranking")
    public Flux<RankingDTO> getPlayerRanking() {
        return gameService.getPlayerRanking();
    }

    @Operation(summary = "Pide carta")
    @PostMapping("/{gameId}/play/hit")
    public Mono<GameResponseDTO> playerHit(@PathVariable Long gameId) {
        return gameService.playerHit(gameId);
    }

    @Operation(summary = "Se planta")
    @PostMapping("/{gameId}/play/stand")
    public Mono<GameResponseDTO> playerStand(@PathVariable Long gameId) {
        return gameService.playerStand(gameId);
    }

}