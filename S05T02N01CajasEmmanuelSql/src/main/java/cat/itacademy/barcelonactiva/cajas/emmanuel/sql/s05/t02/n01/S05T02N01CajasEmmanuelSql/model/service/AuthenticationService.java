package cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.service;

import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.domain.Player;
import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.domain.Role;
import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.dto.AuthenticationResponse;
import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.dto.LoginDto;
import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.dto.RegisterDto;
import cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.repo.PlayerRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//hace la lógica del registro y la autentificación
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PlayerRepo repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final DiceGameServiceImp diceGameServiceImp;
// comprueba que un jugador no existe por el nombre en la BD y si es correcto crea el jugador,
// en caso de que algo falle devuelve un null
    public AuthenticationResponse register(RegisterDto request) {
        if(!diceGameServiceImp.existingPlayerbyName(request.getName())){
                var user = Player.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(Role.USER)
                        .build();
                repository.save(user);
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build();
        } else {
            return null;
        }

    }

//autentifica que el jugador existe y devuelve un token con el nombre del jugador
    public AuthenticationResponse authenticate(LoginDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
