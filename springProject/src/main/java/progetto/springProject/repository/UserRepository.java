package progetto.springProject.repository;
import progetto.springProject.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaccia per il repository degli utenti.
 * Estende JpaRepository per fornire operazioni CRUD standard (create, read, update, delete).
 * Grazie a Spring Data JPA, non è necessario implementare manualmente le query di base.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
     * Metodo custom per trovare un utente in base al suo username.
     *
     * @param username Il nome utente da cercare
     * @return Optional contenente l'utente se presente, altrimenti vuoto
     */
	Optional<User> findByUsername(String username);
    // Metodi customizzati possono essere aggiunti qui, se necessario.
}
