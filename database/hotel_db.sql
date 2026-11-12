-- =============================================================
-- CSDL: Hệ thống Quản lý Khách sạn / Homestay
-- Chạy file này trong phpMyAdmin (XAMPP) hoặc MySQL Workbench
-- Thông tin kết nối: database = hotel_db, user = root, password = (trống)
-- =============================================================
DROP DATABASE IF EXISTS hotel_db;
CREATE DATABASE hotel_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hotel_db;

-- Tài khoản đăng nhập (role: admin = quản lý, user = lễ tân)
CREATE TABLE users (
    username  VARCHAR(50)  PRIMARY KEY,
    password  VARCHAR(50)  NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role      VARCHAR(10)  NOT NULL
);

-- Loại phòng (dữ liệu phụ)
CREATE TABLE room_types (
    id    INT AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(50)   NOT NULL,
    price DECIMAL(12,0) NOT NULL          -- giá 1 đêm
);

-- Phòng (dữ liệu chính). status: Trống / Đã đặt / Đang ở
CREATE TABLE rooms (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    room_no VARCHAR(10) NOT NULL UNIQUE,
    type_id INT         NOT NULL,
    status  VARCHAR(20) NOT NULL DEFAULT 'Trống',
    FOREIGN KEY (type_id) REFERENCES room_types(id)
);

-- Dịch vụ đi kèm
CREATE TABLE services (
    id    INT AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(100)  NOT NULL,
    price DECIMAL(12,0) NOT NULL
);

-- Đặt phòng (giao dịch). status: Đã đặt / Đang ở / Đã trả / Đã hủy
CREATE TABLE bookings (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100)  NOT NULL,
    phone         VARCHAR(15)   NOT NULL,
    room_id       INT           NOT NULL,
    check_in      DATE          NOT NULL,
    check_out     DATE          NOT NULL,
    status        VARCHAR(20)   NOT NULL DEFAULT 'Đã đặt',
    total         DECIMAL(12,0) NOT NULL DEFAULT 0,   -- tổng tiền khi check-out
    staff         VARCHAR(50)   NOT NULL,             -- người thực hiện
    created_at    DATETIME      DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES rooms(id),
    FOREIGN KEY (staff)   REFERENCES users(username)
);

-- Dịch vụ khách đã dùng trong mỗi lần đặt phòng
CREATE TABLE booking_services (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT           NOT NULL,
    service_id INT           NOT NULL,
    quantity   INT           NOT NULL,
    price      DECIMAL(12,0) NOT NULL,                -- đơn giá lúc sử dụng
    used_at    DATETIME      DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE,
    FOREIGN KEY (service_id) REFERENCES services(id)
);

-- ===================== DỮ LIỆU MẪU =====================
INSERT INTO users VALUES
('admin', 'admin123', 'Quản lý', 'admin'),
('letan', '123',      'Lễ tân',  'user');

INSERT INTO room_types (name, price) VALUES
('Phòng đơn', 300000),
('Phòng đôi', 500000),
('Phòng VIP', 1000000);

INSERT INTO rooms (room_no, type_id, status) VALUES
('101', 1, 'Đang ở'),
('102', 1, 'Trống'),
('103', 2, 'Đã đặt'),
('201', 2, 'Trống'),
('202', 3, 'Trống'),
('203', 3, 'Trống');

INSERT INTO services (name, price) VALUES
('Nước suối', 10000),
('Bữa sáng', 50000),
('Giặt ủi', 30000),
('Thuê xe máy', 120000),
('Mini bar', 80000);

INSERT INTO bookings (customer_name, phone, room_id, check_in, check_out, status, total, staff) VALUES
('Nguyễn Văn An',  '0901234567', 2, '2026-09-10', '2026-09-12', 'Đã trả', 700000,  'admin'),
('Trần Thị Bình',  '0912345678', 5, '2026-10-05', '2026-10-08', 'Đã trả', 3160000, 'letan'),
('Lê Văn Cường',   '0987654321', 4, '2026-10-20', '2026-10-21', 'Đã trả', 530000,  'letan'),
('Phạm Thị Dung',  '0934567890', 1, '2026-11-04', '2026-11-07', 'Đang ở', 0,       'letan'),
('Hoàng Văn Em',   '0976543210', 3, '2026-11-08', '2026-11-10', 'Đã đặt', 0,       'admin');

INSERT INTO booking_services (booking_id, service_id, quantity, price) VALUES
(1, 2, 2, 50000),
(2, 4, 1, 120000),
(2, 1, 4, 10000),
(3, 3, 1, 30000),
(4, 2, 2, 50000);
