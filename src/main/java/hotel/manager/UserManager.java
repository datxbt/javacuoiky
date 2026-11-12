package hotel.manager;

import hotel.db.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Xử lý đăng nhập. */
public class UserManager {

    /**
     * Kiểm tra tài khoản.
     * @return quyền ("admin" hoặc "user") nếu đúng, null nếu sai tài khoản/mật khẩu
     */
    public String login(String username, String password) throws SQLException {
        if (username.isBlank() || password.isBlank()) {
            throw new IllegalArgumentException("Vui lòng nhập tài khoản và mật khẩu!");
        }
        String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getString("role") : null;
            }
        }
    }
}
