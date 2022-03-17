package main.View;

import main.Controller.PropertyDaoImpl;
import main.Database.dbConnection;
import main.Model.Property;
import main.Model.SingletonLogin;
import main.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

/**
 * An interface layout showing a form to edit or upload property details.
 *
 * @author Chang See Jie
 */
public class EditUploadProperty extends JFrame {
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel bodyPanel;
    private JPanel footerPanel;
    private JLabel nameLabel;
    private JLabel numOfRoomsLabel;
    private JLabel numOfBathroomsLabel;
    private JLabel propertyTypeLabel;
    private JLabel rentalLabel;
    private JLabel addressLabel;
    private JLabel facilitiesLabel;
    private JLabel statusLabel;
    private JLabel titleLabel;
    private JLabel ownerLabel;
    private JLabel instructionsLabel;
    private JCheckBox gymCheckBox;
    private JCheckBox swimmingPoolCheckBox;
    private JCheckBox airConditionerCheckBox;
    private JCheckBox badmintonCourtCheckBox;
    private JCheckBox basketballCourtCheckBox;
    private JCheckBox playgroundCheckBox;
    private JButton BACKButton;
    private JButton SAVEUPLOADButton;
    private JTextField addressTextField;
    private JTextField nameTextField;
    private JSpinner numOfRoomsSpinner;
    private JSpinner numOfBathroomsSpinner;
    private JSpinner rentalSpinner;
    private JComboBox <String> propertyTypeOption;
    private JComboBox <String> ownerComboBox;
    private JRadioButton activeRadioButton;
    private JRadioButton inactiveRadioButton;

    private final SingletonLogin singletonLogin = SingletonLogin.getLoginInstance();
    private final PropertyDaoImpl propertyDaoImpl = new PropertyDaoImpl();

