package hotel.manager;

import hotel.db.DatabaseConnection;
import hotel.model.Room;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Thêm / sửa / xóa / tìm kiếm phòng. */
public class RoomManager {

    private static final String SELECT =
            "SELECT r.id, r.room_no, r.type_id, t.name AS type_name, t.price, r.status "
            + "FROM rooms r JOIN room_types t ON r.type_id = t.id ";

    /** Lấy tất cả phòng */
    public List<Room> getAll() throws SQLException {
        return search("", 0, null);
    }

    /** Lấy các phòng đang trống (dùng khi đặt phòng) */
    public List<Room> getEmptyRooms() throws SQLException {
        return search("", 0, Room.EMPTY);
    }

    /**
     * Tìm kiếm phòng theo nhiều tiêu chí. Tiêu chí nào để trống thì bỏ qua.
     * @param roomNo số phòng (tìm gần đúng)
     * @param typeId mã loại phòng (0 = tất cả)
     * @param status trạng thái (null = tất cả)
     */
    public List<Room> search(String roomNo, int typeId, String status) throws SQLException {
        String sql = SELECT + "WHERE r.room_no LIKE ?";
        if (typeId > 0) sql += " AND r.type_id = ?";
        if (status != null) sql += " AND r.status = ?";
        sql += " ORDER BY r.room_no";

        List<Room> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            int i = 1;
            ps.setString(i++, "%" + roomNo.trim() + "%");
            if (typeId > 0) ps.setInt(i++, typeId);
            if (status != null) ps.setString(i, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Room(rs.getInt("id"), rs.getString("room_no"), rs.getInt("type_id"),
                            rs.getString("type_name"), rs.getDouble("price"), rs.getString("status")));
                }
            }
        }
        return list;
    }

    public void add(String roomNo, int typeId, String status) throws SQLException {
        validate(roomNo, typeId);
        DatabaseConnection.executeUpdate("INSERT INTO rooms(room_no, type_id, status) VALUES (?, ?, ?)",
                roomNo.trim(), typeId, status);
    }

    public void update(int id, String roomNo, int typeId, String status) throws SQLException {
        validate(roomNo, typeId);
        DatabaseConnection.executeUpdate("UPDATE rooms SET room_no = ?, type_id = ?, status = ? WHERE id = ?",
                roomNo.trim(), typeId, status, id);
    }

    public void delete(int id) throws SQLException {
        DatabaseConnection.executeUpdate("DELETE FROM rooms WHERE id = ?", id);
    }

    /** Đổi trạng thái phòng (dùng khi đặt phòng, check-in, check-out) */
    public void updateStatus(int roomId, String status) throws SQLException {
        DatabaseConnection.executeUpdate("UPDATE rooms SET status = ? WHERE id = ?", status, roomId);
    }

    /** Kiểm tra dữ liệu đầu vào */
    private void validate(String roomNo, int typeId) {
        if (roomNo.isBlank()) throw new IllegalArgumentException("Số phòng không được để trống!");
        if (typeId <= 0) throw new IllegalArgumentException("Vui lòng chọn loại phòng!");
    }
}
