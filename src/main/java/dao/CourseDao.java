package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import entity.Classes;
import entity.Course;
import service.ConnectDB;

public class CourseDao {
	public Course selectCourseById(int courseId) {
		var course = new Course();
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call selectCourseById(?)}");) {
			cs.setInt(1, courseId);
			var rs = cs.executeQuery();
			if (rs.next()) {
				course.setCourseId(rs.getInt("courseId"));
				course.setCourseName(rs.getString("courseName"));
				course.setCourseDesc(rs.getString("courseDesc"));
				course.setCourseDuration(rs.getInt("courseDuration"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return course;

	}
	
	public String selectCourseNameById(int id) {
		var name = "";
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call selectCourseNameById(?)}")) {
			cs.setInt(1, id);
			var rs = cs.executeQuery();
			while (rs.next()) {
				name = rs.getString("courseName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return name;
	}
	
	public String selectCourseNameById(Classes cl) {
		var name = "";
		var clDao = new ClassesDao();
		var courseId = clDao.selectByClassID(cl.getClassId()).getCourseId();
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call selectCourseNameById(?)}")) {
			cs.setInt(1, courseId);
			var rs = cs.executeQuery();
			while (rs.next()) {
				name = rs.getString("courseName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return name;
	}
	
	public List<Course> selectAll() {
		List<Course> list = new ArrayList<>();
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call selectCourse()}");) {
			var rs = cs.executeQuery();
			while (rs.next()) {
				var course = new Course();
				course.setCourseId(rs.getInt("courseId"));
				course.setCourseName(rs.getString("courseName"));
				course.setCourseDesc(rs.getString("courseDesc"));
				course.setCourseDuration(rs.getInt("courseDuration"));
				
				list.add(course);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}
	
	public List<Course> pagingCourse(int currentPage, int numberOfRows){
		List<Course> list = new ArrayList<>();
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call pagingCourse(?, ?)}");) {
			cs.setInt(1, currentPage);
			cs.setInt(2, numberOfRows);
			var rs = cs.executeQuery();
			while (rs.next()) {
				var course = new Course();
				course.setCourseId(rs.getInt("courseId"));
				course.setCourseName(rs.getString("courseName"));
				course.setCourseDesc(rs.getString("courseDesc"));
				course.setCourseDuration(rs.getInt("courseDuration"));

				list.add(course);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int countCourse() {
		var count = 0;
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call countCourse()}");) {
			var rs = cs.executeQuery();
			if (rs.next()) {
				count = rs.getInt("total");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public void insertCourse(Course course) {
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call createCourse(?, ?, ?)}");) {
			cs.setString(1, course.getCourseName());
			cs.setString(2, course.getCourseDesc());
			cs.setInt(3, course.getCourseDuration());
			cs.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateCourse(Course course) {
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call updateCourse(?, ?, ?, ?)}");) {
			cs.setInt(1, course.getCourseId());
			cs.setString(2, course.getCourseName());
			cs.setString(3, course.getCourseDesc());
			cs.setInt(4, course.getCourseDuration());
			cs.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteCourse(int courseId) {
	    try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call deleteCourse(?)}")) {
	        cs.setInt(1, courseId);
	        cs.execute();
	        JOptionPane.showMessageDialog(null, "Course deleted successfully!");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Failed to delete course!");
	    }
	}


}
