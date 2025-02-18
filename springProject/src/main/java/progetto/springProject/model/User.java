package progetto.springProject.model;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    /**
     * Identificativo primario, generato automaticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome utente per il login. Deve essere univoco.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * Password dell'utente.
     * **Nota Bene:** Per semplicità è salvata in chiaro, ma in un'applicazione reale
     * dovrebbe essere salvata in forma hashed (es. BCrypt).
     */
    @Column(nullable = false)
    private String password;

    /**
     * Ruolo dell'utente, es. "admin" o "user".
     */
    @Column(nullable = false)
    private String name;

    /**
     * Nome completo dell'utente.
     */
    @Column(nullable = false)
    private String surname;

    /**
     * Email dell'utente, anch'essa univoca.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Dettagli aggiuntivi relativi all'utente.
     */
    
    @Column(length = 36, nullable = true, unique = true)
    private String token;
    

    // Costruttore di default richiesto da JPA
    public User() {}

    /**
     * Costruttore con parametri.
     *
     * @param id       Identificativo
     * @param username Username per il login
     * @param password Password in chiaro (da non usare in produzione così)
     * @param role     Ruolo dell'utente (es. admin o user)
     * @param name     Nome completo dell'utente
     * @param email    Email dell'utente
     * @param details  Dettagli aggiuntivi
     */
    public User(Long id, String username, String password, String name, String email, String token, String surname) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.token = token;
   }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
  
    public void setPassword(String password) {
        this.password = password;
    }
 
  
    public String getName() {
        return name;
    }
  
    public void setName(String name) {
        this.name = name;
    }
  
    public String getEmail() {
        return email;
    }
  
    public void setEmail(String email) {
        this.email = email;
    }
  

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	
}
