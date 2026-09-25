package com.sadi.FinanceManager.config;

import com.sadi.FinanceManager.security.JwtRequestFilter;
import com.sadi.FinanceManager.service.impl.AppUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.List;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    // after creating jwt Request Filter in security
    private final JwtRequestFilter jwtRequestFilter;

    private final AppUserDetailsService appUserDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers("/status", "/run", "/register", "/activateprofile", "/login",    "/forgot-password",
                                        "/reset-password").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // after creating jwt Request Filter in security
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
//        Allow requests from ANY origin
        configuration.setAllowedOriginPatterns(List.of("*"));//List.of("http://localhost:3000")
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "OPTIONS","DELETE"));

        configuration.setAllowedHeaders(List.of("Authorization","Content-Type", "Accept"));
//        Authorization  for JWT tokens
//        Content-Type  JSON, etc.
//                Accept response format

        configuration.setAllowCredentials(true);
//        Cookies
//        Authorization headers (like JWT)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);
//        Apply these CORS rules to every API endpoint

        return source; // now use globally
    }



    @Bean
    public AuthenticationManager authenticationManager() {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        Fetches user from DB Checks password
//        Uses UserDetailsService

        authenticationProvider.setUserDetailsService(appUserDetailsService);
//        User enters email/password
//        Spring calls:
//        loadUserByUsername(email)

        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        passwordEncoder.matches(rawPassword, storedPassword)

        return new ProviderManager(authenticationProvider);
    }




//    When  call in service:
//
//            authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(email, password)
//);
//
//            1. ProviderManager receives request
//        2. Passes to DaoAuthenticationProvider
//        3. Calls your service:AppUserDetailsService.loadUserByUsername(email)
//            4. Gets user from DB
//        5. Compares passwords using BCrypt
//        6. If match  success
//        7. If not exception


}

