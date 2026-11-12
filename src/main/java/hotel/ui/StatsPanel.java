package hotel.ui;

import hotel.manager.StatsManager;
import hotel.model.Room;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 * Màn hình thống kê: số phòng theo trạng thái, tổng doanh thu, doanh thu theo tháng.
 * Giao diện thiết kế bằng NetBeans GUI Builder (file StatsPanel.form).
 */
public class StatsPanel extends javax.swing.JPanel {

    private final StatsManager manager = new StatsManager();
    private final DefaultTableModel model = UiHelper.createModel("Tháng", "Số lượt thuê", "Doanh thu (VNĐ)");

    public StatsPanel() {
        initComponents();
        UiHelper.setupTable(tblMonth, model);
        reload();
    }

    /** Tính lại các số liệu thống kê */
    public void reload() {
        try {
            Map<String, Integer> rooms = manager.countRoomsByStatus();
            int empty = rooms.getOrDefault(Room.EMPTY, 0);
            int booked = rooms.getOrDefault(Room.BOOKED, 0);
            int occupied = rooms.getOrDefault(Room.OCCUPIED, 0);
            lblTotalRooms.setText(String.valueOf(empty + booked + occupied));
            lblEmpty.setText(String.valueOf(empty));
            lblBooked.setText(String.valueOf(booked));
            lblOccupied.setText(String.valueOf(occupied));
            lblRevenue.setText(String.format("%,.0fđ", manager.totalRevenue()));

            model.setRowCount(0);
            for (Object[] row : manager.revenueByMonth()) model.addRow(row);
        } catch (Exception ex) {
            UiHelper.showError(this, ex);
        }
    }

