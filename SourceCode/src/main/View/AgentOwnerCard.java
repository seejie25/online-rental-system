package main.View;

import javax.swing.*;

/**
 * Implements MVC design pattern. A View GUI inherits components from CardLayoutBase
 * to show a homepage having Agent/Owner's use cases.
 *
 * @author Chang See Jie
 */
public class AgentOwnerCard extends CardLayoutBase {

    /**
     * Creates a homepage for agent/owner which containing View Property tab,
     * View My Property tab and User Profile tab.
     */
    public AgentOwnerCard() {
        CardLayoutBase CLB = new CardLayoutBase();
        JTabbedPane tabbedPane = CLB.getTabbedPane();

        ViewPropertyPanel VPPALL = new ViewPropertyPanel("all");
        ViewPropertyPanel VPPMY = new ViewPropertyPanel("my");
        UserProfile UP = new UserProfile();

        tabbedPane.addTab("Property", VPPALL);
        tabbedPane.addTab("My Property", VPPMY);
        tabbedPane.addTab("Profile", UP);
    }
}
