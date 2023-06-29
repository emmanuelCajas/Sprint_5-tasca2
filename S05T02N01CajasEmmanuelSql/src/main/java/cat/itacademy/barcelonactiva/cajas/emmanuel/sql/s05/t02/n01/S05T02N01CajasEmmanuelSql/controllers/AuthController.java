package cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.controllers;

import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.dto.AuthenticationResponse;
import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.dto.LoginDto;
import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.dto.RegisterDto;
import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.service.AuthenticationService;
import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.service.DiceGameServiceImp;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//Aquesta classe es l'encarregada de fer el registre i el login dels usuaris
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService service;
    private final DiceGameServiceImp diceGameServiceImp;

//vuelve ok si el registro ha sido correcto, si el nombre ya está en uso devuelve un aviso (i'm used), si el nombre escogido es anónimo
//lo crea si es el primero y si no hace el login del jugador anónimo (todo el que juegue en anónimo jugara con el mismo jugador)
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterDto request
    ) {
        if (request.getName().equals("ANONIMUS") && diceGameServiceImp.existingPlayerbyName("ANONIMUS")) {
            return ResponseEntity.ok(service.authenticate(new LoginDto(request.getEmail(), request.getPassword())));
        } else if (service.register(request) != null){
            return ResponseEntity.ok(service.register(request));
        } else {
            return new ResponseEntity<>(HttpStatus.IM_USED);
        }

    }
    //genera un token a partir de un jugador y contraseña, en el caso de los jugadores Anonims los login se hace con una contraseña vacía
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody LoginDto request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
