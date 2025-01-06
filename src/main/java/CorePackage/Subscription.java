package CorePackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Subscription {

    private int subscriptionId;
    private int subscriberId;
    private String nationalId;
    private int subscriptionTypeId;
    private String startDate;
    private String endDate;
    private int addressId;
    private boolean interruptionStatus;
    private List<Bill> bills;
    private int customerId;

    public Subscription() {
        this.bills = new ArrayList<>();
    }

    public Subscription(int subscriptionId, int subscriberId, String nationalId, int subscriptionTypeId, String startDate, String endDate, int addressId, boolean interruptionStatus) {
        this.subscriptionId = subscriptionId;
        this.subscriberId = subscriberId;
        this.nationalId = nationalId;
        this.subscriptionTypeId = subscriptionTypeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.addressId = addressId;
        this.interruptionStatus = interruptionStatus;
        this.bills = new ArrayList<>();
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public int getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(int subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public int getSubscriptionTypeId() {
        return subscriptionTypeId;
    }

    public void setSubscriptionTypeId(int subscriptionTypeId) {
        this.subscriptionTypeId = subscriptionTypeId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public boolean isInterruptionStatus() {
        return interruptionStatus;
    }

    public void setInterruptionStatus(boolean interruptionStatus) {
        this.interruptionStatus = interruptionStatus;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public static void deleteSubscriptionFromDatabase(int subscriptionId) {
        String query = "DELETE FROM Subscription WHERE subscription_id = ?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, subscriptionId);
            stmt.executeUpdate();
            System.out.println("Subscription deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Subscription> getSubscriptionsByAddressAndType(int addressId, int subscriptionTypeId) {
        List<Subscription> subscriptions = new ArrayList<>();
        String query = "SELECT * FROM Subscription WHERE address_id = ? AND subscription_type_id = ?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, addressId);
            stmt.setInt(2, subscriptionTypeId);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    subscriptions.add(new Subscription(
                            rs.getInt("subscription_id"),
                            rs.getInt("subscriber_id"),
                            rs.getString("National_id"),
                            rs.getInt("subscription_type_id"),
                            rs.getString("subscribe_start_date"),
                            rs.getString("subscribe_end_date"),
                            rs.getInt("address_id"),
                            rs.getBoolean("interruption_status")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptions;
    }

    public static List<Object[]> getAllSubscriptionDetails() {
        List<Object[]> subscriptionDetails = new ArrayList<>();
        String query = """
        SELECT
            c.idnum AS National_ID,
            c.Name AS Customer_Name,
            c.Surname AS Customer_Surname,
            s.subscription_id AS Subscription_ID,
            st.subscription_type_name AS Subscription_Type,
            a.AddressName AS Address
        FROM Subscription s
        JOIN Subscriber sub ON s.subscriber_id = sub.subscriber_id
        JOIN Customer c ON sub.National_id = c.idnum
        JOIN Subscription_Type st ON s.subscription_type_id = st.subscription_type_id
        JOIN Address a ON s.address_id = a.address_id
    """;

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query);  ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                subscriptionDetails.add(new Object[]{
                    rs.getInt("National_ID"),
                    rs.getString("Customer_Name"),
                    rs.getString("Customer_Surname"),
                    rs.getInt("Subscription_ID"),
                    rs.getString("Subscription_Type"),
                    rs.getString("Address")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptionDetails;
    }

    public static void addSubscription(int subscriberId, int nationalId, int subscriptionTypeId, int addressId) {
        String insertQuery = "INSERT INTO Subscription (subscriber_id, National_id, subscription_type_id, subscribe_start_date, subscribe_end_date, address_id, interruption_status) "
                + "VALUES (?, ?, ?, GETDATE(), NULL, ?, ?)";
        try ( Connection connection = DBConnection.getConnection();  PreparedStatement ps = connection.prepareStatement(insertQuery)) {

            ps.setInt(1, subscriberId);
            ps.setInt(2, nationalId);
            ps.setInt(3, subscriptionTypeId);
            ps.setInt(4, addressId);
            ps.setBoolean(5, false);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Subscription added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding subscription: " + e.getMessage());
        }
    }

    public static int getSubscriberId(int nationalId) {
        String query = "SELECT subscriber_id FROM Subscriber WHERE National_id = ?";
        try ( Connection connection = DBConnection.getConnection();  PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, nationalId);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("subscriber_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void loadBillsForSubscription() {
        String query = """
        SELECT i.invoice_id, st.subscription_type_name, i.issue_date, i.due_date, 
               i.total_amount, i.payment_status
        FROM Invoice i
        JOIN Subscription s ON i.subscription_id = s.subscription_id
        JOIN Subscription_Type st ON s.subscription_type_id = st.subscription_type_id
        WHERE s.subscription_id = ?
    """;

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, this.subscriptionId);
            ResultSet rs = stmt.executeQuery();
            this.bills.clear();

            while (rs.next()) {
                this.bills.add(new Bill(
                        rs.getInt("invoice_id"),
                        rs.getString("subscription_type_name"),
                        rs.getDate("issue_date"),
                        rs.getDate("due_date"),
                        rs.getDouble("total_amount"),
                        rs.getString("payment_status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Bill> getBills() {
        return bills;
    }

    public static boolean addSubscription(Subscription subscription) {
        String query = """
        INSERT INTO Subscription (subscriber_id, National_id, subscription_type_id, 
                                   subscribe_start_date, subscribe_end_date, address_id, 
                                   interruption_status)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """;

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, subscription.getSubscriberId());
            stmt.setString(2, subscription.getNationalId());
            stmt.setInt(3, subscription.getSubscriptionTypeId());
            stmt.setString(4, subscription.getStartDate());
            stmt.setString(5, subscription.getEndDate());
            stmt.setInt(6, subscription.getAddressId());
            stmt.setBoolean(7, subscription.isInterruptionStatus());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateSubscription(Subscription subscription) {
        String query = """
        UPDATE Subscription
        SET subscriber_id = ?, 
            National_id = ?, 
            subscription_type_id = ?, 
            subscribe_start_date = ?, 
            subscribe_end_date = ?, 
            address_id = ?, 
            interruption_status = ?
        WHERE subscription_id = ?
    """;

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, subscription.getSubscriberId());
            stmt.setString(2, subscription.getNationalId());
            stmt.setInt(3, subscription.getSubscriptionTypeId());
            stmt.setString(4, subscription.getStartDate());
            stmt.setString(5, subscription.getEndDate());
            stmt.setInt(6, subscription.getAddressId());
            stmt.setBoolean(7, subscription.isInterruptionStatus());
            stmt.setInt(8, subscription.getSubscriptionId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Subscription> getSubscriptionsByCustomerId(int customerId) {
        List<Subscription> subscriptions = new ArrayList<>();
        String query = "SELECT * FROM dbo.Subscription WHERE customer_id = ?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerId);

            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Subscription subscription = new Subscription();
                    subscription.setSubscriptionId(rs.getInt("subscription_id"));
                    subscription.setCustomerId(rs.getInt("customer_id"));
                    subscription.setSubscriptionTypeId(rs.getInt("subscription_type_id"));
                    subscription.setStartDate(rs.getString("subscribe_start_date"));
                    subscription.setEndDate(rs.getString("subscribe_end_date"));
                    subscription.setAddressId(rs.getInt("address_id"));
                    subscription.setInterruptionStatus(rs.getBoolean("interruption_status"));
                    subscriptions.add(subscription);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptions;
    }

}
