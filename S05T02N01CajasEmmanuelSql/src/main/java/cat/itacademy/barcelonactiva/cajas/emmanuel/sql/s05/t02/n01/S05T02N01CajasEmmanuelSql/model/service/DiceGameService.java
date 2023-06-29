package cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.service;

import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.domain.Game;
import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.dto.GameDto;
import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.dto.PlayerDto;

import java.util.List;

public interface DiceGameService {

    //Metodos para crear partidos y jugadores
    Game createGame(PlayerDto playerDto);

    //Metodos de comprobacion
    boolean existsByIdPlayer (int id);
    boolean existingPlayerbyName (String name);
    boolean existingPlayerbyEmail (String email);
    boolean existsByIdGame (int id);
    //Metodos para obtener informacion
    PlayerDto getPlayerbyId(int id);
    List<PlayerDto> getAllPlayers();
    List<GameDto> getAllGames();
    List<GameDto> getGamesByPlayer(int id);
    //Metodos de actualizacion
    PlayerDto updatePlayer (PlayerDto playerDto);
    //Metodos de eliminacion
    void deleteGamesFromPlayer(int id);
    //Ranking
    double totalRanking();
    PlayerDto worstPlayer();
    PlayerDto bestPlayer();
    //Metodos auxiliares
    int diceRoll();
    float setPlayerExitPercent(int id);


}
