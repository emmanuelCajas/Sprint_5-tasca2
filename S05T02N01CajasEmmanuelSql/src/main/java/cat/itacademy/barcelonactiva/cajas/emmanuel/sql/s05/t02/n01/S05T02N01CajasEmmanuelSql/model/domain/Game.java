package cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "games")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int game_id;
    @Column
    private String gameresult;
    @ManyToOne
    @JoinColumn(name = "player_player_id")
    private Player player;
    @Column
    private int dice1;
    @Column
    private int dice2;
    @Column
    private int result;

    public Game(Player player) {
        this.player = player;
    }

    public Game(String gameresult, Player player, int dice1, int dice2, int result) {
        this.gameresult = gameresult;
        this.player = player;
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.result = result;
    }
}
