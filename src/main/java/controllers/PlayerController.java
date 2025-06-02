package controllers;

import entities.Player;
import service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/player")
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
        return playerService.updatePlayerName(playerId, newName)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/ranking")
    public Flux<Player> getPlayerRanking() {
        return playerService.getPlayerRanking();
    }
}