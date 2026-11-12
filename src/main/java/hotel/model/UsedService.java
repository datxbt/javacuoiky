package hotel.model;

/** Một dịch vụ mà khách đã sử dụng trong lần đặt phòng. */
public class UsedService {

    private final String serviceName;
    private final int quantity;
    private final double price;

    public UsedService(String serviceName, int quantity, double price) {
        this.serviceName = serviceName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getServiceName() { return serviceName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    /** Thành tiền = số lượng x đơn giá */
    public double getAmount() {
        return quantity * price;
    }
}
