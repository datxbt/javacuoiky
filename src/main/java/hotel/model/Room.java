package hotel.model;

/** Phòng (dữ liệu chính). */
public class Room {

    // Các trạng thái của phòng
    public static final String EMPTY = "Trống";
    public static final String BOOKED = "Đã đặt";
    public static final String OCCUPIED = "Đang ở";
    public static final String[] STATUSES = {EMPTY, BOOKED, OCCUPIED};

    private final int id;
    private final String roomNo;
    private final int typeId;
    private final String typeName;
    private final double price;
    private final String status;

    public Room(int id, String roomNo, int typeId, String typeName, double price, String status) {
        this.id = id;
        this.roomNo = roomNo;
        this.typeId = typeId;
        this.typeName = typeName;
        this.price = price;
        this.status = status;
    }

    public int getId() { return id; }
    public String getRoomNo() { return roomNo; }
    public int getTypeId() { return typeId; }
    public String getTypeName() { return typeName; }
    public double getPrice() { return price; }
    public String getStatus() { return status; }

    /** Chữ hiển thị trong JComboBox, ví dụ: "102 - Phòng đơn (300,000đ)" */
    @Override
    public String toString() {
        return roomNo + " - " + typeName + String.format(" (%,.0fđ)", price);
    }
}
