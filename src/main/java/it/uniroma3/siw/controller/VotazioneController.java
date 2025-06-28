package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.Comparator.ComparatoreOpere;
import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.model.Votazione;
import it.uniroma3.siw.repository.OperaRepository;
import it.uniroma3.siw.service.ArtistaService;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.OperaService;
import it.uniroma3.siw.service.VotazioneService;

@Controller
public class VotazioneController {
	@Autowired
    private OperaService operaService;

    @Autowired
    private VotazioneService votazioneService;
    @Autowired
    private CredenzialiService credenzialiService;
    @Autowired
    private OperaRepository operaRepository;
    @Autowired
    private ArtistaService artistaService;

    @RequestMapping("/votazione")
    public String votazione(@RequestParam(value = "username") String username,
                            @RequestParam(value = "titolo", required = false) String titolo,
                            Model model) {
        // Verifica identità utente autenticato
        String usernameAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!username.equals(usernameAutenticato)) {
            return "erroreAutorizzazione";
        }

        // Recupero tutte le opere e le ordino
        List<Opera> opere = new ArrayList<>(operaService.findAll());
        opere.sort(new ComparatoreOpere());

        model.addAttribute("username", username);

        // Filtro per titolo, anche solo parziale e case-insensitive
        if (titolo != null && !titolo.trim().isEmpty()) {
            String filtro = titolo.trim().toLowerCase();
            List<Opera> opereFiltrate = opere.stream()
                    .filter(opera -> opera.getTitolo().toLowerCase().contains(filtro))
                    .collect(Collectors.toList());

            if (opereFiltrate.isEmpty()) {
                model.addAttribute("errore", "Nessuna opera trovata con questo titolo!");
                return "votazione";
            }

            model.addAttribute("opere", opereFiltrate);
            model.addAttribute("titolo", titolo); // utile per mantenere il valore cercato
            return "votazione";
        }

