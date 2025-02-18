package progetto.springProject;
import progetto.springProject.model.User;
import progetto.springProject.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Componente che carica dati iniziali nel database al momento dell'avvio dell'applicazione.
 * Implementa l'interfaccia CommandLineRunner, che esegue il metodo run() dopo l'avvio.
 */
@Component
public class DataLoader implements CommandLineRunner{
	
	private final UserRepository userRepository;

    /**
     * Iniezione del repository tramite costruttore.
     *
     * @param userRepository Repository per la gestione degli utenti.
     */
    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //se il db è vuoto, inserisce utenti di esempio
    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            // Creazione di un utente admin
            User adminUser = new User(
                null,         // ID (generato automaticamente)
                "mariobross",      // username
                "Mario",      // nome
                "Rossi",      //cognome
                "mariopass",   // password (in chiaro per semplicità)
                "mario.rossi@example.com", // email
                null
            );
            userRepository.save(adminUser);

            // Creazione di un utente normale
            User normalUser = new User(
                null,
                "giovannino",
                "Giovanni",
                "Bianchi",
                "giovannipass",
                "giovanni.bianchi@example.com",
                null
            );
            userRepository.save(normalUser);

            // Creazione di un altro utente di esempio
            User user2 = new User(
                null,
                "annina",
                "Anna",
                "Verdi",
                "annapass",
                "anna.verdi@example.com",
                null
            );
            userRepository.save(user2);
        }
    }

}
