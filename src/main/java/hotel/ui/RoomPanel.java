package hotel.ui;

import hotel.manager.RoomManager;
import hotel.manager.RoomTypeManager;
import hotel.model.Room;
import hotel.model.RoomType;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 * Màn hình quản lý phòng (dữ liệu chính) + tìm kiếm nâng cao.
 * Giao diện thiết kế bằng NetBeans GUI Builder (file RoomPanel.form).
 */
public class RoomPanel extends javax.swing.JPanel {

    private static final String ALL = "Tất cả";

    private final RoomManager manager = new RoomManager();
    private final RoomTypeManager typeManager = new RoomTypeManager();
    private final DefaultTableModel model = UiHelper.createModel("Mã", "Số phòng", "Loại phòng", "Giá / đêm (VNĐ)", "Trạng thái");
    private List<Room> list = new ArrayList<>();

    /** @param isAdmin chỉ admin mới được thêm / sửa / xóa phòng */
    public RoomPanel(boolean isAdmin) {
        initComponents();
        UiHelper.setupTable(tblRooms, model);
        cbStatus.setModel(new DefaultComboBoxModel<>(Room.STATUSES));
        cbSearchStatus.setModel(new DefaultComboBoxModel<>(new String[]{ALL, Room.EMPTY, Room.BOOKED, Room.OCCUPIED}));
        btnAdd.setEnabled(isAdmin);
        btnUpdate.setEnabled(isAdmin);
        btnDelete.setEnabled(isAdmin);

        // Bấm vào một dòng -> đưa dữ liệu lên ô nhập
        tblRooms.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) fillForm();
        });
        reload();
    }

    /** Tải lại loại phòng vào các JComboBox và hiển thị toàn bộ phòng */
    public void reload() {
        try {
            cbType.removeAllItems();
            cbSearchType.removeAllItems();
            cbSearchType.addItem(new RoomType(0, ALL, 0)); // lựa chọn "Tất cả"
            for (RoomType t : typeManager.getAll()) {
                cbType.addItem(t);
                cbSearchType.addItem(t);
            }
            txtRoomNo.setText("");
            txtSearchNo.setText("");
            cbSearchStatus.setSelectedIndex(0);
            search();
        } catch (Exception ex) {
            UiHelper.showError(this, ex);
        }
    }

    /** Tìm kiếm theo số phòng + loại phòng + trạng thái, hiển thị kết quả lên bảng */
    private void search() {
        try {
            RoomType type = (RoomType) cbSearchType.getSelectedItem();
            String status = (String) cbSearchStatus.getSelectedItem();
            list = manager.search(txtSearchNo.getText(),
                    type == null ? 0 : type.getId(),
                    ALL.equals(status) ? null : status);
            model.setRowCount(0);
            for (Room r : list) {
                model.addRow(new Object[]{r.getId(), r.getRoomNo(), r.getTypeName(), r.getPrice(), r.getStatus()});
            }
        } catch (Exception ex) {
            UiHelper.showError(this, ex);
        }
    }

    private Room selected() {
        int row = UiHelper.selectedRow(tblRooms);
        return (row < 0 || row >= list.size()) ? null : list.get(row);
    }

    /** Đưa dữ liệu dòng đang chọn lên ô nhập */
    private void fillForm() {
        Room r = selected();
        if (r == null) return;
        txtRoomNo.setText(r.getRoomNo());
        cbStatus.setSelectedItem(r.getStatus());
        for (int i = 0; i < cbType.getItemCount(); i++) {
            if (cbType.getItemAt(i).getId() == r.getTypeId()) cbType.setSelectedIndex(i);
        }
    }

    /** Mã loại phòng đang chọn trong ô nhập (0 nếu chưa có) */
    private int selectedTypeId() {
        RoomType t = (RoomType) cbType.getSelectedItem();
        return t == null ? 0 : t.getId();
    }

    /**
     * Code giao diện do NetBeans tự sinh từ file .form - KHÔNG sửa tay đoạn này,
     * hãy sửa trong tab Design.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlInput = new javax.swing.JPanel();
        lblRoomNo = new javax.swing.JLabel();
        txtRoomNo = new javax.swing.JTextField();
        lblType = new javax.swing.JLabel();
        cbType = new javax.swing.JComboBox<>();
        lblStatus = new javax.swing.JLabel();
        cbStatus = new javax.swing.JComboBox<>();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        pnlSearch = new javax.swing.JPanel();
        lblSearchNo = new javax.swing.JLabel();
        txtSearchNo = new javax.swing.JTextField();
        lblSearchType = new javax.swing.JLabel();
        cbSearchType = new javax.swing.JComboBox<>();
        lblSearchStatus = new javax.swing.JLabel();
        cbSearchStatus = new javax.swing.JComboBox<>();
        btnSearch = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        spRooms = new javax.swing.JScrollPane();
        tblRooms = new javax.swing.JTable();

        pnlInput.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin phòng"));
        lblRoomNo.setText("Số phòng:");

        lblType.setText("Loại phòng:");

        lblStatus.setText("Trạng thái:");

        btnAdd.setBackground(new java.awt.Color(46, 160, 90));
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(33, 105, 170));
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(205, 60, 60));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnRefresh.setBackground(new java.awt.Color(230, 140, 30));
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
                                .addComponent(lblRoomNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(10, 10, 10)
                            .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtRoomNo, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                            .addGap(10, 10, 10)
                            .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(10, 10, 10)
                            .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cbType, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                            .addGap(10, 10, 10)
                            .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(10, 10, 10)
                            .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cbStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)))
                        .addGroup(pnlInputLayout.createSequentialGroup()
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(8, 8, 8)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(8, 8, 8)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                .addComponent(lblRoomNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtRoomNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10)
                        .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(8, 8, 8))
        );

        pnlSearch.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm phòng"));
        lblSearchNo.setText("Số phòng:");

        lblSearchType.setText("Loại phòng:");

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
                        .addComponent(lblSearchNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(txtSearchNo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(lblSearchType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(cbSearchType, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(lblSearchStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(cbSearchStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(lblSearchNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSearchNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSearchType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbSearchType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSearchStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbSearchStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(8, 8, 8))
        );

        spRooms.setViewportView(tblRooms);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnlInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(spRooms, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addComponent(spRooms, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                    .addGap(10, 10, 10))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        try {
            manager.add(txtRoomNo.getText(), selectedTypeId(), (String) cbStatus.getSelectedItem());
            reload();
            UiHelper.info(this, "Thêm phòng thành công!");
        } catch (Exception ex) {
            UiHelper.showError(this, ex);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        Room r = selected();
        if (r == null) {
            UiHelper.warn(this, "Hãy chọn một phòng trong bảng!");
            return;
        }
        try {
            manager.update(r.getId(), txtRoomNo.getText(), selectedTypeId(), (String) cbStatus.getSelectedItem());
            reload();
            UiHelper.info(this, "Cập nhật thành công!");
        } catch (Exception ex) {
            UiHelper.showError(this, ex);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        Room r = selected();
        if (r == null) {
            UiHelper.warn(this, "Hãy chọn một phòng trong bảng!");
            return;
        }
        if (!UiHelper.confirm(this, "Bạn có chắc chắn xóa phòng " + r.getRoomNo() + " không?")) return;
        try {
            manager.delete(r.getId());
            reload();
        } catch (Exception ex) {
            UiHelper.showError(this, ex);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        reload();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        search();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        UiHelper.exportCsv(this, tblRooms, "DanhSachPhong");
    }//GEN-LAST:event_btnExportActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbSearchStatus;
    private javax.swing.JComboBox<RoomType> cbSearchType;
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JComboBox<RoomType> cbType;
    private javax.swing.JLabel lblRoomNo;
    private javax.swing.JLabel lblSearchNo;
    private javax.swing.JLabel lblSearchStatus;
    private javax.swing.JLabel lblSearchType;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblType;
    private javax.swing.JPanel pnlInput;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JScrollPane spRooms;
    private javax.swing.JTable tblRooms;
    private javax.swing.JTextField txtRoomNo;
    private javax.swing.JTextField txtSearchNo;
    // End of variables declaration//GEN-END:variables
}
