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

public class frm_AES_CTR extends JFrame {

    private JTextArea txt_plaintext;
    private JTextField txt_key;
    private JButton btn_genkey;
    private JTextField txt_nonce;
    private JButton btn_gennonce;
    private JTextArea txt_ciphertext;
    private JButton btn_encrypt;
    private JButton btn_decrypt;
    private JButton btn_save;
    private JButton btn_open;
    private JLabel lbl_status;

    public frm_AES_CTR() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("AES-CTR Cipher");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(730, 460);
        setResizable(false);
        setLayout(null);

        JLabel jLabel1 = new JLabel("BÀI NÂNG CAO 01 - AES-256 CHẾ ĐỘ CTR");
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

        JLabel jLabel3 = new JLabel("Key (Base64):");
        jLabel3.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel3.setBounds(25, 155, 100, 22);
        add(jLabel3);

        txt_key = new JTextField();
        txt_key.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txt_key.setBounds(130, 153, 420, 28);
        add(txt_key);

        btn_genkey = new JButton("Generate Key");
        btn_genkey.setBounds(560, 153, 140, 28);
        btn_genkey.addActionListener(evt -> btn_genkeyActionPerformed());
        add(btn_genkey);

        JLabel jLabel4 = new JLabel("Nonce (Base64):");
        jLabel4.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel4.setBounds(25, 195, 100, 22);
        add(jLabel4);

        txt_nonce = new JTextField();
        txt_nonce.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txt_nonce.setBounds(130, 193, 420, 28);
        add(txt_nonce);

        btn_gennonce = new JButton("Generate Nonce");
        btn_gennonce.setBounds(560, 193, 140, 28);
        btn_gennonce.addActionListener(evt -> btn_gennonceActionPerformed());
        add(btn_gennonce);

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

    private void btn_genkeyActionPerformed() {
        txt_key.setText(AESCTRCipher.newKey());
        lbl_status.setText("Đã tạo khóa ngẫu nhiên 256 bit.");
    }

    private void btn_gennonceActionPerformed() {
        txt_nonce.setText(AESCTRCipher.newNonce());
        lbl_status.setText("Đã tạo nonce ngẫu nhiên 96 bit. Mỗi lần mã hóa nên tạo nonce mới.");
    }

    private void btn_encryptActionPerformed() {
        String plaintext = txt_plaintext.getText();
        if (plaintext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập Plaintext trước khi mã hóa.");
            return;
        }
        try {
            String ciphertext = AESCTRCipher.encrypt(plaintext, txt_key.getText(), txt_nonce.getText());
            txt_ciphertext.setText(ciphertext);
            lbl_status.setText("<html>Đã mã hóa AES-CTR. "
                    + HistoryDao.tryInsert("AES-CTR", "ENCRYPT", null, plaintext.length(), ciphertext.length())
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
            String plaintext = AESCTRCipher.decrypt(ciphertext, txt_key.getText(), txt_nonce.getText());
            txt_plaintext.setText(plaintext);
            lbl_status.setText("<html>Đã giải mã AES-CTR. "
                    + HistoryDao.tryInsert("AES-CTR", "DECRYPT", null, ciphertext.length(), plaintext.length())
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
        String content = AESCTRCipher.toFile(txt_nonce.getText(), txt_ciphertext.getText());
        String path = FileTextIO.saveString(this, content, "aes_ctr_ciphertext.txt");
        if (path != null) {
            lbl_status.setText("Đã lưu nonce + bản mã (không lưu Key) vào: " + path);
        }
    }

    private void btn_openActionPerformed() {
        String content = FileTextIO.openString(this);
        if (content == null) {
            return;
        }
        try {
            String[] parts = AESCTRCipher.fromFile(content);
            txt_nonce.setText(parts[0]);
            txt_ciphertext.setText(parts[1]);
            lbl_status.setText("Đã mở tệp. Nhập đúng Key rồi bấm Decrypt.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Tệp không hợp lệ", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_AES_CTR().setVisible(true));
    }
}