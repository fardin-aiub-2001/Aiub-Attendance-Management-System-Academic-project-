package Wlc;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ProfileView extends JFrame {
    private JLabel lblName, lblID, lblPhone, lblRole;
    private int id;

    public ProfileView(int id) {
        this.id = id;

        // Frame setup
        this.setTitle("Profile View");
        this.setSize(400, 300);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Fetch user details
        Map<String, String> userData = fetchUserData(id);
        String role = userData.get("Uid").equals("1") ? "Student" : "Faculty"; // Determine role

        // Labels for profile information
        lblName = new JLabel("Name: " + userData.getOrDefault("Name", "N/A"));
        lblID = new JLabel("ID: " + userData.getOrDefault("ID", "N/A"));
        lblPhone = new JLabel("Phone: " + userData.getOrDefault("Phone", "N/A"));
        lblRole = new JLabel("Role: " + role); // Show whether Student or Faculty

        lblName.setBounds(50, 50, 300, 30);
        lblID.setBounds(50, 100, 300, 30);
        lblPhone.setBounds(50, 150, 300, 30);
        lblRole.setBounds(50, 200, 300, 30);

        this.add(lblName);
        this.add(lblID);
        this.add(lblPhone);
        this.add(lblRole);

        this.setVisible(true);
    }

    private Map<String, String> fetchUserData(int id) {
        Map<String, String> userData = new HashMap<>();
        File file = new File("File/User.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("ID:") && line.contains(String.valueOf(id))) {
                    found = true;
                }
                if (found && line.contains(":")) {
                    String[] parts = line.split(": ", 2);
                    if (parts.length == 2) {
                        userData.put(parts[0].trim(), parts[1].trim());
                    }
                }
                if (found && line.startsWith("-")) break;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error: Unable to load profile data!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return userData;
    }
}