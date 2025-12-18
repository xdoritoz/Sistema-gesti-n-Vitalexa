package org.example.sistema_gestion_vitalexa.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.sistema_gestion_vitalexa.dto.ClientResponse;
import org.example.sistema_gestion_vitalexa.dto.CreateClientRequest;
import org.example.sistema_gestion_vitalexa.entity.Client;
import org.example.sistema_gestion_vitalexa.exceptions.BusinessExeption;
import org.example.sistema_gestion_vitalexa.mapper.ClientMapper;
import org.example.sistema_gestion_vitalexa.repository.ClientRepository;
import org.example.sistema_gestion_vitalexa.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;
    private final ClientMapper clientMapper;


    @Override
    public Client findEntityById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessExeption("Cliente no encontrado"));
    }

    @Override
    public ClientResponse findById(UUID id) {
        return clientMapper.toResponse(findEntityById(id));
    }

    @Override
    public ClientResponse create(CreateClientRequest request) {
        // Verificar si ya existe un cliente con ese email
        if (repository.existsByEmail(request.email())) {
            throw new BusinessExeption("Ya existe un cliente con ese email");
        }

        Client client = clientMapper.toEntity(request);
        Client savedClient = repository.save(client);
        return clientMapper.toResponse(savedClient);
    }



    @Override
    public List<ClientResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(clientMapper::toResponse)
                .toList();
    }


    @Override
    public void delete(UUID id) {
        Client client = findEntityById(id);
        repository.delete(client);
    }


    @Override
    public ClientResponse update(UUID id, CreateClientRequest request) {
        Client client = findEntityById(id);

        // Verificar si el email ya está en uso por otro cliente
        if (!client.getEmail().equals(request.email()) &&
                repository.existsByEmail(request.email())) {
            throw new BusinessExeption("El email ya está en uso por otro cliente");
        }

        // Actualizar campos
        client.setNombre(request.nombre());
        client.setEmail(request.email());
        client.setTelefono(request.telefono());
        client.setDireccion(request.direccion());

        Client updatedClient = repository.save(client);
        return clientMapper.toResponse(updatedClient);
    }
}
