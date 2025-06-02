//TODO: TENGO QUE CAMBIAR COSITAS DE AQUÍ

package repositories.mysql;

import entities.gameSessions.Game;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends ReactiveCrudRepository<Game, Long> {
}