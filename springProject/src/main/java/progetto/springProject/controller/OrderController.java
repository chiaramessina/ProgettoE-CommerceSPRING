package progetto.springProject.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
	private OrdersRepository userRepository;
	
	@Autowired
    private TokenService tokenService;
	
	//ricerca tutti gli ordini dell'utente autorizzato
	//da verificare
	 @GetMapping
	    public Object getUserOrders(HttpServletRequest request, HttpServletResponse response) {
	        // Verifica la presenza di un token valido
	    	AuthUser authUser = getAuthenticatedUser(request);
	        if (authUser == null) {
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            return Collections.singletonMap("message", "Non autorizzato");
	        }
	        
	        Optional<User> userOptional = userRepository.findByUsername(authUser.getUsername());
	        
	        if(!userOptional.isPresent()) {
	        	 response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	        	 
	        	 return Collections.singletonMap("message", "User not found");
	        }
	        User user= userOptional.get();
	        
	        List<Orders> orders = orderRepo.findByUser(user); 
	        return orders;
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
    public Object addOrder(@RequestBody Orders newOrder, HttpServletRequest request, HttpServletResponse response) {
    	// Verifica l'autenticazione
    	AuthUser authUser = getAuthenticatedUser(request);
        if (authUser == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Collections.singletonMap("message", "Non autorizzato");
        }
        
        // Salva il nuovo ordine nel database
        newOrder.setOrder_date(newOrder.getOrder_date());
        newOrder.setTotal(newOrder.getTotal());
        orderRepo.save(newOrder);
        return Collections.singletonMap("message", "Ordine aggiunto con successo");
    }
}
