package br.com.med.voll.api.infra.security;

import br.com.med.voll.api.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;


@Service
public class TokenService {

    @Value("${api.security.toke.secret}")
    private String secret;

    //metodo que gera o token
    public String gerarToken(Usuario usuario) {

        try {
            var algoritmo = Algorithm.HMAC256(secret);

            Instant dataExperiracao;
            return JWT.create()
                    .withIssuer("API voll.med")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(dataExperiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("erro ao gerar token jwt", exception);
        }

    }

    // metodo que valida o token
    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API voll.med")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("TokenJWT invalido ou expirado");
        }
    }

    private Instant dataExperiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}


