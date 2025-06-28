package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;



@Service
public class UtenteService {
	@Autowired
    private UtenteRepository utenteRepository;

    public void save(Utente utente) {
        this.utenteRepository.save(utente);
    }

    public Utente findByCredenzialiUsername(String username) {
        return this.utenteRepository.findByCredenzialiUsername(username);
    }
}
