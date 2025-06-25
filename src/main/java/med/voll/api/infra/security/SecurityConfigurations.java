package med.voll.api.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//Para dizer que a classe é uma configuração
@Configuration
//Indica que vai personalizar as configurações de segurança
@EnableWebSecurity
public class SecurityConfigurations {

    //Serve para exportar uma classe para o Spring, podendo ser injetado depois
    @Bean
    //Serve para a autenticação e para autorização
    //Esse metodo desabilitou o progesso padrão de autenticação do Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception {
        return http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().build();
    }

    @Bean
    //Esse metodo ira saber criar o objeto
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    //Ensina que deve usar esse algoritmo de hash de senha
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

}
