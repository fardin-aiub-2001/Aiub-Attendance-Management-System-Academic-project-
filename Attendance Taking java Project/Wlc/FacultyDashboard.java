package Wlc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FacultyDashboard extends JFrame implements ActionListener {

    private JPanel main, headerPanel;
    private JLabel welcomeLabel, profileImage, courseImage, takeAttendanceImage, viewAttendanceImage;
    private JButton profileButton, courseButton, takeAttendanceButton, viewAttendanceButton, logoutButton;
    private int facultyId;

    public FacultyDashboard(int facultyId) {
        this.facultyId = facultyId;

        // Frame setup
        setTitle("Faculty Dashboard");
        setSize(1200, 700);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Header panel setup (Sky Blue)
        headerPanel = new JPanel(null);
        headerPanel.setBounds(0, 0, 1200, 100);
        headerPanel.setBackground(new Color(137, 207, 240));
        add(headerPanel);

        // Welcome label
        welcomeLabel = new JLabel("Welcome, Faculty ID: " + facultyId);
        welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 26));
        welcomeLabel.setBounds(350, 25, 600, 50);
        headerPanel.add(welcomeLabel);

        // Main panel setup (White)
        main = new JPanel(null);
        main.setBounds(0, 100, 1200, 600);
        main.setBackground(Color.WHITE);
        add(main);

        // Define button & image dimensions
        int buttonWidth = 200;
        int buttonHeight = 40;
        int imageSize = 150;
        int spacing = (1200 - (4 * buttonWidth)) / 5;
        int imageYPos = 120;
        int buttonYPos = 300;

        // Set images above corresponding buttons
        String basePath = "." + File.separator + "image" + File.separator;
        profileImage = new JLabel();
        courseImage = new JLabel();
        takeAttendanceImage = new JLabel();
        viewAttendanceImage = new JLabel();

        setImage(profileImage, basePath + "profile.jpg", imageSize, imageSize, spacing, imageYPos);
        setImage(courseImage, basePath + "online-course.png", imageSize, imageSize, spacing * 2 + buttonWidth, imageYPos);
        setImage(takeAttendanceImage, basePath + "checking-attendance.png", imageSize, imageSize, spacing * 3 + (2 * buttonWidth), imageYPos);
        setImage(viewAttendanceImage, basePath + "attendanceview.png", imageSize, imageSize, spacing * 4 + (3 * buttonWidth), imageYPos);

        main.add(profileImage);
        main.add(courseImage);
        main.add(takeAttendanceImage);
        main.add(viewAttendanceImage);

        // Buttons
        profileButton = new JButton("Profile");
        courseButton = new JButton("Course");
        takeAttendanceButton = new JButton("Take Attendance");
        viewAttendanceButton = new JButton("View Attendance");
        logoutButton = new JButton("Logout");

        profileButton.setBounds(spacing, buttonYPos, buttonWidth, buttonHeight);
        courseButton.setBounds(spacing * 2 + buttonWidth, buttonYPos, buttonWidth, buttonHeight);
        takeAttendanceButton.setBounds(spacing * 3 + (2 * buttonWidth), buttonYPos, buttonWidth, buttonHeight);
        viewAttendanceButton.setBounds(spacing * 4 + (3 * buttonWidth), buttonYPos, buttonWidth, buttonHeight);
        logoutButton.setBounds(500, 450, buttonWidth, buttonHeight);

        // Styling buttons
        Color skyBlue = new Color(135, 206, 250);
        styleButton(profileButton, skyBlue);
        styleButton(courseButton, skyBlue);
        styleButton(takeAttendanceButton, skyBlue);
        styleButton(viewAttendanceButton, skyBlue);
        styleButton(logoutButton, Color.RED, Color.WHITE);

        // Add action listeners
        profileButton.addActionListener(this);
        courseButton.addActionListener(this);
        takeAttendanceButton.addActionListener(this);
        viewAttendanceButton.addActionListener(this);
        logoutButton.addActionListener(this);

        // Adding buttons to main panel
        main.add(profileButton);
        main.add(courseButton);
        main.add(takeAttendanceButton);
        main.add(viewAttendanceButton);
        main.add(logoutButton);

        // Show frame
        setVisible(true);
    }

    private void setImage(JLabel label, String path, int width, int height, int x, int y) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(img));
        label.setBounds(x, y, width, height);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == logoutButton) {
            dispose();
            new Login(); // Assuming Login() exists
        } else if (src == profileButton) {
            new ProfileView(facultyId); // Open ProfileView
        } else if (src == courseButton) {
            new Course(facultyId); // Opens Course class
        } else if (src == takeAttendanceButton) {
            new TakeAttendance(facultyId); // Enable Attendance Taking
        } else if (src == viewAttendanceButton) {
            new ViewAttendance(facultyId); // Open ViewAttendance for detailed student records
        }
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
    }
}