        model.addAttribute("opere", opere);
        return "votazione";
    }


    @GetMapping("/votaOpera")
    public String votaOpera(@RequestParam("username") String username, @RequestParam("operaId") Long operaId, Model model) {
        Opera opera = operaService.findById(operaId);
        List<Opera> opere = operaService.findAll();

        // Ricava l'username dell'utente attualmente autenticato
        String usernameAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verifica se l'utente sta cercando di accedere a un altro profilo
        if (!username.equals(usernameAutenticato)) {
            return "erroreAutorizzazione";
        }

        // Blocco per impedire che l'autore voti la propria opera
        if (username.equals(opera.getArtista().getCredenziali().getUsername())) {
            model.addAttribute("stessoUtente", "Non puoi votare un'opera di cui sei l'autore!");
            model.addAttribute("opere", opere);
            model.addAttribute("username", username);
            return "votazione";
        }

        //  utente ha già votato quest'opera?
        Long utenteId = credenzialiService.findIdByUsername(username);
        List<Votazione> votazioni = votazioneService.findAll();
        for (Votazione v : votazioni) {
            if (Objects.equals(v.getMittenteId(), utenteId) && v.getOpera().equals(opera)) {
                model.addAttribute("votoPassato", "Hai già votato questa opera! Elimina il voto per eseguirne un altro!");
                model.addAttribute("opere", opere);
                model.addAttribute("username", username);
                return "votazione";
            }
        }

        //mostra il form per votare
        model.addAttribute("opera", opera);
        model.addAttribute("username", username);
        return "votaOpera";
    }


    @PostMapping("/inviaVoto")
    public String inviaVoto(@RequestParam(value = "username") String username, @RequestParam("voto") int voto, @RequestParam("operaId") Long operaId, Model model) {
        List<Votazione> votazioni = votazioneService.findAll();
        Long utenteId = credenzialiService.findIdByUsername(username);
        
     // Ricava l'username dell'utente attualmente autenticato
        String usernameAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verifica se l'utente sta cercando di accedere a un altro profilo
        if (!username.equals(usernameAutenticato)) {
            // Puoi mostrare una pagina di errore o reindirizzare
            return "erroreAutorizzazione"; // crea un template errore_autorizzazione.html
        }

        Artista artista = operaService.findById(operaId).getArtista();
        Votazione votazione = new Votazione();
        votazione.setMittenteId(utenteId);
        votazione.setDestinatario(artista);
        votazione.setOpera(operaService.findById(operaId));
        votazione.setVoto(voto);
        votazioneService.save(votazione);
        int votoArtista = artista.getVotoTotale();
        int nuovoVotoArtista = votoArtista + voto;
        artista.setVotoTotale(nuovoVotoArtista);
        artistaService.save(artista);
        Opera opera = operaService.findById(operaId);
        int nuovoVoto = opera.getVoto() + voto;
        opera.setVoto(nuovoVoto);
        operaService.save(opera);
        List<Opera> opere = operaService.findAll();
        opere.sort(new ComparatoreOpere());
        model.addAttribute("username", username);
        model.addAttribute("opere", opere);
        model.addAttribute("votoInviato", "Voto salvato con successo!");
        return "votazione";
    }
    
    @GetMapping("/eliminaVoto")
    public String eliminaVoto(@RequestParam("username") String username, @RequestParam("operaId") Long operaId, Model model) {
        model.addAttribute("username", username);
        Opera opera = operaService.findById(operaId);
        List<Votazione> votazioni = votazioneService.findAll();
        Long utenteId = credenzialiService.findIdByUsername(username); 
     // Qui stampi l'utenteId per verificare che sia corretto
        System.out.println("utenteId: " + utenteId);
        
     // Ricava l'username dell'utente attualmente autenticato
        String usernameAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verifica se l'utente sta cercando di accedere a un altro profilo
        if (!username.equals(usernameAutenticato)) {
            // Puoi mostrare una pagina di errore o reindirizzare
            return "erroreAutorizzazione"; // crea un template errore_autorizzazione.html
        }
        
        for (Votazione votazione : votazioni) {
            if (Objects.equals(votazione.getMittenteId(), utenteId) && Objects.equals(votazione.getOpera(), operaService.findById(operaId))) {
                int votoSingolo = votazione.getVoto();
                int votoNuovo = opera.getVoto() - votoSingolo;
                opera.setVoto(votoNuovo);
                operaService.save(opera);
                Artista artista = opera.getArtista();
                int votoArtista = artista.getVotoTotale();
                int nuovoVotoArtista = votoArtista - votazione.getVoto();
                artista.setVotoTotale(nuovoVotoArtista);
                artistaService.save(artista);
                votazioneService.delete(votazione);
                model.addAttribute("votoEliminato", "Il voto è stato eliminato con successo!");
                List<Opera> opere = operaService.findAll();
                opere.sort(new ComparatoreOpere());
                model.addAttribute("opere", opere);
                return "votazione";
            }
        }
        model.addAttribute("nonVotato", "Non hai votato quest'opera!");
        List<Opera> opere = operaService.findAll();
        opere.sort(new ComparatoreOpere());
        model.addAttribute("opere", opere);
        return "votazione";
    }

    @GetMapping("/opera/{id}")
    public String getOpera(@PathVariable("id") Long id,
                           @RequestParam("username") String username,
                           Model model) {
    	
    	 // Ricava l'username dell'utente attualmente autenticato
        String usernameAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verifica se l'utente sta cercando di accedere a un altro profilo
        if (!username.equals(usernameAutenticato)) {
            // Puoi mostrare una pagina di errore o reindirizzare
            return "erroreAutorizzazione"; // crea un template errore_autorizzazione.html
        }
        
        model.addAttribute("opera", this.operaService.findById(id));
        model.addAttribute("username", username);
        return "opera";
    }

    
}
