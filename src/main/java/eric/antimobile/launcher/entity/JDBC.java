package eric.antimobile.launcher.entity;

/**
 *
 * @author Eric
 */
import java.sql.*;

public class JDBC {
    private static final String URL = "jdbc:mariadb://localhost:3306/amb?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root"; // Thay đổi nếu cần
    private static final String PASSWORD = "admin"; // Thay đổi nếu cần

    private static boolean driverLoaded = false;

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            driverLoaded = true;
        } catch (ClassNotFoundException e) {
            System.err.println("MariaDB JDBC Driver not found. Please add mariadb-java-client.jar to your classpath.");
            driverLoaded = false;
        }
    }

    public static Connection getConnection() throws SQLException {
        if (!driverLoaded) {
            throw new SQLException("MariaDB JDBC Driver not loaded. Cannot connect to database.");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (Exception ignored) {
        }
        try {
            if (stmt != null)
                stmt.close();
        } catch (Exception ignored) {
        }
        try {
            if (conn != null)
                conn.close();
        } catch (Exception ignored) {
        }
    }

    public static ResultSet executeQuery(String sql, Object... params) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
        return stmt.executeQuery(); // Lưu ý: cần đóng conn, stmt, rs sau khi dùng
    }

    public static int executeUpdate(String sql, Object... params) throws SQLException {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            return stmt.executeUpdate();
        }
    }

    public static boolean checkLogin(String username, String password) {
        String sql = "SELECT COUNT(*) FROM accounts WHERE name = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception ex) {
            System.err.println("Database error in checkLogin: " + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }
}
