package progetto.springProject.controller;

import progetto.springProject.auth.AuthUser;
import progetto.springProject.auth.TokenService;
import progetto.springProject.repository.UserRepository;
import progetto.springProject.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {}) // Disabilita richieste CORS da origini esterne
public class UserController {

	 @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private TokenService tokenService;

	    /**
	     * Endpoint pubblico che restituisce la lista dei nomi degli utenti.
	     *
	     * @return Lista di nomi (String) ottenuti chiamando getName() per ogni utente
	     */
	    @GetMapping("/names")
	    public List<String> getUserNames() {
	 // Usa il metodo map per trasformare la lista di User in una lista di String contenenti il nome       
	        return userRepository.findAll().stream()
	                .map(User::getName) // Equivalente a: user -> user.getName()
	                .collect(Collectors.toList());
	    }

	    /**
	     * Endpoint privato che restituisce i dettagli di un singolo utente.
	     * Richiede il token di autenticazione nell'header "Authorization".
	     *
	     * @param id       L'ID dell'utente da cercare
	     * @param request  Oggetto HttpServletRequest per leggere l'header "Authorization"
	     * @param response Oggetto HttpServletResponse per impostare lo status in caso di errore
	     * @return L'oggetto User se trovato, altrimenti un messaggio di errore
	     */
	    @GetMapping("/{id}")
	    public Object getUserDetails(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
	    	 // Ottiene l'utente autenticato dal token
	    	AuthUser authUser = getAuthenticatedUser(request);
	        if (authUser == null) {
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            return Collections.singletonMap("message", "Non autorizzato");
	        }

	        // Cerca l'utente per ID
	        Optional<User> userOpt = userRepository.findById(id);
	        if (!userOpt.isPresent()) {
	            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	            return Collections.singletonMap("message", "Utente non trovato");
	        }
	        return userOpt.get();
	    }

	    /**
	     * Endpoint privato che restituisce la lista delle email di tutti gli utenti.
	     * Richiede un token valido nell'header "Authorization".
	     *
	     * @param request  Oggetto HttpServletRequest per leggere l'header
	     * @param response Oggetto HttpServletResponse per impostare lo status
	     * @return Lista di email degli utenti (ottenuta con User::getEmail)
	     */
	    @GetMapping("/emails")
	    public Object getUserEmails(HttpServletRequest request, HttpServletResponse response) {
	        // Verifica la presenza di un token valido
	    	AuthUser authUser = getAuthenticatedUser(request);
	        if (authUser == null) {
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            return Collections.singletonMap("message", "Non autorizzato");
	        }

	        // Trasforma la lista di User in una lista di email
	        return userRepository.findAll().stream()
	                .map(User::getEmail)
	                .collect(Collectors.toList());
	    }

	    /**
	     * Endpoint privato che restituisce la lista completa degli utenti.
	     * Richiede un token di autenticazione valido.
	     *
	     * @param request  Oggetto HttpServletRequest per leggere l'header "Authorization"
	     * @param response Oggetto HttpServletResponse per impostare lo status in caso di errore
	     * @return Lista completa degli utenti, oppure un messaggio di errore
	     */
	    @GetMapping("/full")
	    public Object getFullUsers(HttpServletRequest request, HttpServletResponse response) {
	    	  // Verifica l'autenticazione tramite il token
	    	AuthUser authUser = getAuthenticatedUser(request);
	        /*if (authUser == null) {
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            return Collections.singletonMap("message", "Non autorizzato");
	        }*/

	        return userRepository.findAll();
	    }

