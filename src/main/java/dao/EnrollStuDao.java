package dao;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

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
				student.setStuGender(rs.getBoolean("stuGender"));
				student.setStuDob(rs.getDate("stuDob").toLocalDate());
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
	
	public List<EnrollStu> pagingStudentNotInClass(int currentPage, int numberOfRows, Classes classes) {
		List<EnrollStu> list = new ArrayList<>();
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call pagingStudentNotInClass(?,?,?)}")) {
			cs.setInt(1, currentPage);
			cs.setInt(2, numberOfRows);
			cs.setInt(3, classes.getClassId());
			var rs = cs.executeQuery();
			while (rs.next()) {
				var enrollstu  = new EnrollStu();
				var student = new Student();
				student.setStuId(rs.getInt("stuId"));
				student.setStuName(rs.getString("stuName"));
				student.setStuGender(rs.getBoolean("stuGender"));
				student.setStuDob(rs.getDate("stuDob").toLocalDate());
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
	
	public Integer countStudentNotInClass(Classes classes) {
		var count = 0;

		try (var conn = ConnectDB.getCon();
				var cs = conn.prepareCall("{call countStudentNotInClass(?)}");
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
	
	public List<EnrollStu> pagingEnrollListOfStu (int currentPage, int numberOfRows, int stuId){
		List<EnrollStu> list = new ArrayList<>();
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call pagingEnrollListOfStu(?,?,?)}")) {
			cs.setInt(1, currentPage);
			cs.setInt(2, numberOfRows);
			cs.setInt(3, stuId);
			var rs = cs.executeQuery();
			while (rs.next()) {
				var enrollstu  = new EnrollStu();
				enrollstu.setEnrollId(rs.getInt("enrollId"));
				enrollstu.setEnrollDate(rs.getDate("enrollDate").toLocalDate());
				enrollstu.setClassId(rs.getInt("classId"));
				enrollstu.setStuId(rs.getInt("stuId"));
				list.add(enrollstu);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public Integer countEnrollListOfStu(int stuId) {
		var count = 0;

		try (var conn = ConnectDB.getCon();
				var cs = conn.prepareCall("{call countStudentNotInClass(?)}");
				) {
			cs.setInt(1, stuId);
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
