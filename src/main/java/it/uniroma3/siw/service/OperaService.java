package it.uniroma3.siw.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.repository.OperaRepository;

@Service
public class OperaService {
	 @Autowired
	    private OperaRepository operaRepository;

	    public void save(Opera opera) {
	         this.operaRepository.save(opera);
	    }

	    public List<Opera> findAll() {
	        return (List<Opera>) this.operaRepository.findAll();
	    }

		public Opera findById(Long operaId) {
			return this.operaRepository.findById(operaId).get();
		}
		
		public int sommaVoti(List<Opera> opere) {
	        int somma = 0;
	        for (Opera opera : opere) {
	            somma += opera.getVoto();
	        }
	        return somma;
	    }
}
