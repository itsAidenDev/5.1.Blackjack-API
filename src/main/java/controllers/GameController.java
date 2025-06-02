package controllers;

import entities.gameSessions.Game;
import service.GameService;
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

    @PostMapping("/new")
    public Mono<ResponseEntity<Game>> createNewGame(@RequestBody String playerName) {
        return gameService.createNewGame(playerName)
                .map(game -> ResponseEntity.status(HttpStatus.CREATED).body(game));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Game>> getGameDetails(@PathVariable Long id) {
        return Mono.justOrEmpty(gameService.getGameById(id))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/play")
    public Mono<ResponseEntity<Game>> playGame(@PathVariable Long id, @RequestBody String action) {
        return gameService.playGame(id, action)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/delete")
    public Mono<ResponseEntity<Void>> deleteGame(@PathVariable Long id) {
        return gameService.deleteGame(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}