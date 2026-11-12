package hotel.model;

/** Loại phòng (dữ liệu phụ): Phòng đơn, Phòng đôi, Phòng VIP... */
public class RoomType {

    private final int id;
    private final String name;
    private final double price; // giá 1 đêm

    public RoomType(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    /** Chữ hiển thị trong JComboBox */
    @Override
    public String toString() {
        return name;
    }
}