	    /**
	     * Endpoint riservato agli amministratori che consente di aggiungere un nuovo utente.
	     * Richiede un token valido e che l'utente autenticato abbia il ruolo "admin".
	     *
	     * @param newUser  Oggetto User da aggiungere (ricevuto in formato JSON)
	     * @param request  Oggetto HttpServletRequest per leggere l'header "Authorization"
	     * @param response Oggetto HttpServletResponse per impostare lo status in caso di errore
	     * @return Messaggio di successo oppure errore
	     */
	    @PostMapping("/addUser")
	    public Object addUser(@RequestBody User newUser, HttpServletRequest request, HttpServletResponse response) {
	    	// Verifica l'autenticazione
//	    	AuthUser authUser = getAuthenticatedUser(request);
//	        if (authUser == null) {
//	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//	            return Collections.singletonMap("message", "Non autorizzato");
//	        }
//	        
//	        // Salva il nuovo utente nel database
//	        newUser.setUsername(newUser.getName());
	    	
	        userRepository.save(newUser);
	        return Collections.singletonMap("message", "Utente registrato con successo");
	    }

	    /**
	     * Metodo di utilità per estrarre il token di autenticazione dall'header "Authorization".
	     * Il token viene inviato nel formato "Bearer <token>".
	     *
	     * @param request Oggetto HttpServletRequest contenente gli header della richiesta
	     * @return L'oggetto AuthUser associato al token, oppure null se il token non è presente o non valido
	     */
	    
	    
	    @PostMapping("/checkLogin")
	    @CrossOrigin(origins = {})
	    private AuthUser getAuthenticatedUser(HttpServletRequest request) {
	    	
	    	System.out.println(request);
	        // Legge l'header "Authorization"
	        String authHeader = request.getHeader("Authorization");
	        if (authHeader != null && !authHeader.isEmpty()) {
	            String token;
	            // Se il token è inviato come "Bearer <token>", lo estrae
	            if (authHeader.startsWith("Bearer ")) {
	                token = authHeader.substring(7);
	            } else {
	                token = authHeader;
	            }
	            // Usa il TokenService per ottenere l'utente associato al token
	            return tokenService.getAuthUser(token);
	        }
	        // Se non c'è header "Authorization", restituisce null
	        return null;
	    }
	    
	    /*//per visualizzare il profilo
	    @GetMapping("/me")
	    public Object getAuthenticatedUserDetails(HttpServletRequest request, HttpServletResponse response) {
	        // Verifica l'utente autenticato tramite il token
	        AuthUser authUser = getAuthenticatedUser(request);
	        if (authUser == null) {
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            return Collections.singletonMap("message", "Non autorizzato");
	        }

	        // Cerca l'utente nel database usando l'username ottenuto dal token
	        Optional<User> userOpt = userRepository.findByUsername(authUser.getUsername());
	        if (!userOpt.isPresent()) {
	            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	            return Collections.singletonMap("message", "Utente non trovato");
	        }

	        // Restituisce l'utente autenticato
	        return userOpt.get();
	    }*/
	   /* @PutMapping("/me")
	    public Object updateProfile(@RequestBody Map<String, String> updates, HttpServletRequest request, HttpServletResponse response) {
	        // Ottieni l'utente autenticato dal token
	        AuthUser authUser = getAuthenticatedUser(request);
	        if (authUser == null) {
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            return Collections.singletonMap("message", "Non autorizzato");
	        }

	        // Trova l'utente nel database tramite username
	        Optional<User> userOpt = userRepository.findByUsername(authUser.getUsername());
	        if (!userOpt.isPresent()) {
	            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	            return Collections.singletonMap("message", "Utente non trovato");
	        }

	        // Aggiorna i dati dell'utente con i nuovi valori
	        User user = userOpt.get();
	        if (updates.containsKey("username")) {
	            user.setUsername(updates.get("username"));
	        }
	        if (updates.containsKey("name")) {
	            user.setName(updates.get("name"));
	        }
	        if (updates.containsKey("email")) {
	            user.setEmail(updates.get("email"));
	        }

	        // Salva l'utente aggiornato nel database
	        userRepository.save(user);

	        return Collections.singletonMap("message", "Profilo aggiornato con successo");
	    }*/
}
