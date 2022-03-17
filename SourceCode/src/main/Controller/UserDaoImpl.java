package main.Controller;

import main.Database.dbConnection;
import main.Model.SingletonLogin;
import main.Model.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * Implements DAO design pattern. A concrete class implementing UserDao interface.
 * Contains lots of methods to work with 'user' table in the database.
 */
public class UserDaoImpl implements UserDao {
    private final SingletonLogin singletonLogin = SingletonLogin.getLoginInstance();
    private final Connection connection = dbConnection.getConnection();

    /**
     * Gets user details from database then create a user object and store
     * into an array list.
     *
     * @author Tee Ming Yuan
     * @param sql sql query
     * @return an array list containing user details
     */
    @Override
    public ArrayList<User> view(String sql) {
        ArrayList<User> userList = new ArrayList<>();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String fname = rs.getString("first_name");
                String lname = rs.getString("last_name");
                String phoneNum = rs.getString("phone_number");
                String email = rs.getString("email");
                String userType = rs.getString("user_type");

                User user = new User(username, password, fname, lname, phoneNum, email, userType);
                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    /**
     * Inserts a new row for user table in database.
     *
     * @@author Tee Ming Yuan
     * @param user User Object
     */
    @Override
    public void register(User user) {
        String sql = "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = null;

        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getFirst_name());
            pst.setString(4, user.getLast_name());
            pst.setString(5, user.getPhone_number());
            pst.setString(6, user.getEmail());
            pst.setString(7, user.getUser_type());

            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing row for user table in database.
     *
     * @@author Tee Ming Yuan
     * @param user User Object
     */
    @Override
    public void update(User user) {
        String sql = "UPDATE user SET username = ?, password = ?, first_name = ?, last_name = ?, phone_number = ?, email = ? WHERE username = ?";
        PreparedStatement pst = null;

        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getFirst_name());
            pst.setString(4, user.getLast_name());
            pst.setString(5, user.getPhone_number());
            pst.setString(6, user.getEmail());
            pst.setString(7, singletonLogin.getUsername());

            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes an existing row for user table in database.
     *
     * @author Vizeard Yeow
     * @param username selected user's username
     */
    @Override
    public void delete(String username) {
        String sql;
        PreparedStatement pst = null;

        try {
            sql = "DELETE FROM user WHERE username = ?";
            pst = connection.prepareStatement(sql);
            pst.setString(1, username);

            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check whether if the user is having property.
     *
     * @@author Vizeard Yeow
     * @param username selected user's username
     * @return returns true if the user owns property
     */
    @Override
    public boolean checkProperty(String username) {
        String sql = "SELECT * FROM property WHERE owner_username = ?";
        PreparedStatement pst = null;

        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, username);

            ResultSet rs = pst.executeQuery();

            if (rs.next())
                return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
