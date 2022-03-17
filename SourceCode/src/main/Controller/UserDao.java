package main.Controller;

import main.Model.User;

import java.util.ArrayList;

/**
 * Implements DAO design pattern. An interface defining the DAO API.
 */
public interface UserDao {
    ArrayList<User> view(String sql);
    void register(User user);
    void update(User user);
    void delete(String username);
    boolean checkProperty(String username);
}
