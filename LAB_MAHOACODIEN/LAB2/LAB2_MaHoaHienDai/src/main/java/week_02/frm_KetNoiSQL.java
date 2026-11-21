package week_02;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class frm_KetNoiSQL extends JFrame {

    private JTextField txt_server;
    private JTextField txt_port;
    private JTextField txt_database;
    private JTextField txt_user;
    private JPasswordField txt_password;
    private JButton btn_test;
    private JButton btn_history;
    private JLabel lbl_status;

    public frm_KetNoiSQL() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Kết nối Microsoft SQL Server");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(520, 380);
        setResizable(false);
        setLayout(null);

        JLabel jLabel1 = new JLabel("KẾT NỐI MICROSOFT SQL SERVER");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 18));
        jLabel1.setBounds(25, 20, 450, 34);
        add(jLabel1);

        JLabel jLabel2 = new JLabel("Server:");
        jLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel2.setBounds(25, 70, 100, 22);
        add(jLabel2);

        txt_server = new JTextField("127.0.0.1");
        txt_server.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txt_server.setBounds(130, 68, 320, 28);
        add(txt_server);

        JLabel jLabel3 = new JLabel("Port:");
        jLabel3.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel3.setBounds(25, 110, 100, 22);
        add(jLabel3);

        // Cổng 1433 đúng với cấu hình SQL Server trên máy
        txt_port = new JTextField("1433");
        txt_port.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txt_port.setBounds(130, 108, 120, 28);
        add(txt_port);

        JLabel jLabel4 = new JLabel("Database:");
        jLabel4.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel4.setBounds(25, 150, 100, 22);
        add(jLabel4);

        txt_database = new JTextField("LAB2_MaHoaHienDai");
        txt_database.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txt_database.setBounds(130, 148, 320, 28);
        add(txt_database);

        JLabel jLabel5 = new JLabel("User:");
        jLabel5.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel5.setBounds(25, 190, 100, 22);
        add(jLabel5);

        txt_user = new JTextField("lab2_user");
        txt_user.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txt_user.setBounds(130, 188, 320, 28);
        add(txt_user);

        JLabel jLabel6 = new JLabel("Password:");
        jLabel6.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel6.setBounds(25, 230, 100, 22);
        add(jLabel6);

        txt_password = new JPasswordField();
        txt_password.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txt_password.setBounds(130, 228, 320, 28);
        add(txt_password);

        btn_test = new JButton("Test Connection");
        btn_test.setBounds(130, 272, 150, 30);
        btn_test.addActionListener(evt -> btn_testActionPerformed());
        add(btn_test);

        btn_history = new JButton("Xem lịch sử");
        btn_history.setBounds(295, 272, 130, 30);
        btn_history.addActionListener(evt -> new frm_LichSu().setVisible(true));
        add(btn_history);

        lbl_status = new JLabel("Chưa kết nối.");
        lbl_status.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl_status.setBounds(25, 312, 460, 40);
        add(lbl_status);
    }

    private void btn_testActionPerformed() {
        try {
            int port = Integer.parseInt(txt_port.getText().trim());
            DbConfig.set(txt_server.getText().trim(), port, txt_database.getText().trim(),
                    txt_user.getText().trim(), txt_password.getPassword());
            lbl_status.setText("<html>" + DbConfig.test() + "</html>");
        } catch (Exception ex) {
            DbConfig.clear();
            lbl_status.setText("<html>Kết nối thất bại: " + ex.getMessage() + "</html>");
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Không kết nối được SQL Server", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_KetNoiSQL().setVisible(true));
    }
}