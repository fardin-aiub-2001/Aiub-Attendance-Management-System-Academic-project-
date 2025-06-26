package Wlc;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Welcome extends JFrame implements ActionListener{
    private Color c1,c2;
	private JLabel background,l2;//text and image
	private JPanel l1;
	private JButton Login,Registration;
		
	public Welcome(){
		
		setTitle("Welcome To Aiub Attendance Manager");
		setSize(1200,700);
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);
		setVisible(true);
		
		//Load Icon
		
		ImageIcon icon=new ImageIcon(".\\image\\American_International_University-Bangladesh_Monogram.svg.png");
		
		setIconImage(icon.getImage());//changing icon
		//background color
		c1=new Color(137,207,240);
		c2=new Color(18, 37, 96);
		l1=new JPanel();
		l1.setBackground(c1);
		l1.setBounds(0,0,1200,700);
		add(l1);
		
		//load image
		ImageIcon image=new ImageIcon(".\\image\\American_International_University-Bangladesh_Monogram.svg.png");		
		Image img=image.getImage();
		Image scaledImg=img.getScaledInstance(200,200, Image.SCALE_SMOOTH);
        ImageIcon scaledBackground=new ImageIcon(scaledImg);
        background=new JLabel(scaledBackground);
        background.setSize(getSize());
		background.setBounds((getWidth()-200)/2,200,200,200);
        l1.add(background);
		
		// Title Label
        l2 = new JLabel("Aiub Attendance Management System", SwingConstants.CENTER);
        l2.setForeground(c2);
        l2.setFont(new Font("Arial", Font.BOLD, 20));
        l2.setBounds((getWidth() - 400) / 2, 100, 400, 100); // Centered on X-axis
        l1.add(l2);
				
		Login=new JButton("Login");
		Login.setForeground(Color.WHITE);
		Login.setFocusPainted(false);
        Login.setOpaque(true);
		Login.setBorder(BorderFactory.createEmptyBorder());
		Login.setBackground(c2);
		Login.setBounds((getWidth() - 260) / 2, 500, 120, 50); // Left side
		
		l1.add(Login);
		
		Registration=new JButton("Registration");
		Registration.setForeground(Color.WHITE);
		Registration.setFocusPainted(false);
        Registration.setOpaque(true);
		Registration.setBorder(BorderFactory.createEmptyBorder());
		Registration.setBackground(c2);
		
        Registration.setBounds((getWidth() + 20) / 2, 500, 120, 50); // Right side
		
		l1.add(Registration);
        
        // Ensure elements remain centered when window resizes
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                background.setBounds((getWidth() - 200) / 2, 100, 200, 200);
                l2.setBounds((getWidth() - 400) / 2, 300, 400, 100);
				Login.setBounds((getWidth() - 260) / 2, 500, 120, 50); // Left side
                Registration.setBounds((getWidth() + 20) / 2, 500, 120, 50); // Right side 
            }
        });

		Registration.addActionListener(this);
		Login.addActionListener(this);
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Login) {
            new Login(); 
			this.dispose();// Opens the login page
        } else if (e.getSource() == Registration) {
            new Registration();
            this.dispose(); 			// Opens the registration page
        }
    }

}