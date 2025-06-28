package it.uniroma3.siw.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.ArtistaService;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.OperaService;
import it.uniroma3.siw.service.UtenteService;


@Controller
public class RegisterController {
	@Autowired
    private CredenzialiService credenzialiService;

    @Autowired
    private ArtistaService artistaService;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OperaService operaService;

    @GetMapping("/register")
    public String showRegistrationForm(@RequestParam(value = "ruolo", required = false) Credenziali.Ruolo ruolo,
                                       Model model) {
        if (ruolo == null) {
            ruolo = Credenziali.Ruolo.ARTISTA;
        }

        model.addAttribute("ruoli", Credenziali.Ruolo.values());
        model.addAttribute("ruoloSelezionato", ruolo);

        return "register";
    }

    @GetMapping("/aggiorna")
    public String aggiorna(@RequestParam Credenziali.Ruolo ruolo, Model model) {
        model.addAttribute("ruoli", Credenziali.Ruolo.values());
        model.addAttribute("ruoloSelezionato", ruolo);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String ruolo,
                               @RequestParam(required = false) String nome,
                               @RequestParam(required = false) String cognome,
                               @RequestParam(required = false) String email,
                               Model model) {

        Credenziali.Ruolo ruoloEnum = Credenziali.Ruolo.valueOf(ruolo.toUpperCase());
        String encodedPassword = passwordEncoder.encode(password);

        if (credenzialiService.existsByUsername(username)) {
            model.addAttribute("esistente", "Username già esistente");
            return "register";
        }

        try {
            Credenziali credenziali = new Credenziali(username, encodedPassword, ruoloEnum);
            credenzialiService.save(credenziali);

            if (ruoloEnum == Credenziali.Ruolo.ARTISTA) {
                Artista artista = new Artista();
                artista.setNome(nome);
                artista.setCognome(cognome);
                artista.setEmail(email);
                artista.setCredenziali(credenziali);
                artistaService.save(artista);
            } else if (ruoloEnum == Credenziali.Ruolo.UTENTE) {
                Utente utente = new Utente();
                utente.setNome(nome);
                utente.setCognome(cognome);
                utente.setEmail(email);
                utente.setCredenziali(credenziali);
                utenteService.save(utente);
            }

            model.addAttribute("success", "Credenziali salvate con successo! Loggati per sfruttare tutte le risorse del sito!");
            return "login";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Errore durante la registrazione. Riprova.");
            return "register";
        }
    }
	

}
