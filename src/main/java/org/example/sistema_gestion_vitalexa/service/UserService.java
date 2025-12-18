package org.example.sistema_gestion_vitalexa.service;

import lombok.RequiredArgsConstructor;
import org.example.sistema_gestion_vitalexa.entity.User;
import org.springframework.stereotype.Service;

import java.util.UUID;


public interface UserService {
    User findById(UUID id);
    User getAuthenticatedUser();
}

