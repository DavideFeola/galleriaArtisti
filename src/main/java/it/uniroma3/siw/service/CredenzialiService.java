package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.repository.CredenzialiRepository;


@Service
public class CredenzialiService {
	
    @Autowired
    private CredenzialiRepository credenzialiRepository;

    public boolean existsByUsername(String username) {
        return credenzialiRepository.existsByUsername(username);
    }

    public void save(Credenziali credenziali) {
        this.credenzialiRepository.save(credenziali);
    }

    public Long findIdByUsername(String username) {
        return this.credenzialiRepository.findIdByUsername(username);
    }
}
