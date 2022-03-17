package main;

import main.Database.dbConnection;
import main.View.LoginPage;

import javax.swing.*;

public class Main extends JFrame {
    public static JFrame mainFrame = new JFrame();
    public static JFrame loginFrame = new JFrame();

    public Main() {};

    public static void main(String[] args) {
        dbConnection.getConnection();
        new LoginPage();
    }

    public static JFrame getMainFrame() {
        mainFrame.setTitle("xSmarthomes");
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setIconImage(new ImageIcon(Main.class.getResource("/main/Image/xSmarthomesLogo.png")).getImage());

        return mainFrame;
    }

    public static JFrame getLoginFrame() {
        loginFrame.setTitle("xSmarthomes");
        loginFrame.setSize(400, 600);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setIconImage(new ImageIcon(Main.class.getResource("/main/Image/xSmarthomesLogo.png")).getImage());

        return loginFrame;
    }
}