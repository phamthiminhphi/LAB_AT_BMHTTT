package week_02;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class frm_AES extends JFrame {

    /** Tên người dùng đã Login thành công; null = chưa đăng nhập. */
    private String currentUser = null;

    private JTextField txt_username;
    private JPasswordField txt_password;
    private JButton btn_sql;
    private JButton btn_register;
    private JButton btn_login;
    private JTextField txt_ticket;
    private JTextArea txt_plaintext;
    private JTextField txt_key;
    private JTextArea txt_ciphertext;
    private JButton btn_encrypt;
    private JButton btn_decrypt;
    private JButton btn_save;
    private JButton btn_open;
    private JLabel lbl_status;

    public frm_AES() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("AES Cipher - Register / Login");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(730, 560);
        setResizable(false);
        setLayout(null);

        JLabel jLabel1 = new JLabel("AES-GCM - ĐĂNG KÝ / ĐĂNG NHẬP (SQL SERVER)");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 18));
        jLabel1.setBounds(25, 20, 570, 34);
        add(jLabel1);

        JLabel jLabel2 = new JLabel("Username:");
        jLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel2.setBounds(25, 68, 100, 22);
        add(jLabel2);

        txt_username = new JTextField("sinhvien01");
        txt_username.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txt_username.setBounds(130, 66, 250, 28);
        add(txt_username);

        JLabel jLabel3 = new JLabel("Password:");
        jLabel3.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel3.setBounds(25, 106, 100, 22);
        add(jLabel3);

        txt_password = new JPasswordField();
        txt_password.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txt_password.setBounds(130, 104, 250, 28);
        add(txt_password);

        JLabel jLabel4 = new JLabel("(ít nhất 8 ký tự)");
        jLabel4.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel4.setBounds(395, 106, 310, 22);
        add(jLabel4);

        btn_sql = new JButton("Kết nối SQL Server");
        btn_sql.setBounds(130, 144, 170, 30);
        btn_sql.addActionListener(evt -> new frm_KetNoiSQL().setVisible(true));
        add(btn_sql);

        btn_register = new JButton("Register");
        btn_register.setBounds(315, 144, 110, 30);
        btn_register.addActionListener(evt -> btn_registerActionPerformed());
        add(btn_register);

        btn_login = new JButton("Login");
        btn_login.setBounds(440, 144, 110, 30);
        btn_login.addActionListener(evt -> btn_loginActionPerformed());
        add(btn_login);

        JLabel jLabel5 = new JLabel("Ticket:");
        jLabel5.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel5.setBounds(25, 188, 100, 22);
        add(jLabel5);

        txt_ticket = new JTextField();
        txt_ticket.setEditable(false);
        txt_ticket.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txt_ticket.setBounds(130, 186, 420, 28);
        add(txt_ticket);

        JLabel jLabel6 = new JLabel("Plaintext:");
        jLabel6.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel6.setBounds(25, 228, 100, 22);
        add(jLabel6);

        txt_plaintext = new JTextArea();
        txt_plaintext.setLineWrap(true);
        txt_plaintext.setWrapStyleWord(true);
        txt_plaintext.setRows(3);
        txt_plaintext.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JScrollPane jScrollPane1 = new JScrollPane(txt_plaintext);
        jScrollPane1.setBounds(130, 228, 570, 70);
        add(jScrollPane1);

        JLabel jLabel7 = new JLabel("Key AES:");
        jLabel7.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel7.setBounds(25, 312, 100, 22);
        add(jLabel7);

        txt_key = new JTextField();
        txt_key.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txt_key.setBounds(130, 310, 300, 28);
        add(txt_key);

        JLabel jLabel8 = new JLabel("Mật khẩu để tạo khóa AES-256");
        jLabel8.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel8.setBounds(440, 312, 260, 22);
        add(jLabel8);

        JLabel jLabel9 = new JLabel("Ciphertext:");
        jLabel9.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel9.setBounds(25, 352, 100, 22);
        add(jLabel9);

        txt_ciphertext = new JTextArea();
        txt_ciphertext.setLineWrap(true);
        txt_ciphertext.setWrapStyleWord(true);
        txt_ciphertext.setRows(3);
        txt_ciphertext.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JScrollPane jScrollPane2 = new JScrollPane(txt_ciphertext);
        jScrollPane2.setBounds(130, 352, 570, 70);
        add(jScrollPane2);

        btn_encrypt = new JButton("Encrypt");
        btn_encrypt.setBounds(130, 438, 100, 30);
        btn_encrypt.addActionListener(evt -> btn_encryptActionPerformed());
        add(btn_encrypt);

        btn_decrypt = new JButton("Decrypt");
        btn_decrypt.setBounds(245, 438, 100, 30);
        btn_decrypt.addActionListener(evt -> btn_decryptActionPerformed());
        add(btn_decrypt);

        btn_save = new JButton("Save File");
        btn_save.setBounds(360, 438, 120, 30);
        btn_save.addActionListener(evt -> btn_saveActionPerformed());
        add(btn_save);

        btn_open = new JButton("Open File");
        btn_open.setBounds(495, 438, 120, 30);
        btn_open.addActionListener(evt -> btn_openActionPerformed());
        add(btn_open);

        lbl_status = new JLabel("Sẵn sàng.");
        lbl_status.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl_status.setBounds(130, 480, 570, 36);
        add(lbl_status);
    }

    private void btn_registerActionPerformed() {
        try {
            String user = txt_username.getText().trim();
            UserDao.register(user, txt_password.getPassword());
            HistoryDao.tryInsert("AES", "REGISTER", user, 0, 0);
            lbl_status.setText("Register thành công: " + user + ". Bấm Login để đăng nhập.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
        } catch (java.sql.SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi SQL Server: " + ex.getMessage(),
                    "Lỗi SQL Server", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_loginActionPerformed() {
        try {
            String user = txt_username.getText().trim();
            if (UserDao.login(user, txt_password.getPassword())) {
                currentUser = user;
                txt_ticket.setText(UserDao.newTicket());
                HistoryDao.tryInsert("AES", "LOGIN", user, 0, 0);
                lbl_status.setText("Login thành công: " + user + ". Có thể dùng Encrypt/Decrypt.");
            } else {
                currentUser = null;
                txt_ticket.setText("");
                JOptionPane.showMessageDialog(this, "Sai username hoặc mật khẩu.",
                        "Login thất bại", JOptionPane.ERROR_MESSAGE);
            }
        } catch (java.sql.SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi SQL Server: " + ex.getMessage(),
                    "Lỗi SQL Server", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_encryptActionPerformed() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Hãy Login trước khi dùng AES.");
            return;
        }
        String plaintext = txt_plaintext.getText();
        if (plaintext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập Plaintext trước khi mã hóa.");
            return;
        }
        try {
            String ciphertext = AESCipher.encrypt(plaintext, txt_key.getText());
            txt_ciphertext.setText(ciphertext);
            lbl_status.setText("<html>Đã mã hóa AES. "
                    + HistoryDao.tryInsert("AES", "ENCRYPT", currentUser, plaintext.length(), ciphertext.length())
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
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Hãy Login trước khi dùng AES.");
            return;
        }
        String ciphertext = txt_ciphertext.getText();
        if (ciphertext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập Ciphertext trước khi giải mã.");
            return;
        }
        try {
            String plaintext = AESCipher.decrypt(ciphertext, txt_key.getText());
            txt_plaintext.setText(plaintext);
            lbl_status.setText("<html>Đã giải mã AES. "
                    + HistoryDao.tryInsert("AES", "DECRYPT", currentUser, ciphertext.length(), plaintext.length())
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
        String path = FileTextIO.saveText(this, txt_ciphertext, "aes_ciphertext.txt");
        if (path != null) {
            lbl_status.setText("Đã lưu tệp: " + path);
        }
    }

    private void btn_openActionPerformed() {
        String path = FileTextIO.openText(this, txt_ciphertext);
        if (path != null) {
            lbl_status.setText("Đã mở tệp: " + path);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_AES().setVisible(true));
    }
}