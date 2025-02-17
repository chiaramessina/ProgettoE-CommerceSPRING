package progetto.springProject.auth;
import org.springframework.stereotype.Service;

import progetto.springProject.auth.AuthUser;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
//classe che incapsula e implementa la logica
@Service
public class TokenService {
	
	// Mappa per associare un token (String) all'oggetto AuthUser
    private Map<String, AuthUser> tokens = new ConcurrentHashMap<>();

    /**
     * Metodo che genera un token casuale (UUID) e lo associa ad un utente autenticato.
     *
     * @param username lo username dell'utente
     * @param role     il ruolo dell'utente (es. admin o user)
     * @return il token generato
     */
    public String generateToken(String username, String role) {
    	 // Genera un token univoco usando UUID
        String token = UUID.randomUUID().toString();
      
        System.out.println("Token generato: " + token); //stringa di debug in console
        
        // Associa il token all'utente autenticato nella mappa
        tokens.put(token, new AuthUser(username, role));
        return token;
    }

    /**
     * Restituisce l'oggetto AuthUser associato al token.
     *
     * @param token il token di autenticazione
     * @return l'oggetto AuthUser, oppure null se il token non è valido
     */
    public AuthUser getAuthUser(String token) {
        return tokens.get(token);
    }

    /**
     * Rimuove un token dalla mappa, ad esempio durante il logout.
     *
     * @param token il token da rimuovere
     */
    public void removeToken(String token) {
        tokens.remove(token);
    }
}