    /**
     * Creates a form and prompts for inputs to upload a new property or
     * update an existing property.
     *
     * @param editUpload edit or upload property
     */
    public EditUploadProperty(String editUpload) {
        JFrame frame = Main.getMainFrame();
        frame.getContentPane().removeAll();
        frame.setContentPane(mainPanel);
        frame.setVisible(true);

        Statement statement = null;
        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.createStatement();
            ownerLabel.setVisible(false);
            ownerComboBox.setVisible(false);

            if (singletonLogin.getUsertype().equals("Admin")) { // if admin is uploading, can select the owner of the property
                ownerLabel.setVisible(true);
                ownerComboBox.setVisible(true);

//                adds agent/owner into a combo box
                ResultSet rs = statement.executeQuery("SELECT username FROM user WHERE user_type = 'Owner' OR user_type = 'Agent'");
                while (rs.next()) {
                    ownerComboBox.addItem(rs.getString(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

//        clicks on the "back" button to go back to homepage
        BACKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (singletonLogin.getUsertype().equals("Agent") || singletonLogin.getUsertype().equals("Owner"))
                    new AgentOwnerCard();
                else
                    new AdminCard();

            }
        });

//        adds all radio buttons into one button group to allow only one selection
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(activeRadioButton);
        buttonGroup.add(inactiveRadioButton);

        if (editUpload.equals("edit")) { // if the agent or owner selects EDIT property
            titleLabel.setText("Edit Property");
            SAVEUPLOADButton.setText("SAVE");

//            gets which property has been selected to edit and get the original data
            ResultSet resultSet = null;
            try {
                resultSet = statement.executeQuery("SELECT * FROM property WHERE property_id = " + ViewPropertyPanel.selectedPropID);

//                fills all the data into fields accordingly
                while (resultSet.next()) {
                    nameTextField.setText(resultSet.getString(2));
                    numOfRoomsSpinner.setModel(new SpinnerNumberModel(resultSet.getInt(3), 0, 9, 1));
                    numOfBathroomsSpinner.setModel(new SpinnerNumberModel(resultSet.getInt(4), 0, 9, 1));

                    String[] propertyList = {"Townhouse", "Single Storey", "Double Storey", "Bungalow", "Condominium", "Others"};
                    for (int i = 0; i < propertyList.length; i++) {
                        if (resultSet.getString(5).equals(propertyList[i]))
                            propertyTypeOption.setSelectedIndex(i);
                    }

                    rentalSpinner.setModel(new SpinnerNumberModel(resultSet.getInt(7), 0, 100000, 1));
                    addressTextField.setText(resultSet.getString(6));

                    if (resultSet.getString(9).equals("Active"))
                        activeRadioButton.setSelected(true);
                    else
                        inactiveRadioButton.setSelected(true);

                    JCheckBox[] facilitiesCheckBox = new JCheckBox[]{gymCheckBox, swimmingPoolCheckBox, airConditionerCheckBox, badmintonCourtCheckBox, basketballCourtCheckBox, playgroundCheckBox};
                    String[] facilitiesList = {"Gym", "Swimming Pool", "Air Conditioner", "Badminton Court", "Basketball Court", "Playground"};
                    for (int i = 0; i < propertyList.length; i++)
                        if (!(resultSet.getString(8) == null) && resultSet.getString(8).contains(facilitiesList[i]))
                            for (JCheckBox box : facilitiesCheckBox)
                                if (box.getText().equals(facilitiesList[i]))
                                    box.setSelected(true);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else { // if the agent or owner selects UPLOAD property
            titleLabel.setText("Upload Property");
            SAVEUPLOADButton.setText("UPLOAD");
            activeRadioButton.setSelected(true);

//            all fields default blank
            numOfRoomsSpinner.setModel(new SpinnerNumberModel(0, 0, 9, 1));
            numOfBathroomsSpinner.setModel(new SpinnerNumberModel(0, 0, 9, 1));
            rentalSpinner.setModel(new SpinnerNumberModel(0, 0, 100000, 1));
        }

//        clicks on the "save" button to get inputs and save all the changes made
        SAVEUPLOADButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                Integer room = (Integer) numOfRoomsSpinner.getValue();
                Integer bathroom = (Integer) numOfBathroomsSpinner.getValue();
                String type = propertyTypeOption.getSelectedItem().toString();
                String address = addressTextField.getText();
                Integer rental = (Integer) rentalSpinner.getValue();

                JCheckBox[] facilitiesCheckBox = new JCheckBox[]{gymCheckBox, swimmingPoolCheckBox, airConditionerCheckBox, badmintonCourtCheckBox, basketballCourtCheckBox, playgroundCheckBox};
                ArrayList<String> insertFacilitiesList = new ArrayList<>();
                for (JCheckBox box : facilitiesCheckBox) {
                    if (box.isSelected()) {
                        insertFacilitiesList.add(box.getText());
                    }
                }
                String facility = insertFacilitiesList.toString().replace("[", "").replace("]", "");

                String updatedStatus;
                if (activeRadioButton.isSelected())
                    updatedStatus = "Active";
                else
                    updatedStatus = "Inactive";

                String propertyOwner = singletonLogin.getUsername();
                if (singletonLogin.getUsertype().equals("Admin")) {
                    propertyOwner = ownerComboBox.getSelectedItem().toString();
                }

//                validates data
                if (nameTextField.getText().equals("") || addressTextField.getText().equals("")) { // if leave the field blank
                    JOptionPane.showMessageDialog(null, "Please fill in all the fields.");
                } else {
                    Property property = new Property(name, room, bathroom, type, address, rental, facility, updatedStatus, propertyOwner);
                    if (editUpload.equals("edit")) {
                        JOptionPane.showMessageDialog(new JFrame(), "Your changes have been saved.");
                        propertyDaoImpl.update(property, ViewPropertyPanel.selectedPropID);
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Property has been uploaded.");
                        propertyDaoImpl.insert(property);
                    }

                    if (singletonLogin.getUsertype().equals("Admin"))
                        new AdminCard();
                    else
                        new AgentOwnerCard();
                }
            }
        });
    }
}
