package com.board.study.studyboard.config;

import com.board.study.studyboard.jwt.JwtAccessDeniedHandler;
import com.board.study.studyboard.jwt.JwtAuthenticationEntryPoint;
import com.board.study.studyboard.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
    private static final String[] AUTH_WHITELIST = {
            "/", "/user/**", "/issue"
    };
    private static final String[] AUTH_ALL = {
            "/api/authenticate", "/api/signup", "/api/user", "/user", "/api/user/{username}"
    };
    private static final String[] AUTH_GET = {
            "/get/board", "/api/health-check"
    };
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    public SecurityConfig(TokenProvider tokenProvider, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            web.ignoring()
                    .requestMatchers(AUTH_ALL);
        };
    }
    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        return
                http
                .csrf().disable()
                .exceptionHandling(
                        handling->
                    handling.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests (authorize -> authorize
                        .shouldFilterAllDispatcherTypes(false)
                        .requestMatchers(HttpMethod.GET, AUTH_GET)
                        .permitAll()
                        .anyRequest().authenticated()
                )
                        .build();
    }
}
