import java.awt.EventQueue;
import java.sql.*;
import javax.swing.*;
import javax.swing.JInternalFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;


public class WelcomeUser extends JFrame {
	private JTextField textFieldUser;
	private int CURRENT_USER = -1;
	private String username = null;

	public WelcomeUser(Connection connection) {
		getContentPane().setBackground(new Color(0, 204, 204));
		//connection = ConnectionMySQL.dbConnector();
		setBounds(388, 210, 550, 400);
		//setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		textFieldUser = new JTextField();
		textFieldUser.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldUser.setFont(new Font("Verdana", Font.BOLD, 24));
		textFieldUser.setBounds(128, 68, 259, 36);
		getContentPane().add(textFieldUser);
		textFieldUser.setColumns(10);
		textFieldUser.setEditable(false);
		
		try{
			String query = "select username, userid FROM UserView";
			PreparedStatement pst = connection.prepareStatement(query);
			
			
			
			ResultSet rs=pst.executeQuery();
			rs.next();
			textFieldUser.setText(rs.getString("username"));
			CURRENT_USER = rs.getInt("userid");
			username = rs.getString("username");
			//textFieldID.setText(rs.getString("SID"));
			//textFieldfirstName.setText(rs.getString("firstName"));
			
			
			//connection.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		
		
		
		
		JLabel lblWel = new JLabel("Hello");
		lblWel.setHorizontalAlignment(SwingConstants.CENTER);
		lblWel.setForeground(new Color(255, 255, 255));
		lblWel.setFont(new Font("Imprint MT Shadow", Font.BOLD, 30));
		lblWel.setBounds(145, 11, 213, 48);
		getContentPane().add(lblWel);
		
		JLabel lblGoto = new JLabel("");
		lblGoto.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		lblGoto.setBounds(16, 202, 124, 30);
		getContentPane().add(lblGoto);
		
		JButton btnGroups = new JButton("My Groups");
		btnGroups.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//------------------------------------------
				// code to implement group foundation
				try {
					
					//LoginMember frame = new LoginMember(connection);
					GroupFoundation groupFrame = new GroupFoundation(CURRENT_USER, username);
					
					//frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}	
				
				//--------------------------------------------------
			}
		});
		btnGroups.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 14));
		btnGroups.setBounds(301, 150, 132, 41);
		getContentPane().add(btnGroups);
		
		JButton btnAccount = new JButton("My Account");
		btnAccount.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 14));
		btnAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					dispose();
					UpdateAccount frame = new UpdateAccount(connection);
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}	
				
			}
		});
		btnAccount.setBounds(86, 150, 131, 41);
		getContentPane().add(btnAccount);

	}
}
