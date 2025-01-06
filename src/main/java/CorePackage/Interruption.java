package CorePackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Interruption {

    private int interruptionId;
    private int subscriptionTypeId;
    private int neighborhoodId;
    private Timestamp startTime;
    private Timestamp endTime;
    private String reason;

    public Interruption() {
    }

    public Interruption(int subscriptionTypeId, int neighborhoodId, Timestamp startTime, String reason) {
        this.subscriptionTypeId = subscriptionTypeId;
        this.neighborhoodId = neighborhoodId;
        this.startTime = startTime;
        this.reason = reason;
    }

    public int getInterruptionId() {
        return interruptionId;
    }

    public void setInterruptionId(int interruptionId) {
        this.interruptionId = interruptionId;
    }

    public int getSubscriptionTypeId() {
        return subscriptionTypeId;
    }

    public void setSubscriptionTypeId(int subscriptionTypeId) {
        this.subscriptionTypeId = subscriptionTypeId;
    }

    public int getNeighborhoodId() {
        return neighborhoodId;
    }

    public void setNeighborhoodId(int neighborhoodId) {
        this.neighborhoodId = neighborhoodId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Interruption{"
                + "interruptionId=" + interruptionId
                + ", subscriptionTypeId=" + subscriptionTypeId
                + ", neighborhoodId=" + neighborhoodId
                + ", startTime=" + startTime
                + ", endTime=" + endTime
                + ", reason='" + reason + '\''
                + '}';
    }

    public static List<String[]> searchSubscriberByID(int nationalId) throws SQLException {
        String query = "SELECT c.idnum AS NationalID, st.subscription_type_name AS SubscriptionType, "
                + "c.Name, c.Surname, ci.CityName, n.Neighborhood, a.OpenAddress, s.interruption_status "
                + "FROM Customer c "
                + "JOIN Subscription s ON c.idnum = s.National_id "
                + "JOIN Address a ON s.address_id = a.address_id "
                + "JOIN Neighborhood n ON a.neighborhood_id = n.neighborhood_id "
                + "JOIN City ci ON n.city_id = ci.city_id "
                + "JOIN Subscription_Type st ON s.subscription_type_id = st.subscription_type_id "
                + "WHERE c.idnum = ?";

        List<String[]> resultList = new ArrayList<>();

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, nationalId);
            try ( ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    resultList.add(new String[]{
                        rs.getString("NationalID"),
                        rs.getString("SubscriptionType"),
                        rs.getString("Name"),
                        rs.getString("Surname"),
                        rs.getString("CityName"),
                        rs.getString("Neighborhood"),
                        rs.getString("OpenAddress"),
                        rs.getBoolean("interruption_status") ? "True" : "False"
                    });
                }
            }
        }
        return resultList;
    }

    public static List<String[]> searchSubscribers(String subscriptionType, String city, String neighborhood) throws SQLException {
        StringBuilder query = new StringBuilder(
                "SELECT c.idnum AS NationalID, st.subscription_type_name AS SubscriptionType, "
                + "c.Name, c.Surname, ci.CityName, n.Neighborhood, a.OpenAddress, s.interruption_status "
                + "FROM Customer c "
                + "JOIN Subscription s ON c.idnum = s.National_id "
                + "JOIN Address a ON s.address_id = a.address_id "
                + "JOIN Neighborhood n ON a.neighborhood_id = n.neighborhood_id "
                + "JOIN City ci ON n.city_id = ci.city_id "
                + "JOIN Subscription_Type st ON s.subscription_type_id = st.subscription_type_id "
                + "WHERE 1=1 "
        );

        List<Object> parameters = new ArrayList<>();

        if (!"All".equalsIgnoreCase(subscriptionType)) {
            query.append(" AND s.subscription_type_id = ?");
            parameters.add(Integer.parseInt(subscriptionType));
        }

        if (!"All".equalsIgnoreCase(city)) {
            query.append(" AND ci.CityName = ?");
            parameters.add(city);
        }

        if (!"All".equalsIgnoreCase(neighborhood)) {
            query.append(" AND n.Neighborhood = ?");
            parameters.add(neighborhood);
        }

        List<String[]> resultList = new ArrayList<>();

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setObject(i + 1, parameters.get(i));
            }

            try ( ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    resultList.add(new String[]{
                        rs.getString("NationalID"),
                        rs.getString("SubscriptionType"),
                        rs.getString("Name"),
                        rs.getString("Surname"),
                        rs.getString("CityName"),
                        rs.getString("Neighborhood"),
                        rs.getString("OpenAddress"),
                        rs.getBoolean("interruption_status") ? "True" : "False"
                    });
                }
            }
        }
        return resultList;
    }

    public static List<String[]> searchInterrupts(String subscriptionType, String city, String neighborhood) throws SQLException {
        StringBuilder query = new StringBuilder(
                "SELECT i.interruption_id, st.subscription_type_name AS SubscriptionType, ci.CityName, n.Neighborhood, "
                + "i.start_time, i.end_time, i.reason "
                + "FROM Interruption i "
                + "JOIN Subscription_Type st ON i.subscription_type_id = st.subscription_type_id "
                + "JOIN Neighborhood n ON i.neighborhood_id = n.neighborhood_id "
                + "JOIN City ci ON n.city_id = ci.city_id "
                + "WHERE 1=1 "
        );

        List<Object> parameters = new ArrayList<>();

        if (!"All".equalsIgnoreCase(subscriptionType)) {
            query.append(" AND i.subscription_type_id = ?");
            parameters.add(Integer.parseInt(subscriptionType));
        }

        if (!"All".equalsIgnoreCase(city)) {
            query.append(" AND ci.CityName = ?");
            parameters.add(city);
        }

        if (!"All".equalsIgnoreCase(neighborhood)) {
            query.append(" AND n.Neighborhood = ?");
            parameters.add(neighborhood);
        }

        List<String[]> resultList = new ArrayList<>();

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setObject(i + 1, parameters.get(i));
            }

            try ( ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    resultList.add(new String[]{
                        rs.getString("interruption_id"),
                        rs.getString("SubscriptionType"),
                        rs.getString("CityName"),
                        rs.getString("Neighborhood"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getString("reason")
                    });
                }
            }
        }
        return resultList;
    }

    public static void createInterruption(int subscriptionTypeId, int neighborhoodId, String reason, Timestamp startTime) throws SQLException {
        String getIdQuery = "SELECT ISNULL(MAX(interruption_id), 0) + 1 AS NextInterruptionId FROM Interruption";
        String insertInterruptionQuery = "INSERT INTO Interruption (interruption_id, subscription_type_id, neighborhood_id, reason, start_time) VALUES (?, ?, ?, ?, ?)";
        String getCustomersQuery = "SELECT c.idnum AS customer_id FROM Customer c "
                + "JOIN Subscription s ON c.idnum = s.National_id "
                + "JOIN Address a ON s.address_id = a.address_id "
                + "WHERE a.neighborhood_id = ?";
        String insertCustomerInterruptionQuery = "INSERT INTO Customer_Interruption (customer_interruption_id, interruption_id, customer_id) VALUES (?, ?, ?)";

        try ( Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            int newInterruptionId;
            try ( PreparedStatement idStmt = conn.prepareStatement(getIdQuery);  ResultSet rs = idStmt.executeQuery()) {
                if (rs.next()) {
                    newInterruptionId = rs.getInt("NextInterruptionId");
                } else {
                    throw new SQLException("Failed to generate new interruption ID.");
                }
            }

            try ( PreparedStatement insertStmt = conn.prepareStatement(insertInterruptionQuery)) {
                insertStmt.setInt(1, newInterruptionId);
                insertStmt.setInt(2, subscriptionTypeId);
                insertStmt.setInt(3, neighborhoodId);
                insertStmt.setString(4, reason);
                insertStmt.setTimestamp(5, startTime);

                insertStmt.executeUpdate();
            }

            List<Integer> customerIds = new ArrayList<>();
            try ( PreparedStatement customerStmt = conn.prepareStatement(getCustomersQuery)) {
                customerStmt.setInt(1, neighborhoodId);
                try ( ResultSet rs = customerStmt.executeQuery()) {
                    while (rs.next()) {
                        customerIds.add(rs.getInt("customer_id"));
                    }
                }
            }

            try ( PreparedStatement insertCustomerInterruptionStmt = conn.prepareStatement(insertCustomerInterruptionQuery)) {
                for (int customerId : customerIds) {
                    insertCustomerInterruptionStmt.setInt(1, getNextCustomerInterruptionId(conn));
                    insertCustomerInterruptionStmt.setInt(2, newInterruptionId);
                    insertCustomerInterruptionStmt.setInt(3, customerId);
                    insertCustomerInterruptionStmt.executeUpdate();
                }
            }

            conn.commit();
        } catch (SQLException ex) {
            throw new SQLException("Error creating interruption and updating Customer_Interruption: " + ex.getMessage(), ex);
        }
    }

    private static int getNextCustomerInterruptionId(Connection conn) throws SQLException {
        String query = "SELECT ISNULL(MAX(customer_interruption_id), 0) + 1 AS NextCustomerInterruptionId FROM Customer_Interruption";
        try ( PreparedStatement stmt = conn.prepareStatement(query);  ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("NextCustomerInterruptionId");
            } else {
                throw new SQLException("Failed to generate new customer_interruption_id.");
            }
        }
    }

    public static int getSubscriptionTypeIdByName(String subscriptionTypeName) throws SQLException {
        String query = "SELECT subscription_type_id FROM Subscription_Type WHERE subscription_type_name = ?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, subscriptionTypeName);
            try ( ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("subscription_type_id");
                } else {
                    throw new SQLException("Subscription Type not found: " + subscriptionTypeName);
                }
            }
        }
    }

    public static void updateInterruptionStatusByNeighborhood(int neighborhoodId, int index) throws SQLException {
        String query = "UPDATE Subscription "
                + "SET interruption_status = 1 "
                + "WHERE address_id IN (SELECT address_id FROM Address WHERE neighborhood_id = ?)";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(index, neighborhoodId);
            pstmt.executeUpdate();
        }
    }

    public static void resetInterruptionStatusByNeighborhood(int neighborhoodId) throws SQLException {
        String query = "UPDATE Subscription "
                + "SET interruption_status = 0 "
                + "WHERE address_id IN (SELECT address_id FROM Address WHERE neighborhood_id = ?)";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, neighborhoodId);
            pstmt.executeUpdate();
        }
    }

    public static void removeInterruption(int interruptionId, int neighborhoodId) throws SQLException {
        String deleteCustomerInterruptionQuery = "DELETE FROM Customer_Interruption WHERE interruption_id = ?";
        String deleteInterruptionQuery = "DELETE FROM Interruption WHERE interruption_id = ?";

        try ( Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            resetInterruptionStatusByNeighborhood(neighborhoodId);

            try ( PreparedStatement customerInterruptionStmt = conn.prepareStatement(deleteCustomerInterruptionQuery)) {
                customerInterruptionStmt.setInt(1, interruptionId);
                customerInterruptionStmt.executeUpdate();
            }

            try ( PreparedStatement interruptionStmt = conn.prepareStatement(deleteInterruptionQuery)) {
                interruptionStmt.setInt(1, interruptionId);
                interruptionStmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException ex) {
            throw new SQLException("Error removing interruption: " + ex.getMessage(), ex);
        }
    }

    public static int getNeighborhoodId(String neighborhood) throws SQLException {
        String query = "SELECT neighborhood_id FROM Neighborhood WHERE Neighborhood = ?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, neighborhood);
            try ( ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("neighborhood_id");
                } else {
                    throw new SQLException("Neighborhood not found.");
                }
            }
        }
    }

    public static List<String[]> searchInterruptById(int interruptionId) throws SQLException {
        String query = "SELECT i.interruption_id, st.subscription_type_name AS SubscriptionType, ci.CityName, n.Neighborhood, "
                + "i.start_time, i.reason "
                + "FROM Interruption i "
                + "JOIN Subscription_Type st ON i.subscription_type_id = st.subscription_type_id "
                + "JOIN Neighborhood n ON i.neighborhood_id = n.neighborhood_id "
                + "JOIN City ci ON n.city_id = ci.city_id "
                + "WHERE i.interruption_id = ?";

        List<String[]> resultList = new ArrayList<>();

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, interruptionId);

            try ( ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    resultList.add(new String[]{
                        rs.getString("interruption_id"),
                        rs.getString("SubscriptionType"),
                        rs.getString("CityName"),
                        rs.getString("Neighborhood"),
                        rs.getString("start_time"),
                        rs.getString("reason")
                    });
                }
            }
        }

        return resultList;
    }

}
