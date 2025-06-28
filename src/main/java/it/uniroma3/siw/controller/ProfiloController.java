package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.ArtistaService;
import it.uniroma3.siw.service.UtenteService;

@Controller
public class ProfiloController {
    @Autowired
    private ArtistaService artistaService;

    @Autowired
    private UtenteService utenteService;
    
    //Questo metodo viene eseguito quando qualcuno accede alla pagina /profilo.
    @GetMapping("/profilo")
    public String profilo(Model model) {
    	//Recupera l'oggetto Authentication che rappresenta l'utente attualmente loggato.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String ruolo = null; //verrà poi riempito con il ruolo dell’utente (es. "UTENTE" o "ARTISTA")
        String username = auth.getName(); // getName() ritorna il nome dell'utente attualmente loggato
       
        //Itera su tutti i ruoli associati all'utente. Prende l'ultimo ruolo trovato 
        for (GrantedAuthority authority : auth.getAuthorities()) {
            ruolo = authority.getAuthority();
        }

        model.addAttribute("ruolo", ruolo);
        if ("UTENTE".equals(ruolo)) {
            //System.out.println("Username: " + username);
            Utente utente = utenteService.findByCredenzialiUsername(username);
            model.addAttribute("user", utente);
        } else if ("ARTISTA".equals(ruolo)) {
            Artista artista = artistaService.findByCredenzialiUsername(username);
            model.addAttribute("user", artista);
        }
        return "profilo.html";
    }
}

