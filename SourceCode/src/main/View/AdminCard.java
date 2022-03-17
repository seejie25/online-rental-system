package main.View;

import javax.swing.*;

/**
 * Implements MVC design pattern. A View GUI inherits components from CardLayoutBase
 * to show a homepage having Admin's use cases.
 *
 * @author Chang See Jie
 */
public class AdminCard extends CardLayoutBase {

    /**
     * Creates a frame for admin which containing View Property tab,
     * Manage Account tab and User Profile tab.
     */
    public AdminCard() {
        CardLayoutBase CLB = new CardLayoutBase();
        JTabbedPane tabbedPane = CLB.getTabbedPane();

        ViewPropertyPanel CLT = new ViewPropertyPanel("all");
        AdminManageAcc AMA = new AdminManageAcc();
        UserProfile UP = new UserProfile();

        tabbedPane.addTab("Property", CLT);
        tabbedPane.addTab("Manage Account", AMA);
        tabbedPane.addTab("Profile", UP);
    }
}
