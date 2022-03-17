package main.Model;

/**
 * Implements MVC and DAO design patterns. A Model to manage Property data and
 * containing getter and setter methods.
 */
public class Property {
    private int propertyID;
    private final int numOfRooms;
    private final int numOfBathrooms;
    private final int rental;
    private final String propertyName;
    private final String propertyType;
    private final String propertyAddress;
    private final String facilities;
    private final String propertyStatus;
    private final String ownerUsername;
    private String comment;
    
    public Property(int propertyID, String propertyName, int numOfRooms, int numOfBathrooms, String propertyType, String propertyAddress, int rental, String facilities, String propertyStatus, String ownerUsername, String comment) {
        this.propertyID = propertyID;
        this.propertyName = propertyName;
        this.numOfRooms = numOfRooms;
        this.numOfBathrooms = numOfBathrooms;
        this.propertyType = propertyType;
        this.propertyAddress = propertyAddress;
        this.rental = rental;
        this.facilities = facilities;
        this.propertyStatus = propertyStatus;
        this.ownerUsername = ownerUsername;
        this.comment = comment;
    }

    public Property(String propertyName, int numOfRooms, int numOfBathrooms, String propertyType, String propertyAddress, int rental, String facilities, String propertyStatus, String ownerUsername) {
        this.propertyName = propertyName;
        this.numOfRooms = numOfRooms;
        this.numOfBathrooms = numOfBathrooms;
        this.propertyType = propertyType;
        this.propertyAddress = propertyAddress;
        this.rental = rental;
        this.facilities = facilities;
        this.propertyStatus = propertyStatus;
        this.ownerUsername = ownerUsername;
    }

    public int getPropertyID() {
        return propertyID;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public int getNumOfRooms() {
        return numOfRooms;
    }

    public int getNumOfBathrooms() {
        return numOfBathrooms;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public int getRental() {
        return rental;
    }

    public String getFacilities() {
        return facilities;
    }

    public String getPropertyStatus() {
        return propertyStatus;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public String getComment() {
        return comment;
    }
}
