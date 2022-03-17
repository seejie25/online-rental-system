package main.View;

import main.Controller.UserController;
import main.Controller.UserDaoImpl;
import main.Main;
import main.Model.SingletonLogin;
import main.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountRegistration extends JFrame {
    private JPanel panelMain;
    private JLabel mainLabel;
    private JLabel user;
    private JLabel pw;
    private JLabel fname;
    private JLabel lname;
    private JLabel pnum;
    private JLabel mail;
    private JLabel userTypeLabel;
    private JLabel instructionsLabel;
    private JTextField userInput;
    private JTextField pwInput;
    private JTextField fnameInput;
    private JTextField lnameInput;
    private JTextField pnumInput;
    private JTextField mailInput;
    private JButton registerButton;
    private JButton BACKButton;
    private JComboBox userTypeCombobox;

    private final SingletonLogin singletonLogin = SingletonLogin.getLoginInstance();
    private final UserDaoImpl userDaoImpl = new UserDaoImpl();
    private final UserController userController = new UserController();

    /**
     * Shows Account Registration page to user and prompt user to fill in all details.
     *
     * @author Tee Ming Yuan
     * @param userType new user or admin is registering
     */
    public AccountRegistration(String userType) {
        JFrame frame = Main.getLoginFrame();
        frame.getContentPane().removeAll();
        frame.setContentPane(panelMain);
        frame.setVisible(true);

        if (!userType.equals("admin")) // if not admin is creating account, remove "admin" selection from combobox
            userTypeCombobox.removeItem("Admin");

        if (singletonLogin.getUsertype() != null)
            BACKButton.setVisible(false);

//        click "register" to create an account
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userInput.getText();
                String password = pwInput.getText();
                String fname =  fnameInput.getText();
                String lname = lnameInput.getText();
                String phoneNum = pnumInput.getText();
                String email = mailInput.getText();
                String userType = userTypeCombobox.getSelectedItem().toString();
                String[] profileDetails = {username, password, fname, lname, phoneNum, email, userType};

//                validate register data
                if (userController.validateRegisterData(profileDetails)) {
                    User user = new User(username, password, fname, lname, phoneNum, email, userType);
                    userDaoImpl.register(user);
                    JOptionPane.showMessageDialog(null, "Account successfully created!");

                    if (singletonLogin.getUsertype() == null) { // if user != admin, after registering success will show login page
                        new LoginPage();
                    }
                    else { // if user = admin, after registering success will show admin page
                        frame.dispose();
                        new AdminCard();
                    }
                }
            }
        });

//        click "back" to return to login page
        BACKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (singletonLogin.getUsertype() == null)
                    new LoginPage();
                else
                    new AdminCard();
            }
        });
    }
}

