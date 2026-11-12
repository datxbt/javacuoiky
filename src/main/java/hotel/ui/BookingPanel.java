package hotel.ui;

import hotel.manager.BookingManager;
import hotel.manager.RoomManager;
import hotel.manager.ServiceManager;
import hotel.model.Booking;
import hotel.model.Room;
import hotel.model.Service;
import hotel.model.UsedService;
import java.awt.Font;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 * Màn hình giao dịch: đặt phòng, check-in, thêm dịch vụ, check-out (tính tiền), hủy đặt.
 * Giao diện thiết kế bằng NetBeans GUI Builder (file BookingPanel.form).
 */
public class BookingPanel extends javax.swing.JPanel {

    private static final String ALL = "Tất cả";

    private final BookingManager manager = new BookingManager();
    private final RoomManager roomManager = new RoomManager();
    private final ServiceManager serviceManager = new ServiceManager();
    private final String username; // nhân viên đang đăng nhập = người thực hiện giao dịch

    private final DefaultTableModel model = UiHelper.createModel(
            "Mã", "Khách hàng", "SĐT", "Phòng", "Ngày nhận", "Ngày trả", "Trạng thái", "Tổng tiền (VNĐ)", "Nhân viên");
    private final DefaultTableModel serviceModel = UiHelper.createModel("Dịch vụ", "Số lượng", "Đơn giá", "Thành tiền");
    private List<Booking> list = new ArrayList<>();

