package CorePackage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Request {

    private int requestId;
    private int customerNationalID;
    private String customerName;
    private String customerSurname;
    private String requestType;
    private String address;
    private String addressName;
    private String openAddress;
    private String subscriptionType;
    private String message;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String answer;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getCustomerNationalID() {
        return customerNationalID;
    }

    public void setCustomerNationalID(int customerNationalID) {
        this.customerNationalID = customerNationalID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerSurname() {
        return customerSurname;
    }

    public void setCustomerSurname(String customerSurname) {
        this.customerSurname = customerSurname;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public static List<Request> fetchRequestDetails() {
        List<Request> requestList = new ArrayList<>();
        String query = "SELECT * FROM GetRequestDetails()";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Request request = new Request();
                request.setRequestId(rs.getInt("RequestID"));
                request.setCustomerName(rs.getString("CustomerName"));
                request.setCustomerSurname(rs.getString("CustomerSurname"));
                request.setRequestType(rs.getString("RequestType"));
                request.setAddress(rs.getString("Address"));
                request.setAddressName(rs.getString("AddressName"));
                request.setSubscriptionType(rs.getString("SubscriptionType"));
                request.setMessage(rs.getString("Message"));
                request.setStatus(rs.getString("Status"));
                request.setCreatedAt(rs.getTimestamp("CreatedAt"));
                request.setUpdatedAt(rs.getTimestamp("UpdatedAt"));

                requestList.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requestList;
    }

    public static List<Request> getAllRequests(Connection connection) {
        List<Request> requests = new ArrayList<>();
        String query = "SELECT r.request_id, c.Name AS customerName, c.Surname AS customerSurname, "
                + "rt.request_type_name AS requestType, a.OpenAddress AS address, a.AddressName AS addressName, "
                + "st.subscription_type_name AS subscriptionType, r.message, s.status_name AS status, "
                + "r.created_at, r.updated_at, r.answer "
                + "FROM Request r "
                + "JOIN Customer c ON r.customer_id = c.idnum "
                + "JOIN Address a ON r.address_id = a.address_id "
                + "JOIN Request_Type rt ON r.request_type_id = rt.request_type_id "
                + "JOIN Status s ON r.status_id = s.status_id "
                + "LEFT JOIN Subscription_Type st ON r.subscription_type_id = st.subscription_type_id";
        try ( PreparedStatement ps = connection.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Request request = new Request();
                request.setRequestId(rs.getInt("request_id"));
                request.setCustomerName(rs.getString("customerName"));
                request.setCustomerSurname(rs.getString("customerSurname"));
                request.setRequestType(rs.getString("requestType"));
                request.setAddress(rs.getString("address"));
                request.setAddressName(rs.getString("addressName"));
                request.setSubscriptionType(rs.getString("subscriptionType"));
                request.setMessage(rs.getString("message"));
                request.setStatus(rs.getString("status"));
                request.setCreatedAt(rs.getTimestamp("created_at"));
                request.setUpdatedAt(rs.getTimestamp("updated_at"));
                request.setAnswer(rs.getString("answer"));
                requests.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public static List<Request> getFilteredRequests(Connection connection, String requestType, String status) {
        List<Request> requests = new ArrayList<>();
        String query = "SELECT r.request_id, c.Name AS customerName, c.Surname AS customerSurname, "
                + "rt.request_type_name AS requestType, a.OpenAddress AS address, a.AddressName AS addressName, "
                + "st.subscription_type_name AS subscriptionType, r.message, s.status_name AS status, "
                + "r.created_at, r.updated_at, r.answer "
                + "FROM Request r "
                + "JOIN Customer c ON r.customer_id = c.idnum "
                + "JOIN Address a ON r.address_id = a.address_id "
                + "JOIN Request_Type rt ON r.request_type_id = rt.request_type_id "
                + "JOIN Status s ON r.status_id = s.status_id "
                + "LEFT JOIN Subscription_Type st ON r.subscription_type_id = st.subscription_type_id "
                + "WHERE (rt.request_type_name = ? OR ? = 'All') "
                + "AND (s.status_name = ? OR ? = 'All')";
        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, requestType);
            ps.setString(2, requestType);
            ps.setString(3, status);
            ps.setString(4, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Request request = new Request();
                request.setRequestId(rs.getInt("request_id"));
                request.setCustomerName(rs.getString("customerName"));
                request.setCustomerSurname(rs.getString("customerSurname"));
                request.setRequestType(rs.getString("requestType"));
                request.setAddress(rs.getString("address"));
                request.setAddressName(rs.getString("addressName"));
                request.setSubscriptionType(rs.getString("subscriptionType"));
                request.setMessage(rs.getString("message"));
                request.setStatus(rs.getString("status"));
                request.setCreatedAt(rs.getTimestamp("created_at"));
                request.setUpdatedAt(rs.getTimestamp("updated_at"));
                request.setAnswer(rs.getString("answer"));
                requests.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public static boolean addAnswer(Connection connection, int requestId, String answer) {
        String query = "UPDATE Request SET answer = ?, updated_at = CURRENT_TIMESTAMP, status_id = ? WHERE request_id = ?";
        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, answer);
            ps.setInt(2, 2);
            ps.setInt(3, requestId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Request getRequestById(Connection connection, int requestId) {
        String query = "SELECT r.request_id, r.customer_id AS customerNationalId, c.Name AS customerName, "
                + "c.Surname AS customerSurname, rt.request_type_name AS requestType, a.OpenAddress AS address, "
                + "a.AddressName AS addressName, st.subscription_type_name AS subscriptionType, r.message, "
                + "s.status_name AS status, r.created_at, r.updated_at, r.answer "
                + "FROM Request r "
                + "JOIN Customer c ON r.customer_id = c.idnum "
                + "JOIN Address a ON r.address_id = a.address_id "
                + "JOIN Request_Type rt ON r.request_type_id = rt.request_type_id "
                + "JOIN Status s ON r.status_id = s.status_id "
                + "LEFT JOIN Subscription_Type st ON r.subscription_type_id = st.subscription_type_id "
                + "WHERE r.request_id = ?";
        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, requestId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Request request = new Request();
                request.setRequestId(rs.getInt("request_id"));
                request.setCustomerNationalID(rs.getInt("customerNationalId"));
                request.setCustomerName(rs.getString("customerName"));
                request.setCustomerSurname(rs.getString("customerSurname"));
                request.setRequestType(rs.getString("requestType"));
                request.setAddress(rs.getString("address"));
                request.setAddressName(rs.getString("addressName"));
                request.setSubscriptionType(rs.getString("subscriptionType"));
                request.setMessage(rs.getString("message"));
                request.setStatus(rs.getString("status"));
                request.setCreatedAt(rs.getTimestamp("created_at"));
                request.setUpdatedAt(rs.getTimestamp("updated_at"));
                request.setAnswer(rs.getString("answer"));
                return request;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Request> fetchFilteredRequests(int customerId, String requestStatus) {
        List<Request> requestList = new ArrayList<>();

        String query = "SELECT r.request_id, rt.request_type_name, r.message, a.AddressName, "
                + "a.OpenAddress, st.subscription_type_name, s.status_name, r.created_at, r.updated_at "
                + "FROM Request r "
                + "JOIN Request_Type rt ON r.request_type_id = rt.request_type_id "
                + "JOIN Address a ON r.address_id = a.address_id "
                + "JOIN Subscription_Type st ON r.subscription_type_id = st.subscription_type_id "
                + "JOIN Status s ON r.status_id = s.status_id "
                + "WHERE r.customer_id = ? AND s.status_name = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, customerId);
            ps.setString(2, requestStatus);

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Request request = new Request();
                    request.setRequestId(rs.getInt("request_id"));
                    request.setRequestType(rs.getString("request_type_name"));
                    request.setMessage(rs.getString("message"));
                    request.setAddressName(rs.getString("AddressName"));
                    request.setOpenAddress(rs.getString("OpenAddress"));
                    request.setSubscriptionType(rs.getString("subscription_type_name"));
                    request.setStatus(rs.getString("status_name"));
                    request.setCreatedAt(rs.getTimestamp("created_at"));
                    request.setUpdatedAt(rs.getTimestamp("updated_at"));

                    requestList.add(request);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requestList;
    }

    public static List<String> getRequestTypes(Connection connection) {
        List<String> requestTypes = new ArrayList<>();
        String query = "SELECT request_type_name FROM Request_Type";
        try ( PreparedStatement ps = connection.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                requestTypes.add(rs.getString("request_type_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requestTypes;
    }

    public static List<String> getAddressesByUsername(Connection connection, String username) {
        List<String> addresses = new ArrayList<>();
        String query = "SELECT a.AddressName "
                + "FROM Customer c "
                + "JOIN Address a ON c.idnum = a.idnum "
                + "WHERE c.Username = ?";
        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            System.out.println("Executing query: " + ps);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String address = rs.getString("AddressName");
                    System.out.println("Found AddressName: " + address);
                    addresses.add(address);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addresses;
    }

    public static List<String> getSubscriptionTypes(Connection connection) {
        List<String> subscriptionTypes = new ArrayList<>();
        String query = "SELECT subscription_type_name FROM Subscription_Type";
        try ( PreparedStatement ps = connection.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                subscriptionTypes.add(rs.getString("subscription_type_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptionTypes;
    }

    public static boolean addRequest(Connection connection, int customerId, String requestType, String addressName, String subscriptionType, String message) {
        String query = "INSERT INTO Request (customer_id, request_type_id, address_id, subscription_type_id, message, status_id, created_at) "
                + "VALUES (?, "
                + "(SELECT request_type_id FROM Request_Type WHERE request_type_name = ?), "
                + "(SELECT address_id FROM Address WHERE AddressName = ? AND idnum = ?), "
                + "(SELECT subscription_type_id FROM Subscription_Type WHERE subscription_type_name = ?), ?, 1, CURRENT_TIMESTAMP)";
        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, customerId);
            ps.setString(2, requestType);
            ps.setString(3, addressName);
            ps.setInt(4, customerId);
            ps.setString(5, subscriptionType);
            ps.setString(6, message);

            System.out.println("Executing query: " + ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean addRequest(int nationalId, String requestType, String addressName, String subscriptionType, String message) throws SQLException {
        String query = "INSERT INTO Request (customer_id, request_type_id, address_id, subscription_type_id, message, status_id, created_at) "
                + "VALUES ( "
                + "(SELECT idnum FROM Customer WHERE idnum = ?), "
                + "(SELECT request_type_id FROM Request_Type WHERE request_type_name = ?), "
                + "(SELECT address_id FROM Address WHERE AddressName = ? AND idnum = ?), "
                + "(SELECT subscription_type_id FROM Subscription_Type WHERE subscription_type_name = ?), "
                + "?, 1, CURRENT_TIMESTAMP)";

        try ( Connection connection = DBConnection.getConnection();  PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, nationalId);
            ps.setString(2, requestType);
            ps.setString(3, addressName);
            ps.setInt(4, nationalId);
            ps.setString(5, subscriptionType);
            ps.setString(6, message);

            System.out.println("Executing query: " + ps);
            return ps.executeUpdate() > 0;
        }
    }

    public static boolean hasExistingSubscription(int nationalId, String addressName, String subscriptionType) {
        String query = "SELECT 1 FROM Subscription s "
                + "JOIN Address a ON s.address_id = a.address_id "
                + "JOIN Subscription_Type st ON s.subscription_type_id = st.subscription_type_id "
                + "JOIN Subscriber sb ON s.subscriber_id = sb.subscriber_id "
                + "WHERE a.AddressName = ? AND st.subscription_type_name = ? AND sb.National_id = ?";
        try ( Connection connection = DBConnection.getConnection();  PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, addressName);
            ps.setString(2, subscriptionType);
            ps.setInt(3, nationalId);
            try ( ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Request> getDetailedRequests(Connection connection, String customerId, String status) {
        List<Request> requests = new ArrayList<>();
        String query = "SELECT r.request_id, "
                + "rt.request_type_name, "
                + "st.subscription_type_name, "
                + "r.message, "
                + "s.status_name, "
                + "r.created_at, "
                + "r.updated_at, "
                + "COALESCE(r.answer, 'N/A') AS answer "
                + "FROM Request r "
                + "JOIN Request_Type rt ON r.request_type_id = rt.request_type_id "
                + "JOIN Subscription_Type st ON r.subscription_type_id = st.subscription_type_id "
                + "JOIN Status s ON r.status_id = s.status_id "
                + "WHERE r.customer_id = ? AND s.status_name = ?";
        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, customerId);
            ps.setString(2, status);

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Request request = new Request();
                    request.setRequestId(rs.getInt("request_id"));
                    request.setRequestType(rs.getString("request_type_name"));
                    request.setSubscriptionType(rs.getString("subscription_type_name"));
                    request.setMessage(rs.getString("message"));
                    request.setStatus(rs.getString("status_name"));
                    request.setCreatedAt(rs.getTimestamp("created_at"));
                    request.setUpdatedAt(rs.getTimestamp("updated_at"));
                    request.setAnswer(rs.getString("answer"));
                    requests.add(request);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

}
