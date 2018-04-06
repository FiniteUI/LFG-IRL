
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//delete a group from the group table
public class DeleteGroup {

	//delete the group from the groups, members, owner table
	public static void deleteGroup(Connection conn, int GID) {
		
		//connect();
		
		try{
			
			//delete group from groups table
			String deleteStatement = "delete from Groups where groupid = " + GID;
			PreparedStatement del = conn.prepareStatement(deleteStatement);
			del.execute();
			
			//delete from owner table
			deleteStatement = "delete from Owner where groupid = " + GID;
			del = conn.prepareStatement(deleteStatement);
			del.execute();
			
			//delete from members table
			deleteStatement = "delete from Members where groupid = " + GID;
			del = conn.prepareStatement(deleteStatement);
			del.execute();
			
			//commit if no problems
			conn.commit();

		}//end try
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}//end catch
		finally {
			//closeDB();
		}
	}//end deleteGroup
}//End DeleteGroup
