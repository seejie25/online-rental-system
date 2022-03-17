package main.View;

import main.Database.dbConnection;
import main.Model.SingletonLogin;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Objects;

public class LoginPage extends JFrame {
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel bodyPanel;
    private JPanel footerPanel;
    private JLabel logoLabel;
    private JLabel loginLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel usertypeLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox usertypeOption;
    private JButton RegisterButton;
    private JButton LoginButton;

    private final Connection connection = dbConnection.getConnection();
    private SingletonLogin singletonLogin = SingletonLogin.getLoginInstance();

    /**
     * Creates a login page and prompts for user's username and password in order
     * to log into this system.
     *
     * @author Tee Ming Yuan
     */
    public LoginPage() {
        JFrame frame = Main.getLoginFrame();
        frame.getContentPane().removeAll();
        frame.setContentPane(mainPanel);
        frame.setVisible(true);

        Image logoImage = new ImageIcon(getClass().getResource("/main/Image/xSmarthomesLogo.png")).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
        ImageIcon logoIcon = new ImageIcon(logoImage);
        logoLabel.setIcon(logoIcon);

//        clicks on the "login" button then validate user input
        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                String userType = Objects.requireNonNull(usertypeOption.getSelectedItem()).toString();

                try {
                    PreparedStatement pst = connection.prepareStatement("SELECT * FROM user WHERE username = ? and password = ? and user_type = ?");
                    pst.setString(1, username);
                    pst.setString(2, password);
                    pst.setString(3, userType);
                    ResultSet resultSet = pst.executeQuery();

                    if (resultSet.next()) { // if input matches
                        frame.dispose();
                        singletonLogin.getLoginInstance();
                        singletonLogin.setUsername(resultSet.getString("username"));
                        singletonLogin.setUsertype(resultSet.getString("user_Type"));

                        switch (singletonLogin.getUsertype()) {
                            case "Tenant":  // if user is a tenant
                                new TenantCard();
                                break;
                            case "Agent": // if user is an agent
                            case "Owner": // if user is an owner
                                new AgentOwnerCard();
                                break;
                            case "Admin":  // if user is an admin
                                new AdminCard();
                                break;
                        }
                    } else // if input does not match
                        JOptionPane.showMessageDialog(null, "Invalid account.");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

//        clicks on the "register" button to create an account
        RegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AccountRegistration("newUser");
            }
        });
    }
}

