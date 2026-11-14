package week_01;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class frm_Menu extends JFrame {

    private JButton btn_caesar;
    private JButton btn_vigenere;
    private JButton btn_railfence;
    private JButton btn_playfair;
    private JButton btn_transposition;
    private JButton btn_autokey;
    private JButton btn_tonghop;
    private JButton btn_tancong;
    private JButton btn_sql;

    public frm_Menu() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("LAB 1 - Mã hóa đối xứng cổ điển");
        // Riêng frm_Menu dùng EXIT_ON_CLOSE để tắt toàn bộ chương trình khi đóng cửa sổ chính
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(700, 320);
        setResizable(false);
        setLayout(null);

        JLabel jLabel1 = new JLabel("LAB 1 - MÃ HÓA ĐỐI XỨNG CỔ ĐIỂN");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 20));
        jLabel1.setBounds(25, 20, 640, 38);
        add(jLabel1);

        JLabel jLabel2 = new JLabel("Chọn một bài để mở form tương ứng:");
        jLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel2.setBounds(25, 65, 640, 22);
        add(jLabel2);

        // Hàng 1
        btn_caesar = new JButton("1.1 Caesar");
        btn_caesar.setBounds(25, 102, 200, 30);
        btn_caesar.addActionListener(evt -> new frm_Caesar().setVisible(true));
        add(btn_caesar);

        btn_vigenere = new JButton("1.2 Vigenère");
        btn_vigenere.setBounds(240, 102, 200, 30);
        btn_vigenere.addActionListener(evt -> new frm_Vigenere().setVisible(true));
        add(btn_vigenere);

        btn_railfence = new JButton("1.3 Rail Fence");
        btn_railfence.setBounds(455, 102, 200, 30);
        btn_railfence.addActionListener(evt -> new frm_RailFence().setVisible(true));
        add(btn_railfence);

        // Hàng 2
        btn_playfair = new JButton("1.4 Playfair");
        btn_playfair.setBounds(25, 145, 200, 30);
        btn_playfair.addActionListener(evt -> new frm_Playfair().setVisible(true));
        add(btn_playfair);

        btn_transposition = new JButton("1.5 Transposition");
        btn_transposition.setBounds(240, 145, 200, 30);
        btn_transposition.addActionListener(evt -> new frm_Transposition().setVisible(true));
        add(btn_transposition);

        btn_autokey = new JButton("1.6 Autokey");
        btn_autokey.setBounds(455, 145, 200, 30);
        btn_autokey.addActionListener(evt -> new frm_Autokey().setVisible(true));
        add(btn_autokey);

        // Hàng 3
        btn_tonghop = new JButton("Nâng cao 01 - Tổng hợp");
        btn_tonghop.setBounds(25, 188, 200, 30);
        btn_tonghop.addActionListener(evt -> new frm_TongHop().setVisible(true));
        add(btn_tonghop);

        btn_tancong = new JButton("Nâng cao 02 - Tấn công");
        btn_tancong.setBounds(240, 188, 200, 30);
        btn_tancong.addActionListener(evt -> new frm_TanCong().setVisible(true));
        add(btn_tancong);

        btn_sql = new JButton("Kết nối SQL Server");
        btn_sql.setBounds(455, 188, 200, 30);
        btn_sql.addActionListener(evt -> new frm_KetNoiSQL().setVisible(true));
        add(btn_sql);

        JLabel jLabel3 = new JLabel("Mỗi form mở ra là một cửa sổ riêng; đóng form con không làm tắt chương trình.");
        jLabel3.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel3.setBounds(25, 235, 640, 22);
        add(jLabel3);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_Menu().setVisible(true));
    }
}