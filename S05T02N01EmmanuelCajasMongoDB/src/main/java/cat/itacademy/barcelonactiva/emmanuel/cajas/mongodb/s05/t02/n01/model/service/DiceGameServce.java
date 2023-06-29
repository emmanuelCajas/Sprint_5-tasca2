package cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.service;

import cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.domain.Game;
import cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.domain.Player;
import cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.dto.GameDto;
import cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.dto.PlayerDto;

import java.util.List;

public interface DiceGameServce {

    //Metodos para crear partidos y jugadores
    Game createGame(PlayerDto playerDto);
    Player createPlayer(PlayerDto playerDto);

    //Metodos de comprobacion
    boolean existsByIdPlayer (String id);
    boolean existingPlayerbyName (String name);
    boolean existsByIdGame (String id);
    //Metodos para obtener informacion
    PlayerDto getPlayerbyId(String id);
    List<PlayerDto> getAllPlayers();
    List<GameDto> getGamesByPlayer(String id);
    //Metodos de actualizacion
    Player updatePlayer (PlayerDto playerDto);
    //Metodos de eliminacion
    void deleteGamesFromPlayer(String id);
    //Ranking
    double totalRanking();
    PlayerDto worstPlayer();
    PlayerDto bestPlayer();
    //Metodos auxiliares
    int diceRoll();
    float setPlayerExitPercent(String id);


}
