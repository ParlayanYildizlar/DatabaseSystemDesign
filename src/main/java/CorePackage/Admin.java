package CorePackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author bayra
 */
public class Admin extends User {

    public Admin(String password, String username) {
        super(password, username);
    }

    public Admin() {
    }

    public void insertAdmin(Admin admin) {
        String query = "INSERT INTO Admin (Username, Password) VALUES (?, ?)";

        try {
            Connection conn = new DBConnection().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, admin.getUsername());
            stmt.setString(2, admin.getPassword());
            stmt.executeUpdate();

            System.out.println("Admin successfully added to database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
