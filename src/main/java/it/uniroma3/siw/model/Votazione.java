package it.uniroma3.siw.model;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Votazione {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long mittenteId;
    
    @ManyToOne
    private Artista destinatario;
    @ManyToOne
    private Opera opera;
    
    private int voto;

	@Override
	public int hashCode() {
		return Objects.hash(destinatario, id, mittenteId, opera, voto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Votazione other = (Votazione) obj;
		return Objects.equals(destinatario, other.destinatario) && Objects.equals(id, other.id)
				&& Objects.equals(mittenteId, other.mittenteId) && Objects.equals(opera, other.opera)
				&& voto == other.voto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMittenteId() {
		return mittenteId;
	}

	public void setMittenteId(Long mittenteId) {
		this.mittenteId = mittenteId;
	}

	public Artista getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Artista destinatario) {
		this.destinatario = destinatario;
	}

	public Opera getOpera() {
		return opera;
	}

	public void setOpera(Opera opera) {
		this.opera = opera;
	}

	public int getVoto() {
		return voto;
	}

	public void setVoto(int voto) {
		this.voto = voto;
	}

    
   
}
