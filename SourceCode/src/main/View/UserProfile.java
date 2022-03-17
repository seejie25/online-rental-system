package main.View;

import main.Controller.UserController;
import main.Controller.UserDaoImpl;
import main.Database.dbConnection;
import main.Model.SingletonLogin;
import main.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserProfile extends JPanel {
    private JPanel mainP;
    private JPanel BodyP;
    private JLabel Username;
    private JLabel Password;
    private JLabel fname;
    private JLabel lname;
    private JLabel pnum;
    private JLabel email;
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private JTextField fnameTextField;
    private JTextField lnameTextField;
    private JTextField pnumTextField;
    private JTextField emailTextField;
    private JButton BACKButton;
    private JButton editSaveButton;

    private final UserDaoImpl userDaoImpl = new UserDaoImpl();
    private final UserController userContr = new UserController();
    private SingletonLogin singletonLogin = SingletonLogin.getLoginInstance();

    /**
     * Shows user profile and provides the function to edit profile details.
     *
     * @author Tee Ming Yuan
     */
    public UserProfile() {
        add(mainP);

        editSaveButton.setText("EDIT");

//        gets user profile data and place into text field accordingly
        try {
            String sql = "SELECT * FROM user WHERE username = '" + singletonLogin.getUsername() + "';";
            Connection connection = dbConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                usernameTextField.setText(rs.getString("username"));
                passwordTextField.setText(rs.getString("password"));
                fnameTextField.setText(rs.getString("first_name"));
                lnameTextField.setText(rs.getString("last_name"));
                pnumTextField.setText(rs.getString("phone_number"));
                emailTextField.setText(rs.getString("email"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error in connectivity" + ex);
        }

        editSaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField[] textFieldArray = {passwordTextField, fnameTextField, lnameTextField, pnumTextField, emailTextField};
                if (editSaveButton.getText().equals("EDIT")) { // if user clicks on the "edit" button
                    editSaveButton.setText("SAVE");

                    for (JTextField textfield : textFieldArray) {
                        textfield.setEditable(true);
                    }
                }
                else { // if user clicks on the "save" button
                    String username = usernameTextField.getText();
                    String password = passwordTextField.getText();
                    String fname = fnameTextField.getText();
                    String lname = lnameTextField.getText();
                    String phoneNum = pnumTextField.getText();
                    String email = emailTextField.getText();
                    String userType = singletonLogin.getUsertype();

                    String[] profileDetails = {username, password, fname, lname, phoneNum, email};

                    if (userContr.validateRegisterData(profileDetails)) {
                        User user = new User(username, password, fname, lname, phoneNum, email, userType);
                        userDaoImpl.update(user);
                        JOptionPane.showMessageDialog(new JFrame(), "Your changes have been saved.");

                        singletonLogin.setUsername(username);

                        editSaveButton.setText("EDIT");
                        for (JTextField textfield : textFieldArray) {
                            textfield.setEditable(false);
                        }

                        if (singletonLogin.getUsertype().equals("Admin")) {
                            new AdminCard();
                        }
                    }
                }
            }
        });
    }
}
