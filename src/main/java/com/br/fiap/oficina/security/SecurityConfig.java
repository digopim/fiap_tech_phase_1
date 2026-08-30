package com.br.fiap.oficina.security;

import com.br.fiap.oficina.model.enums.Perfil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(Customizer.withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .authorizeHttpRequests(requests -> requests
            .requestMatchers(HttpMethod.GET, "/ordem/**").hasAnyRole(Perfil.CLIENTE.name(), Perfil.FORNECEDOR.name(), Perfil.COLABORADOR.name(), Perfil.ADMINISTRADOR.name())
            .requestMatchers(HttpMethod.POST, "/ordem/aprovar").hasAnyRole(Perfil.CLIENTE.name(), Perfil.FORNECEDOR.name(), Perfil.COLABORADOR.name(), Perfil.ADMINISTRADOR.name())
            .requestMatchers("/material/**", "/veiculo/**", "/servico/**", "/ordem/**").hasAnyRole(Perfil.COLABORADOR.name(), Perfil.ADMINISTRADOR.name())
            .requestMatchers("/admin/**", "/usuario/**").hasRole(Perfil.ADMINISTRADOR.name())
            .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/v3/api-docs.yaml", "/webjars/**", "/auth/**", "/credencial/**").permitAll()
            )
            .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
            ;
        return http.build();
    }
}
