package com.ma.service_voiture.controllers;

import com.ma.service_voiture.entities.Client;
import com.ma.service_voiture.entities.Voiture;
import com.ma.service_voiture.repositories.VoitureRepository;
import com.ma.service_voiture.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class VoitureController {

    @Autowired
    VoitureRepository voitureRepository;

    @Autowired
    ClientService clientService;

    @GetMapping("/voitures")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(voitureRepository.findAll());
    }

    @GetMapping("/voitures/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Voiture voiture = voitureRepository.findById(id)
                    .orElseThrow(() -> new Exception("Voiture introuvable"));

            Client client = clientService.clientById(voiture.getClientId());
            voiture.setClient(client);

            return ResponseEntity.ok(voiture);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Voiture not found with ID: " + id);
        }
    }

    @GetMapping("/voitures/client/{id}")
    public ResponseEntity<?> findByClient(@PathVariable Long id) {
        try {
            Client client = clientService.clientById(id);

            if (client == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Client not found");
            }

            List<Voiture> voitures = voitureRepository.findByClientId(id);
            return ResponseEntity.ok(voitures);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/voitures/{clientId}")
    public ResponseEntity<?> save(@PathVariable Long clientId, @RequestBody Voiture voiture) {
        try {
            Client client = clientService.clientById(clientId);

            if (client == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Client not found");
            }

            voiture.setClientId(clientId);
            voiture.setClient(client);

            Voiture saved = voitureRepository.save(voiture);
            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving voiture: " + e.getMessage());
        }
    }
}