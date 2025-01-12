package dao;

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

}
