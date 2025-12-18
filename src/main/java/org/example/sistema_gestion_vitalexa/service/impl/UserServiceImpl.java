package org.example.sistema_gestion_vitalexa.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.sistema_gestion_vitalexa.dto.UserResponse;
import org.example.sistema_gestion_vitalexa.entity.User;
import org.example.sistema_gestion_vitalexa.exceptions.BusinessExeption;
import org.example.sistema_gestion_vitalexa.mapper.UserMapper;
import org.example.sistema_gestion_vitalexa.repository.UserRepository;
import org.example.sistema_gestion_vitalexa.service.UserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;

    @Override
    public User getAuthenticatedUser() {
        // temporal hasta JWT
        return repository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new BusinessExeption("No hay usuario autenticado"));
    }

    @Override
    public UserResponse findById(UUID id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new BusinessExeption("Usuario no encontrado"));

        return userMapper.toResponse(user);
    }
}

