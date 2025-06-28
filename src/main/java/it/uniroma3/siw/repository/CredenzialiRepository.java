package it.uniroma3.siw.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Credenziali;

public interface CredenzialiRepository extends CrudRepository<Credenziali,Long>{

    boolean existsByUsername(String username);

    @Query("SELECT c.id FROM Credenziali c WHERE c.username = :username")
    public Long findIdByUsername(String username);
}
