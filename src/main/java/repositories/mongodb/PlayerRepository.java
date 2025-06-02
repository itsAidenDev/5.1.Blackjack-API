
//TODO: TENGO QUE CAMBIAR COSITAS DE AQUÍ

package repositories.mongodb;

import entities.Player;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface PlayerRepository extends ReactiveMongoRepository<Player, String> {
    Flux<Player> findByPlayerId(Long playerId);
}