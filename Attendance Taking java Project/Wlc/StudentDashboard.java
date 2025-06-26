package Wlc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentDashboard extends JFrame implements ActionListener {
    private JPanel LeftPanel, RightPanel;
    private JButton ProfileButton, AttendanceButton, LogoutButton;
    private JLabel lblWelcome, profileImage, attendanceImage;
    private int studentId;

    public StudentDashboard(int studentId) {
        this.studentId = studentId;

        // Frame setup
        setTitle("Student Dashboard");
        setSize(1000, 700);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Right panel
        RightPanel = new JPanel();
        RightPanel.setLayout(null);
        RightPanel.setBounds(0, 0, 1000, 100);
        RightPanel.setBackground(new Color(137, 207, 240));
        add(RightPanel);

        // Welcome label
        lblWelcome = new JLabel("Welcome, Student ID: " + studentId);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 24));
        lblWelcome.setBounds(300, 20, 400, 50);
        RightPanel.add(lblWelcome);

        // Left panel
        LeftPanel = new JPanel();
        LeftPanel.setLayout(null);
        LeftPanel.setBounds(0, 100, 1000, 600);
        LeftPanel.setBackground(Color.WHITE);
        add(LeftPanel);

        // Define button dimensions
        int buttonWidth = 250;
        int buttonHeight = 50;
        int imageSize = 150;
        int spacing = (1000 - (2 * buttonWidth)) / 3;
        int imageYPos = 130;
        int buttonYPos = 320;

        // Set images above corresponding buttons
        profileImage = new JLabel();
        attendanceImage = new JLabel();

        setImage(profileImage, "./image/profile.jpg", imageSize, imageSize, spacing, imageYPos);
        setImage(attendanceImage, "./image/checking-attendance.png", imageSize, imageSize, spacing * 2 + buttonWidth, imageYPos);

        LeftPanel.add(profileImage);
        LeftPanel.add(attendanceImage);

        // Buttons
        ProfileButton = new JButton("Profile");
        AttendanceButton = new JButton("View Attendance");
        LogoutButton = new JButton("Logout");

        ProfileButton.setBounds(spacing, buttonYPos, buttonWidth, buttonHeight);
        AttendanceButton.setBounds(spacing * 2 + buttonWidth, buttonYPos, buttonWidth, buttonHeight);
        LogoutButton.setBounds(375, 420, buttonWidth, buttonHeight);

        // Style buttons
        Color skyBlue = new Color(135, 206, 250);
        styleButton(ProfileButton, skyBlue);
        styleButton(AttendanceButton, skyBlue);
        styleButton(LogoutButton, Color.RED, Color.WHITE);

        // Add action listeners
        ProfileButton.addActionListener(this);
        AttendanceButton.addActionListener(this);
        LogoutButton.addActionListener(this);

        // Add buttons to panel
        LeftPanel.add(ProfileButton);
        LeftPanel.add(AttendanceButton);
        LeftPanel.add(LogoutButton);

        setVisible(true);
    }

    private void styleButton(JButton button, Color bgColor) {
        styleButton(button, bgColor, Color.BLACK);
    }

    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
    }

    private void setImage(JLabel label, String imagePath, int width, int height, int x, int y) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(scaledImage));
        label.setBounds(x, y, width, height);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == LogoutButton) {
            dispose();
            new Login();
        } else if (source == ProfileButton) {
            new ProfileView(studentId);
        } else if (source == AttendanceButton) {
            new ViewStudentAttendance(studentId);
        }
    }
}