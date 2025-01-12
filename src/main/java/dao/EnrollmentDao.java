package dao;

import java.util.ArrayList;
import java.util.List;

import entity.Enrollment;
import service.ConnectDB;

public class EnrollmentDao {
	public List<Enrollment> select() {
		List<Enrollment> list = new ArrayList<>();

		try (var conn = ConnectDB.getCon();
		    var cs = conn.prepareCall("{call selectEnrollment}");
		    var rs = cs.executeQuery();) {
			while (rs.next()) {
				var enrollment = new Enrollment();
				enrollment.setEnrollId(rs.getInt("enrollId"));
				enrollment.setEnrollDate(rs.getDate("enrollDate"));
				list.add(enrollment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}


}
