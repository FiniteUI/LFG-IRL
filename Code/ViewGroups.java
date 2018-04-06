
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewGroups {
	
	//returns all specific groups a user is in
	public static ResultSet viewGroups(int AccountID, Connection conn){
		String sqlStat = "select Groups.groupID, Groups.name, Groups.category, Groups.date, Groups.city, Groups.zipcode, Groups.description, Groups.maxmembers"
				+ " from Groups INNER JOIN Members on Groups.groupID = Members.groupID WHERE Members.userID = " + AccountID;
		ResultSet rs = null;
			
		try{
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlStat);
		}//end try
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}//end catch
		return rs;
		
	}//end viewGroups
}//end ViwGroups
