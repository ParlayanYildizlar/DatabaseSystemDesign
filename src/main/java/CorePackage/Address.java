package CorePackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Address {

    private int addressId;
    private String addressName;
    private String openAddress;
    private Neighborhood neighborhood;

    public Address() {
    }

    public Address(int addressId, String addressName, String openAddress, Neighborhood neighborhood) {
        this.addressId = addressId;
        this.addressName = addressName;
        this.openAddress = openAddress;
        this.neighborhood = neighborhood;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getOpenAddress() {
        return openAddress;
    }

    public void setOpenAddress(String openAddress) {
        this.openAddress = openAddress;
    }

    public Neighborhood getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
    }

    public static void addAddress(String nationalIdNumber, Address address, int neighborhoodId) {
        String query = "INSERT INTO Address (idnum, AddressName, OpenAddress, neighborhood_id) VALUES (?, ?, ?, ?)";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nationalIdNumber);
            stmt.setString(2, address.getAddressName());
            stmt.setString(3, address.getOpenAddress());
            stmt.setInt(4, neighborhoodId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateAddress(Address address, int neighborhoodId) {
        String query = "UPDATE Address SET AddressName = ?, OpenAddress = ?, neighborhood_id = ? WHERE address_id = ?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, address.getAddressName());
            stmt.setString(2, address.getOpenAddress());
            stmt.setInt(3, neighborhoodId);
            stmt.setInt(4, address.getAddressId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getAddressIdByName(String addressName) {
        String query = "SELECT address_id FROM Address WHERE AddressName = ?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, addressName);
            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("address_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; 
    }
}
