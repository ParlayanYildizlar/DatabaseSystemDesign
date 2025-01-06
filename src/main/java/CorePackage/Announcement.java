package CorePackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Announcement {

    private int announcementId;
    private String announcementType;
    private String announcementContent;
    private String createdAt;

    public Announcement(int announcementId, String announcementType, String announcementContent, String createdAt) {
        this.announcementId = announcementId;
        this.announcementType = announcementType;
        this.announcementContent = announcementContent;
        this.createdAt = createdAt;
    }

    public int getAnnouncementId() {
        return announcementId;
    }

    public String getAnnouncementType() {
        return announcementType;
    }

    public String getAnnouncementContent() {
        return announcementContent;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public static List<Announcement> getAnnouncementsByNeighborhoodId(int neighborhoodId) {
        List<Announcement> announcements = new ArrayList<>();
        String query = "SELECT a.announcement_id, tt.topic_type AS announcement_type, "
                + "a.announcement_content, a.created_at "
                + "FROM Announcement a "
                + "JOIN Topic_Type tt ON a.topic_id = tt.topic_id "
                + "WHERE a.neighborhood_id = ? "
                + "ORDER BY a.created_at DESC";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, neighborhoodId);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    announcements.add(new Announcement(
                            rs.getInt("announcement_id"),
                            rs.getString("announcement_type"),
                            rs.getString("announcement_content"),
                            rs.getString("created_at")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return announcements;
    }

    public static List<Integer> getAffectedCustomers(int interruptionId) throws SQLException {
        String query = "SELECT customer_id FROM Customer_Interruption WHERE interruption_id = ?";
        List<Integer> customerIds = new ArrayList<>();

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, interruptionId);
            try ( ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    customerIds.add(rs.getInt("customer_id"));
                }
            }
        }
        return customerIds;
    }

    public static void createAnnouncement(int customerId, int interruptionId, String message) throws SQLException {
        String query = "INSERT INTO Announcement (announcement_id, neighborhood_id, subscription_type_id, topic_id, created_at, announcement_content) "
                + "VALUES ((SELECT ISNULL(MAX(announcement_id), 0) + 1 FROM Announcement), "
                + "(SELECT neighborhood_id FROM Interruption WHERE interruption_id = ?), "
                + "(SELECT subscription_type_id FROM Interruption WHERE interruption_id = ?), "
                + "2, CURRENT_TIMESTAMP, ?)";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, interruptionId);
            pstmt.setInt(2, interruptionId);
            pstmt.setString(3, message);
            pstmt.executeUpdate();
        }
    }

    public static List<String[]> searchInterruptsWithMessage(String subscriptionType, String city, String neighborhood) throws SQLException {
        StringBuilder query = new StringBuilder(
                "SELECT i.interruption_id, st.subscription_type_name AS SubscriptionType, ci.CityName AS CityName, n.Neighborhood AS NeighborhoodName, "
                + "i.start_time, i.end_time, ISNULL(a.announcement_content, '') AS AnnouncementMessage "
                + "FROM Interruption i "
                + "JOIN Subscription_Type st ON i.subscription_type_id = st.subscription_type_id "
                + "JOIN Neighborhood n ON i.neighborhood_id = n.neighborhood_id "
                + "JOIN City ci ON n.city_id = ci.city_id "
                + "LEFT JOIN Announcement a ON i.neighborhood_id = a.neighborhood_id AND i.subscription_type_id = a.subscription_type_id "
                + "WHERE 1=1"
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
                        rs.getString("NeighborhoodName"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getString("AnnouncementMessage")
                    });
                }
            }
        }
        return resultList;
    }

    public static List<String[]> searchInterruptByIDWithMessage(int interruptionId) throws SQLException {
        String query
                = "SELECT i.interruption_id, st.subscription_type_name AS SubscriptionType, ci.CityName AS CityName, n.Neighborhood AS NeighborhoodName, "
                + "i.start_time, i.end_time, ISNULL(a.announcement_content, '') AS AnnouncementMessage "
                + "FROM Interruption i "
                + "JOIN Subscription_Type st ON i.subscription_type_id = st.subscription_type_id "
                + "JOIN Neighborhood n ON i.neighborhood_id = n.neighborhood_id "
                + "JOIN City ci ON n.city_id = ci.city_id "
                + "LEFT JOIN Announcement a ON i.neighborhood_id = a.neighborhood_id AND i.subscription_type_id = a.subscription_type_id "
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
                        rs.getString("NeighborhoodName"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getString("AnnouncementMessage")
                    });
                }
            }
        }
        return resultList;
    }
}
