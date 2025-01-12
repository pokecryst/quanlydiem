package dao;

import java.util.ArrayList;
import java.util.List;

import entity.Classes;
import service.ConnectDB;

public class ClassesDao {
	public List<Classes> selectAll(){
		List<Classes> list = new ArrayList<>();
		try(
				var conn = ConnectDB.getCon();
				var cs = conn.prepareCall("{call selectClass}");
				var rs = cs.executeQuery();
				){
			while(rs.next()) {
				var classes = new Classes();
				classes.setClassId(rs.getInt("classId"));
				classes.setClassName(rs.getString("className"));
				classes.setStartDate(rs.getDate("startDate"));
				classes.setEndDate(rs.getDate("endDate"));

				list.add(classes);
			}
				} catch (Exception e) {
					e.printStackTrace();
				}


		return list;

	}

	public Classes selectByClassName(String className) {

		var classes = new Classes();

		try(
				var conn = ConnectDB.getCon();
				var cs = conn.prepareCall("{call selectClassByName(?)}");

				){
				cs.setString(1, className);
				var rs = cs.executeQuery();
					if (rs.next()) {
						classes.setClassId(rs.getInt("classId"));
						classes.setClassName(rs.getString("className"));
						classes.setStartDate(rs.getDate("startDate"));
						classes.setEndDate(rs.getDate("endDate"));
						classes.setCourseId(rs.getInt("courseId"));
					}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classes;
	}

}
