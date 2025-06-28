package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
public class Opera {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank
	    private String titolo;
	    @NotBlank
	    private String descrizione;
	    @NotNull
	    private int voto;
	    
	    @NotBlank
	    private String correnteArtistica;
	    
	    @OneToOne(mappedBy = "opera", cascade = CascadeType.ALL)
	    private Immagine immagine;
	    
	    @NotNull
		@Min(1800)
	    @Max(2025)
	    private Integer annoCreazione;
	    	    

	    @ManyToOne
	    private Artista artista;

	    @OneToMany(mappedBy = "opera")
	    private List<Votazione> votazioni;

		@Override
		public int hashCode() {
			return Objects.hash(annoCreazione, artista, correnteArtistica, descrizione, id, immagine, titolo, votazioni,
					voto);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Opera other = (Opera) obj;
			return Objects.equals(annoCreazione, other.annoCreazione) && Objects.equals(artista, other.artista)
					&& Objects.equals(correnteArtistica, other.correnteArtistica)
					&& Objects.equals(descrizione, other.descrizione) && Objects.equals(id, other.id)
					&& Objects.equals(immagine, other.immagine) && Objects.equals(titolo, other.titolo)
					&& Objects.equals(votazioni, other.votazioni) && voto == other.voto;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getTitolo() {
			return titolo;
		}

		public void setTitolo(String titolo) {
			this.titolo = titolo;
		}

		public String getDescrizione() {
			return descrizione;
		}

		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}

		public int getVoto() {
			return voto;
		}

		public void setVoto(int voto) {
			this.voto = voto;
		}

		public String getCorrenteArtistica() {
			return correnteArtistica;
		}

		public void setCorrenteArtistica(String correnteArtistica) {
			this.correnteArtistica = correnteArtistica;
		}

		public Immagine getImmagine() {
			return immagine;
		}

		public void setImmagine(Immagine immagine) {
			this.immagine = immagine;
		}

		public Integer getAnnoCreazione() {
			return annoCreazione;
		}

		public void setAnnoCreazione(Integer annoCreazione) {
			this.annoCreazione = annoCreazione;
		}

		public Artista getArtista() {
			return artista;
		}

		public void setArtista(Artista artista) {
			this.artista = artista;
		}

		public List<Votazione> getVotazioni() {
			return votazioni;
		}

		public void setVotazioni(List<Votazione> votazioni) {
			this.votazioni = votazioni;
		}

		    
}
