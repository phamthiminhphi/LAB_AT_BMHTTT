package week_02;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class frm_Menu extends JFrame {

    private JButton btn_des;
    private JButton btn_tripledes;
    private JButton btn_aes;
    private JButton btn_rc4;
    private JButton btn_twofish;
    private JButton btn_aesctr;
    private JButton btn_sql;
    private JButton btn_history;

    public frm_Menu() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("LAB 2 - Mã hóa đối xứng hiện đại");
        // Riêng frm_Menu dùng EXIT_ON_CLOSE để tắt chương trình khi đóng cửa sổ chính
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(700, 310);
        setResizable(false);
        setLayout(null);

        JLabel jLabel1 = new JLabel("LAB 2 - MÃ HÓA ĐỐI XỨNG HIỆN ĐẠI");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 20));
        jLabel1.setBounds(25, 20, 640, 38);
        add(jLabel1);

        JLabel jLabel2 = new JLabel("Chọn một bài để mở form tương ứng:");
        jLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel2.setBounds(25, 65, 640, 22);
        add(jLabel2);

        // Hàng 1
        btn_des = new JButton("2.1 DES");
        btn_des.setBounds(25, 102, 200, 30);
        btn_des.addActionListener(evt -> new frm_DES().setVisible(true));
        add(btn_des);

        btn_tripledes = new JButton("2.2 TripleDES");
        btn_tripledes.setBounds(240, 102, 200, 30);
        btn_tripledes.addActionListener(evt -> new frm_TripleDES().setVisible(true));
        add(btn_tripledes);

        btn_aes = new JButton("2.3 AES + Login");
        btn_aes.setBounds(455, 102, 200, 30);
        btn_aes.addActionListener(evt -> new frm_AES().setVisible(true));
        add(btn_aes);

        // Hàng 2
        btn_rc4 = new JButton("2.4 RC4");
        btn_rc4.setBounds(25, 145, 200, 30);
        btn_rc4.addActionListener(evt -> new frm_RC4().setVisible(true));
        add(btn_rc4);

        btn_twofish = new JButton("2.5 Twofish");
        btn_twofish.setBounds(240, 145, 200, 30);
        btn_twofish.addActionListener(evt -> new frm_Twofish().setVisible(true));
        add(btn_twofish);

        btn_aesctr = new JButton("2.6 Nâng cao - AES-CTR");
        btn_aesctr.setBounds(455, 145, 200, 30);
        btn_aesctr.addActionListener(evt -> new frm_AES_CTR().setVisible(true));
        add(btn_aesctr);

        // Hàng 3
        btn_sql = new JButton("Kết nối SQL Server");
        btn_sql.setBounds(25, 188, 200, 30);
        btn_sql.addActionListener(evt -> new frm_KetNoiSQL().setVisible(true));
        add(btn_sql);

        btn_history = new JButton("Xem lịch sử");
        btn_history.setBounds(240, 188, 200, 30);
        btn_history.addActionListener(evt -> new frm_LichSu().setVisible(true));
        add(btn_history);

        JLabel jLabel3 = new JLabel("Bài nâng cao 02 (AES-CBC) viết bằng Python: chạy tệp aes_cbc_gui.py.");
        jLabel3.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel3.setBounds(25, 232, 640, 22);
        add(jLabel3);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_Menu().setVisible(true));
    }
}