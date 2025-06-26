package Wlc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class ViewStudentAttendance extends JFrame implements ActionListener {
    private int studentId;
    private JComboBox<String> courseDropdown;
    private JPanel middlePanel;
    private JButton backButton;
    private String selectedCourse = "";

    public ViewStudentAttendance(int studentId) {
        this.studentId = studentId;

        // Frame setup
        setTitle("View Attendance Summary");
        setSize(1000, 700);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Top Panel for Course Selection
        JPanel topPanel = new JPanel(null);
        topPanel.setBounds(0, 0, 1000, 100);
        topPanel.setBackground(new Color(135, 206, 250));
        add(topPanel);

        JLabel lblCourse = new JLabel("Select Course:");
        lblCourse.setFont(new Font("Arial", Font.BOLD, 18));
        lblCourse.setBounds(50, 30, 200, 30);
        topPanel.add(lblCourse);

        courseDropdown = new JComboBox<>();
        courseDropdown.setBounds(250, 30, 300, 30);
        courseDropdown.addActionListener(e -> loadAttendanceSummary());
        topPanel.add(courseDropdown);

        middlePanel = new JPanel();
        middlePanel.setBounds(0, 100, 1000, 500);
        middlePanel.setBackground(Color.WHITE);
        middlePanel.setLayout(new BorderLayout());
        add(middlePanel);

        JPanel bottomPanel = new JPanel(null);
        bottomPanel.setBounds(0, 600, 1000, 100);
        bottomPanel.setBackground(new Color(135, 206, 250));
        add(bottomPanel);

        backButton = new JButton("Back");
        backButton.setBounds(400, 30, 200, 40);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.addActionListener(this);
        bottomPanel.add(backButton);

        loadCourses();

        setVisible(true);
    }

    // **Updated method: Only load courses where student is assigned**
    private void loadCourses() {
        File file = new File("File/Course.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String currentCourse = "";
            boolean isStudentAssigned = false;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("Course Name:")) {
                    currentCourse = line.substring(13).trim();  // Extract course name
                    isStudentAssigned = false;  // Reset flag for new course
                } else if (line.matches(studentId + " - .*")) { // Match student ID presence
                    isStudentAssigned = true;  // Found student inside this course
                } else if (line.startsWith("--------------------------------")) { // End of course block
                    if (isStudentAssigned && !currentCourse.isEmpty()) {
                        courseDropdown.addItem(currentCourse); // Add relevant courses
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading student-assigned courses!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAttendanceSummary() {
        middlePanel.removeAll();

        File file = new File("File/Course.txt");
        selectedCourse = (String) courseDropdown.getSelectedItem();
        int presentCount = 0, totalClasses = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isCurrentCourse = false;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("Course Name:") && line.contains(selectedCourse)) {
                    isCurrentCourse = true;
                } else if (line.startsWith("--------------------------------")) {
                    isCurrentCourse = false;
                }

                if (isCurrentCourse && line.matches(studentId + " - .*")) {
                    totalClasses++;
                    if (line.endsWith("P")) {
                        presentCount++;
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading attendance data!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        double percentage = totalClasses > 0 ? ((double) presentCount / totalClasses) * 100 : 0;
        int marksOutOf10 = (int) Math.round((percentage / 100) * 10);

        JLabel lblAttendance = new JLabel("Attendance Percentage: " + String.format("%.2f", percentage) + "%", SwingConstants.CENTER);
        lblAttendance.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel lblMarks = new JLabel("Marks (Out of 10): " + marksOutOf10, SwingConstants.CENTER);
        lblMarks.setFont(new Font("Arial", Font.BOLD, 18));

        middlePanel.add(lblAttendance);
        middlePanel.add(lblMarks);

        middlePanel.revalidate();
        middlePanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            dispose();
            new StudentDashboard(studentId);
        }
    }
}