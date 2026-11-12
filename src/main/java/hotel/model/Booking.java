package hotel.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/** Một lần đặt phòng (giao dịch). */
public class Booking {

    // Các trạng thái của đơn đặt phòng
    public static final String BOOKED = "Đã đặt";
    public static final String STAYING = "Đang ở";
    public static final String PAID = "Đã trả";
    public static final String CANCELED = "Đã hủy";
    public static final String[] STATUSES = {BOOKED, STAYING, PAID, CANCELED};

    private final int id;
    private final String customerName;
    private final String phone;
    private final int roomId;
    private final String roomNo;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final String status;
    private final double total;
    private final String staff; // người thực hiện

    public Booking(int id, String customerName, String phone, int roomId, String roomNo,
                   LocalDate checkIn, LocalDate checkOut, String status, double total, String staff) {
        this.id = id;
        this.customerName = customerName;
        this.phone = phone;
        this.roomId = roomId;
        this.roomNo = roomNo;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
        this.total = total;
        this.staff = staff;
    }

    public int getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getPhone() { return phone; }
    public int getRoomId() { return roomId; }
    public String getRoomNo() { return roomNo; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public String getStatus() { return status; }
    public double getTotal() { return total; }
    public String getStaff() { return staff; }

    /** Số đêm ở (ít nhất 1 đêm) */
    public long getNights() {
        return Math.max(1, ChronoUnit.DAYS.between(checkIn, checkOut));
    }
}
