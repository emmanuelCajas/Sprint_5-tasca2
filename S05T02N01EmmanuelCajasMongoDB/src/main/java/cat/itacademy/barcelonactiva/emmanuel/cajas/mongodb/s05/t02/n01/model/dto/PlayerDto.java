package cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.dto;

import cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.domain.Game;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class PlayerDto {
    private String id;
    private String name;
    private LocalDateTime createDateTime;
    private List<Game> games;
    private float exitpercent;
    public PlayerDto(String name) {
        this.name = name;
        this.games = new ArrayList<>();
    }
    public PlayerDto(){
        this.games = new ArrayList<>();
    }
}
