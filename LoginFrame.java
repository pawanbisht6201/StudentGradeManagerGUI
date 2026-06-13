import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;

    public LoginFrame() {

        setTitle("Login - Student Grade Tracker");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel userLabel = new JLabel("Username");
        JLabel passLabel = new JLabel("Password");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginBtn = new JButton("Login");

        panel.add(userLabel);
        panel.add(usernameField);

        panel.add(passLabel);
        panel.add(passwordField);

        panel.add(new JLabel(""));
        panel.add(loginBtn);

        add(panel);

        loginBtn.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {

        String username = usernameField.getText();
        String password =
                String.valueOf(passwordField.getPassword());

        if (username.equals("pawan")
                && password.equals("123456")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login Successful"
            );

            dispose();

            new GradeTrackerGUI();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Username or Password"
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                LoginFrame::new
        );
    }
}
