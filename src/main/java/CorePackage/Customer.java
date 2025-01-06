package CorePackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User {

    private String name;
    private String surname;
    private String phone;
    private String nationalIdNumber;
    private String email;
    private List<Address> addresses;
    private List<Subscription> subscriptions;

    public Customer(String name, String surname, String password, String username, String phone, String nationalIdNumber, String email, List<Address> addresses, List<Subscription> subscriptions) {
        super(password, username);
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.nationalIdNumber = nationalIdNumber;
        this.email = email;
        this.addresses = addresses;
        this.subscriptions = subscriptions;
    }

    public Customer(String name, String surname, String password, String username, String phone, String nationalIdNumber) {
        super(password, username);
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.nationalIdNumber = nationalIdNumber;
    }

    public Customer(String password, String username) {
        super(password, username);
    }

    public Customer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNationalIdNumber() {
        return nationalIdNumber;
    }

    public void setNationalIdNumber(String nationalIdNumber) {
        this.nationalIdNumber = nationalIdNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Address> getAddresses() {
        if (addresses == null) {
            addresses = new ArrayList<>();
        }
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Subscription> getSubscriptions() {
        if (subscriptions == null) {
            subscriptions = new ArrayList<>();
        }
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public void addAddress(Address address, int neighborhoodId) {
        Address.addAddress(nationalIdNumber, address, neighborhoodId);
    }

    public void updateAddress(Address address, int neighborhoodId) {
        Address.updateAddress(address, neighborhoodId);
    }

    public void insertCustomer(Customer customer) {
        String query = "INSERT INTO Customer (Name, Surname, Username, Password, PhoneNum, IdNum) VALUES (?, ?, ?, ?, ?, ?)";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getSurname());
            stmt.setString(3, customer.getUsername());
            stmt.setString(4, customer.getPassword());
            stmt.setString(5, customer.getPhone());
            stmt.setString(6, customer.getNationalIdNumber());
            stmt.executeUpdate();
            System.out.println("Customer successfully added to database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Customer getCustomerByUsername(String username) {
        String query = "SELECT c.Name, c.Surname, c.Username, c.idnum, c.PhoneNum, c.Email, "
                + "a.address_id, a.AddressName, a.OpenAddress, n.neighborhood_id, n.Neighborhood, n.city_id, ci.CityName "
                + "FROM Customer c "
                + "LEFT JOIN Address a ON c.idnum = a.idnum "
                + "LEFT JOIN Neighborhood n ON a.neighborhood_id = n.neighborhood_id "
                + "LEFT JOIN City ci ON n.city_id = ci.city_id "
                + "WHERE c.Username = ?";
        Customer customer = null;
        List<Address> addresses = new ArrayList<>();
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    if (customer == null) {
                        customer = new Customer();
                        customer.setName(rs.getString("Name"));
                        customer.setSurname(rs.getString("Surname"));
                        customer.setUsername(rs.getString("Username"));
                        customer.setNationalIdNumber(rs.getString("idnum"));
                        customer.setPhone(rs.getString("PhoneNum"));
                        customer.setEmail(rs.getString("Email"));
                    }
                    int addressId = rs.getInt("address_id");
                    if (addressId != 0) {
                        Neighborhood neighborhood = new Neighborhood(
                                rs.getInt("neighborhood_id"),
                                rs.getString("Neighborhood"),
                                rs.getInt("city_id")
                        );
                        Address address = new Address(
                                addressId,
                                rs.getString("AddressName"),
                                rs.getString("OpenAddress"),
                                neighborhood
                        );
                        addresses.add(address);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (customer != null) {
            customer.setAddresses(addresses);
        }
        return customer;
    }

    public void updateCustomerDetails() {
        String query = "UPDATE Customer SET Name = ?, Surname = ?, Username = ?, PhoneNum = ?, Email = ? WHERE idnum = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, getName());
            stmt.setString(2, getSurname());
            stmt.setString(3, getUsername());
            stmt.setString(4, getPhone());
            stmt.setString(5, getEmail());
            stmt.setString(6, getNationalIdNumber());
            stmt.executeUpdate();
            System.out.println("Customer details updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAddress(Address address) {
        String query = "DELETE FROM Address WHERE address_id = ?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, address.getAddressId());
            stmt.executeUpdate();
            System.out.println("Address deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getOpenAddressByName(String addressName, String nationalIdNumber) {
        String query = "SELECT OpenAddress FROM Address WHERE AddressName = ? AND idnum = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, addressName);
            stmt.setString(2, nationalIdNumber);

            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("OpenAddress");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "No address found.";
    }

    public void loadAddressesFromDatabase() {
        String query = "SELECT a.address_id, a.AddressName, a.OpenAddress, n.neighborhood_id, n.Neighborhood, n.city_id, ci.CityName "
                + "FROM Address a "
                + "LEFT JOIN Neighborhood n ON a.neighborhood_id = n.neighborhood_id "
                + "LEFT JOIN City ci ON n.city_id = ci.city_id "
                + "WHERE a.idnum = ?";
        List<Address> loadedAddresses = new ArrayList<>();
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nationalIdNumber);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Neighborhood neighborhood = new Neighborhood(
                            rs.getInt("neighborhood_id"),
                            rs.getString("Neighborhood"),
                            rs.getInt("city_id")
                    );
                    Address address = new Address(
                            rs.getInt("address_id"),
                            rs.getString("AddressName"),
                            rs.getString("OpenAddress"),
                            neighborhood
                    );
                    loadedAddresses.add(address);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setAddresses(loadedAddresses);
        System.out.println("Addresses loaded successfully from the database.");
    }

    public static boolean isPasswordCorrect(String username, String password) {
        String query = "SELECT Password FROM Customer WHERE Username = ?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);

            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("Password");
                    return storedPassword.equals(password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updatePasswordInDatabase() {
        String query = "UPDATE Customer SET Password = ? WHERE Username = ?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, getPassword());
            stmt.setString(2, getUsername());
            stmt.executeUpdate();
            System.out.println("Password updated successfully in the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT idnum AS National_ID_Number, Name, Surname, Username, Password, PhoneNum, Email FROM Customer";

        try ( Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.out.println("Veritabanı bağlantısı başarısız.");
                return customers;
            }
            try ( PreparedStatement stmt = conn.prepareStatement(query);  ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.setNationalIdNumber(rs.getString("National_ID_Number"));
                    customer.setName(rs.getString("Name"));
                    customer.setSurname(rs.getString("Surname"));
                    customer.setUsername(rs.getString("Username"));
                    customer.setPassword(rs.getString("Password"));
                    customer.setPhone(rs.getString("PhoneNum"));
                    customer.setEmail(rs.getString("Email"));
                    customers.add(customer);
                }
                System.out.println("Toplam müşteri sayısı: " + customers.size());
            }
        } catch (SQLException e) {
            System.out.println("Müşterileri çekerken hata oluştu: " + e.getMessage());
        }
        return customers;
    }

    public Customer getCustomerDetails(String nationalId) {
        String query = "SELECT idnum AS National_ID_Number, Name, Surname, Username, Password, PhoneNum, Email FROM Customer WHERE idnum = ?";
        Customer customer = null;

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, nationalId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    customer = new Customer();
                    customer.setNationalIdNumber(rs.getString("National_ID_Number"));
                    customer.setName(rs.getString("Name"));
                    customer.setSurname(rs.getString("Surname"));
                    customer.setUsername(rs.getString("Username"));
                    customer.setPassword(rs.getString("Password"));
                    customer.setPhone(rs.getString("PhoneNum"));
                    customer.setEmail(rs.getString("Email"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public boolean deleteCustomer(String nationalId) {
        String deleteSubscriptionsQuery = "DELETE FROM Subscription WHERE subscriber_id = ?";
        String deleteCustomerQuery = "DELETE FROM Customer WHERE idnum = ?";

        try ( Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.out.println("Veritabanı bağlantısı başarısız.");
                return false;
            }

            try ( PreparedStatement deleteSubscriptionsStmt = conn.prepareStatement(deleteSubscriptionsQuery)) {
                deleteSubscriptionsStmt.setString(1, nationalId);
                int subscriptionRowsDeleted = deleteSubscriptionsStmt.executeUpdate();
                System.out.println("Silinen abonelik sayısı: " + subscriptionRowsDeleted);
            }

            try ( PreparedStatement deleteCustomerStmt = conn.prepareStatement(deleteCustomerQuery)) {
                deleteCustomerStmt.setString(1, nationalId);
                int customerRowsDeleted = deleteCustomerStmt.executeUpdate();
                if (customerRowsDeleted > 0) {
                    System.out.println("Müşteri başarıyla silindi: " + nationalId);
                    return true;
                } else {
                    System.out.println("Müşteri bulunamadı: " + nationalId);
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Müşteri silme sırasında hata oluştu: " + e.getMessage());
            return false;
        }
    }

    public int getIdNumByUsername(Connection connection, String username) {
        String query = "SELECT idnum FROM Customer WHERE Username = ?";
        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idnum");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;

    }

    public void loadSubscriptions(Connection connection) {
        String query = "SELECT * FROM Subscription WHERE subscriber_id = ?";
        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, getIdNumByUsername(connection, this.getUsername()));
            try ( ResultSet rs = ps.executeQuery()) {
                List<Subscription> subscriptionList = new ArrayList<>();
                while (rs.next()) {
                    Subscription subscription = new Subscription();
                    subscription.setSubscriptionId(rs.getInt("subscription_id"));
                    subscription.setSubscriptionTypeId(rs.getInt("subscription_type_id"));
                    subscription.setStartDate(rs.getString("subscribe_start_date"));
                    subscription.setEndDate(rs.getString("subscribe_end_date"));
                    subscription.setAddressId(rs.getInt("address_id"));
                    subscriptionList.add(subscription);
                }
                this.subscriptions = subscriptionList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadAddresses(Connection connection) {
        String query = "SELECT * FROM Address WHERE idnum = ?";
        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, this.nationalIdNumber);
            try ( ResultSet rs = ps.executeQuery()) {
                List<Address> addressList = new ArrayList<>();
                while (rs.next()) {
                    Address address = new Address();
                    address.setAddressId(rs.getInt("address_id"));
                    address.setAddressName(rs.getString("AddressName"));
                    address.setOpenAddress(rs.getString("OpenAddress"));
                    addressList.add(address);
                }
                this.addresses = addressList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getNationalIdByUsername(String username) {
        String query = "SELECT idnum FROM Customer WHERE Username = ?";
        try ( Connection connection = DBConnection.getConnection();  PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idnum");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
