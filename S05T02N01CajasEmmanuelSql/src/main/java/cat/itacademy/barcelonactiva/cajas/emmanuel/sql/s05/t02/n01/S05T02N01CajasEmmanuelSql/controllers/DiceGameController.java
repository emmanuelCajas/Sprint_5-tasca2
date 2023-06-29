package cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.controllers;

import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.dto.GameDto;
import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.dto.PlayerDto;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface DiceGameController {
    //PUT /players: modifica el nombre del jugador/a.
    ResponseEntity<PlayerDto> updatePlayer(@PathVariable("id") int id, @RequestBody PlayerDto playerDto, HttpServletRequest request);
    //POST /players/{id}/games/ : un jugador/a específico realiza una tirada del dado.
    ResponseEntity<GameDto> createGame(@PathVariable("id") int id,HttpServletRequest request);
    //DELETE /players/{id}/games: elimina les tirades del jugador/a.
    ResponseEntity<HttpStatus> deleteGames(@PathVariable("id") int id, HttpServletRequest request);
    //GET /players/: devuelve el listado de todos los jugadores/as del sistema con su porcentaje mediod’èxits.
    ResponseEntity<List<PlayerDto>> getAllPlayers();
    //GET /players/{id}/games: devuelve el listado de jugadas por un jugador/a .
    ResponseEntity<List<GameDto>> getPlayerGamesById(@PathVariable("id") int id, HttpServletRequest request);
    //GET /players/ranking: devuelve el ranking medio de todos los jugadores/as del sistema. Es decir, el porcentaje medio de logros.
    ResponseEntity<Float> getTotalRanking();
    //GET /players/ranking/loser: devuelve al jugador/a con peor porcentaje de éxito.
    ResponseEntity<PlayerDto> getWorstPlayer();
    //GET /players/ranking/winner: devuelve al jugador con mejor porcentaje de éxito.
    ResponseEntity<PlayerDto> getBestPlayer();










}
