package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Irá recuperar o token
        var tokenJWT = recuperarToken(request);

        if(tokenJWT != null) {
            //Recupera o Token, se não vêm ele segue o fluxo
            var subject = tokenService.getSubject(tokenJWT);
            //Precisa chamar o usuário que vêm do banco
            var usuario = repository.findByLogin(subject);
            //Realiza a autenticação do usuário, criando o DTO
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

            //Realiza a autenticação para validar que o usuário está logado
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Logado na requisição");

        }

        //filterChain: representa a cadeia de filtros na aplicação
        //Necessário para chamar os próximos filtros na aplicação
        filterChain.doFilter(request, response);

    }

    private String recuperarToken(HttpServletRequest request) {

        var authorizationHeader = request.getHeader("Authorization");
        //Se não receber o cabeçalho irá receber null e com uma mensagem
        if(authorizationHeader != null) {
            //Irá apagar o prefixo que recebe junto com o token
            return authorizationHeader.replace("Bearer", "").trim();

        }
        return null;

    }
}
