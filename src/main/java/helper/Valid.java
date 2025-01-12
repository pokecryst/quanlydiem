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

}
