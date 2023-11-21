import java.util.HashMap;
import java.util.Map;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }



    public String getPassword() {
        return password;
    }
}

class AuthenticationManager {
    private Map<String, User> registeredUsers;

    public AuthenticationManager() {
        this.registeredUsers = new HashMap<>();
    }

    public void registerUser(String username, String password) {
        User user = new User(username, password);
        registeredUsers.put(username, user);
    }

    public boolean authenticateUser(String username, String password) {
        User user = registeredUsers.get(username);
        return user != null && user.getPassword().equals(password);
    }
}