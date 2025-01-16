package com.ilyass.microservicecommandes.controller;

import com.ilyass.microservicecommandes.config.CustomConfigProperties;
import com.ilyass.microservicecommandes.exceptions.CommandeNotFoundException;
import com.ilyass.microservicecommandes.model.Commande;
import com.ilyass.microservicecommandes.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CommandeController implements HealthIndicator {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private CustomConfigProperties customConfigProperties;

    // Affiche la liste de toutes les commandes disponibles
    @GetMapping(value = "/Commandes")
    public List<Commande> listeDesCommandes() {
        System.out.println("********* CommandeController listeDesCommandes() ");

        List<Commande> commandes = commandeRepository.findAll();

        if (commandes.isEmpty()) {
            throw new CommandeNotFoundException("Aucune commande n'est disponible.");
        }

        List<Commande> listeLimitee = commandes.subList(0, customConfigProperties.getCommandesLast());

        return listeLimitee;
    }

    // Récupérer une commande par son id
    @GetMapping(value = "/Commandes/{id}")
    public Optional<Commande> recupererUneCommande(@PathVariable Long id) {
        System.out.println("********* CommandeController recupererUneCommande(@PathVariable Long id) ");

        Optional<Commande> commande = commandeRepository.findById(id);

        if (!commande.isPresent()) {
            throw new CommandeNotFoundException("La commande correspondant à l'id " + id + " n'existe pas");
        }

        return commande;
    }

    @Override
    public Health health() {
        System.out.println("****** Actuator : CommandeController health() ");
        List<Commande> commandes = commandeRepository.findAll();

        if (commandes.isEmpty()) {
            return Health.down().build();
        }

        return Health.up().build();
    }
}
