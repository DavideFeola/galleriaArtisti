package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.service.ArtistaService;
import it.uniroma3.siw.service.OperaService;


@Controller
public class ClassificaController {
	@Autowired
    private ArtistaService artistaService;

    @Autowired
    private OperaService operaService;

    @GetMapping("/classifica")
    public String classifica(@RequestParam(value = "cognome", required = false) String cognome, Model model) {
        // Recupero tutti gli artisti
        List<Artista> artisti = new ArrayList<>(artistaService.findAll());

        // Calcolo e salvo il voto totale per ogni artista
        for (Artista artista : artisti) {
            int votoTotale = operaService.sommaVoti(artista.getOpere());
            artista.setVotoTotale(votoTotale);
            artistaService.save(artista);
        }

        // Filtraggio per cognome, se è stato inserito qualcosa (anche solo una lettera)
        if (cognome != null && !cognome.trim().isEmpty()) {
            String filtro = cognome.trim().toLowerCase();
            List<Artista> listaFiltrata = artisti.stream()
                .filter(a -> a.getCognome().toLowerCase().contains(filtro))
                .collect(Collectors.toList());

            if (listaFiltrata.isEmpty()) {
                model.addAttribute("errore", "Nessun Artista trovato con questo cognome!");
                return "classifica";
            }

            // Ordino la lista filtrata
            listaFiltrata.sort((a1, a2) -> {
                int diff = a2.getVotoTotale() - a1.getVotoTotale();
                if (diff == 0) {
                    return a1.getCognome().compareToIgnoreCase(a2.getCognome());
                }
                return diff;
            });

            model.addAttribute("artisti", listaFiltrata);
            model.addAttribute("cognome", cognome);
            return "classifica";
        }

        // Ordino la lista completa
        artisti.sort((a1, a2) -> {
            int diff = a2.getVotoTotale() - a1.getVotoTotale();
            if (diff == 0) {
                return a1.getCognome().compareToIgnoreCase(a2.getCognome());
            }
            return diff;
        });

        model.addAttribute("artisti", artisti);
        return "classifica";
    }
}
