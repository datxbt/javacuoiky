package hotel.manager;

import hotel.db.DatabaseConnection;
import hotel.model.RoomType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Thêm / sửa / xóa / lấy danh sách loại phòng. */
public class RoomTypeManager {

    /** Lấy tất cả loại phòng */
    public List<RoomType> getAll() throws SQLException {
        List<RoomType> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM room_types ORDER BY id");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new RoomType(rs.getInt("id"), rs.getString("name"), rs.getDouble("price")));
            }
        }
        return list;
    }

    public void add(String name, double price) throws SQLException {
        validate(name, price);
        DatabaseConnection.executeUpdate("INSERT INTO room_types(name, price) VALUES (?, ?)", name.trim(), price);
    }

    public void update(int id, String name, double price) throws SQLException {
        validate(name, price);
        DatabaseConnection.executeUpdate("UPDATE room_types SET name = ?, price = ? WHERE id = ?", name.trim(), price, id);
    }

    public void delete(int id) throws SQLException {
        DatabaseConnection.executeUpdate("DELETE FROM room_types WHERE id = ?", id);
    }

    /** Kiểm tra dữ liệu đầu vào */
    private void validate(String name, double price) {
        if (name.isBlank()) throw new IllegalArgumentException("Tên loại phòng không được để trống!");
        if (price <= 0) throw new IllegalArgumentException("Giá phải lớn hơn 0!");
    }
}
