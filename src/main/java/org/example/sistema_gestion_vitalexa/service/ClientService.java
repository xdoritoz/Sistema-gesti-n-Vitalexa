package org.example.sistema_gestion_vitalexa.service;

import org.example.sistema_gestion_vitalexa.dto.ClientResponse;
import org.example.sistema_gestion_vitalexa.entity.Client;

import java.util.UUID;

public interface ClientService {
    ClientResponse findById(UUID id);
    Client findEntityById(UUID id);
}
