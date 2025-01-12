package dao;

import java.util.ArrayList;
import java.util.List;

import entity.Classes;
import entity.EnrollStu;
import entity.Student;
import service.ConnectDB;

public class EnrollStuDao {

	public List<EnrollStu> pagingStudentList(int currentPage, int numberOfRows, Classes classes) {
		List<EnrollStu> list = new ArrayList<>();
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call pagingStudentList(?,?,?)}")) {
			cs.setInt(1, currentPage);
			cs.setInt(2, numberOfRows);
			cs.setInt(3, classes.getClassId());
			var rs = cs.executeQuery();
			while (rs.next()) {
				var enrollstu  = new EnrollStu();
				var student = new Student();
				enrollstu.setEnrollId(rs.getInt("enrollId"));
				student.setStuId(rs.getInt("stuId"));
				student.setStuName(rs.getString("stuName"));
				student.setstuGender(rs.getBoolean("stuGender"));
				student.setStuDob(rs.getDate("stuDob"));
				student.setStuAddress(rs.getString("stuAddress"));
				student.setStuEmail(rs.getString("stuEmail"));
				student.setStuPhone(rs.getString("stuPhone"));
				student.setStuImage(rs.getString("stuImage"));
				enrollstu.setStudent(student);
				list.add(enrollstu);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Integer countStudentList(Classes classes) {
		var count = 0;

		try (var conn = ConnectDB.getCon();
				var cs = conn.prepareCall("{call countStudentList(?)}");
				) {
			cs.setInt(1, classes.getClassId());
			var rs = cs.executeQuery();
			if (rs.next()) {
				count = rs.getInt("total");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}

}
