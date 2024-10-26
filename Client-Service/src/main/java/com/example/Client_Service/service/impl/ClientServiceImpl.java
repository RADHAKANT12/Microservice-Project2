package com.example.Client_Service.service.impl;

import com.example.Client_Service.entity.Client;
import com.example.Client_Service.exception.ResourceNotFoundException;
import com.example.Client_Service.payload.ClientDto;
import com.example.Client_Service.repository.ClientRepository;
import com.example.Client_Service.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    private ModelMapper mapper;
    private ClientRepository clientRepo;

    public ClientServiceImpl(ModelMapper mapper,ClientRepository clientRepo){
        this.mapper=mapper;
        this.clientRepo=clientRepo;
    }
    @Override
    public List<ClientDto> getAllClients() {
        List<Client> clients = clientRepo.findAll();
        List<ClientDto> dtos = clients.stream().map(client -> mapToDto(client)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public ClientDto getClientById(Long id) {
        Client client = clientRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Client", "Id", id)
        );
        return mapToDto(client);
    }

    @Override
    public ClientDto saveClient(ClientDto clientDto) {
        Client client = mapToEntity(clientDto);
        Client client1 = clientRepo.save(client);
        ClientDto dto = mapToDto(client1);
        return dto;
    }
    private Client mapToEntity(ClientDto clientDto) {
        Client client = mapper.map(clientDto, Client.class);
        return client;
    }
    private ClientDto mapToDto(Client client1) {
        ClientDto clientDto = mapper.map(client1, ClientDto.class);
        return clientDto;
    }

    @Override
    public ClientDto updateClient(Long id, ClientDto clientDto) {
        Client client = clientRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Client", "Id", id)
        );
        client.setId(clientDto.getId());
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setEmail(clientDto.getEmail());
        Client client1 = clientRepo.save(client);
        return mapToDto(client1);
    }

    @Override
    public void deleteClient(Long id) {
        Client client = clientRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Client", "Id", id)
        );
        clientRepo.delete(client);

    }
}
