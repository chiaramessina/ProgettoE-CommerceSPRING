package progetto.springProject.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import progetto.springProject.auth.TokenService;
import progetto.springProject.model.Orders;
import progetto.springProject.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import progetto.springProject.auth.AuthUser;
import progetto.springProject.repository.OrdersRepository;
import progetto.springProject.repository.UserRepository;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = {}) // Disabilita richieste CORS da origini esterne
public class OrderController {

	@Autowired
	private OrdersRepository orderRepo;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private TokenService tokenService;
	
	public OrderController(UserRepository userRepository, OrdersRepository orderRepo) {
        this.userRepository = userRepository;
        this.orderRepo = orderRepo;
    }
	
	//ricerca tutti gli ordini dell'utente autorizzato
	//da verificare
	@GetMapping
	public ResponseEntity<?> getUserOrders(HttpServletRequest request) {
	    // Verifica l'autenticazione
	    AuthUser authUser = getAuthenticatedUser(request);
	    if (authUser == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(Collections.singletonMap("message", "Non autorizzato"));
	    }

	    // Cerca l'utente nel database
	    Optional<User> userOptional = userRepository.findByUsername(authUser.getUsername());
	    if (!userOptional.isPresent()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(Collections.singletonMap("message", "Utente non trovato"));
	    }

	    // Recupera gli ordini dell'utente
	    User user = userOptional.get();
	    List<Orders> orders = orderRepo.findByUser(user);

	    return ResponseEntity.ok(orders);
	}
	//metodo che ricerca l'ordine tramite l'ID UTENTE
	@GetMapping("/orders/{userId}")
	public ResponseEntity<?> getOrdersByUserId(@PathVariable Long userId) {
	    // Cerca l'utente nel database tramite l'ID
	    Optional<User> userOptional = userRepository.findById(userId);
	    if (!userOptional.isPresent()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(Collections.singletonMap("message", "Utente non trovato"));
	    }

	    // Recupera gli ordini dell'utente
	    User user = userOptional.get();
	    List<Orders> orders = orderRepo.findByUser(user);

	    return ResponseEntity.ok(orders);
	}
	 
	private AuthUser getAuthenticatedUser(HttpServletRequest request) {
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
	
	//aggiunge ordini
	@PostMapping("/addOrder")
	public ResponseEntity<?> addOrder(@RequestBody Orders newOrder, HttpServletRequest request) {
	    // Verifica l'autenticazione
	    AuthUser authUser = getAuthenticatedUser(request);
	    if (authUser == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(Collections.singletonMap("message", "Non autorizzato"));
	    }

	    // Trova l'utente nel database usando l'username di AuthUser
	    Optional<User> userOptional = userRepository.findByUsername(authUser.getUsername());

	    if (!userOptional.isPresent()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(Collections.singletonMap("message", "Utente non trovato"));
	    }

	    User user = userOptional.get(); // Recupera l'oggetto User

	    // Verifica che i dati essenziali siano presenti
	    if (newOrder.getTotal() == null || newOrder.getOrderDate() == null) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(Collections.singletonMap("message", "Dati ordine mancanti o non validi"));
	    }

	    // Associa l'ordine all'utente autenticato
	    newOrder.setUser(user);

	    // Salva il nuovo ordine nel database
	    Orders savedOrder = orderRepo.save(newOrder);

	    return ResponseEntity.status(HttpStatus.CREATED)
	            .body(Map.of("message", "Ordine aggiunto con successo", "orderId", savedOrder.getId()));
	}
}
