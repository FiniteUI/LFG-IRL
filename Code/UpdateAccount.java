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

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class UpdateAccount extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldUserName;
	private JTextField textFieldFname;
	private JTextField textFieldLname;
	private JTextField textFieldZip;
	private JTextField textFieldSex;
	private JPasswordField textFieldPassword;
	private JTextField textFieldAge;
	private JTextField textFieldPassword_1;
	private JTextField textFieldEmail;
	private JTextField textFieldID;

	public UpdateAccount(Connection connection) {
		//connection = ConnectionMySQL.dbConnector();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(150, 150, 550, 480);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 204, 204));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFname = new JLabel("FName");
		lblFname.setForeground(Color.WHITE);
		lblFname.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFname.setBounds(23, 67, 59, 14);
		contentPane.add(lblFname);
		
		JLabel lblLname_1 = new JLabel("LName");
		lblLname_1.setForeground(Color.WHITE);
		lblLname_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLname_1.setBounds(289, 67, 78, 14);
		contentPane.add(lblLname_1);
		
		textFieldFname = new JTextField();
		textFieldFname.setBounds(95, 68, 129, 20);
		contentPane.add(textFieldFname);
		textFieldFname.setColumns(10);		
		
		
		textFieldLname = new JTextField();
		textFieldLname.setBounds(343, 68, 160, 20);
		contentPane.add(textFieldLname);
		textFieldLname.setColumns(10);
		
		
		JLabel lblZipCode = new JLabel("Zip Code");
		lblZipCode.setForeground(Color.WHITE);
		lblZipCode.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblZipCode.setBounds(23, 140, 73, 14);
		contentPane.add(lblZipCode);
		
		textFieldZip = new JTextField();
		textFieldZip.setBounds(95, 141, 129, 20);
		contentPane.add(textFieldZip);
		textFieldZip.setColumns(10);
		
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setForeground(Color.WHITE);
		lblUserName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUserName.setBounds(135, 238, 101, 14);
		contentPane.add(lblUserName);
		
		textFieldUserName = new JTextField();
		textFieldUserName.setBounds(275, 237, 101, 20);
		contentPane.add(textFieldUserName);
		textFieldUserName.setColumns(10);
		
		
			
		
		JLabel lblSex_1 = new JLabel("Sex");
		lblSex_1.setForeground(Color.WHITE);
		lblSex_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSex_1.setBounds(289, 98, 46, 14);
		contentPane.add(lblSex_1);
		
		textFieldSex = new JTextField();
		textFieldSex.setBounds(343, 99, 160, 23);
		contentPane.add(textFieldSex);
		textFieldSex.setColumns(10);
		
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPassword.setBounds(135, 269, 73, 14);
		contentPane.add(lblPassword);
		
		JButton btnSubmit = new JButton("Update");
		btnSubmit.setForeground(Color.BLACK);
		btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/*//-------------------------------------------------------------------------------
				try {
					connection = ConnectionMySQL.dbConnector();
					String query = "update Accounts set zipcode=? where userid=?";    
					
				
					PreparedStatement pst= connection.prepareStatement(query);
					
					
					pst.setString(1,textFieldZip.getText() );					
					
										
					pst.setString(2,textFieldID.getText() );
					
					pst.execute();	// variable object r s  to keep track of result
					
					JOptionPane.showMessageDialog(null, "Your information has been actualized");
					
					pst.close();					
					
					connection.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				*///---------------------------------------------------------------------------------------
								
				
				try {
					//connection = ConnectionMySQL.dbConnector();
					String query2 = "UPDATE Accounts SET firstname=?, lastname=?, email=?, age=?, sex=?, zipcode=?,password=md5(?) WHERE userid=?";    
					
					PreparedStatement pst= connection.prepareStatement(query2);
					
					pst.setString(1,textFieldFname.getText() );					
					pst.setString(2,textFieldLname.getText() );
					pst.setString(3,textFieldEmail.getText() );
					pst.setString(4,textFieldAge.getText() );
					pst.setString(5,textFieldSex.getText() );
					pst.setString(6,textFieldZip.getText() );
					pst.setString(7,textFieldPassword.getText());					
					pst.setString(8,textFieldID.getText() );
					
					if(textFieldUserName.getText().equals("")|| textFieldFname.getText().equals("") || textFieldLname.getText().equals("") || textFieldEmail.getText().equals("") || textFieldAge.getText().equals("") || textFieldSex.getText().equals("") || textFieldZip.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Error: Please enter all information");
					}//end if
					else {
						if (!(Integer.parseInt(textFieldAge.getText()) < 18)) {
							if(textFieldPassword.getText().equals(textFieldPassword_1.getText())) {
								pst.execute();	// variable object r s  to keep track of result
								JOptionPane.showMessageDialog(null, "Your information has been actualized");
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
					
					//pst.execute();	// variable object r s  to keep track of result
					
					pst.close();					
					//connection.close();
				
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				
				
				
			}
		});
		btnSubmit.setBounds(146, 379, 101, 23);
		contentPane.add(btnSubmit);
		
		textFieldPassword = new JPasswordField();
		textFieldPassword.setBounds(275, 268, 101, 20);
		contentPane.add(textFieldPassword);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setForeground(Color.WHITE);
		lblAge.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAge.setBounds(23, 115, 46, 23);
		contentPane.add(lblAge);
		
		textFieldAge = new JTextField();
		textFieldAge.setBounds(95, 110, 129, 20);
		contentPane.add(textFieldAge);
		textFieldAge.setColumns(10);
		
		
		
		JLabel lblConfirmPasssword = new JLabel("Confirm Password");
		lblConfirmPasssword.setForeground(Color.WHITE);
		lblConfirmPasssword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblConfirmPasssword.setBounds(121, 300, 144, 14);
		contentPane.add(lblConfirmPasssword);
		
		textFieldPassword_1 = new JTextField();
		textFieldPassword_1.setBounds(275, 299, 101, 20);
		contentPane.add(textFieldPassword_1);
		textFieldPassword_1.setColumns(10);
		
		JLabel lblAccountReview = new JLabel("Account Review");
		lblAccountReview.setHorizontalAlignment(SwingConstants.CENTER);
		lblAccountReview.setForeground(new Color(255, 215, 0));
		lblAccountReview.setFont(new Font("Verdana", Font.BOLD, 24));
		lblAccountReview.setBounds(113, 11, 300, 22);
		contentPane.add(lblAccountReview);
		
		JButton btnBack = new JButton("Back");
		btnBack.setForeground(Color.BLACK);
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					WelcomeUser frame = new WelcomeUser(connection);
					frame.setVisible(true);
					dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		btnBack.setBounds(268, 379, 89, 23);
		contentPane.add(btnBack);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(20, 364, 504, -9);
		contentPane.add(separator_1);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		btnExit.setForeground(Color.WHITE);
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnExit.setBounds(380, 379, 89, 23);
		contentPane.add(btnExit);
		
		JLabel lblNewLabelMail = new JLabel("E-Mail");
		lblNewLabelMail.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabelMail.setForeground(Color.WHITE);
		lblNewLabelMail.setBounds(289, 142, 46, 14);
		contentPane.add(lblNewLabelMail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(343, 138, 160, 23);
		contentPane.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		textFieldID = new JTextField();
		textFieldID.setForeground(new Color(0, 204, 204));
		textFieldID.setBackground(new Color(255, 255, 255));
		textFieldID.setBounds(451, 19, 73, 14);
		contentPane.add(textFieldID);
		textFieldID.setColumns(10);
		
		JButton btnNewButton = new JButton("Load Info");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					//String query = " select * from Accounts where userid=?";
					String query = "select * from UserView";
				
				PreparedStatement pst = connection.prepareStatement(query);
				//pst.setString(1,textFieldID.getText() );
				ResultSet rs=pst.executeQuery();	// variable object r s  to keep track of result
				rs.next();
				//table.setModel(DbUtils.resultSetToTableModel(rs));
				textFieldFname.setText(rs.getString("firstname"));
				textFieldLname.setText(rs.getString("lastname"));
				textFieldAge.setText(rs.getString("age"));
				textFieldSex.setText(rs.getString("sex"));				
				textFieldZip.setText(rs.getString("zipcode"));
				textFieldEmail.setText(rs.getString("email"));
				textFieldUserName.setText(rs.getString("username"));
				//textFieldPassword.setText(rs.getString("password"));
				
				pst.close();					
				rs.close();
				//connection.close();
				
				} catch (Exception e1) {
				e1.printStackTrace();
				}
			
				
				
				
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBounds(20, 379, 101, 23);
		contentPane.add(btnNewButton);
		try{
			String query = "select userid FROM UserView";
			PreparedStatement pst = connection.prepareStatement(query);	
			
			ResultSet rs=pst.executeQuery();
			rs.next();
			textFieldID.setText(rs.getString("userid"));
			textFieldID.setEditable(false);
			}catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
	}
}
