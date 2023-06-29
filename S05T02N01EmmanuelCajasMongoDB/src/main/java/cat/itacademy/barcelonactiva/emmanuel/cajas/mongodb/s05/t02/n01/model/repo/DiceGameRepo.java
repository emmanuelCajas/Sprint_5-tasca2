package cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.repo;

import cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.domain.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DiceGameRepo extends MongoRepository<Player, String> {
    boolean existsByName(String name);

}
