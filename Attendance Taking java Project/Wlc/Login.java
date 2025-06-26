package Wlc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Login extends JFrame implements ActionListener {

    private JPanel main, main1;
    private JLabel background, l1, l2, l3, l4, l5;
    private JTextField f3;
    private JPasswordField f4;
    private JButton b1, b2;
    private static int loggedInUserId; // Store logged-in user's ID

    public Login() {
        this.setTitle("Login");
        this.setSize(1200, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        main = new JPanel();
        main.setLayout(null);
        main.setBounds(0, 0, 800, 700);
        main.setBackground(new Color(137, 207, 240));
        this.add(main);

        ImageIcon image = new ImageIcon(".\\image\\American_International_University-Bangladesh_Monogram.svg.png");
        Image img = image.getImage();
        Image scaledImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledBackground = new ImageIcon(scaledImg);
        background = new JLabel(scaledBackground);
        background.setBounds(300, 100, 200, 200);
        main.add(background);

        l1 = new JLabel("American International University-Bangladesh");
        l1.setFont(new Font("Times New Roman", Font.BOLD, 26));
        l1.setBounds(125, 300, 800, 100);
        main.add(l1);

        l2 = new JLabel("Login with your student ID and password");
        l2.setFont(new Font("Times New Roman", Font.BOLD, 20));
        l2.setBounds(230, 370, 800, 100);
        main.add(l2);

        main1 = new JPanel();
        main1.setLayout(null);
        main1.setBounds(800, 0, 400, 700);
        main1.setBackground(Color.WHITE);
        this.add(main1);

        l3 = new JLabel("User ID");
        l3.setFont(new Font("Times New Roman", Font.BOLD, 18));
        l3.setBounds(10, 100, 360, 30);
        main1.add(l3);

        f3 = new JTextField();
        f3.setBounds(10, 150, 360, 30);
        main1.add(f3);

        l4 = new JLabel("Password");
        l4.setFont(new Font("Times New Roman", Font.BOLD, 18));
        l4.setBounds(10, 220, 360, 30);
        main1.add(l4);

        f4 = new JPasswordField();
        f4.setBounds(10, 270, 360, 30);
        main1.add(f4);

        b1 = new JButton("Login");
        b1.setBounds(140, 340, 120, 40);
        b1.setBackground(new Color(0, 0, 128));
        b1.setForeground(Color.WHITE);
        b1.setBorderPainted(false);
        b1.setFocusPainted(false);
        main1.add(b1);

        l5 = new JLabel("Don't have an account?");
        l5.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        l5.setBounds(120, 400, 160, 30);
        main1.add(l5);

        b2 = new JButton("Register");
        b2.setBounds(140, 440, 120, 30);
        b2.setBackground(Color.GRAY);
        b2.setForeground(Color.WHITE);
        b2.setBorderPainted(false);
        b2.setFocusPainted(false);
        main1.add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            String enteredId = f3.getText().trim();
            String enteredPassword = new String(f4.getPassword()).trim();

            try {
                File file = new File("File/User.txt");
                if (!file.exists()) {
                    JOptionPane.showMessageDialog(this, "User data file not found!");
                    return;
                }

                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                boolean authenticated = false;

                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("ID:")) {
                        String id = line.split(":")[1].trim();
                        String password = reader.readLine().split(":")[1].trim();
                        String uid = reader.readLine().split(":")[1].trim();
                        reader.readLine(); // skip name
                        reader.readLine(); // skip phone
                        reader.readLine(); // skip separator (----)

                        if (enteredId.equals(id) && enteredPassword.equals(password)) {
                            authenticated = true;
                            loggedInUserId = Integer.parseInt(id); // Store ID globally

                            int key = Integer.parseInt(uid);
                            JOptionPane.showMessageDialog(this, "Login Successful!");
                            this.dispose();

                            if (key == 1) {
                                new StudentDashboard(loggedInUserId); // Pass ID to student dashboard
                            } else if (key == 2) {
                                new FacultyDashboard(loggedInUserId); // Pass ID to faculty dashboard
                            } else {
                                JOptionPane.showMessageDialog(this, "Unknown user type!");
                            }
                            break;
                        }
                    }
                }

                reader.close();

                if (!authenticated) {
                    JOptionPane.showMessageDialog(this, "Invalid ID or Password!");
                }

            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred while reading the file!");
            }
        } else if (e.getSource() == b2) {
            this.dispose();
            new Registration();
        }
    }
}