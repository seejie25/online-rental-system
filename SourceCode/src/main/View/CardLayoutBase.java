package main.View;

import main.Model.SingletonLogin;
import main.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * An interface layout base having common components for other classes to inherit.
 *
 * @author Chang See Jie
 */
public class CardLayoutBase extends JFrame {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JButton LOGOUTButton;
    private JLabel hiLabel;
    private JPanel headerPanel;

    private SingletonLogin singletonLogin = SingletonLogin.getLoginInstance();

    JFrame frame = Main.getMainFrame();

    public CardLayoutBase() {
        frame.getContentPane().removeAll();
        frame.setContentPane(mainPanel);
        frame.setVisible(true);

        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

//        shows username
        hiLabel.setText("Hi, " + singletonLogin.getUsertype() + " " + singletonLogin.getUsername() + "!");

//        clicks on the "logout" button to logout from the system and redirect back to login page
        LOGOUTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "You have successfully logged out.");
                singletonLogin.setUsername(null);
                singletonLogin.setUsertype(null);
                new LoginPage();
                frame.dispose();
            }
        });
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
}
