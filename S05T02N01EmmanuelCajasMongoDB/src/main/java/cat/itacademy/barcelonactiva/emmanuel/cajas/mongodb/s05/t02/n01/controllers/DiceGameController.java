package cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.controllers;

import cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.dto.GameDto;
import cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.dto.PlayerDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface DiceGameController {
    //POST: /players: crea un jugador/a.
    ResponseEntity<PlayerDto> createPlayer(@RequestBody PlayerDto playerDto);
    //PUT /players: modifica el nombre del jugador/a.
    ResponseEntity<PlayerDto> updatePlayer(@PathVariable("id") String id, @RequestBody PlayerDto playerDto);
    //POST /players/{id}/games/ : un jugador/a específico realiza una tirada del dado.
    ResponseEntity<GameDto> createGame(@PathVariable("id") String id);
    //DELETE /players/{id}/games: elimina las tiradas del jugador/a.
    ResponseEntity<HttpStatus> deleteGames(@PathVariable("id") String id);
    //GET /players/: devuelve el listado de todos los jugadores/as del sistema con su porcentaje medio de logros
    ResponseEntity<List<PlayerDto>> getAllPlayers();
    //GET /players/{id}/games: devuelve el listado de jugadas por un jugador/a.
    ResponseEntity<List<GameDto>> getPlayerGamesById(@PathVariable("id") String id);
    //GET /players/ranking: devuelve el ranking medio de todos los jugadores/as del sistema. Es decir, el porcentaje medio de logros.
    ResponseEntity<Float> getTotalRanking();
    //GET /players/ranking/loser: devuelve al jugador/a con peor porcentaje de éxito.
    ResponseEntity<PlayerDto> getWorstPlayer();
    //GET /players/ranking/winner: devuelve al jugador con mejor porcentaje de éxito.
    ResponseEntity<PlayerDto> getBestPlayer();


}
