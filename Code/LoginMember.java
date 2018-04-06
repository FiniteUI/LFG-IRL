import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Window;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.SwingConstants;

public class LoginMember extends JFrame {

	private JPanel Reset;

	private JTextField UserName;
	private JPasswordField passwordField_1;
	/**
	 * Create the frame.
	 */
	public LoginMember(Connection connection) {
		//connection = ConnectionMySQL.dbConnector();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(388, 210, 550, 400);
		//setBounds(100, 100, 450, 300);
		Reset = new JPanel();
		Reset.setBackground(new Color(0, 204, 204));
		Reset.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(Reset);
		Reset.setLayout(null);

		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setBounds(20, 60, 90, 26);
		lblUserName.setForeground(Color.WHITE);
		lblUserName.setBackground(Color.WHITE);
		lblUserName.setFont(new Font("Dialog", Font.BOLD, 16));
		Reset.add(lblUserName);

		UserName = new JTextField();
		UserName.setBounds(20, 85, 144, 31);
		UserName.setBackground(Color.WHITE);
		UserName.setFont(new Font("Dialog", Font.BOLD, 16));
		Reset.add(UserName);
		UserName.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(20, 164, 90, 26);
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setBackground(Color.WHITE);
		lblPassword.setFont(new Font("Dialog", Font.BOLD, 16));
		Reset.add(lblPassword);

		JButton btnNext = new JButton("Login");
		btnNext.setBounds(262, 130, 111, 39);
		btnNext.setForeground(Color.BLACK);
		///btnNext.setBackground(Color.WHITE);
		btnNext.addActionListener(new ActionListener() {
			private JLabel passwordField;
			private Window frame;

			public void actionPerformed(ActionEvent arg0) {

				try{
					String query = "select * from Accounts where username=? and password= md5(?) or username = ? and password = ?";
					//this or part is a temporary fix because of lots of old accounts we had before we implemented the md5
					PreparedStatement PSTM = connection.prepareStatement(query);
					PSTM.setString(1, UserName.getText());
					PSTM.setString(2, passwordField_1.getText());
					PSTM.setString(3, UserName.getText());
					PSTM.setString(4, passwordField_1.getText());
					ResultSet RS=PSTM.executeQuery();
					int count = 0;
					while(RS.next()){
						count=count+1;
					}
					if (count == 1){

						{
							//JOptionPane.showMessageDialog(null, "WELCOME ");

							try{
								String query1 = "Drop view UserView";
								PreparedStatement pst1 = connection.prepareStatement(query1);

								pst1.execute();
								pst1.close();

							}
							catch (Exception e1) {
								e1.printStackTrace();
							}



							try {

								String query2 = "create view UserView as select * from Accounts where username='"+UserName.getText()+"'  ";

								PreparedStatement pst2 = connection.prepareStatement(query2);
								PSTM.setString(1,UserName.getText() );

								pst2.execute();


								pst2.close();




								} catch (Exception e1) {
									e1.printStackTrace();
								}

							dispose();
							WelcomeUser f = new WelcomeUser(connection);	// redirect resources
							f.setVisible(true);
						}


					}
					else {
						JOptionPane.showMessageDialog(null, "Error: Incorrect login information");
					}
					RS.close();
					PSTM.close();
					//connection.close();
				} catch ( Exception e)
				{

					JOptionPane.showMessageDialog(null,e);


				}

			}
		});
		btnNext.setFont(new Font("Dialog", Font.BOLD, 24));
		Reset.add(btnNext);

		JLabel lblNewLabel = new JLabel("LFG-IRL");
		lblNewLabel.setBounds(192, 11, 156, 38);
		lblNewLabel.setForeground(new Color(255, 165, 0));
		lblNewLabel.setFont(new Font("AR DESTINE", Font.BOLD | Font.ITALIC, 34));
		Reset.add(lblNewLabel);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(20, 190, 144, 31);
		passwordField_1.setBackground(Color.WHITE);
		passwordField_1.setFont(new Font("Dialog", Font.BOLD, 16));
		Reset.add(passwordField_1);

		JButton btnForgetpassword = new JButton("Forget Password?");
		btnForgetpassword.setBounds(20, 221, 144, 26);
		btnForgetpassword.setHorizontalAlignment(SwingConstants.LEFT);
		btnForgetpassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnForgetpassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					dispose();
					LoginFail frame = new LoginFail(connection);
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		btnForgetpassword.setForeground(Color.BLACK);
		Reset.add(btnForgetpassword);



	}
}
