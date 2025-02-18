package progetto.springProject.model;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name= "users")
public class User {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
//	@OneToMany(mappedBy = "user")
//	List<Orders> orders;
	
	//nome utente id univoco
	@Column(nullable= false, unique= true)
	private String username;
	
	@Column(nullable = false)
	 private String name;
	
	@Column(nullable = false)
	 private String surname;
	
	//anche email univoca
	@Column(nullable = false, unique = true)
	private String email;
		 
	 @Column(nullable = false)
	 private String password;
	 
	 //colonna token
	 @Column(nullable = true, unique = true, length = 36)
	 private String token;
	 
	 //costruttore default per jpa
	 public User() {}
	 /**
	     * Costruttore con parametri.
	     *
	     * @param id       Identificativo
	     * @param username Username per il login
	     * @param name     Nome dell'utente
	     * @param surname  Cognome dell'utente
	     * @param password Password in chiaro (da non usare in produzione così)
	     * @param email    Email dell'utente
	     * @param token    Token
	     */
	    public User(Long id, String username, String name, String surname, String password, String email, String token) {
	        this.id = id;
	        this.username = username;
	        this.name = name;
	        this.surname= surname;
	        this.password = password;
	        this.email = email;
	        this.token= token;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

}
