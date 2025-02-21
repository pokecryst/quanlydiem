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
	
	public void delete(int enrollId) {
		try (var conn = ConnectDB.getCon();
			var cs = conn.prepareCall("{call deleteEnrollment(?)}");
			var cs2 = conn.prepareCall("{call deleteGradeByEnrollId(?)}");
		) {
			cs.setInt(1, enrollId);
			cs2.setInt(1, enrollId);
			cs2.executeUpdate();
			cs.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void add(int stuId, int classId) {
		try (var conn = ConnectDB.getCon();
			var cs = conn.prepareCall("{call createEnrollmentAGrade(?, ?)}");
			) {
			cs.setInt(1, stuId);
			cs.setInt(2, classId);
			cs.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}


}
