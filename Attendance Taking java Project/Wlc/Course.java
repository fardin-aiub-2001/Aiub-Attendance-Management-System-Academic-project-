package Wlc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Course extends JFrame implements ActionListener {
    private JTextField courseNameField, searchField;
    private DefaultListModel<String> studentListModel, selectedStudentListModel;
    private JList<String> studentList, selectedStudentList;
    private JButton addStudentButton, saveButton, backButtonTop, backButtonBottom; // Back Buttons Added
    private JPanel topPanel, middlePanel, bottomPanel;
    private ArrayList<String> allStudents = new ArrayList<>();
    private int facultyId;

    public Course(int facultyId) {
        this.facultyId = facultyId;

        // Frame setup
        setTitle("Course Creation & Student Selection");
        setSize(1200, 700);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Top Panel (Sky Blue) for Course Info
        topPanel = new JPanel(null);
        topPanel.setBounds(0, 0, 1200, 100);
        topPanel.setBackground(new Color(135, 206, 250));
        add(topPanel);

        JLabel lblCourse = new JLabel("Course Name:");
        lblCourse.setFont(new Font("Arial", Font.BOLD, 18));
        lblCourse.setBounds(50, 30, 150, 30);
        topPanel.add(lblCourse);

        courseNameField = new JTextField();
        courseNameField.setBounds(200, 30, 250, 30);
        topPanel.add(courseNameField);

        JLabel lblFaculty = new JLabel("Faculty ID: " + facultyId);
        lblFaculty.setFont(new Font("Arial", Font.BOLD, 18));
        lblFaculty.setBounds(500, 30, 200, 30);
        topPanel.add(lblFaculty);

        JLabel lblSearch = new JLabel("Search Student:");
        lblSearch.setFont(new Font("Arial", Font.BOLD, 18));
        lblSearch.setBounds(800, 30, 150, 30);
        topPanel.add(lblSearch);

        searchField = new JTextField();
        searchField.setBounds(950, 30, 200, 30);
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterStudents();
            }
        });
        topPanel.add(searchField);

        

        // Middle Panel (White) for Student Selection
        middlePanel = new JPanel(null);
        middlePanel.setBounds(0, 100, 1200, 400);
        middlePanel.setBackground(Color.WHITE);
        add(middlePanel);

        JLabel lblStudents = new JLabel("Available Students:");
        lblStudents.setFont(new Font("Arial", Font.BOLD, 18));
        lblStudents.setBounds(50, 20, 200, 30);
        middlePanel.add(lblStudents);

        studentListModel = new DefaultListModel<>();
        studentList = new JList<>(studentListModel);
        studentList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane studentScrollPane = new JScrollPane(studentList);
        studentScrollPane.setBounds(50, 60, 500, 300);
        middlePanel.add(studentScrollPane);

        JLabel lblSelectedStudents = new JLabel("Selected Students:");
        lblSelectedStudents.setFont(new Font("Arial", Font.BOLD, 18));
        lblSelectedStudents.setBounds(650, 20, 200, 30);
        middlePanel.add(lblSelectedStudents);

        selectedStudentListModel = new DefaultListModel<>();
        selectedStudentList = new JList<>(selectedStudentListModel);
        JScrollPane selectedScrollPane = new JScrollPane(selectedStudentList);
        selectedScrollPane.setBounds(650, 60, 500, 300);
        middlePanel.add(selectedScrollPane);

        // Load students from User.txt (Filtering by Uid: 1)
        loadStudents();

        // Bottom Panel (Sky Blue) for Buttons
        bottomPanel = new JPanel(null);
        bottomPanel.setBounds(0, 500, 1200, 200);
        bottomPanel.setBackground(new Color(135, 206, 250));
        add(bottomPanel);

        addStudentButton = new JButton("Add Student");
        addStudentButton.setBounds(250, 60, 180, 40);
        addStudentButton.setFont(new Font("Arial", Font.BOLD, 16));
        addStudentButton.addActionListener(this);
        bottomPanel.add(addStudentButton);

        saveButton = new JButton("Save Course");
        saveButton.setBounds(510, 60, 180, 40);
        saveButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveButton.addActionListener(this);
        bottomPanel.add(saveButton);

        // **Back Button (Bottom)**
        backButtonBottom = new JButton("Back");
        backButtonBottom.setBounds(770, 60, 180, 40);
        backButtonBottom.setFont(new Font("Arial", Font.BOLD, 16));
        backButtonBottom.addActionListener(this);
        bottomPanel.add(backButtonBottom);

        setVisible(true);
    }

    private void loadStudents() {
        File file = new File("File/User.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String studentId = "", studentName = "";
            boolean isStudent = false;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("ID:")) {
                    studentId = line.substring(4).trim();
                } else if (line.startsWith("Name:")) {
                    studentName = line.substring(6).trim();
                } else if (line.startsWith("Uid:") && line.contains("1")) {
                    isStudent = true;
                }

                if (isStudent && studentId.length() > 0 && studentName.length() > 0) {
                    String studentInfo = studentId + " - " + studentName;
                    allStudents.add(studentInfo);
                    studentListModel.addElement(studentInfo);
                    studentId = "";
                    studentName = "";
                    isStudent = false;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error: Unable to load student data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterStudents() {
        String query = searchField.getText().trim().toLowerCase();
        studentListModel.clear();

        for (String student : allStudents) {
            if (student.toLowerCase().contains(query)) {
                studentListModel.addElement(student);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addStudentButton) {
            List<String> selected = studentList.getSelectedValuesList();
            for (String student : selected) {
                if (!selectedStudentListModel.contains(student)) {
                    selectedStudentListModel.addElement(student);
                }
            }
        } else if (e.getSource() == saveButton) {
            saveCourse();
			dispose();
        } else if (e.getSource() == backButtonTop || e.getSource() == backButtonBottom) {
            dispose(); // Close Course frame
            //new FacultyDashboard(facultyId); // Return to Faculty Dashboard
        }
    }

    private void saveCourse() {
    String courseName = courseNameField.getText().trim();
    if (courseName.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Course name cannot be empty!", "Warning", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (selectedStudentListModel.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please select at least one student.", "Warning", JOptionPane.WARNING_MESSAGE);
        return;
    }

    File dir = new File("File");
    if (!dir.exists()) {
        dir.mkdirs(); // Ensure "File" folder exists
    }

    File file = new File(dir, "Course.txt");
    try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
        pw.println("Course Name: " + courseName);
        pw.println("Faculty ID: " + facultyId);
        pw.println("Students:");
        for (int i = 0; i < selectedStudentListModel.size(); i++) {
            pw.println(selectedStudentListModel.getElementAt(i));
        }
        pw.println("--------------------------------");

        JOptionPane.showMessageDialog(this, "Course saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "Error saving course file.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
}