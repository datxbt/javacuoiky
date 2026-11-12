# Hệ thống Quản lý Khách sạn / Homestay (Java Swing + MySQL)

## 1. Cài đặt database
1. Mở **XAMPP Control Panel** → bấm **Start** ở dòng **MySQL**.
2. Vào http://localhost/phpmyadmin → tab **Import** → chọn file `database/hotel_db.sql` → **Go**.
   (File này tạo lại database `hotel_db` kèm dữ liệu mẫu.)

Thông tin kết nối (sửa trong `src/main/java/hotel/db/DatabaseConnection.java` nếu cần):

| Mục | Giá trị |
|---|---|
| Database | `hotel_db` |
| Username | `root` |
| Password | *(để trống)* |
| Cổng | `3306` |

## 2. Mở và chạy bằng NetBeans
1. NetBeans → **File → Open Project** → chọn thư mục `javacuoiky` (biểu tượng Maven).
2. Bấm **Run (F6)**. Lần đầu NetBeans sẽ tự tải driver MySQL.
3. Đóng gói JAR: chuột phải project → **Clean and Build**. File chạy được nằm ở
   `target/QuanLyKhachSan-jar-with-dependencies.jar` (nhấp đúp hoặc `java -jar ...`).

### Sửa giao diện bằng kéo thả (GUI Builder)
Mỗi màn hình trong `src/main/java/hotel/ui/` gồm 2 file: `XxxPanel.java` (code) và `XxxPanel.form`
(bản thiết kế NetBeans đọc). Các màn hình: `LoginFrame`, `MainFrame`, `BookingPanel`, `RoomPanel`,
`RoomTypePanel`, `ServicePanel`, `StatsPanel`.

1. Mở file `.java` của màn hình → bấm tab **Design** ở góc trên trình soạn thảo.
2. Kéo thả thành phần từ **Palette** (bên phải), chỉnh chữ / màu / font ở **Properties**.
3. Muốn thêm xử lý cho nút mới: nhấp đúp vào nút → NetBeans tạo hàm `btnXxxActionPerformed`,
   viết code vào trong hàm đó.

Lưu ý:
- Đoạn `initComponents()` (màu xám) do NetBeans tự sinh, **không sửa tay**, mọi thay đổi làm trong tab Design.
- Đổi tên biến của thành phần: chuột phải → **Change Variable Name** (không đổi tên trực tiếp trong code).
- Cột của các bảng (JTable), danh sách trong JComboBox và các tab của cửa sổ chính được gán bằng code
  trong constructor (vì lấy từ CSDL / theo quyền), nên trong tab Design chúng trông trống là bình thường.

## 3. Tài khoản đăng nhập
| Tài khoản | Mật khẩu | Quyền |
|---|---|---|
| admin | admin123 | Quản lý: dùng được tất cả chức năng |
| letan | 123 | Lễ tân: chỉ Đặt phòng và xem / tìm phòng |

## 4. Chức năng (đối chiếu yêu cầu)
| Yêu cầu | Trong chương trình |
|---|---|
| 1. Dữ liệu chính | Tab **Quản lý phòng**: thêm/sửa/xóa phòng, JTable 5 cột |
| 2. Dữ liệu phụ | Tab **Loại phòng** và **Dịch vụ**: thêm/sửa/xóa; loại phòng hiện trong JComboBox |
| 3. Tìm kiếm nâng cao | Phòng: theo số phòng + loại + trạng thái. Đơn đặt: theo tên khách + trạng thái |
| 4. Giao dịch | Tab **Đặt phòng**: Đặt → Check-in → Thêm dịch vụ → Check-out (in hóa đơn) / Hủy. Lưu ngày, nhân viên thực hiện, chi tiết dịch vụ |
| 5. Thống kê | Tab **Thống kê**: số phòng theo trạng thái, tổng doanh thu, doanh thu theo tháng |
| Mở rộng | Đăng nhập phân quyền, xuất Excel (CSV), bấm tiêu đề cột để sắp xếp |

Quy trình một lượt khách: chọn phòng trống → **Đặt phòng** (phòng thành *Đã đặt*) → chọn đơn → **Check-in**
(phòng *Đang ở*) → chọn dịch vụ + số lượng → **Thêm dịch vụ** → **Check-out & Tính tiền**
(tiền = số đêm × giá phòng + tiền dịch vụ; phòng về *Trống*).

## 5. Cấu trúc mã nguồn
```
src/main/java/hotel/
├── Main.java                  Chạy chương trình
├── db/DatabaseConnection.java Kết nối MySQL
├── model/                     Lớp dữ liệu: Room, RoomType, Service, Booking, UsedService
├── manager/                   Lớp xử lý logic + truy vấn SQL:
│                              RoomManager, RoomTypeManager, ServiceManager,
│                              BookingManager, StatsManager, UserManager
└── ui/                        Lớp giao diện (mỗi màn hình có file .java + .form để kéo thả):
                               LoginFrame, MainFrame, RoomPanel, RoomTypePanel, ServicePanel,
                               BookingPanel, StatsPanel; UiHelper = hàm tiện ích dùng chung
```

## 6. Sơ đồ quan hệ các bảng
```
users (username PK) ─────────────┐
                                 │ staff (FK)
room_types (id PK)               ▼
   │ 1 - n                  bookings (id PK) ── 1 - n ──► booking_services (id PK) ◄── n - 1 ── services (id PK)
   ▼                             ▲                          booking_id (FK), service_id (FK)
rooms (id PK, type_id FK) ── 1 - n (room_id FK)
```
