package it.uniroma3.siw.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.model.Immagine;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.service.ArtistaService;
import it.uniroma3.siw.service.OperaService;


@RequestMapping("/artista")
@Controller
public class NuovaOperaController {
	    @Autowired
	    private ArtistaService artistaService;
	
	    @Autowired
	    private OperaService operaService;

	    @GetMapping("/nuovaOpera")
	    public String nuovaOpera(@RequestParam("username") String username, Model model) {
	        // Ricava l'username dell'utente attualmente autenticato
	        String usernameAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();

	        // Verifica se l'utente sta cercando di accedere a un altro profilo
	        if (!username.equals(usernameAutenticato)) {
	            // Puoi mostrare una pagina di errore o reindirizzare
	            return "erroreAutorizzazione"; // crea un template errore_autorizzazione.html
	        }

	        // Se l'utente è autorizzato
	        Artista artista = artistaService.findByCredenzialiUsername(username);
	        model.addAttribute("artistaId", artista.getId());

	        return "nuovaOpera";
	    }

	    @PostMapping("/operaAggiunta")
	    public String operaAggiunta(@RequestParam("titolo") String titolo,
	                                @RequestParam("descrizione") String descrizione,
	                                @RequestParam("artistaId") Long artistaId,
	                                @RequestParam("correnteArtistica") String correnteArtistica,
	                                @RequestParam("annoCreazione") Integer annoCreazione,
	                                @RequestParam("immagine") MultipartFile immagineFile,
	                                RedirectAttributes redirectAttributes,
	                                Model model) {
	    	

	        if (immagineFile == null || immagineFile.isEmpty()) {
	            redirectAttributes.addFlashAttribute("errore", "L'immagine è obbligatoria");
	            return "redirect:/nuovaOpera";
	        }

	        try {
	            // Salva il file su disco
	            String nomeFile = UUID.randomUUID().toString() + "_" + immagineFile.getOriginalFilename();
	            Path path = Paths.get("uploads/images/" + nomeFile);
	            Files.copy(immagineFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

	            // Crea Opera
	            Opera opera = new Opera();
	            opera.setTitolo(titolo);
	            opera.setDescrizione(descrizione);
	            opera.setVoto(0);
	            opera.setCorrenteArtistica(correnteArtistica);
	            opera.setAnnoCreazione(annoCreazione);

	            Artista artista = artistaService.findById(artistaId);
	            opera.setArtista(artista);
	            artista.getOpere().add(opera);

	            // Crea Immagine
	            Immagine immagine = new Immagine();
	            immagine.setNomeFile(nomeFile);
	            immagine.setPath("/images/" + nomeFile);
	            immagine.setOpera(opera); // facoltativo se unidirezionale

	            // Associa immagine all’opera
	            opera.setImmagine(immagine);

	            // Salva
	            operaService.save(opera);

	            redirectAttributes.addFlashAttribute("operaAggiunta", "Opera aggiunta con successo!");
	            return "redirect:/";

	        } catch (IOException e) {
	            e.printStackTrace();
	            redirectAttributes.addFlashAttribute("errore", "Errore nel salvataggio dell'immagine.");
	            return "redirect:/nuovaOpera";
	        }
	    }

}
