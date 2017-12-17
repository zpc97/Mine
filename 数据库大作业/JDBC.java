package 数据库大作业;

import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.Label;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

class JDBC {
	public static String IP = "192.168.1.103";
	private String url = "jdbc:sqlserver://" + IP + ";DatabaseName=UserInfo_DB;";
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	public static String[] friend = new String[101];// 好友String变量

	public JDBC() throws SQLException {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		connection = DriverManager.getConnection(url, "sa", "123456");
		statement = connection.createStatement();
		for (int i = 0; i < 101; i++) {
			friend[i] = null;
		}
	}

	public int load(String username, String password) {
		String sql = "SELECT Password FROM UserInfo_Table Where Username = " + "'" + username + "';";
		try {
			resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				if (resultSet.getString("Password").trim().equals(password)) {
					return 1;
				} else {
					return -1;
				}
			} else {
				return -2;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -3;
		}
	}

	public int register(String username, String password) {
		String sql = "SET NOCOUNT ON " + "INSERT INTO UserInfo_Table VALUES('" + username + "','" + password + "');"
				+ "SET NOCOUNT OFF";
		try {
			if (load(username, password) == -2) {
				statement.execute(sql);
				return 1;
			} else {
				return 2;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -3;
		}
	}

	public int checkfriend(String username, String friendname) {
		String sql = "SELECT Friendname FROM Friend_Table Where Username = " + "'" + username + "';";
		try {
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				if (resultSet.getString("Friendname").trim().equals(friendname)) {
					return 1;
				}
			}
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -3;
		}
	}

	public int addfriend(String username, String friendname) {
		String sql = "SET NOCOUNT ON " + "INSERT INTO Friend_Table VALUES('" + username + "','" + friendname + "'),('"
				+ friendname + "','" + username + "');" + "SET NOCOUNT OFF";
		try {
			if (load(friendname, "") == -2) {
				return -2;
			} else if (checkfriend(username, friendname) == -1) {
				statement.execute(sql);
				return 1;
			} else {
				return 2;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -3;
		}
	}

	public int deletefriend(String username, String friendname) throws SQLException {
		String sql = "SET NOCOUNT ON " + "DELETE Friend_Table WHERE (Username = '" + username + "' AND Friendname = '"
				+ friendname + "') OR (Username = '" + friendname + "' AND Friendname = '" + username + "');"
				+ "SET NOCOUNT OFF";
		try {
			statement.execute(sql);
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -3;
		}
	}

	public void pollingfriend(String username) {
		String sql = "SELECT Friendname FROM Friend_Table Where Username = '" + username + "';";
		try {
			resultSet = statement.executeQuery(sql);
			int i = 1;
			while (resultSet.next()) {
				friend[i++] = resultSet.getString("Friendname").trim();
			}
			while (i < 101) {
				friend[i++] = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String username, String friendname, String message) {
		String sql = "SET NOCOUNT ON " + "INSERT INTO Message_Table VALUES('" + username + "','" + friendname + "','"
				+ message + "');" + "SET NOCOUNT OFF";
		try {
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void receiveMessage(String username, String friendname, Label friendMessage) {
		String sql = "SELECT Username FROM Message_Table WHERE Receiver = '" + username + "'" + " AND Username = '"+ friendname + "';";
		try {
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				QQ.messageNumber(resultSet.getString("Username").trim(), friendMessage);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteMessage(String username) {
		String sql = "SET NOCOUNT ON " + "DELETE Message_Table WHERE Receiver = '" + username + "';"
				+ "SET NOCOUNT OFF ";
		try {
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String[] readMessage(String username) {
		String[] message = new String[100];
		String sql = "SELECT Message FROM Message_Table WHERE Receiver = '" + username + "';";
		try {
			resultSet = statement.executeQuery(sql);
			int i = 0;
			while (resultSet.next()) {
				message[i] = new String(resultSet.getString("Message").trim());
				i++;
			}
			deleteMessage(username);
			return message;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}