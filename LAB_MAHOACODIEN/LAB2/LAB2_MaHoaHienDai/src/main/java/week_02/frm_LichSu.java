package week_02;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

public class frm_LichSu extends JFrame {

    private JTable tbl_history;
    private JButton btn_refresh;
    private JLabel lbl_status;

    public frm_LichSu() {
        initComponents();
        setLocationRelativeTo(null);
        tbl_history.setModel(HistoryDao.emptyModel());
        loadData();
    }

    private void initComponents() {
        setTitle("Lịch sử thao tác (SQL Server)");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(820, 410);
        setResizable(false);
        setLayout(null);

        JLabel jLabel1 = new JLabel("LỊCH SỬ THAO TÁC - BẢNG DBO.CRYPTO_HISTORY");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 18));
        jLabel1.setBounds(25, 20, 760, 34);
        add(jLabel1);

        tbl_history = new JTable();
        tbl_history.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tbl_history.setRowHeight(22);
        JScrollPane jScrollPane1 = new JScrollPane(tbl_history);
        jScrollPane1.setBounds(25, 65, 760, 260);
        add(jScrollPane1);

        btn_refresh = new JButton("Tải lại");
        btn_refresh.setBounds(25, 335, 110, 30);
        btn_refresh.addActionListener(evt -> loadData());
        add(btn_refresh);

        lbl_status = new JLabel("Đang tải...");
        lbl_status.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl_status.setBounds(150, 339, 640, 22);
        add(lbl_status);
    }

    /** Đọc 50 dòng mới nhất từ SQL Server và hiện lên JTable. */
    private void loadData() {
        try {
            tbl_history.setModel(HistoryDao.loadLatest(50));
            lbl_status.setText("Đã tải " + tbl_history.getRowCount() + " dòng mới nhất từ dbo.crypto_history.");
        } catch (java.sql.SQLException ex) {
            lbl_status.setText("Không đọc được lịch sử: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_LichSu().setVisible(true));
    }
}