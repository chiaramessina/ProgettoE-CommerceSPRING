package progetto.springProject.model;
import jakarta.persistence.*;

@Entity
@Table(name= "users")
public class User {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	//nome utente id univoco
	@Column(nullable= false, unique= true)
	private String username;
	
	 @Column(nullable = false)
	 private String password;
	 
	 //ruolo user o admin
	 @Column(nullable = false)
	 private String role;
	 
	 @Column(nullable = false)
	 private String name;
	 
	 //anche email univoca
	 @Column(nullable = false, unique = true)
	 private String email;

	 //costruttore default per jpa
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
	     */
	    public User(Long id, String username, String password, String role, String name, String email) {
	        this.id = id;
	        this.username = username;
	        this.password = password;
	        this.role = role;
	        this.name = name;
	        this.email = email;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

}
