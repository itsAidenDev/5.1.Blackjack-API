package com.blackjack.java.blackjack.controllers;

import com.blackjack.java.blackjack.dto.PlayerDTO;
import com.blackjack.java.blackjack.model.Player;
import com.blackjack.java.blackjack.repository.PlayerRepository;
import com.blackjack.java.blackjack.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Autowired
    private PlayerRepository playerRepository;

    @ApiResponse(responseCode = "201", description = "CREATED")
    @ApiResponse(responseCode = "400", description = "BAD REQUEST")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Player> createPlayer(
            @Parameter(description = "Creating a new player", required = true)
            @RequestBody Player player) {
        return playerRepository.save(player);
    }

    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "NOT FOUND")
    @GetMapping("/{id}")
    public Mono<Player> getPlayerById(
            @Parameter(description = "Player ID", required = true)
            @PathVariable Long PlayerId) {
        return playerService.getPlayerById(PlayerId);
    }

    @Operation(summary = "List all players in the database", description = "Gets all players registered in the database")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    public Flux<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "NOT FOUND")
    @PutMapping("/{id}")
    public Mono<Player> updatePlayer(
            @Parameter(description = "Player ID", required = true)
            @PathVariable Long PlayerId,
            @RequestBody Player player) {
        return playerService.updatePlayer(PlayerId, player);
    }

    @PutMapping("/{playerId}")
    public Mono<ResponseEntity<Player>> updatePlayerName(
            @PathVariable Long playerId,
            @RequestBody String newPlayerName) {
        return playerService.getPlayerById(playerId)
                .flatMap(player -> {
                    player.setPlayerName(newPlayerName);
                    return playerService.updatePlayer(playerId, player);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @ApiResponse(responseCode = "204", description = "NO CONTENT")
    @ApiResponse(responseCode = "404", description = "NOT FOUND")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deletePlayer(
            @Parameter(description = "Player ID", required = true)
            @PathVariable Long PlayerId) {
        return playerService.deleteByPlayerId(PlayerId);
    }
}

