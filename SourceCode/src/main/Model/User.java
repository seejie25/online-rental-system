package main.Model;

/**
 * Implements MVC and DAO design patterns. A Model to manage User data and
 * containing getter and setter methods.
 */
public class User {
    private final String username;
    private final String password;
    private final String first_name;
    private final String last_name;
    private final String phone_number;
    private final String email;
    private final String user_type;

    public User(String username, String password, String first_name, String last_name, String phone_number, String email, String user_type) {
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.email = email;
        this.user_type = user_type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getEmail() {
        return email;
    }

    public String getUser_type() {
        return user_type;
    }
}