    public BookingPanel(String username) {
        this.username = username;
        initComponents();
        UiHelper.setupTable(tblBookings, model);
        UiHelper.setupTable(tblServices, serviceModel);
        cbSearchStatus.setModel(new DefaultComboBoxModel<>(
                new String[]{ALL, Booking.BOOKED, Booking.STAYING, Booking.PAID, Booking.CANCELED}));

        // Chọn một đơn -> hiện các dịch vụ đã dùng
        tblBookings.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) loadUsedServices();
        });
        reload();
    }

    /** Tải lại phòng trống, dịch vụ và danh sách đơn đặt phòng */
    public void reload() {
        try {
            cbRoom.removeAllItems();
            for (Room r : roomManager.getEmptyRooms()) cbRoom.addItem(r);
            cbService.removeAllItems();
            for (Service s : serviceManager.getAll()) cbService.addItem(s);

            txtName.setText("");
            txtPhone.setText("");
            txtCheckIn.setText(LocalDate.now().toString());
            txtCheckOut.setText(LocalDate.now().plusDays(1).toString());
            search();
        } catch (Exception ex) {
            UiHelper.showError(this, ex);
        }
    }

    /** Tìm đơn theo tên khách + trạng thái */
    private void search() {
        try {
            String status = (String) cbSearchStatus.getSelectedItem();
            list = manager.search(txtSearchName.getText(), ALL.equals(status) ? null : status);
            model.setRowCount(0);
            for (Booking b : list) {
                model.addRow(new Object[]{b.getId(), b.getCustomerName(), b.getPhone(), b.getRoomNo(),
                        b.getCheckIn(), b.getCheckOut(), b.getStatus(), b.getTotal(), b.getStaff()});
            }
            serviceModel.setRowCount(0);
        } catch (Exception ex) {
            UiHelper.showError(this, ex);
        }
    }

    /** Đơn đang chọn trong bảng, nếu chưa chọn thì nhắc người dùng và trả về null */
    private Booking selectedOrWarn() {
        int row = UiHelper.selectedRow(tblBookings);
        if (row < 0 || row >= list.size()) {
            UiHelper.warn(this, "Hãy chọn một đơn đặt phòng trong bảng!");
            return null;
        }
        return list.get(row);
    }

    /** Hiện dịch vụ đã dùng của đơn đang chọn */
    private void loadUsedServices() {
        serviceModel.setRowCount(0);
        int row = UiHelper.selectedRow(tblBookings);
        if (row < 0 || row >= list.size()) return;
        try {
            for (UsedService u : manager.getUsedServices(list.get(row).getId())) {
                serviceModel.addRow(new Object[]{u.getServiceName(), u.getQuantity(), u.getPrice(), u.getAmount()});
            }
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

        pnlInput = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblPhone = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        lblRoom = new javax.swing.JLabel();
        cbRoom = new javax.swing.JComboBox<>();
        lblCheckIn = new javax.swing.JLabel();
        txtCheckIn = new javax.swing.JTextField();
        lblCheckOut = new javax.swing.JLabel();
        txtCheckOut = new javax.swing.JTextField();
        btnBook = new javax.swing.JButton();
        btnCheckIn = new javax.swing.JButton();
        btnCheckOut = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        pnlSearch = new javax.swing.JPanel();
        lblSearchName = new javax.swing.JLabel();
        txtSearchName = new javax.swing.JTextField();
        lblSearchStatus = new javax.swing.JLabel();
        cbSearchStatus = new javax.swing.JComboBox<>();
        btnSearch = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        spBookings = new javax.swing.JScrollPane();
        tblBookings = new javax.swing.JTable();
        pnlServices = new javax.swing.JPanel();
        lblService = new javax.swing.JLabel();
        cbService = new javax.swing.JComboBox<>();
        lblQty = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        btnAddService = new javax.swing.JButton();
        spServices = new javax.swing.JScrollPane();
        tblServices = new javax.swing.JTable();

        pnlInput.setBorder(javax.swing.BorderFactory.createTitledBorder("Đặt phòng"));
        lblName.setText("Tên khách hàng:");

        lblPhone.setText("Số điện thoại:");

        lblRoom.setText("Phòng trống:");

        lblCheckIn.setText("Ngày nhận (yyyy-MM-dd):");

        lblCheckOut.setText("Ngày trả (yyyy-MM-dd):");

        btnBook.setBackground(new java.awt.Color(46, 160, 90));
        btnBook.setForeground(new java.awt.Color(255, 255, 255));
        btnBook.setText("Đặt phòng");
        btnBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookActionPerformed(evt);
            }
        });

        btnCheckIn.setBackground(new java.awt.Color(33, 105, 170));
        btnCheckIn.setForeground(new java.awt.Color(255, 255, 255));
        btnCheckIn.setText("Check-in");
        btnCheckIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckInActionPerformed(evt);
            }
        });

        btnCheckOut.setBackground(new java.awt.Color(230, 140, 30));
        btnCheckOut.setForeground(new java.awt.Color(255, 255, 255));
        btnCheckOut.setText("Check-out & Tính tiền");
        btnCheckOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckOutActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(205, 60, 60));
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Hủy đặt");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnRefresh.setBackground(new java.awt.Color(33, 105, 170));
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Làm mới");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlInputLayout = new javax.swing.GroupLayout(pnlInput);
        pnlInput.setLayout(pnlInputLayout);
        pnlInputLayout.setHorizontalGroup(
            pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlInputLayout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlInputLayout.createSequentialGroup()
                            .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblCheckIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(10, 10, 10)
                            .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                .addComponent(txtCheckIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(10, 10, 10)
                            .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblCheckOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(10, 10, 10)
                            .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                .addComponent(txtCheckOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(10, 10, 10)
                            .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(10, 10, 10)
                            .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cbRoom, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)))
                        .addGroup(pnlInputLayout.createSequentialGroup()
                            .addComponent(btnBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(8, 8, 8)
                            .addComponent(btnCheckIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(8, 8, 8)
                            .addComponent(btnCheckOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(8, 8, 8)
                            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(8, 8, 8)
                            .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(8, 8, 8))
        );
        pnlInputLayout.setVerticalGroup(
            pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlInputLayout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addGroup(pnlInputLayout.createSequentialGroup()
                        .addGroup(pnlInputLayout.createSequentialGroup()
                            .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(8, 8, 8)
                            .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(lblCheckIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCheckIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblCheckOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCheckOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10)
                        .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(btnBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCheckIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCheckOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(8, 8, 8))
        );

        pnlSearch.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm đơn đặt phòng"));
        lblSearchName.setText("Tên khách:");

        lblSearchStatus.setText("Trạng thái:");

        btnSearch.setBackground(new java.awt.Color(33, 105, 170));
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("Tìm kiếm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnExport.setBackground(new java.awt.Color(46, 160, 90));
        btnExport.setForeground(new java.awt.Color(255, 255, 255));
        btnExport.setText("Xuất Excel");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSearchLayout = new javax.swing.GroupLayout(pnlSearch);
        pnlSearch.setLayout(pnlSearchLayout);
        pnlSearchLayout.setHorizontalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlSearchLayout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addGroup(pnlSearchLayout.createSequentialGroup()
                        .addComponent(lblSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(txtSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(lblSearchStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(cbSearchStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(8, 8, 8))
        );
        pnlSearchLayout.setVerticalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlSearchLayout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(lblSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSearchStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbSearchStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(8, 8, 8))
        );

        spBookings.setViewportView(tblBookings);

        pnlServices.setBorder(javax.swing.BorderFactory.createTitledBorder("Dịch vụ đi kèm của đơn đang chọn"));
        lblService.setText("Dịch vụ:");

        lblQty.setText("Số lượng:");

        txtQty.setText("1");

        btnAddService.setBackground(new java.awt.Color(46, 160, 90));
        btnAddService.setForeground(new java.awt.Color(255, 255, 255));
        btnAddService.setText("Thêm dịch vụ cho đơn đang chọn");
        btnAddService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddServiceActionPerformed(evt);
            }
        });

        spServices.setViewportView(tblServices);

        javax.swing.GroupLayout pnlServicesLayout = new javax.swing.GroupLayout(pnlServices);
        pnlServices.setLayout(pnlServicesLayout);
        pnlServicesLayout.setHorizontalGroup(
            pnlServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlServicesLayout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addGroup(pnlServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlServicesLayout.createSequentialGroup()
                            .addComponent(lblService, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(8, 8, 8)
                            .addComponent(cbService, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(8, 8, 8)
                            .addComponent(lblQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(8, 8, 8)
                            .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(8, 8, 8)
                            .addComponent(btnAddService, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(spServices, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(8, 8, 8))
        );
        pnlServicesLayout.setVerticalGroup(
            pnlServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlServicesLayout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addGroup(pnlServicesLayout.createSequentialGroup()
                        .addGroup(pnlServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(lblService, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbService, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddService, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addComponent(spServices, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnlInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(spBookings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlServices, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(pnlSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(spBookings, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                        .addGap(8, 8, 8)
                        .addComponent(pnlServices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10))
        );
    }// </editor-fold>//GEN-END:initComponents

    /** Nút "Đặt phòng" */
    private void btnBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookActionPerformed
        try {
            manager.book(txtName.getText(), txtPhone.getText(), (Room) cbRoom.getSelectedItem(),
                    UiHelper.parseDate(txtCheckIn.getText(), "Ngày nhận"),
                    UiHelper.parseDate(txtCheckOut.getText(), "Ngày trả"),
                    username);
            reload();
            UiHelper.info(this, "Đặt phòng thành công!");
        } catch (Exception ex) {
            UiHelper.showError(this, ex);
        }
    }//GEN-LAST:event_btnBookActionPerformed

    /** Nút "Check-in" */
    private void btnCheckInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckInActionPerformed
        Booking b = selectedOrWarn();
        if (b == null) return;
        try {
            manager.checkIn(b);
            reload();
            UiHelper.info(this, "Khách " + b.getCustomerName() + " đã nhận phòng " + b.getRoomNo());
        } catch (Exception ex) {
            UiHelper.showError(this, ex);
        }
    }//GEN-LAST:event_btnCheckInActionPerformed

    /** Nút "Check-out & Tính tiền": tính tiền rồi hiện hóa đơn */
    private void btnCheckOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckOutActionPerformed
        Booking b = selectedOrWarn();
        if (b == null) return;
        if (!UiHelper.confirm(this, "Xác nhận check-out và thanh toán cho khách " + b.getCustomerName() + "?")) return;
        try {
            String bill = manager.checkOut(b);
            reload();
            JTextArea area = new JTextArea(bill);
            area.setEditable(false);
            area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
            JOptionPane.showMessageDialog(this, new JScrollPane(area), "Hóa đơn", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            UiHelper.showError(this, ex);
        }
    }//GEN-LAST:event_btnCheckOutActionPerformed

    /** Nút "Hủy đặt" */
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        Booking b = selectedOrWarn();
        if (b == null) return;
        if (!UiHelper.confirm(this, "Bạn có chắc chắn hủy đơn đặt phòng của " + b.getCustomerName() + " không?")) return;
        try {
            manager.cancel(b);
            reload();
        } catch (Exception ex) {
            UiHelper.showError(this, ex);
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        reload();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        search();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        UiHelper.exportCsv(this, tblBookings, "DanhSachDatPhong");
    }//GEN-LAST:event_btnExportActionPerformed

    /** Nút "Thêm dịch vụ cho đơn đang chọn" */
    private void btnAddServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddServiceActionPerformed
        Booking b = selectedOrWarn();
        if (b == null) return;
        try {
            manager.addService(b, (Service) cbService.getSelectedItem(), UiHelper.parseInt(txtQty.getText(), "Số lượng"));
            loadUsedServices();
            txtQty.setText("1");
        } catch (Exception ex) {
            UiHelper.showError(this, ex);
        }
    }//GEN-LAST:event_btnAddServiceActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddService;
    private javax.swing.JButton btnBook;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCheckIn;
    private javax.swing.JButton btnCheckOut;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<Room> cbRoom;
    private javax.swing.JComboBox<String> cbSearchStatus;
    private javax.swing.JComboBox<Service> cbService;
    private javax.swing.JLabel lblCheckIn;
    private javax.swing.JLabel lblCheckOut;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblQty;
    private javax.swing.JLabel lblRoom;
    private javax.swing.JLabel lblSearchName;
    private javax.swing.JLabel lblSearchStatus;
    private javax.swing.JLabel lblService;
    private javax.swing.JPanel pnlInput;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlServices;
    private javax.swing.JScrollPane spBookings;
    private javax.swing.JScrollPane spServices;
    private javax.swing.JTable tblBookings;
    private javax.swing.JTable tblServices;
    private javax.swing.JTextField txtCheckIn;
    private javax.swing.JTextField txtCheckOut;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtSearchName;
    // End of variables declaration//GEN-END:variables
}
