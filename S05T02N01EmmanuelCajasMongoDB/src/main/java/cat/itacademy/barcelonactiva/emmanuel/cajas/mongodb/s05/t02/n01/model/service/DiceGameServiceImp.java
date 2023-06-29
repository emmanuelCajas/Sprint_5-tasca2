package cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.service;

import cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.domain.Game;
import cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.domain.Player;
import cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.dto.GameDto;
import cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.dto.PlayerDto;
import cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.repo.DiceGameRepo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class DiceGameServiceImp implements DiceGameServce{
    //region ATTRIBUTES
    @Autowired
    DiceGameRepo diceGameRepo;
    @Autowired
    private ModelMapper modelMapper;
    //endregion ATTRIBUTES

    //region CREATE
    public Game createGame(PlayerDto playerDto){
        int result1 = diceRoll();
        int result2 =diceRoll();
        int total = result1 + result2;
        Player player = modelMapper.map(playerDto,  Player.class);
        Game game;
        if(total == 7){
            game = new Game("WON", result1, result2, total);
        } else {
            game = new Game("LOST", result1, result2, total);
        }
        player.getGames().add(game);
        diceGameRepo.save(player);
        return game;
    }
    //comprobamos que el nombre es anónimo o no existe en la base de datos
    @Override
    public Player createPlayer(PlayerDto playerDto) {
        Player player = modelMapper.map(playerDto, Player.class);
        if(player.getName().equals("ANONIMUS") || !existingPlayerbyName(player.getName())){
            return diceGameRepo.save(player);
        }
        else {
            return null;
        }
    }
    //endregion CREATE

    //region CHECK
    @Override
    public boolean existsByIdPlayer(String id) {
        return diceGameRepo.existsById(id);
    }

    @Override
    public boolean existingPlayerbyName(String name) {
        return diceGameRepo.existsByName(name);
    }

    @Override
    public boolean existsByIdGame(String id) {
        return diceGameRepo.existsById(id);
    }
    //endregion CHECK

    //region GET
    @Override
    public PlayerDto getPlayerbyId(String id) {
        Optional<Player> player = diceGameRepo.findById(id);
        PlayerDto playerDto = modelMapper.map(player.get(), PlayerDto.class);
        return playerDto;
    }

    @Override
    public List<PlayerDto> getAllPlayers() {
        List<PlayerDto> players = diceGameRepo.findAll().stream().map(player -> modelMapper.map(player, PlayerDto.class))
                .collect(Collectors.toList());
        players.forEach(playerDto -> playerDto.setExitpercent(setPlayerExitPercent(playerDto.getId())));
        return players;
    }
    //primero recoge a todos los jugadores y después

    @Override
    public List<GameDto> getGamesByPlayer(String id) {
        List<GameDto> games = diceGameRepo.findById(id).get().getGames()
                .stream().map(game -> modelMapper.map(game, GameDto.class)).collect(Collectors.toList());
        return games;
    }

    //endregion GET

    //region UPDATE
    //
    //recibe un DTO, comprueba que el nombre es correcto (es anónimo o no está repetido) y cambia el único dato modificable de un jugador, el nombre
    @Override
    public Player updatePlayer(PlayerDto playerDto) {
        Player existingPlayer = diceGameRepo.findById(playerDto.getId()).get();
        if(playerDto.getName().equals("ANONIMUS") || !existingPlayerbyName(playerDto.getName())){
            existingPlayer.setName(playerDto.getName());
            return diceGameRepo.save(existingPlayer);
        }
        else {
            return null;
        }
    }

    //endregion UPDATE

    //region DELETE
    @Override
    public void deleteGamesFromPlayer(String id) {
        Player player = modelMapper.map(getPlayerbyId(id), Player.class);
        player.getGames().clear();
        diceGameRepo.save(player);
    }
    //endregion DELETE

    //region RANKING
    //
    //Coge a todos los usuarios que hayan jugado alguna partida y devuelve el porcentaje medio
    @Override
    public double totalRanking() {
        List<PlayerDto> players = getAllPlayers();
        double totalRanking = 0;
        totalRanking = players.stream().filter(playerDto -> playerDto.getExitpercent() >= 0)
                .mapToDouble(playerDto -> playerDto.getExitpercent()).sum()
                / players.stream().filter(playerDto -> playerDto.getExitpercent() >= 0).count();
        return totalRanking;
    }

    //Coge a todos los usuarios que hayan jugado alguna partida y devuelve lo que tenga el peor porcentaje
    @Override
    public PlayerDto worstPlayer() {
        List<PlayerDto> players = getAllPlayers();
        return players.stream().filter(playerDto -> playerDto.getExitpercent() >= 0).min(Comparator.comparingDouble(PlayerDto::getExitpercent)).get();
    }

    //Coge a todos los usuarios que hayan jugado alguna partida y devuelve el que tenga el mejor porcentaje
    @Override
    public PlayerDto bestPlayer() {
        List<PlayerDto> players = getAllPlayers();
        return players.stream().filter(playerDto -> playerDto.getExitpercent() >= 0).max(Comparator.comparingDouble(PlayerDto::getExitpercent)).get();
    }
    //endregion RANKING

    //region AUXILIAR

    //Este método emula la aleatoriedad de un tirón de dados
    @Override
    public int diceRoll() {
        int[] dice = {1, 2, 3, 4, 5, 6};
        Random random_method = new Random();
        int result = dice[random_method.nextInt(dice.length)];
        return result;
    }

    //Calcula el porcentaje si el jugador ha jugado alguna partida, sino devuelve una excepción (-1) que en frente end
    //puede ser reconvertida (p.ej: indicando que el jugador no ha jugado ninguna partida)
    @Override
    public float setPlayerExitPercent(String id) {
        try {
            int totalGames = (int) diceGameRepo.findById(id).get().getGames().stream().count();
            int wonGames = (int) diceGameRepo.findById(id).get().getGames().stream()
                    .filter(game -> game.getGameresult().equals("WON")).count();
            float exitPercent = (wonGames * 100) / totalGames;
            return exitPercent;
        } catch (Exception e){
            return -1;
        }

    }
    //endregion AUXILIAR

}
