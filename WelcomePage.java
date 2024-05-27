package loginSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage implements ActionListener {
    JFrame frame = new JFrame("Login System");
    ImageIcon logo = new ImageIcon("Replace this with the absolute path of your logo image file");
    JLabel welcomeLabel = new JLabel();
    JButton logoutButton = new JButton("Logout");

    WelcomePage(String uid) {
        welcomeLabel.setBounds(10, 10, 200, 35);
        welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        welcomeLabel.setForeground(new Color(68, 76, 56));
        welcomeLabel.setText("Hello " + uid + " !");

        logoutButton.setBounds(300, 325, 80, 25);
        logoutButton.setFocusable(false);
        logoutButton.addActionListener(this);

        frame.add(welcomeLabel);
        frame.add(logoutButton);

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
        if (e.getSource() == logoutButton) {
            frame.dispose();
            new LoginPage();
        }
    }
}
