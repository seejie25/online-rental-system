package main.Controller;

import main.Model.Property;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Implements DAO design pattern. An interface defining the DAO API.
 */
public interface PropertyDao {
    ArrayList<Property> view(String sql);
    void insert(Property property);
    void update(Property property, String selectedPropID);
    void delete(String selectedPropID);
    void sort(DefaultTableModel model, JTable table, JLabel minPriceRangeLabel, JLabel maxPriceRangeLabel, JComboBox sortByOption, String selection);
    void comment(String comment, String selectedPropID);
}
