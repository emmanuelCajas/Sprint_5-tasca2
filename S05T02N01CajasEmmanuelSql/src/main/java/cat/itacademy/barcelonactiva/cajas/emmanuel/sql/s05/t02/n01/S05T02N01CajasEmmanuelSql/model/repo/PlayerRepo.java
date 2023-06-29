package cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.repo;

import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepo extends JpaRepository<Player, Integer> {
    Optional<Player> findByName(String name);
    Optional<Player> findByEmail(String email);
    boolean existsByName(String name);
    boolean existsByEmail(String email);
}
