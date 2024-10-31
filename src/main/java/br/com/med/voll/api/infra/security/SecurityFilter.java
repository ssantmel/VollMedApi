package br.com.med.voll.api.infra.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// essa classe faz o serviço de barrar as requisições para verificar
// os acessos e se o token é valido

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //recupera o token do cabeçalho
        var tokenJWT = recuperarToken(request);

        //se o token estiver dentro do cabeçalho chama nossa classe TokenService com o metodo getSubject e valida
        var subject = tokenService.getSubject(tokenJWT);
        System.out.println(subject);


        filterChain.doFilter(request, response);

    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer", "");
        }
        return null;
    }
}
