package Wlc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class ViewAttendance extends JFrame implements ActionListener {
    private int facultyId;
    private JComboBox<String> courseDropdown;
    private JPanel middlePanel;
    private JButton backButton;
    private String selectedCourse = "";

    public ViewAttendance(int facultyId) {
        this.facultyId = facultyId;

        // Frame setup
        setTitle("View Student Attendance");
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

        // **Middle Panel for Attendance Summary (Move this above loadCourses())**
        middlePanel = new JPanel();
        middlePanel.setBounds(0, 100, 1000, 500);
        middlePanel.setBackground(Color.WHITE);
        middlePanel.setLayout(new BorderLayout()); 
        add(middlePanel);

        // Bottom Panel for Back Button
        JPanel bottomPanel = new JPanel(null);
        bottomPanel.setBounds(0, 600, 1000, 100);
        bottomPanel.setBackground(new Color(135, 206, 250));
        add(bottomPanel);

        backButton = new JButton("Back");
        backButton.setBounds(400, 30, 200, 40);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.addActionListener(this);
        bottomPanel.add(backButton);

        // **Ensure middlePanel is initialized before calling loadCourses()**
        loadCourses();

        setVisible(true);
    }

    // Load courses assigned to this faculty ID
    private void loadCourses() {
        File file = new File("File/Course.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFacultyCourse = false;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("Course Name:")) {
                    selectedCourse = line.substring(13).trim();
                } else if (line.startsWith("Faculty ID:") && line.contains(String.valueOf(facultyId))) {
                    isFacultyCourse = true;
                }

                if (isFacultyCourse) {
                    courseDropdown.addItem(selectedCourse);
                    isFacultyCourse = false;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading courses!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Load attendance summary for selected course
    private void loadAttendanceSummary() {
        middlePanel.removeAll();

        File file = new File("File/Course.txt");
        selectedCourse = (String) courseDropdown.getSelectedItem();
        Map<String, Integer> studentPresentCount = new HashMap<>();
        Map<String, Integer> studentTotalClasses = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isCurrentCourse = false;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("Course Name:") && line.contains(selectedCourse)) {
                    isCurrentCourse = true;
                } else if (line.startsWith("--------------------------------")) {
                    isCurrentCourse = false;
                }

                if (isCurrentCourse && line.matches("\\d+ - .*")) {
                    String[] parts = line.split(" - ");
                    String studentName = parts[1];
                    studentTotalClasses.put(studentName, studentTotalClasses.getOrDefault(studentName, 0) + 1);
                    if (line.endsWith("P")) {
                        studentPresentCount.put(studentName, studentPresentCount.getOrDefault(studentName, 0) + 1);
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading attendance data!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Creating table format for student attendance summary
        String[] columnNames = {"Student Name", "Total Classes", "Present (%)", "Marks (Out of 10)"};
        Object[][] tableData = new Object[studentTotalClasses.size()][4];

        int row = 0;
        for (String studentName : studentTotalClasses.keySet()) {
            int totalClasses = studentTotalClasses.get(studentName);
            int presentCount = studentPresentCount.getOrDefault(studentName, 0);
            double percentage = totalClasses > 0 ? ((double) presentCount / totalClasses) * 100 : 0;
            int marksOutOf10 = (int) Math.round((percentage / 100) * 10);

            tableData[row][0] = studentName;
            tableData[row][1] = totalClasses;
            tableData[row][2] = String.format("%.2f", percentage) + "%";
            tableData[row][3] = marksOutOf10;
            row++;
        }

        JTable attendanceTable = new JTable(tableData, columnNames);
        attendanceTable.setFont(new Font("Arial", Font.PLAIN, 14));
        attendanceTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(attendanceTable);
        middlePanel.add(scrollPane, BorderLayout.CENTER);

        middlePanel.revalidate();
        middlePanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            dispose();
            //new FacultyDashboard(facultyId);
        }
    }
}