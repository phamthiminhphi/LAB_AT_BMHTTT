package week_01;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class frm_TanCong extends JFrame {

    private JTextArea txt_cipher;
    private JButton btn_bruteforce;
    private JButton btn_frequency;
    private JTextField txt_known_plain;
    private JTextField txt_known_cipher;
    private JButton btn_known;
    private JTextArea txt_result;
    private JLabel lbl_status;

    public frm_TanCong() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Phân tích mật mã cổ điển");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(680, 560);
        setResizable(false);
        setLayout(null);

        JLabel jLabel1 = new JLabel("PHÂN TÍCH (TẤN CÔNG) MẬT MÃ CỔ ĐIỂN");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 18));
        jLabel1.setBounds(25, 20, 620, 34);
        add(jLabel1);

        JLabel jLabel2 = new JLabel("Ciphertext:");
        jLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel2.setBounds(25, 68, 130, 22);
        add(jLabel2);

        txt_cipher = new JTextArea();
        txt_cipher.setLineWrap(true);
        txt_cipher.setWrapStyleWord(true);
        txt_cipher.setRows(2);
        txt_cipher.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JScrollPane jScrollPane1 = new JScrollPane(txt_cipher);
        jScrollPane1.setBounds(160, 68, 480, 50);
        add(jScrollPane1);

        btn_bruteforce = new JButton("Brute Force Caesar");
        btn_bruteforce.setBounds(160, 128, 170, 30);
        btn_bruteforce.addActionListener(evt -> btn_bruteforceActionPerformed());
        add(btn_bruteforce);

        btn_frequency = new JButton("Frequency Analysis");
        btn_frequency.setBounds(345, 128, 170, 30);
        btn_frequency.addActionListener(evt -> btn_frequencyActionPerformed());
        add(btn_frequency);

        JLabel jLabel3 = new JLabel("Bản rõ đã biết:");
        jLabel3.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel3.setBounds(25, 175, 130, 22);
        add(jLabel3);

        txt_known_plain = new JTextField("HELLO");
        txt_known_plain.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txt_known_plain.setBounds(160, 173, 140, 28);
        add(txt_known_plain);

        JLabel jLabel4 = new JLabel("Bản mã:");
        jLabel4.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel4.setBounds(315, 175, 60, 22);
        add(jLabel4);

        txt_known_cipher = new JTextField("RIJVS");
        txt_known_cipher.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txt_known_cipher.setBounds(380, 173, 140, 28);
        add(txt_known_cipher);

        btn_known = new JButton("Known Plaintext (Vigenere)");
        btn_known.setBounds(160, 212, 220, 30);
        btn_known.addActionListener(evt -> btn_knownActionPerformed());
        add(btn_known);

        JLabel jLabel5 = new JLabel("Kết quả:");
        jLabel5.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel5.setBounds(25, 258, 130, 22);
        add(jLabel5);

        txt_result = new JTextArea();
        txt_result.setEditable(false);
        txt_result.setLineWrap(false);
        txt_result.setRows(3);
        txt_result.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane jScrollPane2 = new JScrollPane(txt_result);
        jScrollPane2.setBounds(160, 258, 480, 220);
        add(jScrollPane2);

        lbl_status = new JLabel("Sẵn sàng.");
        lbl_status.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl_status.setBounds(160, 488, 480, 22);
        add(lbl_status);
    }

    private void btn_bruteforceActionPerformed() {
        String cipher = txt_cipher.getText().trim();
        if (cipher.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập Ciphertext.");
            return;
        }
        txt_result.setText(AttackDemos.caesarBruteForce(cipher));
        txt_result.setCaretPosition(0);
        lbl_status.setText("Đã thử đủ 26 khóa Caesar.");
    }

    private void btn_frequencyActionPerformed() {
        String cipher = txt_cipher.getText().trim();
        if (cipher.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập Ciphertext.");
            return;
        }
        txt_result.setText(AttackDemos.frequency(cipher));
        txt_result.setCaretPosition(0);
        lbl_status.setText("Đã thống kê tần suất chữ cái.");
    }

    private void btn_knownActionPerformed() {
        try {
            String key = AttackDemos.knownPlaintext(txt_known_plain.getText(), txt_known_cipher.getText());
            txt_result.setText("Khóa Vigenère suy ra từng vị trí: " + key
                    + "\nNếu khóa ngắn hơn bản rõ, khóa sẽ lặp lại (KEY KEY ...).");
            lbl_status.setText("Khóa suy ra: " + key);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_TanCong().setVisible(true));
    }
}