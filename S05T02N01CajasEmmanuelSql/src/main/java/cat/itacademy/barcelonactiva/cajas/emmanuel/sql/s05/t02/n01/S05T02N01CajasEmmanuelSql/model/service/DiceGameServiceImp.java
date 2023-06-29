package cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.service;

import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.domain.Game;
import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.domain.Player;
import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.dto.GameDto;
import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.dto.PlayerDto;
import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.repo.DiceGameRepo;
import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.repo.PlayerRepo;

import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class DiceGameServiceImp implements DiceGameService {
    //region ATTRIBUTES
    @Autowired
    DiceGameRepo diceGameRepo;
    @Autowired
    PlayerRepo playerRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtService jwtService;
    //endregion ATTRIBUTES

    //region CREATE
    public Game createGame(PlayerDto playerDto){
        int result1 = diceRoll();
        int result2 =diceRoll();
        int total = result1 + result2;
        Player player = modelMapper.map(playerDto,  Player.class);
        Game game;
        if(total == 7){
            game = new Game("WON", player, result1, result2, total);
        } else {
            game = new Game("LOST", player, result1, result2, total);
        }
        return diceGameRepo.save(game);
    }

    //comprobamos que el nombre es anónimo o no existe en la base de datos

    public Player createPlayer(Player player) {
        if(player.getName().equals("ANONIMUS") || existingPlayerbyName(player.getName()) == false){
            return playerRepo.save(player);
        }
        else {
            return null;
        }
    }
    //endregion CREATE

    //region CHECK
    @Override
    public boolean existsByIdPlayer(int id) {
        return playerRepo.existsById(id);
    }

    @Override
    public boolean existingPlayerbyName(String name) {
        return playerRepo.existsByName(name);
    }

    @Override
    public boolean existingPlayerbyEmail(String email) {
        return playerRepo.existsByEmail(email);
    }

    @Override
    public boolean existsByIdGame(int id) {
        return diceGameRepo.existsById(id);
    }
    //endregion CHECK

    //region GET
    @Override
    public PlayerDto getPlayerbyId(int id) {
        Optional<Player> player = playerRepo.findById(id);
        PlayerDto playerDto = modelMapper.map(player.get(), PlayerDto.class);
        return playerDto;
    }
    public PlayerDto getPlayerbyEmail(String email){
        Optional<Player> player = playerRepo.findByEmail(email);
        PlayerDto playerDto = modelMapper.map(player, PlayerDto.class);
        return playerDto;
    }

    public String getPlayerEmailByRequest(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String jwt;
        String email;
        jwt = authHeader.substring(7);
       return email = jwtService.extractUsername(jwt);
    }
    @Override
    public List<PlayerDto> getAllPlayers() {
        List<PlayerDto> players = playerRepo.findAll().stream().map(player -> modelMapper.map(player, PlayerDto.class))
                .collect(Collectors.toList());
        players.forEach(playerDto -> playerDto.setExitpercent(setPlayerExitPercent(playerDto.getPlayer_id())));
        return players;
    }

    @Override
    public List<GameDto> getAllGames() {
        List<GameDto> games = diceGameRepo.findAll().stream().map(game -> modelMapper.map(game, GameDto.class))
                .collect(Collectors.toList());
        return games;
    }

    @Override
    public List<GameDto> getGamesByPlayer(int id) {
        List<GameDto> games = diceGameRepo.findAll().stream().filter(game -> game.getPlayer().getPlayer_id() == id)
                .map(game -> modelMapper.map(game, GameDto.class)).collect(Collectors.toList());
        return games;
    }

    //endregion GET

    //region UPDATE
    //recibe un DTO, comprueba que el nombre es correcto (es anónimo o no está repetido) y cambia el único dato modificable de un jugador, el nombre
    @Override
    public PlayerDto updatePlayer(PlayerDto playerDto) {
        Player existingPlayer = playerRepo.findById(playerDto.getPlayer_id()).get();
        if(!existingPlayerbyName(playerDto.getName())){
            existingPlayer.setName(playerDto.getName());
            playerRepo.save(existingPlayer);
            return modelMapper.map(existingPlayer, PlayerDto.class);

        }
        else {
            return null;
        }
    }

    //endregion UPDATE

    //region DELETE
    @Override
    public void deleteGamesFromPlayer(int id) {
        List<Game> games = getGamesByPlayer(id).stream().map(gameDto -> modelMapper.map(gameDto, Game.class)).collect(Collectors.toList());
        games.forEach(game -> diceGameRepo.deleteById(game.getGame_id()));
    }
    //endregion DELETE

    //region RANKING

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

    //
    //Calcula el porcentaje si el jugador ha jugado alguna partida, sino devuelve una excepción (-1) que en frente end

    //puede ser reconvertida (p.ej: indicando que el jugador no ha jugado ninguna partida)
    @Override
    public float setPlayerExitPercent(int id) {
        try {
            int totalGames = (int) diceGameRepo.findAll().stream().filter(game -> game.getPlayer().getPlayer_id() == id).count();
            int wonGames = (int) diceGameRepo.findAll().stream().filter(game -> game.getPlayer().getPlayer_id() == id)
                    .filter(game -> game.getGameresult().equals("WON")).count();
            float exitPercent = (wonGames * 100) / totalGames;
            return exitPercent;
        } catch (Exception e){
            return -1;
        }

    }
    //endregion AUXILIAR

}
