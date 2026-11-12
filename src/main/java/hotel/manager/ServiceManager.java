package hotel.manager;

import hotel.db.DatabaseConnection;
import hotel.model.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Thêm / sửa / xóa / lấy danh sách dịch vụ đi kèm. */
public class ServiceManager {

    /** Lấy tất cả dịch vụ */
    public List<Service> getAll() throws SQLException {
        List<Service> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM services ORDER BY id");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Service(rs.getInt("id"), rs.getString("name"), rs.getDouble("price")));
            }
        }
        return list;
    }

    public void add(String name, double price) throws SQLException {
        validate(name, price);
        DatabaseConnection.executeUpdate("INSERT INTO services(name, price) VALUES (?, ?)", name.trim(), price);
    }

    public void update(int id, String name, double price) throws SQLException {
        validate(name, price);
        DatabaseConnection.executeUpdate("UPDATE services SET name = ?, price = ? WHERE id = ?", name.trim(), price, id);
    }

    public void delete(int id) throws SQLException {
        DatabaseConnection.executeUpdate("DELETE FROM services WHERE id = ?", id);
    }

    /** Kiểm tra dữ liệu đầu vào */
    private void validate(String name, double price) {
        if (name.isBlank()) throw new IllegalArgumentException("Tên dịch vụ không được để trống!");
        if (price <= 0) throw new IllegalArgumentException("Giá phải lớn hơn 0!");
    }
}
