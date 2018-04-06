import java.awt.EventQueue;
import java.sql.*;
import javax.swing.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Image;
public class DriverWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DriverWindow window = new DriverWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection connection = null;
	/**
	 * Create the application.
	 */
	public DriverWindow() {
		initialize();
		connection = ConnectionMySQL.dbConnector();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 11));
		frame.getContentPane().setBackground(new Color(0, 204, 204));
		frame.getContentPane().setForeground(Color.BLACK);
		frame.setBounds(0, 0, 1400, 700);////////////////
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel Lookingforgroup = new JLabel("  <<Looking For A Group In Real Life>>");
		Lookingforgroup.setHorizontalAlignment(SwingConstants.CENTER);
		Lookingforgroup.setBounds(394, 224, 564, 60);
		Lookingforgroup.setForeground(new Color(245, 245, 245));
		Lookingforgroup.setFont(new Font("Courier New", Font.BOLD, 22));
		frame.getContentPane().add(Lookingforgroup);
		
		JLabel lblNewLabel_2 = new JLabel("W E L C O M E");
		lblNewLabel_2.setBounds(418, 63, 540, 60);
		lblNewLabel_2.setBackground(new Color(102, 204, 255));
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Imprint MT Shadow", Font.BOLD, 60));
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("LFG-IRL");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(499, 122, 303, 60);
		lblNewLabel_3.setForeground(Color.ORANGE);
		lblNewLabel_3.setFont(new Font("AR DESTINE", Font.BOLD | Font.ITALIC, 50));
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel = new JLabel("First Time Here? ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(512, 359, 289, 60);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 12));
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnSignUp = new JButton("Sign Up");
		///btnSignUp.setBackground(Color.GRAY);
		btnSignUp.setBounds(421, 318, 456, 60);
		btnSignUp.setForeground(Color.GRAY);
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//frame.dispose();
					UsersAgreement frame = new UsersAgreement(connection);
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}		
				
				
			}
		});
		btnSignUp.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 22));
		frame.getContentPane().add(btnSignUp);
		
		JLabel lblNewLabel_1 = new JLabel("Looking For A Group?");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(486, 468, 337, 60);
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 12));
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(421, 419, 456, 60);
		btnLogin.setForeground(Color.GRAY);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//frame.dispose();
					LoginMember frame = new LoginMember(connection);
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}			
				
				
			}
		});
		btnLogin.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 22));
		frame.getContentPane().add(btnLogin);
		
	}
}
