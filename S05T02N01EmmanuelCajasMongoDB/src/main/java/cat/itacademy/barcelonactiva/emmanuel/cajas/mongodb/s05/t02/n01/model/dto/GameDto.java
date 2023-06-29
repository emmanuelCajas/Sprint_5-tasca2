package cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GameDto {

    private String gameresult;
    private int dice1;

    private int dice2;

    private int result;
}
