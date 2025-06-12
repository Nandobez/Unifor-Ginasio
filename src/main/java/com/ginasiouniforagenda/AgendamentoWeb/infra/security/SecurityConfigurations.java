////package com.ginasiouniforagenda.AgendamentoWeb.infra.security;
////
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.http.HttpMethod;
////import org.springframework.http.HttpStatus;
////import org.springframework.security.authentication.AuthenticationManager;
////import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
////import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer; // Importar
////import org.springframework.security.config.http.SessionCreationPolicy;
////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
////import org.springframework.security.crypto.password.PasswordEncoder;
////import org.springframework.security.web.AuthenticationEntryPoint;
////import org.springframework.security.web.SecurityFilterChain;
////import org.springframework.security.web.authentication.HttpStatusEntryPoint;
////import org.springframework.security.web.util.matcher.AntPathRequestMatcher; // Importar
////
////@Configuration
////@EnableWebSecurity
////public class SecurityConfigurations {
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
////        return httpSecurity
////                .csrf(csrf -> csrf
////                                .disable() // Desabilita CSRF globalmente para APIs REST sem sessão
////                        // .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")) // Alternativa: Ignorar apenas para o H2 Console
////                )
////                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // API RESTful sem estado
////                .authorizeHttpRequests(authorize -> authorize
////                        // Permite acesso ao H2 Console (muito importante!)
////                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll() // <--- ADICIONADO AQUI
////                        // Permite requisições OPTIONS para todos os caminhos (necessário para CORS preflight)
////                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
////                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
////                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
////                        .requestMatchers("/error").permitAll() // Para tratamento de erros
////                        .requestMatchers(HttpMethod.POST, "/produto/cadastro").hasRole("ADMIN") // Endpoint protegido, exige ADMIN
////                        .anyRequest().authenticated() // Todas as outras requisições exigem autenticação
////                )
////                // Necessário para o H2 Console, que é renderizado em um iframe
////                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())) // <--- ADICIONADO AQUI
////                .build();
////    }
////
////    @Bean
////    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
////        return authenticationConfiguration.getAuthenticationManager();
////    }
////
////    @Bean
////    public PasswordEncoder passwordEncoder(){
////        return new BCryptPasswordEncoder();
////    }
////    @Bean
////    public AuthenticationEntryPoint authenticationEntryPoint() {
////        return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
////    }
////}
//
//
//package com.ginasiouniforagenda.AgendamentoWeb.infra.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfigurations {
//
//    @Autowired
//    private SecurityFilter securityFilter; // Injeta o seu filtro customizado
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                // 1. Desabilita CSRF por ser uma API stateless
//                .csrf(csrf -> csrf.disable())
//
//                // 2. Define a política de sessão como stateless
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//
//                // 3. Define as regras de autorização para os endpoints
//                .authorizeHttpRequests(authorize -> authorize
//                        // Endpoints públicos (não exigem autenticação)
//                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
//                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll() // Acesso ao H2 Console
//                        .requestMatchers("/error").permitAll() // Permite a página de erro padrão do Spring
//
//                        // Endpoints protegidos (exigem autenticação)
//                        .requestMatchers("/agendamento/**").authenticated() // <<-- CORREÇÃO PRINCIPAL: Regra explícita
//                        .requestMatchers(HttpMethod.POST, "/produto/cadastro").hasRole("ADMIN")
//
//                        // Qualquer outra requisição deve ser autenticada (boa prática de segurança)
//                        .anyRequest().authenticated()
//                )
//
//                // 4. Adiciona o seu filtro de JWT antes do filtro padrão do Spring
//                //    (passo CRÍTICO que estava faltando)
//                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
//
//                // 5. Permite que o H2 Console seja renderizado em um iframe
//                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
//                .build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}

//
//package com.ginasiouniforagenda.AgendamentoWeb.infra.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Arrays;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfigurations {
//
//    @Autowired
//    private SecurityFilter securityFilter;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                // 1. HABILITA CORS PRIMEIRO - CRÍTICO!
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//
//                // 2. Desabilita CSRF por ser uma API stateless
//                .csrf(csrf -> csrf.disable())
//
//                // 3. Define a política de sessão como stateless
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//
//                // 4. Define as regras de autorização para os endpoints
//                .authorizeHttpRequests(authorize -> authorize
//                        // IMPORTANTE: Permite requisições OPTIONS (preflight CORS)
//                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//
//                        // Endpoints públicos (não exigem autenticação)
//                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
//                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
//                        .requestMatchers("/error").permitAll()
//
//                        // Endpoints protegidos (exigem autenticação)
//                        .requestMatchers("/agendamento/**").authenticated()
//                        .requestMatchers(HttpMethod.POST, "/produto/cadastro").hasRole("ADMIN")
//
//                        // Qualquer outra requisição deve ser autenticada
//                        .anyRequest().authenticated()
//                )
//
//                // 5. Adiciona o filtro JWT
//                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
//
//                // 6. Permite H2 Console em iframe
//                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
//                .build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        // Origens permitidas
//        configuration.setAllowedOrigins(Arrays.asList(
//                "http://localhost:8081",
//                "http://localhost:8080",
//                "http://127.0.0.1:8081",
//                "http://127.0.0.1:8080"
//        ));
//
//        // Métodos HTTP permitidos
//        configuration.setAllowedMethods(Arrays.asList(
//                "GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"
//        ));
//
//        // Headers permitidos
//        configuration.setAllowedHeaders(Arrays.asList("*"));
//
//        // Permite credenciais
//        configuration.setAllowCredentials(true);
//
//        // Cache do preflight
//        configuration.setMaxAge(3600L);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}



package com.ginasiouniforagenda.AgendamentoWeb.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/agendamento/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/produto/cadastro").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8081"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}