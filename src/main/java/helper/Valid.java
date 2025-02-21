package helper;

import java.sql.Connection;
import java.util.Scanner;

public class Valid {

	public static String checkRegex(String Regex, String message) {
		var sc = new Scanner(System.in);
		System.out.println(message);
		while (true) {
			var input = sc.nextLine();
			if (input.matches(Regex)) {
				return input;
			} else {
				System.out.println("Invalid input, " + message);
			}
		}
	}
	
	public static boolean checkRegex2(String Regex, String str) {
		var check = false;
		if(str.matches(Regex)) {
			check = true;
		}
		return check;
	}

	public static boolean checkStudentExists(Connection conn, int id) {
		var check = false;
		try (var cs = conn.prepareCall("{call selectByID(?)}")) {
			cs.setInt(1, id);
			// Execute the stored procedure
			try (var rs = cs.executeQuery()) {
				if (rs.next()) {
					check = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return check;
	}
	
	public static boolean checkScoreExists(Connection conn, int id) {
		var check = false;
		try (var cs = conn.prepareCall("{call selectGradeByID(?)}")){
			cs.setInt(1, id);
			try (var rs = cs.executeQuery()) {
				if (rs.next()) {
					check = true;
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return check;
	}
	
	public static boolean checkClassExists(Connection conn, int id) {
		var check = false;
		try (var cs = conn.prepareCall("{call selectClassByID(?)}")){
			cs.setInt(1, id);
			try (var rs = cs.executeQuery()) {
				if (rs.next()) {
					check = true;
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return check;
	}

}
