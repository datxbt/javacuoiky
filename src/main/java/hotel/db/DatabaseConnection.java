package hotel.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Lớp quản lý kết nối cơ sở dữ liệu MySQL.
 * Sửa URL / USER / PASSWORD nếu máy bạn cấu hình khác.
 */
public class DatabaseConnection {

    // connectTimeout=3000: nếu MySQL không phản hồi sau 3 giây thì báo lỗi
    private static final String URL =
            "jdbc:mysql://localhost:3306/hotel_db?useUnicode=true&characterEncoding=UTF-8&connectTimeout=3000";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /** Mở một kết nối mới tới CSDL. Nhớ đóng sau khi dùng (try-with-resources). */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Chạy câu lệnh INSERT / UPDATE / DELETE với các tham số (thay cho dấu ?).
     * Trả về số dòng bị ảnh hưởng.
     */
    public static int executeUpdate(String sql, Object... params) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            return ps.executeUpdate();
        }
    }
}
