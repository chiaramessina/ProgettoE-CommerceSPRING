package progetto.springProject.controller;
import progetto.springProject.auth.TokenService;
import progetto.springProject.auth.AuthUser;
import progetto.springProject.model.User;
import progetto.springProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {})
public class AuthController {

	@Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Endpoint per effettuare il login.
     * Riceve username e password in formato JSON e restituisce il token se le credenziali sono valide.
     *
     * @param body     mappa contenente "username" e "password"
     * @param response oggetto HttpServletResponse per impostare lo status
     * @return mappa con un messaggio di conferma, il ruolo dell'utente e il token
     */
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body, HttpServletResponse response) {
        
    	 // Estrae username e password dalla richiesta JSON
    	String username = body.get("username");
        String password = body.get("password");

        // Mappa per la risposta
        Map<String, String> result = new HashMap<>();

        // Verifica che username e password siano stati forniti
        if (username == null || password == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            result.put("message", "Credenziali non valide");
            return result;
        }

        // Cerca l'utente nel database tramite username
        Optional<User> optionalUser = userRepository.findByUsername(username);
        // Se l'utente non esiste o la password non corrisponde, ritorna errore 401
        if (!optionalUser.isPresent() || !optionalUser.get().getPassword().equals(password)) {
            // Se l'utente non esiste oppure la password non corrisponde, restituisce 401 Unauthorized
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            result.put("message", "Credenziali non valide");
            return result;
        }

        // Se le credenziali sono corrette, recupera l'utente
        User user = optionalUser.get();

        // Genera un token associato all'utente
        String token = tokenService.generateToken(username);

        // Costruisce la risposta con messaggio e token
        result.put("message", "Login effettuato con successo");
        result.put("token", token);
        return result;
    }
    

    /**
     * Endpoint per effettuare il logout.
     * Riceve il token nell'header "Authorization" e lo rimuove dal TokenService, 
     * invalidando così il token lato client.
     *
     * @param authHeader header contenente il token (formato "Bearer <token>")
     * @return mappa con un messaggio di conferma del logout
     */
    @PostMapping("/logout")
    public Map<String, String> logout(@RequestHeader("Authorization") String authHeader) {
        String token = null;
// Se il token è inviato come "Bearer <token>", estrae la parte dopo "Bearer " e quindi il token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        } else {
            token = authHeader;
        }
        // Rimuove il token dal TokenService
        tokenService.removeToken(token);

        Map<String, String> result = new HashMap<>();
        result.put("message", "Logout effettuato con successo");
        return result;
    }
}
