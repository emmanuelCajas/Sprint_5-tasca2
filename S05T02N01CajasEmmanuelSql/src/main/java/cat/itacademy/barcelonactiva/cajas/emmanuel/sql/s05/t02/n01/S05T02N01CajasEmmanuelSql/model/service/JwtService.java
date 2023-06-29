package cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
public class JwtService {
    //clave generada con https://allkeysgenerator.com/
    private static final String SECRET_KEY = "4428472B4B6250655368566D5971337436773979244226452948404D63516654";

//extrae el nombre del jugador del token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


//extrae toda la info del token, según la funcion introducida
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
//con estos métodos generamos el token utilizando el algoritmo de 256 bits
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // generar token de acceso
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))  //24min
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

//Validar token de acceso
// comprobamos si el token es válido (si el nombre de usuario es correcto y si el tiempo de espiración no ha pasado)
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

//comprobamos si el tiempo de expiración no ha pasado
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

//extraemos toda la info del token
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


// obtener la firma del token
// exteriamos la clave de firma con la que codificamos los token
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

