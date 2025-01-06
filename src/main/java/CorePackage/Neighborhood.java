package CorePackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Neighborhood {

    private int neighborhoodId;
    private String neighborhoodName;
    private int cityId;

    public Neighborhood() {
    }

    public Neighborhood(int neighborhoodId, String neighborhoodName, int cityId) {
        this.neighborhoodId = neighborhoodId;
        this.neighborhoodName = neighborhoodName;
        this.cityId = cityId;
    }

    public int getNeighborhoodId() {
        return neighborhoodId;
    }

    public void setNeighborhoodId(int neighborhoodId) {
        this.neighborhoodId = neighborhoodId;
    }

    public String getNeighborhoodName() {
        return neighborhoodName;
    }

    public void setNeighborhoodName(String neighborhoodName) {
        this.neighborhoodName = neighborhoodName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public static List<Neighborhood> getNeighborhoodsByCityName(String cityName) {
        String query = "SELECT n.neighborhood_id, n.Neighborhood, n.city_id FROM Neighborhood n "
                + "JOIN City c ON n.city_id = c.city_id WHERE c.CityName = ?";
        List<Neighborhood> neighborhoods = new ArrayList<>();
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, cityName);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    neighborhoods.add(new Neighborhood(
                            rs.getInt("neighborhood_id"),
                            rs.getString("Neighborhood"),
                            rs.getInt("city_id")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return neighborhoods;
    }

    public static int getNeighborhoodIdByNameAndCity(String neighborhoodName, String cityName) {
        String query = "SELECT n.neighborhood_id FROM Neighborhood n "
                + "JOIN City c ON n.city_id = c.city_id "
                + "WHERE n.Neighborhood = ? AND c.CityName = ?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, neighborhoodName);
            stmt.setString(2, cityName);
            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("neighborhood_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
