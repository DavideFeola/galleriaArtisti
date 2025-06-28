package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Votazione;
import it.uniroma3.siw.repository.VotazioneRepository;

@Service
public class VotazioneService {

	@Autowired
    private VotazioneRepository votazioneRepository;

    public List<Votazione> findAll() {
        return (List<Votazione>) this.votazioneRepository.findAll();
    }

    public void save(Votazione votazione) {
        this.votazioneRepository.save(votazione);
    }

    public void delete(Votazione votazione) {
        this.votazioneRepository.delete(votazione);
    }

}