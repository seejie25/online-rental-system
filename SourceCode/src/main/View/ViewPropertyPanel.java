package main.View;

import main.Controller.PropertyController;
import main.Controller.PropertyDaoImpl;
import main.Database.dbConnection;
import main.Main;
import main.Model.SingletonLogin;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * An interface layout showing a page containing filter toolbar, some functional buttons,
 * table with property data, and a panel displaying more property details.
 *
 * @author Chang See Jie
 */
public class  ViewPropertyPanel extends JPanel {
    private JPanel mainPanel;
    private JPanel filterPanel;
    private JPanel propertyDetailsPanel;
    private JTable propertyListTable;
    private JCheckBox gymCheckBox;
    private JCheckBox airConditionerCheckBox;
    private JCheckBox swimmingPoolCheckBox;
    private JCheckBox basketballCourtCheckBox;
    private JCheckBox badmintonCourtCheckBox;
    private JCheckBox playgroundCheckBox;
    private JComboBox <String> propertyTypeComboBox;
    private JComboBox <String> commentComboBox;
    private JComboBox <String> sortByComboBox;
    private JComboBox <String> ownerComboBox;
    private JComboBox <String> statusComboBox;
    private JButton APPLYButton;
    private JButton EDITButton;
    private JButton uploadPropertyButton;
    private JButton DELETEButton;
    private JButton COMMENTButton;
    private JButton CANCELButton;
    private JLabel facilitiesLabel;
    private JLabel propertyTypeLabel;
    private JLabel commentLabel1;
    private JLabel sortByLabel;
    private JLabel minPriceLabel;
    private JLabel maxPriceLabel;
    private JLabel dashLabel;
    private JLabel nameLabel;
    private JLabel phoneNumberLabel;
    private JLabel emailLabel;
    private JLabel facilityLabel;
    private JLabel propertyTypeLabel1;
    private JLabel addressLabel;
    private JLabel roomLabel;
    private JLabel bathroomLabel;
    private JLabel contactLabel;
    private JLabel priceLabel;
    private JLabel propertyDetailsLabel;
    private JLabel statusLabel;
    private JLabel maxPriceRangeLabel;
    private JLabel minPriceRangeLabel;
    private JLabel commentLabel;
    private JLabel commentToPropertyLabel;
    private JLabel ownerLabel;
    private JLabel resultsLabel;
    private JSlider minPriceRangeSlider;
    private JSlider maxPriceRangeSlider;
    private JTextArea commentTextArea;

    private final Connection connection = dbConnection.getConnection();
    private final PropertyDaoImpl propertyDaoImpl = new PropertyDaoImpl();
    private final PropertyController propController = new PropertyController();
    private SingletonLogin singletonLogin = SingletonLogin.getLoginInstance();
    private String sql;
    public static String selectedPropID;

    /**
     * Creates a panel to show property.
     *
     * @param selection views ALL property or only MY property
     */
    public ViewPropertyPanel(String selection) {
        add(mainPanel);

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT username FROM user WHERE user_type = 'Owner' OR user_type = 'Agent'");
            while (rs.next())
                ownerComboBox.addItem(rs.getString(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (singletonLogin.getUsertype().equals("Admin")) {
            COMMENTButton.setVisible(true);
            DELETEButton.setVisible(true);
            EDITButton.setVisible(true);
            statusLabel.setVisible(true);
            statusComboBox.setVisible(true);
        } else if ((singletonLogin.getUsertype().equals("Agent") || singletonLogin.getUsertype().equals("Owner")) && selection.equals("my")) {
            contactLabel.setVisible(false);
            phoneNumberLabel.setVisible(false);
            emailLabel.setVisible(false);
            DELETEButton.setVisible(true);
            EDITButton.setVisible(true);
            statusLabel.setVisible(true);
            statusComboBox.setVisible(true);
            ownerLabel.setVisible(false);
            ownerComboBox.setVisible(false);
        }

//        sets the table only allow for one selection, can't be reordered and can't be edited
        propertyListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        propertyListTable.getTableHeader().setReorderingAllowed(false);
        propertyListTable.setDefaultEditor(Object.class, null);

        propertyDetailsPanel.setVisible(false);

//        gets real-time value from jslider
        minPriceRangeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                minPriceRangeLabel.setText(String.valueOf(minPriceRangeSlider.getValue()));
                maxPriceRangeSlider.setMinimum((minPriceRangeSlider.getValue()));
            }
        });

        maxPriceRangeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                maxPriceRangeLabel.setText(String.valueOf(maxPriceRangeSlider.getValue()));
            }
        });

        if (selection.equals("all")) { // if viewing ALL property
            switch (singletonLogin.getUsertype()) {
                case "Tenant":
                case "Agent":
                case "Owner": { // tenant, agent, owner can only view ACTIVE property
                    sql = "SELECT * FROM property WHERE property_status = 'active'";
                    break;
                }
                case "Admin": { // admin can view both ACTIVE and INACTIVE property
                    sql = "SELECT * FROM property";
                    break;
                }
            }
        } else { // if viewing MY property
            sql = "SELECT * FROM property WHERE owner_username = '" + singletonLogin.getUsername() + "'";
        }

//        clears table as refresh then print latest data
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnCount(0);
        model.setRowCount(0);
        propController.printPropertyTable(model, propertyListTable, sql);
        resultsLabel.setText(propertyListTable.getRowCount() + " results");

