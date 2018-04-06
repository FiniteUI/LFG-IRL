import java.sql.*;
import javax.swing.*;
public class ConnectionMySQL {
	Connection conn=null;
	public static Connection dbConnector()
	{
			try {
				//Class.forName("org.sqlite.JDBC");
				//Connection conn=DriveManager.getConnection("jdbc:sqlite:‪C:\\Users\\shradha677\\AppData\\Local\\Temp\\Rar$EXa0.263\\SQLiteStudio")
				//Connection conn=DriverManager.getConnection("jdbc:sqlite:C:\\Users\\\\SHRADH~1\\AppData\\Local\\Temp\\Rar$EXa0.263\\SQLiteStudio\\UserAccount.sqlite");
				//Connection conn=DriverManager.getConnection("jdbc:sqlite:C:\\Users\\\\shradha677\\info.db");

				///Connection conn=DriverManager.getConnection("jdbc:sqlite:C:\\Users\\liliana\\Desktop\\AccountData\\AccountData.sqlite");

			Connection conn = DriverManager.getConnection("jdbc:mysql://rseddon.heliohost.org/rseddon_lfg", "rseddon_new", "passwordhere");
				//JOptionPane.showMessageDialog(null, "Connection Successful");
				return conn;

			}catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, e);
				return null;
			}
	}



}

