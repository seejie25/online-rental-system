package main.Controller;

import main.Database.dbConnection;
import main.Model.Property;
import main.Model.SingletonLogin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Implements DAO design pattern. A concrete class implementing PropertyDao interface.
 * Contains methods to work with 'property' table in the database.
 */
public class PropertyDaoImpl implements PropertyDao {
    private static final PropertyController propController = new PropertyController();
    private final Connection connection = dbConnection.getConnection();
    private final SingletonLogin singletonLogin = SingletonLogin.getLoginInstance();

    /**
     * Gets property details from database then create a property object and store into
     * an array list.
     *
     * @author Chang See Jie
     * @param sql sql query
     * @return an array list containing property details
     */
    @Override
    public ArrayList<Property> view(String sql) {
        ArrayList<Property> propertyList = new ArrayList<>();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                Integer id = rs.getInt("property_id");
                String name = rs.getString("property_name");
                Integer room = rs.getInt("num_room");
                Integer bathroom = rs.getInt("num_bathroom");
                String type = rs.getString("property_type");
                String address = rs.getString("property_address");
                Integer rental = rs.getInt("rental");
                String facility = rs.getString("facilities");
                String status = rs.getString("property_status");
                String username = rs.getString("owner_username");
                String comment = rs.getString("comment");

                Property property = new Property(id, name, room, bathroom, type, address, rental, facility, status, username, comment);
                propertyList.add(property);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return propertyList;
    }

    /**
     * Inserts the new property from user input into database.
     *
     * @author Loo Chen Zhi
     * @param property property details from user input
     */
    @Override
    public void insert(Property property) {
        String sql = "INSERT INTO `property` (`property_name`, `num_room`, `num_bathroom`, `property_type`, `property_address`, `rental`, `facilities`, `property_status`, `owner_username`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement pst = null;

        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, property.getPropertyName());
            pst.setInt(2, property.getNumOfRooms());
            pst.setInt(3, property.getNumOfBathrooms());
            pst.setString(4, property.getPropertyType());
            pst.setString(5, property.getPropertyAddress());
            pst.setInt(6, property.getRental());
            pst.setString(7, property.getFacilities());
            pst.setString(8, property.getPropertyStatus());
            pst.setString(9, property.getOwnerUsername());

            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates or makes changes to the existing property in database.
     *
     * @author Loo Chen Zhi
     * @param property property details from user input
     * @param selectedPropID selected property ID
     */
    @Override
    public void update(Property property, String selectedPropID) {
        String sql = "UPDATE property SET property_name = ?, num_room = ?, num_bathroom = ?, property_type = ?, property_address = ?, rental = ?, facilities = ?, property_status = ?, owner_username = ? WHERE property_id = ?;";
        PreparedStatement pst = null;

        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, property.getPropertyName());
            pst.setInt(2, property.getNumOfRooms());
            pst.setInt(3, property.getNumOfBathrooms());
            pst.setString(4, property.getPropertyType());
            pst.setString(5, property.getPropertyAddress());
            pst.setInt(6, property.getRental());
            pst.setString(7, property.getFacilities());
            pst.setString(8, property.getPropertyStatus());
            pst.setString(9, property.getOwnerUsername());
            pst.setString(10, selectedPropID);

            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the property which has its property ID selected.
     *
     * @author Loo Chen Zhi
     * @param selectedPropID selected property ID
     */
    @Override
    public void delete(String selectedPropID) {
        String sql = "DELETE FROM property WHERE property_id = ?";
        PreparedStatement pst = null;

        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, selectedPropID);

            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sorts the table according to the price range selected and according to ascending or descending
     * order of the price.
     *
     * @author Loo Chen Zhi
     * @param model default table model interface to store property data for table
     * @param table displays table
     * @param minPriceRangeLabel minimum price
     * @param maxPriceRangeLabel maximum price
     * @param sortByOption price order
     * @param selection indication to allow function of price range input
     */
    @Override
    public void sort(DefaultTableModel model, JTable table, JLabel minPriceRangeLabel, JLabel maxPriceRangeLabel, JComboBox sortByOption, String selection) {
        String sql;

//        sort by function
        String sortCondition = "";
        switch ((String) Objects.requireNonNull(sortByOption.getSelectedItem())) {
            case "Lowest Price":
                sortCondition = " ORDER BY rental ASC";
                break;
            case "Highest Price":
                sortCondition = " ORDER BY rental DESC";
                break;
        }

        if (singletonLogin.getUsertype().equals("Admin") || selection.equals("my"))
            sql = "SELECT * FROM property WHERE rental BETWEEN " + minPriceRangeLabel.getText() + " AND " + maxPriceRangeLabel.getText();
        else
            sql = "SELECT * FROM property WHERE property_status = 'active' AND rental BETWEEN " + minPriceRangeLabel.getText() + " AND " + maxPriceRangeLabel.getText();

        if (selection.equals("all")) // if the user is viewing ALL property
            sql = sql + sortCondition;
        else // if the agent or owner is viewing MY property
            sql = sql + " AND owner_username = '" + singletonLogin.getUsername() + "'" + sortCondition;

        model.setColumnCount(0);
        model.setRowCount(0);
        propController.printPropertyTable(model, table, sql);
    }

    /**
     * Add comment to a property.
     *
     * @author Loo Chen Zhi
     * @param comment comment entered
     * @param selectedPropID selected property ID
     */
    @Override
    public void comment(String comment, String selectedPropID) {
        String sql = "UPDATE property SET comment = ? WHERE property_id = ?;";
        PreparedStatement pst = null;

        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, comment);
            pst.setString(2, selectedPropID);

            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
