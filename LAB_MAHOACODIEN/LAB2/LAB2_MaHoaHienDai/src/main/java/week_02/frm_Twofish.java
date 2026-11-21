package week_02;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class frm_Twofish extends JFrame {

    private JTextArea txt_plaintext;
    private JTextField txt_key;
    private JTextField txt_iv;
    private JButton btn_iv;
    private JTextArea txt_ciphertext;
    private JButton btn_encrypt;
    private JButton btn_decrypt;
    private JButton btn_save;
    private JButton btn_open;
    private JLabel lbl_status;

    public frm_Twofish() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Twofish Cipher");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(730, 460);
        setResizable(false);
        setLayout(null);

        JLabel jLabel1 = new JLabel("MẬT MÃ TWOFISH (CBC)");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 18));
        jLabel1.setBounds(25, 20, 570, 34);
        add(jLabel1);

        JLabel jLabel2 = new JLabel("Plaintext:");
        jLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel2.setBounds(25, 70, 100, 22);
        add(jLabel2);

        txt_plaintext = new JTextArea();
        txt_plaintext.setLineWrap(true);
        txt_plaintext.setWrapStyleWord(true);
        txt_plaintext.setRows(3);
        txt_plaintext.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JScrollPane jScrollPane1 = new JScrollPane(txt_plaintext);
        jScrollPane1.setBounds(130, 70, 570, 70);
        add(jScrollPane1);

        JLabel jLabel3 = new JLabel("Key:");
        jLabel3.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel3.setBounds(25, 155, 100, 22);
        add(jLabel3);

        txt_key = new JTextField("Twofish@Lab2");
        txt_key.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txt_key.setBounds(130, 153, 300, 28);
        add(txt_key);

        JLabel jLabel4 = new JLabel("Mật khẩu bất kỳ (khóa = SHA-256)");
        jLabel4.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel4.setBounds(440, 155, 260, 22);
        add(jLabel4);

        JLabel jLabelIv = new JLabel("IV (HEX):");
        jLabelIv.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabelIv.setBounds(25, 195, 100, 22);
        add(jLabelIv);

        txt_iv = new JTextField();
        txt_iv.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txt_iv.setBounds(130, 193, 330, 28);
        add(txt_iv);

        btn_iv = new JButton("Generate IV");
        btn_iv.setBounds(475, 193, 130, 28);
        btn_iv.addActionListener(evt -> btn_ivActionPerformed());
        add(btn_iv);

        JLabel jLabel5 = new JLabel("Ciphertext:");
        jLabel5.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel5.setBounds(25, 235, 100, 22);
        add(jLabel5);

        txt_ciphertext = new JTextArea();
        txt_ciphertext.setLineWrap(true);
        txt_ciphertext.setWrapStyleWord(true);
        txt_ciphertext.setRows(3);
        txt_ciphertext.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JScrollPane jScrollPane2 = new JScrollPane(txt_ciphertext);
        jScrollPane2.setBounds(130, 235, 570, 70);
        add(jScrollPane2);

        btn_encrypt = new JButton("Encrypt");
        btn_encrypt.setBounds(130, 322, 100, 30);
        btn_encrypt.addActionListener(evt -> btn_encryptActionPerformed());
        add(btn_encrypt);

        btn_decrypt = new JButton("Decrypt");
        btn_decrypt.setBounds(245, 322, 100, 30);
        btn_decrypt.addActionListener(evt -> btn_decryptActionPerformed());
        add(btn_decrypt);

        btn_save = new JButton("Save File");
        btn_save.setBounds(360, 322, 120, 30);
        btn_save.addActionListener(evt -> btn_saveActionPerformed());
        add(btn_save);

        btn_open = new JButton("Open File");
        btn_open.setBounds(495, 322, 120, 30);
        btn_open.addActionListener(evt -> btn_openActionPerformed());
        add(btn_open);

        lbl_status = new JLabel("Sẵn sàng.");
        lbl_status.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl_status.setBounds(130, 365, 570, 36);
        add(lbl_status);
    }

    private void btn_ivActionPerformed() {
        txt_iv.setText(TwofishCipher.newIv());
        lbl_status.setText("Đã tạo IV ngẫu nhiên 16 byte. Giữ nguyên IV này để giải mã.");
    }

    private void btn_encryptActionPerformed() {
        String plaintext = txt_plaintext.getText();
        if (plaintext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập Plaintext trước khi mã hóa.");
            return;
        }
        try {
            String ciphertext = TwofishCipher.encrypt(plaintext, txt_key.getText(), txt_iv.getText());
            txt_ciphertext.setText(ciphertext);
            lbl_status.setText("<html>Đã mã hóa Twofish. "
                    + HistoryDao.tryInsert("Twofish", "ENCRYPT", null, plaintext.length(), ciphertext.length())
                    + "</html>");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Mã hóa thất bại:\n" + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_decryptActionPerformed() {
        String ciphertext = txt_ciphertext.getText();
        if (ciphertext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập Ciphertext trước khi giải mã.");
            return;
        }
        try {
            String plaintext = TwofishCipher.decrypt(ciphertext, txt_key.getText(), txt_iv.getText());
            txt_plaintext.setText(plaintext);
            lbl_status.setText("<html>Đã giải mã Twofish. "
                    + HistoryDao.tryInsert("Twofish", "DECRYPT", null, ciphertext.length(), plaintext.length())
                    + "</html>");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Giải mã thất bại: sai Key hoặc bản mã bị sửa.\n" + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_saveActionPerformed() {
        if (txt_ciphertext.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Chưa có bản mã để lưu.");
            return;
        }
        String content = TwofishCipher.toFile(txt_iv.getText(), txt_ciphertext.getText());
        String path = FileTextIO.saveString(this, content, "twofish_ciphertext.txt");
        if (path != null) {
            lbl_status.setText("Đã lưu IV + bản mã vào: " + path);
        }
    }

    private void btn_openActionPerformed() {
        String content = FileTextIO.openString(this);
        if (content == null) {
            return;
        }
        try {
            String[] parts = TwofishCipher.fromFile(content);
            txt_iv.setText(parts[0]);
            txt_ciphertext.setText(parts[1]);
            lbl_status.setText("Đã mở tệp, khôi phục IV và bản mã. Nhập đúng Key rồi bấm Decrypt.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Tệp không hợp lệ", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_Twofish().setVisible(true));
    }
}