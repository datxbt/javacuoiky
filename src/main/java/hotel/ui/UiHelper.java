package hotel.ui;

import java.awt.Component;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/** Các hàm tiện ích dùng chung cho giao diện (bảng, thông báo, kiểm tra dữ liệu, xuất file...). */
public class UiHelper {

    /** Tạo model cho JTable: không cho sửa trực tiếp trên bảng, sắp xếp đúng kiểu số / ngày */
    public static DefaultTableModel createModel(String... columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                // Lấy kiểu dữ liệu từ dòng đầu tiên để sắp xếp đúng (số theo số, chữ theo chữ)
                if (getRowCount() > 0 && getValueAt(0, column) != null) {
                    return getValueAt(0, column).getClass();
                }
                return Object.class;
            }
        };
    }

    /**
     * Gắn model cho JTable (JTable được kéo thả trong NetBeans) và bật sắp xếp khi bấm tiêu đề cột.
     */
    public static void setupTable(JTable table, DefaultTableModel model) {
        table.setModel(model);
        table.setAutoCreateRowSorter(true);
        table.setRowHeight(26);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
    }

    /** Lấy chỉ số dòng đang chọn (theo dữ liệu gốc, kể cả khi bảng đã sắp xếp). -1 nếu chưa chọn. */
    public static int selectedRow(JTable table) {
        int viewRow = table.getSelectedRow();
        return viewRow < 0 ? -1 : table.convertRowIndexToModel(viewRow);
    }

    // ---------------- Kiểm tra dữ liệu nhập ----------------

    /** Chuyển chữ thành số thực, báo lỗi nếu không phải số */
    public static double parseNumber(String text, String fieldName) {
        try {
            return Double.parseDouble(text.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " phải là số!");
        }
    }

    /** Chuyển chữ thành số nguyên, báo lỗi nếu không phải số nguyên */
    public static int parseInt(String text, String fieldName) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " phải là số nguyên!");
        }
    }

    /** Chuyển chữ dạng yyyy-MM-dd thành ngày */
    public static LocalDate parseDate(String text, String fieldName) {
        try {
            return LocalDate.parse(text.trim());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(fieldName + " phải có dạng yyyy-MM-dd (ví dụ 2026-11-05)!");
        }
    }

    // ---------------- Thông báo ----------------

    public static void info(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void warn(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Chú ý", JOptionPane.WARNING_MESSAGE);
    }

    /** Hỏi xác nhận Có / Không */
    public static boolean confirm(Component parent, String msg) {
        return JOptionPane.showConfirmDialog(parent, msg, "Xác nhận", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION;
    }

    /** Hiển thị lỗi phù hợp với từng loại ngoại lệ */
    public static void showError(Component parent, Exception e) {
        if (e instanceof IllegalArgumentException) {
            warn(parent, e.getMessage()); // lỗi do người dùng nhập sai
        } else if (e instanceof SQLIntegrityConstraintViolationException) {
            JOptionPane.showMessageDialog(parent,
                    "Không thực hiện được: dữ liệu bị trùng hoặc đang được sử dụng ở nơi khác.\n" + e.getMessage(),
                    "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
        } else if (e instanceof SQLException) {
            JOptionPane.showMessageDialog(parent,
                    "Lỗi cơ sở dữ liệu! Hãy kiểm tra MySQL đã bật chưa.\n" + e.getMessage(),
                    "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(parent, "Lỗi: " + e, "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ---------------- Xuất file ----------------

    /** Xuất dữ liệu đang hiển thị trong bảng ra file CSV (mở được bằng Excel) */
    public static void exportCsv(Component parent, JTable table, String defaultName) {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File(defaultName + ".csv"));
        if (chooser.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION) return;

        File file = chooser.getSelectedFile();
        try (PrintWriter out = new PrintWriter(file, StandardCharsets.UTF_8)) {
            out.print('﻿'); // BOM: giúp Excel hiển thị đúng tiếng Việt
            for (int c = 0; c < table.getColumnCount(); c++) {
                out.print((c > 0 ? "," : "") + csv(table.getColumnName(c)));
            }
            out.println();
            for (int r = 0; r < table.getRowCount(); r++) {
                for (int c = 0; c < table.getColumnCount(); c++) {
                    Object v = table.getValueAt(r, c);
                    if (v instanceof Double d) v = String.format("%.0f", d);
                    out.print((c > 0 ? "," : "") + csv(v == null ? "" : v.toString()));
                }
                out.println();
            }
            info(parent, "Đã xuất file: " + file.getAbsolutePath());
        } catch (Exception e) {
            showError(parent, e);
        }
    }

    /** Bọc giá trị trong dấu nháy kép để không bị lỗi khi có dấu phẩy */
    private static String csv(String s) {
        return "\"" + s.replace("\"", "\"\"") + "\"";
    }
}
