package main.Controller;

import main.Database.dbConnection;
import main.Model.SingletonLogin;
import main.Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Implements MVC design pattern. A C-Controller class to control the flow
 * between User Model and View.
 */
public class UserController {
    private final UserDaoImpl userDaoImpl = new UserDaoImpl();
    private final SingletonLogin singletonLogin = SingletonLogin.getLoginInstance();

    public UserController() {}

    /**
     * Setups table model and printing user list in tabular format.
     *
     * @author Tee Ming Yuan
     * @param model default table model interface to store data for table
     * @param table table of users
     * @param sql sql query
     */
    public void printUserTable(DefaultTableModel model, JTable table, String sql) {
//        set the column name of user list table
        model.addColumn("Username");
        model.addColumn("Password");
        model.addColumn("First Name");
        model.addColumn("Last Name");
        model.addColumn("Phone Number");
        model.addColumn("Email");
        model.addColumn("User Type");
        table.setModel(model);

        ArrayList<User> userList = userDaoImpl.view(sql);

        Object[] row = new Object[7];
        for (User user : userList) {
            row[0] = user.getUsername();
            row[1] = user.getPassword();
            row[2] = user.getFirst_name();
            row[3] = user.getLast_name();
            row[4] = user.getPhone_number();
            row[5] = user.getEmail();
            row[6] = user.getUser_type();
            model.addRow(row);
        }
    }

    /**
     * Validates user register data.
     *
     * @@author Tee Ming Yuan & Vizeard Yeow
     * @param profileDetails array for user profile details
     * @return returns true if the data is valid
     */
    public boolean validateRegisterData(String[] profileDetails) {
        Connection connection = dbConnection.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM user WHERE username = '" + profileDetails[0] + "'");

            ArrayList<Integer> textFieldLength = new ArrayList<>();
            for (String s : profileDetails) {
                textFieldLength.add(s.length());
            }

            if (profileDetails[0].equals(singletonLogin.getUsername()) || (!rs.next())) {
                if (textFieldLength.contains(0))
                    JOptionPane.showMessageDialog(null, "Please fill in all the fields.");
                else {
                    if (!profileDetails[4].matches("[0-9]+"))
                        JOptionPane.showMessageDialog(null, "Please enter a valid phone number.");
                    else
                        return true;
                }
            }
            else {
                JOptionPane.showMessageDialog(new JFrame(), "This username is already in use.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a selected user.
     *
     * @@author Vizeard Yeow
     * @param selectedRow row number which has been selected
     * @param model default table model interface to store data for table
     * @param table table to show users
     */
    public void deleteUser(int selectedRow, DefaultTableModel model, JTable table) {
        String warning = "";

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(new JFrame(), "Please select a row.");
        }
        else {
            String username = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();

            if (userDaoImpl.checkProperty(username))
                warning = "Are you sure you want to delete " + username + "?\nAll properties uploaded by this user will also be deleted.";
            else
                warning = "Are you sure you want to delete " + username + "?";

            int confirm = JOptionPane.showConfirmDialog(null, warning);
            if (confirm == JOptionPane.YES_OPTION) {
                userDaoImpl.delete(username);
                JOptionPane.showMessageDialog(new JFrame(), "The user is deleted");

                model.setColumnCount(0);
                model.setRowCount(0);
                printUserTable(model, table, "SELECT * FROM user");
            }
        }
    }
}
