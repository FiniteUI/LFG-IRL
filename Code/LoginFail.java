import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginFail extends JFrame {

	private JPanel contentPane;
	private JTextField userEmail;
	private JTextField userSecretAnswer;

	public LoginFail(Connection connection) {
		//connection = ConnectionMySQL.dbConnector();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(388, 210, 550, 400);
		//setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 204, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setBounds(10, 11, 46, 14);
		contentPane.add(label);
		
		JTextPane txtpnLoginFailed = new JTextPane();
		txtpnLoginFailed.setForeground(new Color(255, 255, 255));
		txtpnLoginFailed.setBackground(new Color(0, 204, 204));
		txtpnLoginFailed.setFont(new Font("Calibri", Font.BOLD, 16));
		txtpnLoginFailed.setBounds(58, 11, 414, 116);
		txtpnLoginFailed.setText("LOGIN FAILED \r\n\r\nPlease enter the email associated with your account and provide the secret answer that was used to create the account to reset your password.");
		contentPane.add(txtpnLoginFailed);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(new Color(255, 255, 255));
		lblEmail.setFont(new Font("Verdana", Font.BOLD, 12));
		lblEmail.setBounds(62, 138, 76, 19);
		contentPane.add(lblEmail);
		
		userEmail = new JTextField();
		userEmail.setBounds(208, 138, 179, 20);
		contentPane.add(userEmail);
		userEmail.setColumns(10);
		
		JLabel lblSecretAnswer = new JLabel("Secret Answer:");
		lblSecretAnswer.setForeground(Color.WHITE);
		lblSecretAnswer.setFont(new Font("Verdana", Font.BOLD, 12));
		lblSecretAnswer.setBounds(58, 181, 128, 14);
		contentPane.add(lblSecretAnswer);
		
		userSecretAnswer = new JTextField();
		userSecretAnswer.setBounds(208, 179, 179, 20);
		userSecretAnswer.setColumns(10);
		contentPane.add(userSecretAnswer);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//JOptionPane.showMessageDialog(null, "Testing the button and message display");
				
				//set up queries to pull email and secret answer
				//authentication check
				try {
					String query = "select * from Accounts where email = ? and secretanswer = ?";
					PreparedStatement PSTM = connection.prepareStatement(query);
					PSTM.setString(1, userEmail.getText());
					PSTM.setString(2, userSecretAnswer.getText());
					ResultSet RS = PSTM.executeQuery();
					int count = 0;
					while( RS.next()) {
						count = count + 1;
					}
					
					//if user entry valid - show message and redirect
					if (count == 1) {
						JOptionPane.showMessageDialog(null, "You can reset your password through your account page - redirecting");
						UpdateAccount frm = new UpdateAccount(connection);
						frm.setVisible(true);
					}
					
					//if user entry invalid - show message
					else {
						JOptionPane.showMessageDialog(null, "The email and secret answer combination you submitted is not valid");
					}
					
					RS.close();
					PSTM.close();
					//connection.close(); /////////////////////
					
				} catch( Exception e ) {
					JOptionPane.showMessageDialog(null, e);
				}				 
			}
		});
		btnSubmit.setBounds(335, 227, 89, 23);
		contentPane.add(btnSubmit);
		
		JButton btnDontRemember = new JButton("I don't remember");
		btnDontRemember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Message to user informing of redirection
				JOptionPane.showMessageDialog(null, "Redirecting to Account Creation");
				
				//Kick to the sign up frame
				RegistrationForm frm = new RegistrationForm(connection);
				frm.setVisible(true);
			}
		});
		btnDontRemember.setBounds(58, 227, 128, 23);
		contentPane.add(btnDontRemember);		
	}
}