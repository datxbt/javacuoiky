package hotel;

import hotel.ui.LoginFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/** Điểm bắt đầu chương trình. */
public class Main {

    public static void main(String[] args) {
        // Dùng giao diện Nimbus cho đẹp hơn (nếu có)
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) UIManager.setLookAndFeel(info.getClassName());
            }
        } catch (Exception ignored) {
            // không có Nimbus thì dùng giao diện mặc định
        }
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
