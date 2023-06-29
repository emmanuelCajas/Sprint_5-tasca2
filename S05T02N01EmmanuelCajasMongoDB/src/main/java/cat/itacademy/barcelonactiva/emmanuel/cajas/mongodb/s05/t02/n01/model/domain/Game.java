package cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class Game {

    private String gameresult;
    private int dice1;
    private int dice2;
    private int result;

    public Game(String gameresult, int dice1, int dice2, int result) {
        this.gameresult = gameresult;
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.result = result;
    }
}
