package progetto.springProject;
import progetto.springProject.model.User;
import progetto.springProject.model.Orders;
import progetto.springProject.repository.UserRepository;
import progetto.springProject.repository.OrdersRepository;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Componente che carica dati iniziali nel database al momento dell'avvio dell'applicazione.
 * Implementa l'interfaccia CommandLineRunner, che esegue il metodo run() dopo l'avvio.
 */
@Component
public class DataLoader implements CommandLineRunner{
	
	private final UserRepository userRepository;
	
	private final OrdersRepository orderRepo;

    /**
     * Iniezione del repository tramite costruttore.
     *
     * @param userRepository Repository per la gestione degli utenti.
     */
    public DataLoader(UserRepository userRepository, OrdersRepository orderRepo) {
        this.userRepository = userRepository;
        this.orderRepo = orderRepo;
    }

    //se il db è vuoto, inserisce utenti di esempio
    @Transactional
    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            // Creazione utenti fittizi
            User user1 = new User(null, "mariobross", "Mario", "Rossi", "mariopass", "mario.rossi@example.com", null);
            User user2 = new User(null, "giovannino", "Giovanni", "Bianchi", "giovannipass", "giovanni.bianchi@example.com", null);
            User user3 = new User(null, "annina", "Anna", "Verdi", "annapass", "anna.verdi@example.com", null);

            user1 = userRepository.save(user1);
            user2 = userRepository.save(user2);
            user3 = userRepository.save(user3);

            // Creazione ordini fittizi per gli utenti
            Orders order1 = new Orders();
            order1.setOrderDate(new Date());
            order1.setTotal(100.00);
            order1.setUser(user1);
            orderRepo.save(order1);

            Orders order2 = new Orders();
            order2.setOrderDate(new Date());
            order2.setTotal(150.50);
            order2.setUser(user2);
            orderRepo.save(order2);

            Orders order3 = new Orders();
            order3.setOrderDate(new Date());
            order3.setTotal(200.75);
            order3.setUser(user3);
            orderRepo.save(order3);

            Orders order4 = new Orders();
            order4.setOrderDate(new Date());
            order4.setTotal(300.00);
            order4.setUser(user1);  // Mario fa un altro ordine
            orderRepo.save(order4);
        }
    }

}
