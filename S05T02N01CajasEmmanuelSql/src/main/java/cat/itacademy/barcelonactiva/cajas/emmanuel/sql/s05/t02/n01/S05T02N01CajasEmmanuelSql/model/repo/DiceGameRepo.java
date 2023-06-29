package cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.repo;


import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiceGameRepo extends JpaRepository<Game, Integer> {
}
