package com.ginasiouniforagenda.AgendamentoWeb.controller;

import com.ginasiouniforagenda.AgendamentoWeb.domain.user.AuthenticationDTO;
import com.ginasiouniforagenda.AgendamentoWeb.domain.user.RegisterDTO;
import com.ginasiouniforagenda.AgendamentoWeb.domain.user.User;
import com.ginasiouniforagenda.AgendamentoWeb.infra.security.TokenService;
import com.ginasiouniforagenda.AgendamentoWeb.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticantionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository; // Renomeado para 'repository' para seguir o padrão

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        try {
            // Cria um token de autenticação com o login e a senha fornecidos
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            // Tenta autenticar o usuário
            var auth = this.authenticationManager.authenticate(usernamePassword);

            // Se a autenticação for bem-sucedida, gera um token JWT
            var token = tokenService.generateToken((User) auth.getPrincipal());

            // Retorna o token JWT no corpo da resposta
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            // Se a autenticação falhar, retorna um erro 401 Unauthorized com uma mensagem
            return ResponseEntity.status(401).body("Erro: Login ou senha inválidos. Verifique se você está registrado.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        // Verifica se já existe um usuário com o mesmo login
        if(this.repository.findByLogin(data.login()) != null) {
            // Se existir, retorna um erro 400 Bad Request
            return ResponseEntity.badRequest().body("Login já existe. Por favor, escolha outro.");
        }

        // Criptografa a senha antes de salvar no banco de dados
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        // Cria um novo objeto User com os dados fornecidos
        User newUser = new User(data.login(), data.email(), encryptedPassword, data.role());

        // Salva o novo usuário no banco de dados
        this.repository.save(newUser);

        // Retorna uma resposta 200 OK
        return ResponseEntity.ok().build();
    }
}