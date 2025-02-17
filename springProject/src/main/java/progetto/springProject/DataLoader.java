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
                null,               // ID (generato automaticamente)
                "admin",            // username
                "adminpass",        // password (in chiaro per semplicità)
                "admin",            // ruolo
                "Mario Rossi",      // nome completo
                "mario.rossi@example.com" // email
            );
            userRepository.save(adminUser);

            // Creazione di un utente normale
            User normalUser = new User(
                null,
                "user",
                "userpass",
                "user",
                "Luigi Bianchi",
                "luigi.bianchi@example.com"
            );
            userRepository.save(normalUser);

            // Creazione di un altro utente di esempio
            User user2 = new User(
                null,
                "anna",
                "annapass",
                "user",
                "Anna Verdi",
                "anna.verdi@example.com"
            );
            userRepository.save(user2);
        }
    }

}
