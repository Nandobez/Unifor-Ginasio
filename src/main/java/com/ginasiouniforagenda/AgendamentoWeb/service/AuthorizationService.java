package com.ginasiouniforagenda.AgendamentoWeb.service;

import com.ginasiouniforagenda.AgendamentoWeb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Usa findByLogin para carregar o usuário pelo username (que é o login no seu caso)
        UserDetails userDetails = repository.findByLogin(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }
        return userDetails;
    }
}