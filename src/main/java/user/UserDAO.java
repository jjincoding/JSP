package user;

import java.sql.*;

public class UserDAO {
	
	private Connection conn; 
	private PreparedStatement pstmt; 
	private ResultSet rs; 
	
	public UserDAO() {
		try {
			String dbURL = "jdbc:mariadb://localhost:3306/bbs"; 
			String dbID = "root";
			String dbPassword = "black";
			Class.forName("org.mariadb.jdbc.Driver"); 
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int login(String userID, String userPassword) {
		
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword)) {
					return 1;
				} else
					return 0;
				}
				return -1;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return -2;
	}
	
	public int join(User user) {
		
		String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, ?)";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return -1;
	}
}