package cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.domain;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "Players")
@Getter
@Setter
@ToString
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String name;
    @CreatedDate
    private LocalDateTime createDateTime;
    private List<Game> games;

    public Player(String name) {
        this.name = name;
        this.games = new  ArrayList<>();
    }
    public  Player(){
        this.games = new ArrayList<>();
    }
}
