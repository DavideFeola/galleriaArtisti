package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Artista {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank
	    private String nome;

	    @NotBlank
	    private String cognome;
	    
	    private String email;

	    //voto totale, somma dei voti delle opera -> nella classifica associato all'artista
	    private int votoTotale;

	    @OneToMany(mappedBy="artista", cascade = CascadeType.ALL)
	    private List<Opera> opere;

	    @OneToOne
	    private Credenziali credenziali;

	    @OneToMany(mappedBy = "destinatario")
	    private List<Votazione> votazioni;

		@Override
		public int hashCode() {
			return Objects.hash(cognome, email, id, nome, votoTotale);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Artista other = (Artista) obj;
			return Objects.equals(cognome, other.cognome) && Objects.equals(email, other.email)
					&& Objects.equals(id, other.id) && Objects.equals(nome, other.nome)
					&& votoTotale == other.votoTotale;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getCognome() {
			return cognome;
		}

		public void setCognome(String cognome) {
			this.cognome = cognome;
		}

		public String getEmail() {
			return this.email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public int getVotoTotale() {
			return votoTotale;
		}

		public void setVotoTotale(int votoTotale) {
			this.votoTotale = votoTotale;
		}

		public List<Opera> getOpere() {
			return opere;
		}

		public void setOpere(List<Opera> opere) {
			this.opere = opere;
		}

		public Credenziali getCredenziali() {
			return credenziali;
		}

		public void setCredenziali(Credenziali credenziali) {
			this.credenziali = credenziali;
		}

		public List<Votazione> getVotazioni() {
			return votazioni;
		}

		public void setVotazioni(List<Votazione> votazioni) {
			this.votazioni = votazioni;
		}
    
	    
	    
}
