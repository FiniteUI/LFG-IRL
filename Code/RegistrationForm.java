import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;

public class RegistrationForm extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldFname;
	private JTextField textFieldLname;
	private JTextField textFieldZip;
	private JTextField textFieldUserName;
	private JPasswordField passwordField;
	private JTextField textFieldAge;
	private JTextField textFieldQuestion;
	private JTextField textFieldAnswer;
	private JPasswordField passwordField_1;
	private JTextField textFieldSex;
	private JTextField textFieldMail;
	
	public RegistrationForm(Connection connection) {
		//connection = ConnectionMySQL.dbConnector();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(388, 210, 550, 480);
		//setBounds(150, 150, 550, 480);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 204, 204));
		contentPane.setForeground(Color.RED);
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSignupFellas = new JLabel("SignUp ");
		lblSignupFellas.setForeground(new Color(255, 215, 0));
		lblSignupFellas.setFont(new Font("Verdana", Font.BOLD, 23));
		lblSignupFellas.setBounds(217, -12, 135, 47);
		contentPane.add(lblSignupFellas);
		
		JLabel lblFname = new JLabel("First Name");
		lblFname.setForeground(new Color(255, 255, 255));
		lblFname.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFname.setBounds(10, 44, 83, 14);
		contentPane.add(lblFname);
		
		JLabel lblLname = new JLabel("Last Name");
		lblLname.setForeground(new Color(255, 255, 255));
		lblLname.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLname.setBounds(255, 44, 88, 14);
		contentPane.add(lblLname);
		
		textFieldFname = new JTextField();
		textFieldFname.setBounds(103, 42, 129, 20);
		contentPane.add(textFieldFname);
		textFieldFname.setColumns(10);
		
		textFieldLname = new JTextField();
		textFieldLname.setBounds(329, 42, 142, 20);
		contentPane.add(textFieldLname);
		textFieldLname.setColumns(10);
		
		JLabel lblZipCode = new JLabel("Zip Code");
		lblZipCode.setForeground(new Color(255, 255, 255));
		lblZipCode.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblZipCode.setBounds(10, 105, 59, 14);
		contentPane.add(lblZipCode);
		
		textFieldZip = new JTextField();
		textFieldZip.setBounds(103, 103, 129, 20);
		contentPane.add(textFieldZip);
		textFieldZip.setColumns(10);
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setForeground(new Color(255, 255, 255));
		lblUserName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUserName.setBounds(135, 172, 73, 14);
		contentPane.add(lblUserName);
		
		textFieldUserName = new JTextField();
		textFieldUserName.setBounds(232, 169, 101, 20);
		contentPane.add(textFieldUserName);
		textFieldUserName.setColumns(10);
		
		JLabel lblSex = new JLabel("Sex");
		lblSex.setForeground(new Color(255, 255, 255));
		lblSex.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSex.setBounds(255, 69, 46, 20);
		contentPane.add(lblSex);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassword.setBounds(135, 207, 59, 14);
		contentPane.add(lblPassword);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setForeground(Color.BLACK);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				

				try{
					String query = "select * from Accounts where username=? ";
					PreparedStatement PSTM = connection.prepareStatement(query);
					PSTM.setString(1, textFieldUserName.getText());					
					ResultSet RS=PSTM.executeQuery();
					int count = 0;
					while(RS.next()){
						count=count+1;
					}
					if (count == 0){						

						try {
							String query2 = "INSERT INTO Accounts (username, firstname, lastname, email, age, sex, zipcode, password, secretanswer, rating, ratingnumber)"
						+ "values (?,?,?,?,?,?,?,md5(?),?,?,?)";
						
							PreparedStatement pst= connection.prepareStatement(query2);
							
							pst.setString(1,textFieldUserName.getText() );
							pst.setString(2,textFieldFname.getText() );
							pst.setString(3,textFieldLname.getText() );
							pst.setString(4,textFieldMail.getText() );
							pst.setString(5,textFieldAge.getText() );
							pst.setString(6,textFieldSex.getText() );
							pst.setString(7,textFieldZip.getText() );					
							pst.setString(8,passwordField.getText());					
							pst.setString(9,textFieldAnswer.getText() );
							pst.setFloat(10,0);
							pst.setFloat(11,0 );
							
							if(textFieldUserName.getText().equals("")|| textFieldFname.getText().equals("") || textFieldLname.getText().equals("") || textFieldMail.getText().equals("") || textFieldAge.getText().equals("") || textFieldSex.getText().equals("") || textFieldZip.getText().equals("") || textFieldAnswer.getText().equals("") || passwordField.getText().equals("")){
								JOptionPane.showMessageDialog(null, "Error: Please enter all information");
							}//end if
							else {
								if (!(Integer.parseInt(textFieldAge.getText()) < 18)) {
									if(passwordField.getText().equals(passwordField_1.getText())) {
										pst.execute();	// variable object r s  to keep track of result
										dispose();
										LoginMember frame = new LoginMember(connection);
										frame.setVisible(true);
									}//end if
									else {
										JOptionPane.showMessageDialog(null, "Error: Passwords do not match");
									}//end else
								}
								else {
									JOptionPane.showMessageDialog(null, "Error: Users must be 18 or older");
								}//end else
							}//end else

							
							//JOptionPane.showMessageDialog(null, "Welcome new member");
							
							pst.close();					
							
						
							} catch (Exception e1) {
								e1.printStackTrace();
							}			 
					}
					else {
						JOptionPane.showMessageDialog(null, "Error: this username is already taken"); 
					}
					RS.close();
					PSTM.close();
				} catch ( Exception e1)// i change e for e1
				{
					
					JOptionPane.showMessageDialog(null,e);
					
					
				}
							
				
				
				
			}
		});
		btnSubmit.setBounds(415, 407, 80, 23);
		contentPane.add(btnSubmit);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(232, 204, 101, 20);
		contentPane.add(passwordField);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setForeground(new Color(255, 255, 255));
		lblAge.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAge.setBounds(10, 75, 46, 14);
		contentPane.add(lblAge);
		
		textFieldAge = new JTextField();
		textFieldAge.setBounds(103, 72, 129, 20);
		contentPane.add(textFieldAge);
		textFieldAge.setColumns(10);
		
		JLabel lblEnterYourSecurity = new JLabel("Enter your Security Questions?");
		lblEnterYourSecurity.setForeground(new Color(255, 255, 255));
		lblEnterYourSecurity.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEnterYourSecurity.setBounds(125, 284, 208, 14);
		contentPane.add(lblEnterYourSecurity);
		
		textFieldQuestion = new JTextField();
		textFieldQuestion.setBounds(125, 303, 208, 20);
		contentPane.add(textFieldQuestion);
		textFieldQuestion.setColumns(10);
		
		JLabel lblSecurityAnswer = new JLabel("Security Answer ?");
		lblSecurityAnswer.setForeground(new Color(255, 255, 255));
		lblSecurityAnswer.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSecurityAnswer.setBounds(125, 334, 142, 14);
		contentPane.add(lblSecurityAnswer);
		
		textFieldAnswer = new JTextField();
		textFieldAnswer.setBounds(125, 359, 208, 20);
		contentPane.add(textFieldAnswer);
		textFieldAnswer.setColumns(10);
		
		JLabel lblConfirmPasssword = new JLabel("Confirm Passsword");
		lblConfirmPasssword.setForeground(new Color(255, 255, 255));
		lblConfirmPasssword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblConfirmPasssword.setBounds(93, 237, 129, 14);
		contentPane.add(lblConfirmPasssword);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(232, 235, 101, 20);
		contentPane.add(passwordField_1);
		
		JButton btnNewButton = new JButton("Reset");
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldFname.setText(null);
				//passwordField_1.setText(null);
				textFieldLname.setText(null);
				textFieldZip.setText(null);
				textFieldUserName.setText(null);
			//	textFieldSex.setText(null);
				passwordField.setText(null);
				textFieldAge.setText(null);
				textFieldQuestion.setText(null);
				textFieldAnswer.setText(null);
				passwordField_1.setText(null);
			}
		});
		btnNewButton.setBounds(303, 407, 89, 23);
		contentPane.add(btnNewButton);
		
		textFieldSex = new JTextField();
		textFieldSex.setBounds(329, 70, 142, 22);
		contentPane.add(textFieldSex);
		textFieldSex.setColumns(10);
		
		JLabel labelEmail = new JLabel("E-Mail");
		labelEmail.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelEmail.setForeground(new Color(255, 255, 255));
		labelEmail.setBounds(255, 106, 46, 14);
		contentPane.add(labelEmail);
		
		textFieldMail = new JTextField();
		textFieldMail.setBounds(329, 103, 142, 20);
		contentPane.add(textFieldMail);
		textFieldMail.setColumns(10);
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
	}
}
