package org.example.sistema_gestion_vitalexa.service;

import org.example.sistema_gestion_vitalexa.entity.Client;

import java.util.UUID;

public interface ClientService {
    Client findById(UUID id);
    Client save(Client client);
}
