package CorePackage;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author bayra
 */
public class User {

    private String password;
    private String username;

    public User(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public User() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isUsernameExists(String username) {
        String adminQuery = "SELECT COUNT(*) FROM Admin WHERE Username = ?";
        String customerQuery = "SELECT COUNT(*) FROM Customer WHERE Username = ?";

        try ( Connection conn = DBConnection.getConnection()) {
            try ( PreparedStatement adminStmt = conn.prepareStatement(adminQuery)) {
                adminStmt.setString(1, username);
                try ( ResultSet rs = adminStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        return true;
                    }
                }
            }

            try ( PreparedStatement customerStmt = conn.prepareStatement(customerQuery)) {
                customerStmt.setString(1, username);
                try ( ResultSet rs = customerStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        return true;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isCredentialsValid(String username, String password) {
        String sql = "{? = CALL dbo.IsCredentialsValid(?, ?)}";

        try ( Connection conn = DBConnection.getConnection();  CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, java.sql.Types.BIT);

            stmt.setString(2, username);
            stmt.setString(3, password);

            stmt.execute();

            return stmt.getBoolean(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isAdmin(String username) {
        String query = "SELECT COUNT(*) FROM Admin WHERE Username = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isCustomer(String username) {
        String query = "SELECT COUNT(*) FROM Customer WHERE Username = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
