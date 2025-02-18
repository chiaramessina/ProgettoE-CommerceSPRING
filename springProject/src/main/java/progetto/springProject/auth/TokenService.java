package progetto.springProject.auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import progetto.springProject.model.User;
import progetto.springProject.repository.UserRepository;

import progetto.springProject.auth.AuthUser;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
//classe che incapsula e implementa la logica
@Service
public class TokenService {

	 // Mappa per associare un token (String) all'oggetto AuthUser
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
    	 // Genera un token univoco usando UUID
        String token = UUID.randomUUID().toString();
      
        System.out.println("Token generato: " + token); //stringa di debug in console
        
        // Associa il token all'utente autenticato nella mappa
        
        Optional<User> opt = userRepository.findByUsername(username);
         
        opt.get().setToken(token);
        userRepository.save(opt.get());
        
        return token;
    }

    /**
     * Restituisce l'oggetto AuthUser associato al token.
     *
     * @param token il token di autenticazione
     * @return l'oggetto AuthUser, oppure null se il token non è valido
     */
    public AuthUser getAuthUser(String token) {
    	
    	Optional<User> opt = userRepository.findByToken(token);
    	
    	return new AuthUser(opt.get().getUsername(), opt.get().getToken());
    	

    }

    /**
     * Rimuove un token dalla mappa, ad esempio durante il logout.
     *
     * @param token il token da rimuovere
     */
    public void removeToken(String token) {
    	
    	Optional<User> opt = userRepository.findByToken(token);
        
        opt.get().setToken(null);
        userRepository.save(opt.get());
    	

    }
}
