package controllers;

import entities.Player;
import service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PutMapping("/{playerId}")
    public Mono<ResponseEntity<Player>> updatePlayerName(
            @PathVariable String playerId,
            @RequestBody String newName) {
        return playerService.findPlayerById(playerId)
                .flatMap(player -> {
                    player.setName(newName);
                    return playerService.updatePlayer(player);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    public Flux<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @ApiResponse(responseCode = "201", description = "CREATED")
    @ApiResponse(responseCode = "400", description = "BAD REQUEST")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Player> createPlayer(
            @Parameter(description = "Jugador a crear", required = true)
            @RequestBody Player player) {
        return playerService.createPlayer(player);
    }

    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "NOT FOUND")
    @GetMapping("/{id}")
    public Mono<Player> getPlayerById(
            @Parameter(description = "Id del jugador", required = true)
            @PathVariable Long id) {
        return playerService.getPlayerById(String.valueOf(id));
    }

    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "NOT FOUND")
    @PutMapping("/{id}")
    public Mono<Player> updatePlayer(
            @Parameter(description = "Id del jugador", required = true)
            @PathVariable Long id,
            @RequestBody Player player) {
        return playerService.updatePlayer(id, player);
    }

    @ApiResponse(responseCode = "204", description = "NO CONTENT")
    @ApiResponse(responseCode = "404", description = "NOT FOUND")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deletePlayer(
            @Parameter(description = "Id del jugador", required = true)
            @PathVariable Long id) {
        return playerService.deletePlayerById(id);
    }

    /*
    @GetMapping("/ranking")
    public Flux<Player> getPlayerRanking() {
        return playerService.getPlayerRanking();
    }
    */
}