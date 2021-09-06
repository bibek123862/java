import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {
    JButton btn_login;
    JTextField txt_username;
    JPasswordField txt_password;

    // Constructor for login jframe
    Login() {
        this.setTitle("Lottery Application"); // Sets title
        this.setSize(600, 500); // Sets size

        this.setResizable(false); // Makes window unresizable
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel
        JPanel panel = new JPanel();
        // panel.setBackground(new Color(0x121212));

        // Font
        Font font = new Font("MV Boli", Font.BOLD, 45);

        // Labels
        JLabel lbl_login = new JLabel("Login");
        JLabel lbl_username = new JLabel("Username");
        JLabel lbl_password = new JLabel(" Password");

        // Setting font in label
        lbl_login.setFont(font);
        lbl_username.setFont(new Font("Times new roman", Font.PLAIN, 26));
        lbl_password.setFont(new Font("Times new roman", Font.PLAIN, 26));

        // TextField
        txt_username = new JTextField();
        txt_password = new JPasswordField();

        // Setting font in textfield
        txt_username.setFont(new Font("Arial", Font.PLAIN, 24));
        txt_password.setFont(new Font("Arial", Font.PLAIN, 24));

        // Buttons
        btn_login = new JButton("Login");
        btn_login.setFont(new Font("Arial", Font.BOLD, 26));
        btn_login.addActionListener(this);

        // Placing in window
        lbl_login.setBounds(230, 20, 200, 75);
        lbl_username.setBounds(130, 140, 200, 30);
        txt_username.setBounds(250, 140, 200, 35);
        lbl_password.setBounds(125, 200, 200, 30);
        txt_password.setBounds(250, 200, 200, 35);
        btn_login.setBounds(230, 280, 140, 35);

        this.add(panel);
        panel.setLayout(null);

        // Adding components
        panel.add(lbl_login);
        panel.add(lbl_username);
        panel.add(txt_username);
        panel.add(lbl_password);
        panel.add(txt_password);
        panel.add(btn_login);

        this.setVisible(true); // Window gets visible once set to true, else no window appears
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = txt_username.getText();
        String password = new String(txt_password.getPassword());
        login(username, password);
    }

    public boolean login(String username, String password) {
        // On successful login, this function closes login window and opens up the dashboard.
        boolean isValidUser = false;
        ConnectMySQL conn = new ConnectMySQL();
        isValidUser = conn.verifyUser(username, password);
        if (isValidUser) {
            this.dispose();
            Application application = new Application();
        }
        return isValidUser;
    }
}
