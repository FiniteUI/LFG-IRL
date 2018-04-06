import java.sql.*;

//blocks a user from a group
public class BlockUser {
	
	//add the user group combo to the blocked table
	public static void Block(Connection conn, int GroupID, int AccountID) {
	
		String insertStatement = "INSERT INTO Blocked(groupid, userid) values (?,?)";
		//connect();
		
		try {
			
			//insert into the blocked table
			PreparedStatement ps = conn.prepareStatement(insertStatement);
			ps.setInt(1, GroupID);
			ps.setInt(2, AccountID);
			ps.execute();
				
			//if no problems, commit
			conn.commit();
			
		}//end try
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}//end catch
		
	//closeDB();
	
	//remove from group
	RemoveMember.removeMember(conn, AccountID, GroupID);
	}//end Block
	
	public static boolean isBlocked(Connection conn, int GID, int UID) {
		boolean blocked = false;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select userid from Blocked where groupid = " + GID + " and userid = " + UID);
			if (rs.next())
				blocked = true;
		}//end try
		catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		return blocked;
	}//end isBlocked
}//end block user