import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Enumeration;
import java.awt.event.ActionEvent;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import java.awt.Color;
import java.awt.Font;
import javax.swing.UIManager;

public class UsersAgreement extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	public UsersAgreement(Connection connection) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 250, 550, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 204, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsersAgreement = new JLabel("Users Agreement");
		lblUsersAgreement.setForeground(Color.WHITE);
		lblUsersAgreement.setFont(new Font("Verdana", Font.BOLD, 16));
		lblUsersAgreement.setBounds(10, 11, 179, 23);
		contentPane.add(lblUsersAgreement);
		
		JTextPane txtpnFdg = new JTextPane();
		txtpnFdg.setForeground(new Color(255, 255, 255));
		txtpnFdg.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtpnFdg.setBackground(new Color(0, 204, 204));
		txtpnFdg.setText("By clicking the accept button below I confirm that I am aged 18 years or older.  I acknowledge that if I choose to meet up in person with other users of this application that I am doing so at my own risk and that my safety is solely my responsibility.  I absolve LFG-IRL and all related parties of all fault if I suffer bodily or emotional harm while engaging in activities organized through the LFG-IRL application");
		txtpnFdg.setBounds(10, 59, 414, 166);
		contentPane.add(txtpnFdg);
		txtpnFdg.setEditable(false);
		JRadioButton rdbtnAccept = new JRadioButton("Accept");
		rdbtnAccept.setBackground(new Color(0, 204, 204));
		rdbtnAccept.setFont(new Font("Tahoma", Font.BOLD, 12));
		buttonGroup.add(rdbtnAccept);
		rdbtnAccept.setBounds(10, 272, 109, 23);
		contentPane.add(rdbtnAccept);
		rdbtnAccept.setVisible(false);
		
		JButton btnSubmit = new JButton("Accept");
		btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Enumeration<AbstractButton>bg=buttonGroup.getElements();
				while(bg.hasMoreElements()) {
					JRadioButton jrd=(JRadioButton)bg.nextElement();
					/*
					if(jrd.isSelected())
						JOptionPane.showMessageDialog(null, jrd.getText());
					*/
				}
			
				
				
				try {
					dispose();
					RegistrationForm regForm = new RegistrationForm(connection);
					regForm.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}		
				
				
		
				
			}
		});
		btnSubmit.setBounds(335, 272, 89, 23);
		contentPane.add(btnSubmit);
		
		JLabel label = new JLabel("");
		label.setBounds(303, 211, 46, 14);
		contentPane.add(label);
		
		JButton btnExit = new JButton("Decline");
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(140, 272, 103, 23);
		contentPane.add(btnExit);
	}
}