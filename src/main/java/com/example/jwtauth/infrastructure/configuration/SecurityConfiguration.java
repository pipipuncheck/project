package com.example.jwtauth.infrastructure.configuration;

import com.example.jwtauth.infrastructure.configuration.filter.JwtAuthenticationFilter;
import com.example.jwtauth.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserRepository userRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

//    @Autowired
//    public SecurityConfiguration(UserRepository userRepository, JwtAuthenticationFilter jwtAuthenticationFilter) {
//        this.userRepository = userRepository;
//        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://mynyame.ru", "http://localhost:63342")); // Указываем конкретный источник для доступа
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type","X-Requested-With", "accept", "Origin", "Access-Control-Request-Headers", "Set-Cookie")); // Явно указываем разрешенные заголовки
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;

    }

    @Bean
    public CorsFilter corsFilter(){
        return new CorsFilter(corsConfigurationSource());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN", "MANAGER")
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
                                .requestMatchers("/auth/**").permitAll()
                                .anyRequest().authenticated()
                )
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }
}
