package Wlc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Registration extends JFrame implements ActionListener {

    private JPanel main, main1;
    private JLabel background, l1, l2, l3, l4, l5, l6, l7;
    private JTextField f3, f4, f5;
    private JPasswordField f6, f7;
    private JButton b1;
    private JRadioButton r1, r2;

    public Registration() {
        this.setTitle("Registration");
        this.setSize(1200, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);
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

        l2 = new JLabel("Sign up with a unique id and password");
        l2.setFont(new Font("Times New Roman", Font.BOLD, 20));
        l2.setBounds(250, 370, 800, 100);
        main.add(l2);

        main1 = new JPanel();
        main1.setLayout(null);
        main1.setBounds(800, 0, 400, 700);
        main1.setBackground(Color.WHITE);
        this.add(main1);

        l3 = new JLabel("Full Name");
        l3.setFont(new Font("Times New Roman", Font.BOLD, 18));
        l3.setBounds(10, 20, 360, 30);
        main1.add(l3);

        f3 = new JTextField();
        f3.setBounds(10, 70, 360, 30);
        main1.add(f3);

        l4 = new JLabel("Student Id");
        l4.setFont(new Font("Times New Roman", Font.BOLD, 18));
        l4.setBounds(10, 120, 360, 30);
        main1.add(l4);

        f4 = new JTextField();
        f4.setBounds(10, 170, 360, 30);
        main1.add(f4);

        l5 = new JLabel("Phone Number");
        l5.setFont(new Font("Times New Roman", Font.BOLD, 18));
        l5.setBounds(10, 220, 360, 30);
        main1.add(l5);

        f5 = new JTextField();
        f5.setBounds(10, 270, 360, 30);
        main1.add(f5);

        l6 = new JLabel("Password");
        l6.setFont(new Font("Times New Roman", Font.BOLD, 18));
        l6.setBounds(10, 320, 360, 30);
        main1.add(l6);

        f6 = new JPasswordField();
        f6.setBounds(10, 370, 360, 30);
        main1.add(f6);

        l7 = new JLabel("Re-enter Your Password");
        l7.setFont(new Font("Times New Roman", Font.BOLD, 18));
        l7.setBounds(10, 420, 360, 30);
        main1.add(l7);

        f7 = new JPasswordField();
        f7.setBounds(10, 470, 360, 30);
        main1.add(f7);

        r1 = new JRadioButton("Student");
        r2 = new JRadioButton("Faculty");

        r1.setBounds(80, 520, 100, 30);
        r2.setBounds(220, 520, 100, 30);

        ButtonGroup group = new ButtonGroup();
        group.add(r1);
        group.add(r2);

        main1.add(r1);
        main1.add(r2);

        b1 = new JButton("Sign_Up");
        b1.setBounds(140, 580, 120, 40);
        b1.setBackground(new Color(0, 0, 128));
        b1.setForeground(Color.WHITE);
        b1.setBorderPainted(false);
        b1.setFocusPainted(false);
        main1.add(b1);

        b1.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            String name = f3.getText().trim();
            String idText = f4.getText().trim();
            String phone = f5.getText().trim();
            String pass = new String(f6.getPassword()).trim();
            String confirmPass = new String(f7.getPassword()).trim();

            if (!name.matches("[a-zA-Z ]+")) {
                JOptionPane.showMessageDialog(this, "Error: Name must contain only letters!");
                return;
            }

            int id;
            try {
                id = Integer.parseInt(idText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Student ID must be a number!");
                return;
            }

            if (!phone.matches("\\d{11}")) {
                JOptionPane.showMessageDialog(this, "Error: Phone number must be exactly 11 digits!");
                return;
            }

            if (!pass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(this, "Error: Passwords do not match!");
                return;
            }

            int userType = r1.isSelected() ? 1 : r2.isSelected() ? 2 : 0;
            if (userType == 0) {
                JOptionPane.showMessageDialog(this, "Error: Please select Student or Faculty!");
                return;
            }

            File file = new File("File/User.txt");
            try {
                if (!file.exists()) file.createNewFile();
                try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
                    pw.println("ID: " + id);
                    pw.println("Password: " + pass);
                    pw.println("Uid: " + userType);
                    pw.println("Name: " + name);
                    pw.println("Phone: " + phone);
                    pw.println("--------------------------------");
                }

                JOptionPane.showMessageDialog(this, "Registration Successful!");
                this.dispose();
                new Login();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error: Unable to save registration data!");
                ex.printStackTrace();
            }
        }
    }
}
