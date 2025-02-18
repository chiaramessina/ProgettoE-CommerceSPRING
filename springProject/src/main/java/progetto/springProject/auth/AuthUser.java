package progetto.springProject.auth;

//Classe utilizzata per tenere traccia di username associato a un token.
public class AuthUser {
	// Username dell'utente autenticato
    private String username;
    
    private String token;
    

    /**
     * Costruttore che inizializza l'oggetto AuthUser con username
     *
     * @param username Username dell'utente
     */
    public AuthUser(String username, String token) {
        this.username = username;
        this.token = token;
     }

     // Getter e Setter per username e ruolo

     public String getUsername() {
        return username;
     }

     public void setUsername(String username) {
        this.username = username;
     }

 	public String getToken() {
 		return token;
 	}

 	public void setToken(String token) {
 		this.token = token;
 	}

}
