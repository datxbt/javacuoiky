package hotel.ui;

import java.awt.Component;

/**
 * Cửa sổ chính: menu + các tab chức năng.
 * Giao diện thiết kế bằng NetBeans GUI Builder (file MainFrame.form).
 * Các tab được thêm bằng code trong constructor vì phụ thuộc quyền đăng nhập.
 */
public class MainFrame extends javax.swing.JFrame {

    private BookingPanel bookingPanel;
    private RoomPanel roomPanel;
    private RoomTypePanel roomTypePanel;
    private ServicePanel servicePanel;
    private StatsPanel statsPanel;

    /**
     * @param username tài khoản đang đăng nhập
     * @param role     "admin" thấy tất cả chức năng, "user" (lễ tân) chỉ thấy Đặt phòng và Phòng
     */
    public MainFrame(String username, String role) {
        initComponents();
        boolean isAdmin = "admin".equals(role);
        lblHeader.setText("HỆ THỐNG QUẢN LÝ KHÁCH SẠN / HOMESTAY        Xin chào: "
                + username + " (" + (isAdmin ? "Quản lý" : "Lễ tân") + ")");

        bookingPanel = new BookingPanel(username);
        roomPanel = new RoomPanel(isAdmin);
        tabs.addTab("Đặt phòng / Check-in / Check-out", bookingPanel);
        tabs.addTab("Quản lý phòng", roomPanel);
        if (isAdmin) {
            roomTypePanel = new RoomTypePanel();
            servicePanel = new ServicePanel();
            statsPanel = new StatsPanel();
            tabs.addTab("Loại phòng", roomTypePanel);
            tabs.addTab("Dịch vụ", servicePanel);
            tabs.addTab("Thống kê", statsPanel);
        } else {
            // Lễ tân không được dùng các chức năng này
            itemRoomType.setVisible(false);
            itemService.setVisible(false);
            itemStats.setVisible(false);
        }

        // Mỗi khi chuyển tab thì tải lại dữ liệu mới nhất
        tabs.addChangeListener(e -> reloadTab(tabs.getSelectedComponent()));
    }

    /** Gọi hàm reload() của tab vừa chọn */
    private void reloadTab(Component c) {
        if (c instanceof BookingPanel p) p.reload();
        else if (c instanceof RoomPanel p) p.reload();
        else if (c instanceof RoomTypePanel p) p.reload();
        else if (c instanceof ServicePanel p) p.reload();
        else if (c instanceof StatsPanel p) p.reload();
    }

    /**
     * Code giao diện do NetBeans tự sinh từ file .form - KHÔNG sửa tay đoạn này,
     * hãy sửa trong tab Design.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBar = new javax.swing.JMenuBar();
        menuSystem = new javax.swing.JMenu();
        itemLogout = new javax.swing.JMenuItem();
        itemExit = new javax.swing.JMenuItem();
        menuFunction = new javax.swing.JMenu();
        itemBooking = new javax.swing.JMenuItem();
        itemRoom = new javax.swing.JMenuItem();
        itemRoomType = new javax.swing.JMenuItem();
        itemService = new javax.swing.JMenuItem();
        itemStats = new javax.swing.JMenuItem();
        lblHeader = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý Khách sạn / Homestay");

        menuSystem.setText("Hệ thống");
        itemLogout.setText("Đăng xuất");
        itemLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemLogoutActionPerformed(evt);
            }
        });

        menuSystem.add(itemLogout);
        itemExit.setText("Thoát");
        itemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemExitActionPerformed(evt);
            }
        });

        menuSystem.add(itemExit);

        menuBar.add(menuSystem);
        menuFunction.setText("Chức năng");
        itemBooking.setText("Đặt phòng / Check-in / Check-out");
        itemBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemBookingActionPerformed(evt);
            }
        });

        menuFunction.add(itemBooking);
        itemRoom.setText("Quản lý phòng");
        itemRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemRoomActionPerformed(evt);
            }
        });

        menuFunction.add(itemRoom);
        itemRoomType.setText("Loại phòng");
        itemRoomType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemRoomTypeActionPerformed(evt);
            }
        });

        menuFunction.add(itemRoomType);
        itemService.setText("Dịch vụ");
        itemService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemServiceActionPerformed(evt);
            }
        });

        menuFunction.add(itemService);
        itemStats.setText("Thống kê");
        itemStats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemStatsActionPerformed(evt);
            }
        });

        menuFunction.add(itemStats);

        menuBar.add(menuFunction);

        setJMenuBar(menuBar);

        lblHeader.setText("HỆ THỐNG QUẢN LÝ KHÁCH SẠN / HOMESTAY");
        lblHeader.setBackground(new java.awt.Color(33, 105, 170));
        lblHeader.setFont(new java.awt.Font("Segoe UI", 1, 16));
        lblHeader.setForeground(new java.awt.Color(255, 255, 255));
        lblHeader.setOpaque(true);
        lblHeader.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 14, 10, 10));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 1150, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // ---------------- Menu "Hệ thống" ----------------

    private void itemLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemLogoutActionPerformed
        if (UiHelper.confirm(this, "Bạn muốn đăng xuất?")) {
            dispose();
            new LoginFrame().setVisible(true);
        }
    }//GEN-LAST:event_itemLogoutActionPerformed

    private void itemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemExitActionPerformed
        if (UiHelper.confirm(this, "Bạn muốn thoát chương trình?")) System.exit(0);
    }//GEN-LAST:event_itemExitActionPerformed

    // ---------------- Menu "Chức năng": chuyển sang tab tương ứng ----------------

    private void itemBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemBookingActionPerformed
        tabs.setSelectedComponent(bookingPanel);
    }//GEN-LAST:event_itemBookingActionPerformed

    private void itemRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemRoomActionPerformed
        tabs.setSelectedComponent(roomPanel);
    }//GEN-LAST:event_itemRoomActionPerformed

    private void itemRoomTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemRoomTypeActionPerformed
        tabs.setSelectedComponent(roomTypePanel);
    }//GEN-LAST:event_itemRoomTypeActionPerformed

    private void itemServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemServiceActionPerformed
        tabs.setSelectedComponent(servicePanel);
    }//GEN-LAST:event_itemServiceActionPerformed

    private void itemStatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemStatsActionPerformed
        tabs.setSelectedComponent(statsPanel);
    }//GEN-LAST:event_itemStatsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem itemBooking;
    private javax.swing.JMenuItem itemExit;
    private javax.swing.JMenuItem itemLogout;
    private javax.swing.JMenuItem itemRoom;
    private javax.swing.JMenuItem itemRoomType;
    private javax.swing.JMenuItem itemService;
    private javax.swing.JMenuItem itemStats;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuFunction;
    private javax.swing.JMenu menuSystem;
    private javax.swing.JTabbedPane tabs;
    // End of variables declaration//GEN-END:variables
}
