package progetto.springProject.auth;

//Classe utilizzata per tenere traccia di username e ruolo associato a un token.
public class AuthUser {
	// Username dell'utente autenticato
    private String username;
    
    // Ruolo dell'utente autenticato (es. "admin" o "user")
    private String role;

    /**
     * Costruttore che inizializza l'oggetto AuthUser con username e ruolo.
     *
     * @param username Username dell'utente
     * @param role     Ruolo dell'utente
     */
    public AuthUser(String username, String role) {
       this.username = username;
       this.role = role;
    }

    // Getter e Setter per username e ruolo

    public String getUsername() {
       return username;
    }

    public void setUsername(String username) {
       this.username = username;
    }

    public String getRole() {
       return role;
    }

    public void setRole(String role) {
       this.role = role;
    }
}
