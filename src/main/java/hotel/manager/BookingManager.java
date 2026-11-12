package hotel.manager;

import hotel.db.DatabaseConnection;
import hotel.model.Booking;
import hotel.model.Room;
import hotel.model.Service;
import hotel.model.UsedService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Xử lý nghiệp vụ đặt phòng:
 * Đặt phòng -> Check-in -> Thêm dịch vụ -> Check-out (tính tiền). Có thể hủy khi chưa check-in.
 */
public class BookingManager {

    private final RoomManager roomManager = new RoomManager();

    /**
     * Tìm đơn đặt phòng theo tên khách (gần đúng) và trạng thái.
     * @param status null = tất cả trạng thái
     */
    public List<Booking> search(String customerName, String status) throws SQLException {
        String sql = "SELECT b.*, r.room_no FROM bookings b JOIN rooms r ON b.room_id = r.id "
                + "WHERE b.customer_name LIKE ?";
        if (status != null) sql += " AND b.status = ?";
        sql += " ORDER BY b.id DESC";

        List<Booking> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + customerName.trim() + "%");
            if (status != null) ps.setString(2, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Booking(rs.getInt("id"), rs.getString("customer_name"), rs.getString("phone"),
                            rs.getInt("room_id"), rs.getString("room_no"),
                            rs.getDate("check_in").toLocalDate(), rs.getDate("check_out").toLocalDate(),
                            rs.getString("status"), rs.getDouble("total"), rs.getString("staff")));
                }
            }
        }
        return list;
    }

    /** Đặt phòng mới. Phòng chuyển sang trạng thái "Đã đặt". */
    public void book(String customerName, String phone, Room room,
                     LocalDate checkIn, LocalDate checkOut, String staff) throws SQLException {
        if (customerName.isBlank()) throw new IllegalArgumentException("Tên khách không được để trống!");
        if (!phone.trim().matches("0\\d{9,10}")) throw new IllegalArgumentException("Số điện thoại phải là 10-11 chữ số, bắt đầu bằng 0!");
        if (room == null) throw new IllegalArgumentException("Vui lòng chọn phòng (không còn phòng trống?)");
        if (checkOut.isBefore(checkIn)) throw new IllegalArgumentException("Ngày trả phải sau hoặc bằng ngày nhận!");

        DatabaseConnection.executeUpdate(
                "INSERT INTO bookings(customer_name, phone, room_id, check_in, check_out, status, staff) VALUES (?, ?, ?, ?, ?, ?, ?)",
                customerName.trim(), phone.trim(), room.getId(), checkIn, checkOut, Booking.BOOKED, staff);
        roomManager.updateStatus(room.getId(), Room.BOOKED);
    }

    /** Khách nhận phòng. Chỉ làm được khi đơn đang "Đã đặt". */
    public void checkIn(Booking b) throws SQLException {
        requireStatus(b, Booking.BOOKED, "check-in");
        DatabaseConnection.executeUpdate("UPDATE bookings SET status = ? WHERE id = ?", Booking.STAYING, b.getId());
        roomManager.updateStatus(b.getRoomId(), Room.OCCUPIED);
    }

    /** Hủy đặt phòng. Chỉ hủy được khi khách chưa check-in. */
    public void cancel(Booking b) throws SQLException {
        requireStatus(b, Booking.BOOKED, "hủy");
        DatabaseConnection.executeUpdate("UPDATE bookings SET status = ? WHERE id = ?", Booking.CANCELED, b.getId());
        roomManager.updateStatus(b.getRoomId(), Room.EMPTY);
    }

    /** Thêm dịch vụ khách sử dụng. Chỉ thêm được khi khách đang ở. */
    public void addService(Booking b, Service s, int quantity) throws SQLException {
        requireStatus(b, Booking.STAYING, "thêm dịch vụ");
        if (s == null) throw new IllegalArgumentException("Vui lòng chọn dịch vụ!");
        if (quantity <= 0) throw new IllegalArgumentException("Số lượng phải lớn hơn 0!");
        DatabaseConnection.executeUpdate(
                "INSERT INTO booking_services(booking_id, service_id, quantity, price) VALUES (?, ?, ?, ?)",
                b.getId(), s.getId(), quantity, s.getPrice());
    }

    /** Lấy danh sách dịch vụ đã dùng của một đơn đặt phòng */
    public List<UsedService> getUsedServices(int bookingId) throws SQLException {
        String sql = "SELECT s.name, bs.quantity, bs.price FROM booking_services bs "
                + "JOIN services s ON bs.service_id = s.id WHERE bs.booking_id = ? ORDER BY bs.id";
        List<UsedService> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new UsedService(rs.getString("name"), rs.getInt("quantity"), rs.getDouble("price")));
                }
            }
        }
        return list;
    }

    /**
     * Khách trả phòng: tính tiền = tiền phòng (số đêm x giá) + tiền dịch vụ.
     * Lưu tổng tiền, đổi trạng thái, trả phòng về "Trống".
     * @return nội dung hóa đơn để hiển thị
     */
    public String checkOut(Booking b) throws SQLException {
        requireStatus(b, Booking.STAYING, "check-out");

        double roomPrice = getRoomPrice(b.getRoomId());
        double roomMoney = b.getNights() * roomPrice;

        StringBuilder bill = new StringBuilder();
        bill.append("========== HÓA ĐƠN THANH TOÁN ==========\n");
        bill.append("Mã đơn: ").append(b.getId()).append("\n");
        bill.append("Khách hàng: ").append(b.getCustomerName()).append(" - ").append(b.getPhone()).append("\n");
        bill.append("Phòng: ").append(b.getRoomNo()).append("\n");
        bill.append("Từ ").append(b.getCheckIn()).append(" đến ").append(b.getCheckOut()).append("\n\n");
        bill.append(String.format("Tiền phòng: %d đêm x %,.0f = %,.0fđ%n%n", b.getNights(), roomPrice, roomMoney));

        double serviceMoney = 0;
        bill.append("Dịch vụ đi kèm:\n");
        for (UsedService u : getUsedServices(b.getId())) {
            bill.append(String.format("  - %s: %d x %,.0f = %,.0fđ%n",
                    u.getServiceName(), u.getQuantity(), u.getPrice(), u.getAmount()));
            serviceMoney += u.getAmount();
        }
        bill.append(String.format("Tiền dịch vụ: %,.0fđ%n", serviceMoney));

        double total = roomMoney + serviceMoney;
        bill.append("----------------------------------------\n");
        bill.append(String.format("TỔNG CỘNG: %,.0fđ%n", total));

        DatabaseConnection.executeUpdate("UPDATE bookings SET status = ?, total = ? WHERE id = ?",
                Booking.PAID, total, b.getId());
        roomManager.updateStatus(b.getRoomId(), Room.EMPTY);
        return bill.toString();
    }

    /** Lấy giá 1 đêm của phòng (theo loại phòng) */
    private double getRoomPrice(int roomId) throws SQLException {
        String sql = "SELECT t.price FROM rooms r JOIN room_types t ON r.type_id = t.id WHERE r.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getDouble(1) : 0;
            }
        }
    }

    /** Báo lỗi nếu đơn không ở đúng trạng thái cần thiết */
    private void requireStatus(Booking b, String status, String action) {
        if (!b.getStatus().equals(status)) {
            throw new IllegalArgumentException("Chỉ được " + action + " khi đơn đang ở trạng thái \"" + status
                    + "\".\nĐơn này đang là \"" + b.getStatus() + "\".");
        }
    }
}
