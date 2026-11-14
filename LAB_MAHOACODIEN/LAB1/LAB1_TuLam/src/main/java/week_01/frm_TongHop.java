package week_01;

import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class frm_TongHop extends JFrame {

    private JComboBox<String> cbo_algorithm;
    private JLabel lbl_hint;
    private JTextArea txt_plaintext;
    private JTextField txt_key;
    private JTextArea txt_ciphertext;
    private JButton btn_encrypt;
    private JButton btn_decrypt;
    private JButton btn_open;
    private JButton btn_save;
    private JButton btn_sql;
    private JButton btn_history;
    private JLabel lbl_status;

    public frm_TongHop() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Chương trình tổng hợp - 6 thuật toán");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(730, 490);
        setResizable(false);
        setLayout(null);

        JLabel jLabel1 = new JLabel("CHƯƠNG TRÌNH TỔNG HỢP 6 THUẬT TOÁN CỔ ĐIỂN");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 18));
        jLabel1.setBounds(25, 20, 570, 34);
        add(jLabel1);

        JLabel jLabel2 = new JLabel("Thuật toán:");
        jLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel2.setBounds(25, 70, 100, 22);
        add(jLabel2);

        cbo_algorithm = new JComboBox<>(new DefaultComboBoxModel<>(CipherTongHop.ALGORITHMS));
        cbo_algorithm.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cbo_algorithm.setBounds(130, 68, 200, 28);
        cbo_algorithm.addActionListener(evt -> cbo_algorithmActionPerformed());
        add(cbo_algorithm);

        lbl_hint = new JLabel("Khóa mẫu: 3");
        lbl_hint.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl_hint.setBounds(345, 70, 350, 22);
        add(lbl_hint);

        JLabel jLabel3 = new JLabel("Plaintext:");
        jLabel3.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel3.setBounds(25, 112, 100, 22);
        add(jLabel3);

        txt_plaintext = new JTextArea();
        txt_plaintext.setLineWrap(true);
        txt_plaintext.setWrapStyleWord(true);
        txt_plaintext.setRows(3);
        txt_plaintext.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JScrollPane jScrollPane1 = new JScrollPane(txt_plaintext);
        jScrollPane1.setBounds(130, 112, 560, 70);
        add(jScrollPane1);

        JLabel jLabel4 = new JLabel("Key:");
        jLabel4.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel4.setBounds(25, 197, 100, 22);
        add(jLabel4);

        txt_key = new JTextField("3");
        txt_key.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txt_key.setBounds(130, 195, 200, 28);
        add(txt_key);

        JLabel jLabel5 = new JLabel("Ciphertext:");
        jLabel5.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel5.setBounds(25, 237, 100, 22);
        add(jLabel5);

        txt_ciphertext = new JTextArea();
        txt_ciphertext.setLineWrap(true);
        txt_ciphertext.setWrapStyleWord(true);
        txt_ciphertext.setRows(3);
        txt_ciphertext.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JScrollPane jScrollPane2 = new JScrollPane(txt_ciphertext);
        jScrollPane2.setBounds(130, 237, 560, 70);
        add(jScrollPane2);

        btn_encrypt = new JButton("Encrypt");
        btn_encrypt.setBounds(130, 322, 100, 30);
        btn_encrypt.addActionListener(evt -> btn_encryptActionPerformed());
        add(btn_encrypt);

        btn_decrypt = new JButton("Decrypt");
        btn_decrypt.setBounds(245, 322, 100, 30);
        btn_decrypt.addActionListener(evt -> btn_decryptActionPerformed());
        add(btn_decrypt);

        btn_open = new JButton("Open File");
        btn_open.setBounds(360, 322, 110, 30);
        btn_open.addActionListener(evt -> btn_openActionPerformed());
        add(btn_open);

        btn_save = new JButton("Save File");
        btn_save.setBounds(485, 322, 110, 30);
        btn_save.addActionListener(evt -> btn_saveActionPerformed());
        add(btn_save);

        btn_sql = new JButton("Kết nối SQL Server");
        btn_sql.setBounds(130, 365, 170, 30);
        btn_sql.addActionListener(evt -> new frm_KetNoiSQL().setVisible(true));
        add(btn_sql);

        btn_history = new JButton("Xem lịch sử");
        btn_history.setBounds(315, 365, 130, 30);
        btn_history.addActionListener(evt -> new frm_LichSu().setVisible(true));
        add(btn_history);

        lbl_status = new JLabel("Sẵn sàng.");
        lbl_status.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl_status.setBounds(130, 408, 560, 22);
        add(lbl_status);
    }

    private void cbo_algorithmActionPerformed() {
        String algorithm = (String) cbo_algorithm.getSelectedItem();
        txt_key.setText(CipherTongHop.sampleKey(algorithm));
        lbl_hint.setText("Khóa mẫu: " + CipherTongHop.sampleKey(algorithm));
    }

    private void btn_encryptActionPerformed() {
        String algorithm = (String) cbo_algorithm.getSelectedItem();
        String input = txt_plaintext.getText();
        try {
            String result = CipherTongHop.encrypt(algorithm, input, txt_key.getText());
            txt_ciphertext.setText(result);
            String saved = HistoryDao.tryInsert(algorithm, "ENCRYPT", input, result);
            lbl_status.setText("Đã mã hóa " + algorithm + ". " + saved);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_decryptActionPerformed() {
        String algorithm = (String) cbo_algorithm.getSelectedItem();
        String input = txt_ciphertext.getText();
        try {
            String result = CipherTongHop.decrypt(algorithm, input, txt_key.getText());
            txt_plaintext.setText(result);
            String saved = HistoryDao.tryInsert(algorithm, "DECRYPT", input, result);
            lbl_status.setText("Đã giải mã " + algorithm + ". " + saved);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_openActionPerformed() {
        String path = FileTextIO.openText(this, txt_ciphertext);
        if (path != null) {
            lbl_status.setText("Đã mở tệp: " + path);
        }
    }

    private void btn_saveActionPerformed() {
        String path = FileTextIO.saveText(this, txt_ciphertext, "ciphertext.txt");
        if (path != null) {
            lbl_status.setText("Đã lưu tệp: " + path);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_TongHop().setVisible(true));
    }
}