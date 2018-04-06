
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//TODO: Check for number of members

//adds a member to the group via the member relation
public class AddMember {
	
	//adds a member to the Members relation
	public static void addMember(Connection conn, int Member, int Group){
		
		String addStatement = "Insert into Members(userid, groupid) values (?,?)";
		//String countStatement = "select count(GroupID) from Members where GroupID = " + Group;
		//String maxStatament = "select MaxMembers from Groups where GroupID = " + Group;
		//connect();
		try {
			
			//insert it
			PreparedStatement ps = conn.prepareStatement(addStatement);
			ps.setInt(1, Member);
			ps.setInt(2, Group);
			ps.execute();
			
			//if no problems, commit it
			conn.commit();
		}//end try
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}//end catch
		finally {
			//closeDB();
		}//end finally
		
	}//end addMember
	
	//returns the current number of members in a specific group
	public static int getMemberNumber(Connection conn, int Group) {
		String countStatement = "select count(groupid) from Members where groupid = " + Group;
		//connect();
		int memberCount = 0;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(countStatement);
			if(rs.next())
				memberCount = rs.getInt(1);
		}//end try
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}//end catch
		finally {
			//closeDB();
		}
		
		return memberCount;
	}//end getMemberNumber
}//end class