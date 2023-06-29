package cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.controllers;

import cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.domain.Game;
import cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.dto.GameDto;
import cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.dto.PlayerDto;
import cat.itacademy.barcelonactiva.emmanuel.cajas.mongodb.s05.t02.n01.model.service.DiceGameServiceImp;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class DiceGameControllerImp implements DiceGameController{
    @Autowired
    DiceGameServiceImp diceGameServiceImp;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    @PostMapping("/add")
    public ResponseEntity<PlayerDto> createPlayer(PlayerDto playerDto) {
        try {
           if (diceGameServiceImp.createPlayer(playerDto) !=  null){
               return new ResponseEntity<>(playerDto, HttpStatus.CREATED);
           }
            return new ResponseEntity<>(null, HttpStatus.IM_USED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<PlayerDto> updatePlayer(String id, PlayerDto playerDto) {
        if (diceGameServiceImp.existsByIdPlayer(id)){
            playerDto.setId(id);
            diceGameServiceImp.updatePlayer(playerDto);
            return new ResponseEntity<>(playerDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @PostMapping("{id}/games/add")
    public ResponseEntity<GameDto> createGame(String id) {
        try {
            if (diceGameServiceImp.existsByIdPlayer(id)){
                PlayerDto playerDto = diceGameServiceImp.getPlayerbyId(id);
                Game game = diceGameServiceImp.createGame(playerDto);
                GameDto gameDto = modelMapper.map(game, GameDto.class);
                return new ResponseEntity<>(gameDto, HttpStatus.CREATED);
            }
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);


        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @DeleteMapping("{id}/games/delete")
    public ResponseEntity<HttpStatus> deleteGames(String id) {
        try{
            if (diceGameServiceImp.existsByIdPlayer(id)){
                diceGameServiceImp.deleteGamesFromPlayer(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping("/getAll")
    public ResponseEntity<List<PlayerDto>> getAllPlayers() {
        try {
            List<PlayerDto> players = diceGameServiceImp.getAllPlayers();
            if (players.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(players, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping("{id}/games/getGames")
    public ResponseEntity<List<GameDto>> getPlayerGamesById(String id) {
        try {
            List<GameDto> games = diceGameServiceImp.getGamesByPlayer(id);
            if (games.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(games, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping("/games/getTotalRanking")
    public ResponseEntity<Float> getTotalRanking() {
        try {
            Float result = (float) diceGameServiceImp.totalRanking();
            if(result > 0){
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    @GetMapping("/games/getWorstPlayer")
    public ResponseEntity<PlayerDto> getWorstPlayer() {
        try {
            List<PlayerDto> players = diceGameServiceImp.getAllPlayers();
            if (players.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            PlayerDto playerDto = diceGameServiceImp.worstPlayer();
            return new ResponseEntity<>(playerDto, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping("/games/getBestPlayer")
    public ResponseEntity<PlayerDto> getBestPlayer() {
        try {
            List<PlayerDto> players = diceGameServiceImp.getAllPlayers();
            if (players.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            PlayerDto playerDto = diceGameServiceImp.bestPlayer();
            return new ResponseEntity<>(playerDto, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
