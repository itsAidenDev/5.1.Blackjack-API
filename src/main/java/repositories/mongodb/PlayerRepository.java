
//TODO: TENGO QUE CAMBIAR COSITAS DE AQUÍ

package repositories.mongodb;

import entities.Player;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PlayerRepository extends ReactiveMongoRepository<Player, String> {
    Mono<Player> findByPlayerId(Long playerId);
}