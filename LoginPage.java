package loginSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class LoginPage implements ActionListener {
    JFrame frame = new JFrame("Login System");
    ImageIcon logo = new ImageIcon("Replace this with the absolute path of your logo image file");
    JLabel pageLabel = new JLabel("Login Page");
    JLabel userIDLabel = new JLabel("User ID");
    JLabel userPasswordLabel = new JLabel("Password");
    JTextField userIDField = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();
    JButton signupButton = new JButton("Signup");
    JButton loginButton = new JButton("Login");
    JButton resetButton = new JButton("Reset");
    JLabel messageLabel = new JLabel();
    JLabel signupLabel = new JLabel("Don't have an account?");

    LoginPage() {

        pageLabel.setBounds(125, 35, 200, 45);
        pageLabel.setFont(new Font("Times New Roman", Font.BOLD, 36));
        pageLabel.setForeground(new Color(138, 83, 78));

        userIDLabel.setBounds(50, 130, 75, 25);
        userIDLabel.setFont(new Font(null, Font.BOLD, 14));
        userIDLabel.setForeground(new Color(75, 65,86));
        userIDField.setBounds(135, 130, 200, 25);

        userPasswordLabel.setBounds(50, 180, 75, 25);
        userPasswordLabel.setFont(new Font(null, Font.BOLD, 14));
        userPasswordLabel.setForeground(new Color(75, 65,86));
        userPasswordField.setBounds(135, 180, 200, 25);

        loginButton.setBounds(135, 230, 80, 25);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);
        resetButton.setBounds(235, 230, 80, 25);
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);

        messageLabel.setBounds(45, 280, 250, 35);
        messageLabel.setFont(new Font(null, Font.ITALIC, 20));

        signupLabel.setBounds(55, 330, 200, 35);
        signupLabel.setFont(new Font(null, Font.BOLD, 16));
        signupLabel.setForeground(new Color(75, 65,86));

        signupButton.setBounds(255, 335, 80, 25);
        signupButton.setFocusable(false);
        signupButton.addActionListener(this);

        frame.add(pageLabel);
        frame.add(userIDLabel);
        frame.add(userPasswordLabel);
        frame.add(userIDField);
        frame.add(userPasswordField);
        frame.add(loginButton);
        frame.add(resetButton);
        frame.add(messageLabel);
        frame.add(signupLabel);
        frame.add(signupButton);

        frame.setIconImage(logo.getImage());
        frame.getContentPane().setBackground(new Color(185, 235, 219));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            userIDField.setText("");
            userPasswordField.setText("");
            messageLabel.setText("");
        }

        if (e.getSource() == loginButton) {
            String userID = userIDField.getText();
            String password = String.valueOf(userPasswordField.getPassword());

            String url = "jdbc:mysql://localhost:3306/{name of your sql database without curly braces}";
            Connection conn = null;
            String fetchQuery = "select * from {name of your database table without curly braces}";
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(url, "your MySQL user id", "your MySQL password");
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(fetchQuery);
                HashMap<String, String> userdata = new HashMap<String, String>();
                while (rs.next()) {
                    String[] data = {rs.getString(1), rs.getString(2)};
                    userdata.put(data[0], data[1]);
                }
                if (userdata.containsKey(userID)) {
                    if (userdata.get(userID).equals(password)) {
                        messageLabel.setText("Login Successful !");
                        messageLabel.setForeground(new Color(118, 238, 0));
                        frame.dispose();
                        new WelcomePage(userID);
                    } else {
                        messageLabel.setText("Incorrect Password !!");
                        messageLabel.setForeground(new Color(227, 38, 54));
                    }
                } else {
                    messageLabel.setText("Username does not exist !");
                    messageLabel.setForeground(new Color(227, 38, 54));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource() == signupButton) {
            frame.dispose();
            new SignupPage();
        }
    }
}
