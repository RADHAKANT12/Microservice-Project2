package com.example.Client_Service.controller;



import com.example.Client_Service.payload.ClientDto;
import com.example.Client_Service.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private ClientService clientService;
    public ClientController(ClientService clientService){
        this.clientService=clientService;
    }

    // http://localhost:8081/api/clients
    @GetMapping
    public List<ClientDto> getAllClients() {
        return clientService.getAllClients();
    }
    // http://localhost:8081/api/clients/1


    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable Long id) {
        ClientDto dto = clientService.getClientById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    // http://localhost:8081/api/clients

    @PostMapping
    public ResponseEntity<ClientDto> saveClient(@RequestBody ClientDto clientDto) {
        ClientDto dto = clientService.saveClient(clientDto);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }
    // http://localhost:8081/api/clients/update/1

    @PutMapping("/update/{id}")
    public ResponseEntity<ClientDto> updateClientById(@PathVariable("id") Long id,@RequestBody ClientDto clientDto){
        ClientDto dto = clientService.updateClient(id, clientDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    // http://localhost:8081/api/clients/delete/1

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClientById(@PathVariable("id") Long id){
        clientService.deleteClient(id);
        return new ResponseEntity<String>("Client Entity Deleted !!!",HttpStatus.OK);
    }
}
