package main.Model;

/**
 * Implements Singleton design pattern. Restricts only one user can access this
 * system simultaneously. Logouts form this system will clear the instance.
 *
 * @author Chang See Jie
 */
public class SingletonLogin {
    private static SingletonLogin loginInstance;
    private String username, usertype;

    private SingletonLogin() {}

    /**
     * Gets login instance.
     *
     * @author Chang See Jie
     * @return login instance
     */
    public static SingletonLogin getLoginInstance() {
        if (loginInstance == null) {
            loginInstance = new SingletonLogin();
        }
        return loginInstance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}
