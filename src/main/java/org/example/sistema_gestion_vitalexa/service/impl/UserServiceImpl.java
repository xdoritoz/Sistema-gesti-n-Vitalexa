package org.example.sistema_gestion_vitalexa.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.sistema_gestion_vitalexa.entity.User;
import org.example.sistema_gestion_vitalexa.exceptions.BusinessExeption;
import org.example.sistema_gestion_vitalexa.repository.UserRepository;
import org.example.sistema_gestion_vitalexa.service.UserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public User findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessExeption("Usuario no encontrado"));
    }

    @Override
    public User getAuthenticatedUser() {
        // temporal (luego JWT)
        return repository.findAll().get(0);
    }

}
