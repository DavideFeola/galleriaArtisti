package it.uniroma3.siw.model;
import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Credenziali {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotNull
	    private String username;
	    @NotNull
	    private String password;

	    @Enumerated(EnumType.STRING)
	    private Ruolo ruolo;
	    @OneToOne(mappedBy = "credenziali")
	    private Artista artista;
	    @OneToOne(mappedBy = "credenziali")
	    private Utente utente;

	    public Credenziali(String username, String password, Ruolo ruolo) {
	        this.username = username;
	        this.password = password;
	        this.ruolo = ruolo;
	    }

	    public Credenziali() {

	    }
	    
	    public Long getId() {
			return id;
		}
	    
		public void setId(Long id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public Ruolo getRuolo() {
			return ruolo;
		}

		public void setRuolo(Ruolo ruolo) {
			this.ruolo = ruolo;
		}

		public Artista getArtista() {
			return artista;
		}

		public void setArtista(Artista artista) {
			this.artista = artista;
		}

		public Utente getUtente() {
			return utente;
		}

		public void setUtente(Utente utente) {
			this.utente = utente;
		}

		@Override
		public int hashCode() {
			return Objects.hash(artista, id, password, ruolo, username);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Credenziali other = (Credenziali) obj;
			return Objects.equals(artista, other.artista) && Objects.equals(id, other.id)
					&& Objects.equals(password, other.password) && ruolo == other.ruolo
					&& Objects.equals(username, other.username);
		}

		public enum Ruolo {
	        ARTISTA,
	        UTENTE 
	    }
}
