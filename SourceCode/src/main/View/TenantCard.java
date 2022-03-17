package main.View;

import javax.swing.*;

/**
 * Implements MVC design pattern. A View GUI inherits components from CardLayoutBase
 * to show a homepage having Tenant's use cases.
 *
 * @author Chang See Jie
 */
public class TenantCard extends CardLayoutBase {

    /**
     * Creates a homepage for tenant which containing View Property tab
     * and User Profile tab
     */
    public TenantCard() {
        CardLayoutBase CLB = new CardLayoutBase();
        JTabbedPane tabbedPane = CLB.getTabbedPane();

        UserProfile UP = new UserProfile();
        ViewPropertyPanel CLT = new ViewPropertyPanel("all");

        tabbedPane.addTab("Property", CLT);
        tabbedPane.addTab("Profile", UP);
    }
}
