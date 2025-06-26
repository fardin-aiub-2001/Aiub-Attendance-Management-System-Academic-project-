package Wlc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TakeAttendance extends JFrame implements ActionListener {
    private int facultyId;
    private JComboBox<String> courseDropdown;
    private JPanel middlePanel;
    private JButton doneButton;
    private List<JCheckBox> studentCheckboxes = new ArrayList<>();
    private String selectedCourse = "";

    public TakeAttendance(int facultyId) {
        this.facultyId = facultyId;

        System.out.println("TakeAttendance frame is being created!"); // Debugging statement

        // Frame setup
        setTitle("Take Attendance");
        setSize(1000, 700); // **Reduced width**
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Top Panel for Course Selection
        JPanel topPanel = new JPanel(null);
        topPanel.setBounds(0, 0, 1000, 100); // **Adjusted width**
        topPanel.setBackground(new Color(135, 206, 250));
        add(topPanel);

        JLabel lblCourse = new JLabel("Select Course:");
        lblCourse.setFont(new Font("Arial", Font.BOLD, 18));
        lblCourse.setBounds(50, 30, 180, 30);
        topPanel.add(lblCourse);

        courseDropdown = new JComboBox<>();
        courseDropdown.setBounds(230, 30, 250, 30); // **Reduced width slightly**
        courseDropdown.addActionListener(e -> loadStudents());
        topPanel.add(courseDropdown);

        // Middle Panel for Student List
        middlePanel = new JPanel();
        middlePanel.setBounds(0, 100, 1000, 400); // **Adjusted width**
        middlePanel.setBackground(Color.WHITE);
        middlePanel.setLayout(new GridLayout(0, 1)); // Vertical Layout
        add(middlePanel);

        // Bottom Panel for Save Button
        JPanel bottomPanel = new JPanel(null);
        bottomPanel.setBounds(0, 500, 1000, 200); // **Adjusted width**
        bottomPanel.setBackground(new Color(135, 206, 250));
        add(bottomPanel);

        doneButton = new JButton("Save Attendance");
        doneButton.setBounds(400, 80, 200, 50); // **Centered for new width**
        doneButton.addActionListener(this);
        bottomPanel.add(doneButton);

        // Load courses after components are initialized
        loadCourses();

        setVisible(true); // Ensure frame visibility
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

    // Load students assigned to the selected course
    private void loadStudents() {
        middlePanel.removeAll();
        studentCheckboxes.clear();

        File file = new File("File/Course.txt");
        selectedCourse = (String) courseDropdown.getSelectedItem();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isCurrentCourse = false;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("Course Name:") && line.contains(selectedCourse)) {
                    isCurrentCourse = true;
                } else if (line.startsWith("--------------------------------")) {
                    isCurrentCourse = false;
                }

                if (isCurrentCourse && line.matches("\\d+ - .*")) { // Match student format "ID - Name"
                    JCheckBox studentCheckBox = new JCheckBox(line);
                    studentCheckboxes.add(studentCheckBox);
                    middlePanel.add(studentCheckBox);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading students!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        middlePanel.revalidate();
        middlePanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == doneButton) {
            saveAttendance();
        }
    }

    // Save attendance in Course.txt with "P" for present and "A" for absent students
    private void saveAttendance() {
        File file = new File("File/Course.txt");
        List<String> updatedLines = new ArrayList<>();

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
                    for (JCheckBox checkbox : studentCheckboxes) {
                        if (checkbox.getText().equals(line)) {
                            if (checkbox.isSelected()) {
                                line += " - P"; // Mark as Present
                            } else {
                                line += " - A"; // Mark as Absent
                            }
                        }
                    }
                }

                updatedLines.add(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading course file!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Write back updated attendance data
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            for (String updatedLine : updatedLines) {
                pw.println(updatedLine);
            }

            JOptionPane.showMessageDialog(this, "Attendance saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving attendance!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}