package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.repository.ArtistaRepository;



@Service
public class ArtistaService {
	@Autowired
    private ArtistaRepository artistaRepository;
	
	 public void save(Artista artista) {
	        this.artistaRepository.save(artista);
	    }

	    public List<Artista> findAll() {
	        return (List<Artista>) this.artistaRepository.findAll();
	    }

	    public Artista findByCredenzialiUsername(String username) {
	        return this.artistaRepository.findByCredenzialiUsername(username);
	    }
	    
	    public Artista findById(Long id) {
	        return this.artistaRepository.findById(id).orElse(null);
	    }

}
