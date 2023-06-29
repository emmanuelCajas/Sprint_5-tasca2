package cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.dto;

import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PlayerDto {
    private int player_id;
    private String name;
    private String email;
    private LocalDateTime createDateTime;
    private float exitpercent;
    private Role role;
    public PlayerDto(String name) {
        this.name = name;
    }
}
