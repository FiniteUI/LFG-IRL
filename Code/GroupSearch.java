
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//searches for groups with input criteria
public class GroupSearch {
	
	public static ResultSet search(Connection conn, String str) {
		
		String sqlStat = "select * from Groups where category like '%" + str + "%' or name like '%" + str + "%' or description like '%" + str + "%'";
		//connect();
		ResultSet rs = null;
		
		try{
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlStat);
		}//end try
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}//end catch
		
		return rs;
		
	}//end search
	
	public static ResultSet NearbySearch(Connection conn, String str, int zip) {
		
		String sqlStat = "select * from Groups where category like '%" + str + "%' or name like '%" + str + "%' or description like '%" + str + "%' and zipcode <= " + (zip + 100) + " and zipcode >= " + (zip-100);
		//connect();
		ResultSet rs = null;
		
		try{
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlStat);
		}//end try
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}//end catch
		
		return rs;
		
	}//end search

	public static ResultSet searchCategory(Connection conn, String str) {
	
		String sqlStat = "select * from Groups where category = '" + str + "'";
		//connect();
		ResultSet rs = null;
	
		try{
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlStat);
		}//end try
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}//end catch
	
		return rs;
	
	}//end searchcategory
	
	public static ResultSet searchID(Connection conn, int GID) {
		
		String sqlStat = "select * from Groups where groupid = " + GID;
		//connect();
		ResultSet rs = null;
	
		try{
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlStat);
		}//end try
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}//end catch
	
		return rs;
	
	}//end searchID
	
	//returns if a user is in a group already or not
	public static boolean searchGroupUser(Connection conn, int GID, int UID) {
		
		
		String sqlStat = "select * from Members where groupid = " + GID + " and userid = " + UID;
		//connect();
		ResultSet rs = null;
	
		try{
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlStat);
			if (rs.next())
				return true;
			return false;
		}//end try
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}//end catch
		return false;
	}//end searchGroupUser
	
public static ResultSet NearbyCategorySearch(Connection conn, String str, int zip) {
		
		String sqlStat = "select * from Groups where category = '" + str + "' and zipcode <= " + (zip + 100) + " and zipcode >= " + (zip-100);
		//connect();
		ResultSet rs = null;
		
		try{
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlStat);
		}//end try
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}//end catch
		
		return rs;
		
	}//end search
}//end GroupSearch