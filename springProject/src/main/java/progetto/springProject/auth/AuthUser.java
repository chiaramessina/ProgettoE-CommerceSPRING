package progetto.springProject.auth;

//Classe utilizzata per tenere traccia di username associato a un token.
public class AuthUser {
	// Username dell'utente autenticato
    private String username;
    
    private Long id;
    

    /**
     * Costruttore che inizializza l'oggetto AuthUser con username
     *
     * @param username Username dell'utente
     */
    public AuthUser(String username) {
       this.username = username;
    }

    // Getter e Setter per username e ruolo

    public String getUsername() {
       return username;
    }

    public void setUsername(String username) {
       this.username = username;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