    /**
     * Code giao diện do NetBeans tự sinh từ file .form - KHÔNG sửa tay đoạn này,
     * hãy sửa trong tab Design.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cardTotalRooms = new javax.swing.JPanel();
        lblTotalRoomsTitle = new javax.swing.JLabel();
        lblTotalRooms = new javax.swing.JLabel();
        cardEmpty = new javax.swing.JPanel();
        lblEmptyTitle = new javax.swing.JLabel();
        lblEmpty = new javax.swing.JLabel();
        cardBooked = new javax.swing.JPanel();
        lblBookedTitle = new javax.swing.JLabel();
        lblBooked = new javax.swing.JLabel();
        cardOccupied = new javax.swing.JPanel();
        lblOccupiedTitle = new javax.swing.JLabel();
        lblOccupied = new javax.swing.JLabel();
        cardRevenue = new javax.swing.JPanel();
        lblRevenueTitle = new javax.swing.JLabel();
        lblRevenue = new javax.swing.JLabel();
        pnlMonth = new javax.swing.JPanel();
        spMonth = new javax.swing.JScrollPane();
        tblMonth = new javax.swing.JTable();
        btnRefresh = new javax.swing.JButton();

        cardTotalRooms.setBackground(new java.awt.Color(33, 105, 170));
        lblTotalRoomsTitle.setText("Tổng số phòng");
        lblTotalRoomsTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalRoomsTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblTotalRooms.setText("0");
        lblTotalRooms.setFont(new java.awt.Font("Segoe UI", 1, 22));
        lblTotalRooms.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalRooms.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout cardTotalRoomsLayout = new javax.swing.GroupLayout(cardTotalRooms);
        cardTotalRooms.setLayout(cardTotalRoomsLayout);
        cardTotalRoomsLayout.setHorizontalGroup(
            cardTotalRoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(cardTotalRoomsLayout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addGroup(cardTotalRoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblTotalRoomsTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTotalRooms, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(10, 10, 10))
        );
        cardTotalRoomsLayout.setVerticalGroup(
            cardTotalRoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(cardTotalRoomsLayout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addGroup(cardTotalRoomsLayout.createSequentialGroup()
                        .addComponent(lblTotalRoomsTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(lblTotalRooms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10))
        );

        cardEmpty.setBackground(new java.awt.Color(46, 160, 90));
        lblEmptyTitle.setText("Phòng trống");
        lblEmptyTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblEmptyTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblEmpty.setText("0");
        lblEmpty.setFont(new java.awt.Font("Segoe UI", 1, 22));
        lblEmpty.setForeground(new java.awt.Color(255, 255, 255));
        lblEmpty.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout cardEmptyLayout = new javax.swing.GroupLayout(cardEmpty);
        cardEmpty.setLayout(cardEmptyLayout);
        cardEmptyLayout.setHorizontalGroup(
            cardEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(cardEmptyLayout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addGroup(cardEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblEmptyTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblEmpty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(10, 10, 10))
        );
        cardEmptyLayout.setVerticalGroup(
            cardEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(cardEmptyLayout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addGroup(cardEmptyLayout.createSequentialGroup()
                        .addComponent(lblEmptyTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(lblEmpty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10))
        );

        cardBooked.setBackground(new java.awt.Color(230, 140, 30));
        lblBookedTitle.setText("Phòng đã đặt");
        lblBookedTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblBookedTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblBooked.setText("0");
        lblBooked.setFont(new java.awt.Font("Segoe UI", 1, 22));
        lblBooked.setForeground(new java.awt.Color(255, 255, 255));
        lblBooked.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout cardBookedLayout = new javax.swing.GroupLayout(cardBooked);
        cardBooked.setLayout(cardBookedLayout);
        cardBookedLayout.setHorizontalGroup(
            cardBookedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(cardBookedLayout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addGroup(cardBookedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblBookedTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblBooked, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(10, 10, 10))
        );
        cardBookedLayout.setVerticalGroup(
            cardBookedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(cardBookedLayout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addGroup(cardBookedLayout.createSequentialGroup()
                        .addComponent(lblBookedTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(lblBooked, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10))
        );

        cardOccupied.setBackground(new java.awt.Color(205, 60, 60));
        lblOccupiedTitle.setText("Phòng đang ở");
        lblOccupiedTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblOccupiedTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblOccupied.setText("0");
        lblOccupied.setFont(new java.awt.Font("Segoe UI", 1, 22));
        lblOccupied.setForeground(new java.awt.Color(255, 255, 255));
        lblOccupied.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout cardOccupiedLayout = new javax.swing.GroupLayout(cardOccupied);
        cardOccupied.setLayout(cardOccupiedLayout);
        cardOccupiedLayout.setHorizontalGroup(
            cardOccupiedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(cardOccupiedLayout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addGroup(cardOccupiedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblOccupiedTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblOccupied, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(10, 10, 10))
        );
        cardOccupiedLayout.setVerticalGroup(
            cardOccupiedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(cardOccupiedLayout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addGroup(cardOccupiedLayout.createSequentialGroup()
                        .addComponent(lblOccupiedTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(lblOccupied, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10))
        );

        cardRevenue.setBackground(new java.awt.Color(120, 70, 170));
        lblRevenueTitle.setText("Tổng doanh thu");
        lblRevenueTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblRevenueTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblRevenue.setText("0");
        lblRevenue.setFont(new java.awt.Font("Segoe UI", 1, 22));
        lblRevenue.setForeground(new java.awt.Color(255, 255, 255));
        lblRevenue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout cardRevenueLayout = new javax.swing.GroupLayout(cardRevenue);
        cardRevenue.setLayout(cardRevenueLayout);
        cardRevenueLayout.setHorizontalGroup(
            cardRevenueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(cardRevenueLayout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addGroup(cardRevenueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblRevenueTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblRevenue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(10, 10, 10))
        );
        cardRevenueLayout.setVerticalGroup(
            cardRevenueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(cardRevenueLayout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addGroup(cardRevenueLayout.createSequentialGroup()
                        .addComponent(lblRevenueTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(lblRevenue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10))
        );

        pnlMonth.setBorder(javax.swing.BorderFactory.createTitledBorder("Doanh thu theo tháng (các đơn đã thanh toán)"));
        spMonth.setViewportView(tblMonth);

        javax.swing.GroupLayout pnlMonthLayout = new javax.swing.GroupLayout(pnlMonth);
        pnlMonth.setLayout(pnlMonthLayout);
        pnlMonthLayout.setHorizontalGroup(
            pnlMonthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlMonthLayout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addComponent(spMonth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(8, 8, 8))
        );
        pnlMonthLayout.setVerticalGroup(
            pnlMonthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlMonthLayout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addComponent(spMonth, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                    .addGap(8, 8, 8))
        );

        btnRefresh.setBackground(new java.awt.Color(33, 105, 170));
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Làm mới thống kê");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(cardTotalRooms, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                            .addGap(10, 10, 10)
                            .addComponent(cardEmpty, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                            .addGap(10, 10, 10)
                            .addComponent(cardBooked, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                            .addGap(10, 10, 10)
                            .addComponent(cardOccupied, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                            .addGap(10, 10, 10)
                            .addComponent(cardRevenue, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                        .addComponent(pnlMonth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(cardTotalRooms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardEmpty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardBooked, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardOccupied, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardRevenue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(pnlMonth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(10, 10, 10))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        reload();
    }//GEN-LAST:event_btnRefreshActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefresh;
    private javax.swing.JPanel cardBooked;
    private javax.swing.JPanel cardEmpty;
    private javax.swing.JPanel cardOccupied;
    private javax.swing.JPanel cardRevenue;
    private javax.swing.JPanel cardTotalRooms;
    private javax.swing.JLabel lblBooked;
    private javax.swing.JLabel lblBookedTitle;
    private javax.swing.JLabel lblEmpty;
    private javax.swing.JLabel lblEmptyTitle;
    private javax.swing.JLabel lblOccupied;
    private javax.swing.JLabel lblOccupiedTitle;
    private javax.swing.JLabel lblRevenue;
    private javax.swing.JLabel lblRevenueTitle;
    private javax.swing.JLabel lblTotalRooms;
    private javax.swing.JLabel lblTotalRoomsTitle;
    private javax.swing.JPanel pnlMonth;
    private javax.swing.JScrollPane spMonth;
    private javax.swing.JTable tblMonth;
    // End of variables declaration//GEN-END:variables
}
