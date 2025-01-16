package com.ilyass.microservicecommandes.controller;

import com.ilyass.microservicecommandes.model.Commande;
import com.ilyass.microservicecommandes.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {
    @Autowired
    private CommandeService commandeService;

    @GetMapping
    public List<Commande> getCommandes() {
        return commandeService.getAllCommandes();
    }

    @PostMapping
    public Commande saveCommande(@RequestBody Commande commande) {
        return commandeService.saveCommande(commande);
    }

    @DeleteMapping("/{id}")
    public void deleteCommande(@PathVariable Long id) {
        commandeService.deleteCommande(id);
    }
}
