import java.sql.*;

public class UserCRUD {
    // Replace with your Azure MySQL Flexible Server details
    private static final String URL = "jdbc:mysql://<your-mysql-flexible-server>.mysql.database.azure.com:3306/<your-database-name>?useSSL=true&requireSSL=false";
    private static final String USER = "<your-username>@<your-mysql-flexible-server>";
    private static final String PASSWORD = "<your-password>";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("✅ Connected to Azure MySQL Flexible Server!");

            // CREATE
            String insertSQL = "INSERT INTO users (name, email) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
                stmt.setString(1, "Alice");
                stmt.setString(2, "alice@example.com");
                stmt.executeUpdate();
                System.out.println("Inserted user Alice");
            }

            // READ
            String selectSQL = "SELECT * FROM users";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(selectSQL)) {
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + " | " +
                                       rs.getString("name") + " | " +
                                       rs.getString("email"));
                }
            }

            // UPDATE
            String updateSQL = "UPDATE users SET email=? WHERE name=?";
            try (PreparedStatement stmt = conn.prepareStatement(updateSQL)) {
                stmt.setString(1, "alice@newdomain.com");
                stmt.setString(2, "Alice");
                stmt.executeUpdate();
                System.out.println("Updated Alice's email");
            }

            // DELETE
            String deleteSQL = "DELETE FROM users WHERE name=?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {
                stmt.setString(1, "Alice");
                stmt.executeUpdate();
                System.out.println("Deleted Alice");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
