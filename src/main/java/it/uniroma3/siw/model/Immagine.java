package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Immagine {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String nomeFile;
	private String path;

	@OneToOne
	@JoinColumn(name="opera_id")
	private Opera opera;

	@Override
	public int hashCode() {
		return Objects.hash(id, nomeFile, opera, path);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Immagine other = (Immagine) obj;
		return Objects.equals(id, other.id) && Objects.equals(nomeFile, other.nomeFile)
				&& Objects.equals(opera, other.opera) && Objects.equals(path, other.path);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Opera getOpera() {
		return opera;
	}

	public void setOpera(Opera opera) {
		this.opera = opera;
	}
	
	
}
