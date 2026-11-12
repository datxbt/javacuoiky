package hotel.manager;

import hotel.db.DatabaseConnection;
import hotel.model.Booking;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Các truy vấn thống kê. */
public class StatsManager {

    /** Đếm số phòng theo từng trạng thái, ví dụ {Trống=4, Đang ở=1, Đã đặt=1} */
    public Map<String, Integer> countRoomsByStatus() throws SQLException {
        Map<String, Integer> map = new LinkedHashMap<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT status, COUNT(*) FROM rooms GROUP BY status");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString(1), rs.getInt(2));
            }
        }
        return map;
    }

    /** Tổng doanh thu của các đơn đã thanh toán */
    public double totalRevenue() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT COALESCE(SUM(total), 0) FROM bookings WHERE status = ?")) {
            ps.setString(1, Booking.PAID);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getDouble(1);
            }
        }
    }

    /** Doanh thu theo tháng. Mỗi dòng: {tháng, số lượt thuê, doanh thu} */
    public List<Object[]> revenueByMonth() throws SQLException {
        String sql = "SELECT DATE_FORMAT(check_out, '%m/%Y') AS thang, COUNT(*), SUM(total) "
                + "FROM bookings WHERE status = ? GROUP BY thang ORDER BY MIN(check_out)";
        List<Object[]> rows = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, Booking.PAID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new Object[]{rs.getString(1), rs.getInt(2), rs.getDouble(3)});
                }
            }
        }
        return rows;
    }
}
