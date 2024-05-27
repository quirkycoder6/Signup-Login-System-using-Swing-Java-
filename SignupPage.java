package loginSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;

public class SignupPage implements ActionListener {
    JFrame frame = new JFrame("Login System");
    ImageIcon logo = new ImageIcon("Replace this with the absolute path of your logo image file");
    JLabel pageLabel = new JLabel("Signup Page");
    JLabel userIDLabel = new JLabel("User ID");
    JLabel userPasswordLabel = new JLabel("Password");
    JLabel userConfirmPasswordLabel = new JLabel("Confirm Password");
    JTextField userIDField = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();
    JPasswordField userConfirmPasswordField = new JPasswordField();
    JButton signupButton = new JButton("Signup");
    JButton resetButton = new JButton("Reset");
    JLabel messageLabel1 = new JLabel();
    JLabel messageLabel2 = new JLabel();
    JLabel loginLabel = new JLabel("Already have an account?");
    JButton loginButton = new JButton("Login");

    SignupPage() {

        pageLabel.setBounds(115, 25, 200, 45);
        pageLabel.setFont(new Font("Times New Roman", Font.BOLD, 36));
        pageLabel.setForeground(new Color(138, 83, 78));

        userIDLabel.setBounds(35, 100, 135, 25);
        userIDLabel.setFont(new Font(null, Font.BOLD, 14));
        userIDLabel.setForeground(new Color(75, 65,86));
        userIDField.setBounds(175, 100, 200, 25);

        userPasswordLabel.setBounds(35, 150, 135, 25);
        userPasswordLabel.setFont(new Font(null, Font.BOLD, 14));
        userPasswordLabel.setForeground(new Color(75, 65,86));
        userPasswordField.setBounds(175, 150, 200, 25);

        userConfirmPasswordLabel.setBounds(35, 200, 135, 25);
        userConfirmPasswordLabel.setFont(new Font(null, Font.BOLD, 14));
        userConfirmPasswordLabel.setForeground(new Color(75, 65,86));
        userConfirmPasswordField.setBounds(175, 200, 200, 25);

        signupButton.setBounds(175, 240, 80, 25);
        signupButton.setFocusable(false);
        signupButton.addActionListener(this);
        resetButton.setBounds(275, 240, 80, 25);
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);

        messageLabel1.setBounds(35, 275, 250, 25);
        messageLabel1.setFont(new Font(null, Font.ITALIC, 20));
        messageLabel2.setBounds(35, 305, 350, 25);
        messageLabel2.setFont(new Font(null, Font.ITALIC, 14));

        loginLabel.setBounds(45, 330, 200, 35);
        loginLabel.setFont(new Font(null, Font.BOLD, 16));
        loginLabel.setForeground(new Color(75, 65,86));

        loginButton.setBounds(265, 335, 80, 25);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);

        frame.add(pageLabel);
        frame.add(userIDLabel);
        frame.add(userIDField);
        frame.add(userPasswordLabel);
        frame.add(userPasswordField);
        frame.add(userConfirmPasswordLabel);
        frame.add(userConfirmPasswordField);
        frame.add(signupButton);
        frame.add(resetButton);
        frame.add(messageLabel1);
        frame.add(messageLabel2);
        frame.add(loginLabel);
        frame.add(loginButton);

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
            userConfirmPasswordField.setText("");
            messageLabel1.setText("");
            messageLabel2.setText("");
        }

        if (e.getSource() == signupButton) {
            // validation & data storing logic
            if (String.valueOf(userPasswordField.getPassword()).equals(String.valueOf(userConfirmPasswordField.getPassword()))) {
                String url = "jdbc:mysql://localhost:3306/{name of your sql database without curly braces}";
                Connection conn = null;
                String userid = userIDField.getText();
                String password = String.valueOf(userPasswordField.getPassword());
                String fetchQuery = "select * from {name of your database table without curly braces}";
                String insertQuery = "insert into {name of your database table without curly braces}({name of your userid column in the table}, {name of your password column in the table}) " + "values('" + userid + "', '" + password + "');";
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
                    if (!userdata.containsKey(userid)) {
                        int status = st.executeUpdate(insertQuery);
                        if (status == 1) {
                            messageLabel1.setText("Signup Successful !");
                            messageLabel1.setForeground(new Color(118, 238, 0));
                            messageLabel2.setText("Go to Login Page and log into your account");
                            messageLabel2.setForeground(new Color(118, 238, 0));
                        } else {
                            messageLabel1.setText("Signup Failed !!");
                            messageLabel1.setForeground(new Color(227, 38, 54));
                            messageLabel2.setText("Data Insertion error !");
                            messageLabel2.setForeground(new Color(227, 38, 54));
                        }
                    } else {
                        messageLabel1.setText("Username already exists !!");
                        messageLabel1.setForeground(new Color(227, 38, 54));
                        messageLabel2.setText("Try another username !");
                        messageLabel2.setForeground(new Color(227, 38, 54));
                    }
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            } else {
                messageLabel1.setText("Passwords do not match !!");
                messageLabel1.setForeground(new Color(227, 38, 54));
                messageLabel2.setText("Check your password and try again !");
                messageLabel2.setForeground(new Color(227, 38, 54));
            }
        }

        if (e.getSource() == loginButton) {
            frame.dispose();
            new LoginPage();
        }
    }
}