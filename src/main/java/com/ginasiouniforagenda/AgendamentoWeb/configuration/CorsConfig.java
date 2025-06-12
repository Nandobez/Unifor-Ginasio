////package com.ginasiouniforagenda.AgendamentoWeb.configuration;
////
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.web.servlet.config.annotation.CorsRegistry;
////import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
////
////@Configuration
////public class CorsConfig {
////
////    @Bean
////    public WebMvcConfigurer corsConfigurer() {
////        return new WebMvcConfigurer() {
////            @Override
////            public void addCorsMappings(CorsRegistry registry) {
////                registry.addMapping("/**") // Aplica CORS a todos os endpoints
////                        .allowedOrigins("http://localhost:8081", "http://localhost:8080") // <-- **MUDE AQUI para a PORTA REAL DO SEU FRONTEND**
////                        // (No seu caso, o log diz que é 8081)
////                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
////                        .allowedHeaders("*") // Permite todos os cabeçalhos
////                        .allowCredentials(true); // Permite o envio de cookies de credenciais (se aplicável)
////            }
////        };
////    }
////}
//
//
//package com.ginasiouniforagenda.AgendamentoWeb.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig {
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins(
//                                "http://localhost:8081",
//                                "http://localhost:8080",
//                                "http://127.0.0.1:8081",
//                                "http://127.0.0.1:8080"
//                        )
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH")
//                        .allowedHeaders("*")
//                        .allowCredentials(true)
//                        .maxAge(3600); // Cache preflight por 1 hora
//            }
//        };
//    }
//}