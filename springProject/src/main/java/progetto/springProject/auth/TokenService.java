package progetto.springProject.auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import progetto.springProject.auth.AuthUser;
import progetto.springProject.model.User;
import progetto.springProject.repository.UserRepository;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
//classe che incapsula e implementa la logica
@Service
public class TokenService {
	@Autowired
	private UserRepository userRepository;

    /**
     * Metodo che genera un token casuale (UUID) e lo associa ad un utente autenticato.
     *
     * @param username lo username dell'utente
     * @param role     il ruolo dell'utente (es. admin o user)
     * @return il token generato
     */
	public String generateToken(String username) {
	    Optional<User> optionalUser = userRepository.findByUsername(username); //cerca utente nel db in base all'username

	        String token = UUID.randomUUID().toString();  // viene generato un token in modo random e vene convertito in stringa 
	        optionalUser.get().setToken(token);  //viene associato token creato random all'utente
	        userRepository.save(optionalUser.get());  // Salva l'utente aggiornato nel DB
	        System.out.println(optionalUser.get());
	        System.out.println(token);
	        return token;  // Ritorna il token
	   }

    /**
     * Restituisce l'oggetto AuthUser associato al token.
     *
     * @param token il token di autenticazione
     * @return l'oggetto AuthUser, oppure null se il token non è valido
     */
	public AuthUser getAuthUser(String token) {
	    Optional<User> optionalUser = userRepository.findByToken(token);  //cerca utente nel db in base al token
	        User user = optionalUser.get();   //.get ritorna i dati dell'utente trovato nel db
	        return new AuthUser(user.getUsername()); // ritorna l'authuser con i dati dell'utente
	}

    /**
     * Rimuove un token dalla mappa, ad esempio durante il logout.
     *
     * @param token il token da rimuovere
     */
	 public void removeToken(String token) {
	        Optional<User> optionalUser = userRepository.findByToken(token); //cerca utente nel db in base all'username
	            User user = optionalUser.get();    //.get prende il token dell'utente esistente
	            user.setToken(null);  // setta token su null
	            userRepository.save(user);  // Salva l'utente con il token azzerato
	}
}
