package main.View;

import main.Controller.UserController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class AdminManageAcc extends JPanel {
    private JPanel mainPanel;
    private JPanel toolbarPanel;
    private JPanel footerPanel;
    private JButton DELETEButton;
    private JButton addAccountButton;
    private JLabel deleteLabel;
    private JLabel resultsLabel;
    private JTable userListTable;
    private JComboBox filterUsertypeCombobox;

    private final UserController userContr = new UserController();
    DefaultTableModel model = new DefaultTableModel();

    /**
     * Manages user account which include function of adding accounts and deleting accounts.
     *
     * @author Vizeard Yeow
     */
    public AdminManageAcc() {
        add(mainPanel);

        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        userListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userListTable.getTableHeader().setReorderingAllowed(false);
        userListTable.setDefaultEditor(Object.class, null);

//        shows table of all user type
        userContr.printUserTable(model, userListTable, "SELECT * FROM user");
        resultsLabel.setText(userListTable.getRowCount() + " results");

//        shows table accordingly to sort user type
        filterUsertypeCombobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = "";

                switch (Objects.requireNonNull(filterUsertypeCombobox.getSelectedItem()).toString()) {
                    case "All User Types":
                        sql = "SELECT * FROM user";
                        break;
                    case "Tenant":
                        sql = "SELECT * FROM user WHERE user_type = 'Tenant'";
                        break;
                    case "Owner":
                        sql = "SELECT * FROM user WHERE user_type = 'Owner'";
                        break;
                    case "Agent":
                        sql = "SELECT * FROM user WHERE user_type = 'Agent'";
                        break;
                    case "Admin":
                        sql = "SELECT * FROM user WHERE user_type = 'Admin'";
                        break;
                }

                model.setColumnCount(0);
                model.setRowCount(0);

                userContr.printUserTable(model, userListTable, sql);
                resultsLabel.setText(userListTable.getRowCount() + " results");
            }
        });

//        display registration window when "add account" button is clicked
        addAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AccountRegistration("admin");
            }
        });

//        delete selected row when "delete" button is clicked
        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userListTable.getSelectedRow();
                userContr.deleteUser(selectedRow, model, userListTable);

                new AdminCard();
            }
        });
    }
}
