package main.Controller;

import main.Model.Property;
import main.Model.SingletonLogin;
import main.View.AdminCard;
import main.View.AgentOwnerCard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Implements MVC design pattern. A C-Controller class to control the flow
 * between Property Model and View.
 */
public class PropertyController {
    private static final PropertyDaoImpl propertyDaoImpl = new PropertyDaoImpl();
    private final SingletonLogin singletonLogin = SingletonLogin.getLoginInstance();

    public PropertyController() {}

    /**
     * Setups table model and prints property list in tabular format.
     *
     * @author Chang See Jie
     * @param model default table model interface to store data for table
     * @param table table of properties
     * @param sql sql query
     */
    public void printPropertyTable(DefaultTableModel model, JTable table, String sql) {
//        sets the column name of property list table
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Room(s)");
        model.addColumn("Bathroom(s)");
        model.addColumn("Property Type");
        model.addColumn("Address");
        model.addColumn("Rental");
        model.addColumn("Facilities");
        model.addColumn("Status");
        model.addColumn("Owner");
        model.addColumn("Comment");
        table.setModel(model);

        ArrayList<Property> propertyList = propertyDaoImpl.view(sql);

//        initializes the number of column for the table
        Object[] row = new Object[11];
        for (Property property : propertyList) {
            row[0] = property.getPropertyID();
            row[1] = property.getPropertyName();
            row[2] = property.getNumOfRooms();
            row[3] = property.getNumOfBathrooms();
            row[4] = property.getPropertyType();
            row[5] = property.getPropertyAddress();
            row[6] = property.getRental();
            row[7] = property.getFacilities();
            row[8] = property.getPropertyStatus();
            row[9] = property.getOwnerUsername();
            row[10] = property.getComment();
            model.addRow(row);
        }

//        removes some columns to hide from viewing
        table.removeColumn(table.getColumnModel().getColumn(7));
        table.removeColumn(table.getColumnModel().getColumn(7));
        table.removeColumn(table.getColumnModel().getColumn(7));
        table.removeColumn(table.getColumnModel().getColumn(7));
    }

    /**
     * Delete a property by identifying the property ID from the selected row
     * in the table.
     *
     * @author Loo Chen Zhi
     * @param table table of properties
     */
    public void deleteProperty(JTable table) {
        String selectedPropID = table.getModel().getValueAt(table.convertRowIndexToModel(table.getSelectedRow()), 0).toString();
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete Property " + selectedPropID + "?");
        if (confirm == JOptionPane.YES_OPTION) {
            propertyDaoImpl.delete(selectedPropID);
            JOptionPane.showMessageDialog(new JFrame(), "Property has been successfully deleted.");

            if (singletonLogin.getUsertype().equals("Admin"))
                new AdminCard();
            else
                new AgentOwnerCard();
        }
    }

    /**
     * Applies filter according to user's sorting choice.
     *
     * @author Loo Chen Zhi
     *
     * @param model default table model interface to store property data for table
     * @param table displays table
     * @param facilitiesCheckBox Checkboxes for facilities
     * @param propertyTypeComboBox Combobox for property type
     * @param statusComboBox Combobox for property status
     * @param ownerComboBox Combobox for owner name
     * @param commentComboBox Combobox for comments
     */
    public void rowFilter(DefaultTableModel model, JTable table, JCheckBox[] facilitiesCheckBox, JComboBox propertyTypeComboBox, JComboBox statusComboBox, JComboBox ownerComboBox, JComboBox commentComboBox) {
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);
        List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>();

        // get the selected facilities
        for (JCheckBox box : facilitiesCheckBox) {
            if (box.isSelected()) {
                filters.add(RowFilter.regexFilter(box.getText(), 7));
            }
        }

        // get the selected property type
        if (!propertyTypeComboBox.getSelectedItem().toString().equals("All Property Types")) {
            String regex = String.format("^%s$", Pattern.quote(propertyTypeComboBox.getSelectedItem().toString()));
            filters.add(RowFilter.regexFilter(regex, 4));
        }

        // get the property status
        if (!statusComboBox.getSelectedItem().toString().equals("Any Status"))
            filters.add(RowFilter.regexFilter(statusComboBox.getSelectedItem().toString(), 8));

        // get the selected owner
        if (!ownerComboBox.getSelectedItem().toString().equals("All Owners")) {
            String regex = String.format("^%s$", Pattern.quote(ownerComboBox.getSelectedItem().toString()));
            filters.add(RowFilter.regexFilter(regex, 9));
        }

        // get the comment choice
        if (commentComboBox.getSelectedItem().toString().equals("Comments"))
            filters.add(RowFilter.regexFilter("^(?!\\s*$).+", 10));
        else if (commentComboBox.getSelectedItem().toString().equals("No Comment"))
            filters.add(RowFilter.regexFilter("^$", 10));

        RowFilter<Object,Object> af = RowFilter.andFilter(filters);
        sorter.setRowFilter(af);
    }
}