//        clicks on "apply button to apply filter
        APPLYButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox[] facilitiesCheckBox = new JCheckBox[]{gymCheckBox, swimmingPoolCheckBox, airConditionerCheckBox, badmintonCourtCheckBox, basketballCourtCheckBox, playgroundCheckBox};
                propertyDaoImpl.sort(model, propertyListTable, minPriceRangeLabel, maxPriceRangeLabel, sortByComboBox, selection);
                propController.rowFilter(model, propertyListTable, facilitiesCheckBox, propertyTypeComboBox, statusComboBox, ownerComboBox, commentComboBox);
                resultsLabel.setText(propertyListTable.getRowCount() + " results");
            }
        });

//        clicks on "upload property" button to upload property
        uploadPropertyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (singletonLogin.getUsertype().equals("Tenant")) { // tenant cannot upload property
                    String[] options = {"Logout and Sign Up Now", "Continue as Tenant"};
                    int option = JOptionPane.showOptionDialog(null,
                            "You don't have permission to upload property. Please sign up as an owner/agent to continue.",
                            null,
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.ERROR_MESSAGE,
                            null,
                            options,
                            options[1]);

                    if (option == JOptionPane.YES_OPTION) {
                        singletonLogin.setUsername(null);
                        singletonLogin.setUsertype(null);
                        JFrame frame = Main.getMainFrame();
                        frame.dispose();
                        new AccountRegistration("newUser");
                    }
                } else {
                    new EditUploadProperty("upload");
                }
            }
        });

//        clicks on "edit" button to edit a property
        EDITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedPropRow = propertyListTable.getSelectedRow();
                if (selectedPropRow == -1)
                    JOptionPane.showMessageDialog(new JFrame(), "Please select a row.");
                else {
                    new EditUploadProperty("edit");
                }
            }
        });

//        click on "delete" button to delete a selected property
        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedPropRow = propertyListTable.getSelectedRow();
                if (selectedPropRow == -1)
                    JOptionPane.showMessageDialog(new JFrame(), "Please select a row.");
                else
                    propController.deleteProperty(propertyListTable);
            }
        });

//        clicks on the "comment" button to leave a comment to a selected property
        COMMENTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedPropRow = propertyListTable.getSelectedRow();
                if (selectedPropRow == -1) {
                    JOptionPane.showMessageDialog(new JFrame(), "Please select a row.");
                } else {
                    String selectedPropID = propertyListTable.getModel().getValueAt(propertyListTable.convertRowIndexToModel(propertyListTable.getSelectedRow()), 0).toString();
                    if (COMMENTButton.getText().equals("COMMENT")) { // if user clicks on the "comment" button
                        commentTextArea.setOpaque(true);
                        commentTextArea.setEditable(true);
                        commentToPropertyLabel.setVisible(true);
                        commentToPropertyLabel.setText("Comment to Property " + selectedPropID + ":");
                        COMMENTButton.setText("SEND");
                        CANCELButton.setVisible(true);
                    } else { // if user clicks on the "send" button
                        String comment = commentTextArea.getText();

                        if (comment.equals("")) {
                            comment = null;
                        }

                        propertyDaoImpl.comment(comment, selectedPropID);
                        commentLabel.setText(comment);
                        JOptionPane.showMessageDialog(null, "Your comment has been sent.");

                        commentTextArea.setText("");
                        commentTextArea.setOpaque(false);
                        commentTextArea.setEditable(false);
                        commentToPropertyLabel.setVisible(false);
                        COMMENTButton.setText("COMMENT");
                        CANCELButton.setVisible(false);
                    }
                }
            }
        });

//        clicks on the "cancel" button to clear texts in the text area and hide the text area
        CANCELButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commentTextArea.setText("");
                commentTextArea.setOpaque(false);
                commentTextArea.setEditable(false);
                commentToPropertyLabel.setVisible(false);

                COMMENTButton.setText("COMMENT");
                CANCELButton.setVisible(false);
            }
        });

//        gets selection on table in order to show property details panel
        propertyListTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String owner = "";
                int selectedRow = propertyListTable.getSelectedRow();

                try {
                    if (!(selectedRow == -1)) { // if got selected row
                        selectedPropID = propertyListTable.getModel().getValueAt(propertyListTable.convertRowIndexToModel(selectedRow), 0).toString();
                        propertyDetailsPanel.setVisible(true);
                        Statement statement = connection.createStatement();
                        ResultSet rs = statement.executeQuery("SELECT * FROM property WHERE property_id = " + selectedPropID);

                        if (rs.next()) {
                            String facility = rs.getString("facilities");
                            if (facility == null)
                                facility = "-";

                            String comment = rs.getString("comment");
                            if (comment == null)
                                comment = "-";

                            nameLabel.setText("<html><p style=\"width:200px\">" + rs.getString("property_name") + "</p></html>");
                            priceLabel.setText(rs.getString("rental"));
                            roomLabel.setText(rs.getString("num_room"));
                            bathroomLabel.setText(rs.getString("num_bathroom"));
                            addressLabel.setText("<html><p style=\"width:200px\">" + rs.getString("property_address") + "</p></html>");
                            propertyTypeLabel1.setText(rs.getString("property_type"));
                            facilityLabel.setText("<html><p style=\"width:200px\">" + facility + "</p></html>");
                            commentLabel.setText("<html><p style=\"width:200px\">" + comment + "</p></html>");
                            contactLabel.setText("Contact " + (rs.getString("owner_username")) + " to know more about this property.");
                            owner = rs.getString("owner_username");
                        }

                        rs = statement.executeQuery("SELECT * FROM user WHERE username = '" + owner + "'");
                        while (rs.next()) {
                            phoneNumberLabel.setText(rs.getString("phone_number"));
                            emailLabel.setText(rs.getString("email"));
                        }
                    } else {
                        propertyDetailsPanel.setVisible(false);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
