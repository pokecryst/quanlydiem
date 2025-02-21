package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Classes;
import entity.GradeStu;
import service.ConnectDB;

public class GradeStuDao {

	public List<GradeStu> pagingGradeStu(int currentPage, int numberOfRows, Classes classes){
		List<GradeStu> list = new ArrayList<>();

		try(
				var conn = ConnectDB.getCon();
				var cs = conn.prepareCall("{call pagingGradeStu(?,?,?)}")
				){
			cs.setInt(1, currentPage);
			cs.setInt(2, numberOfRows);
			cs.setInt(3, classes.getClassId());
			var rs = cs.executeQuery();
			while (rs.next()) {
				var gradestu  = new GradeStu();
				gradestu.setEnrollId(rs.getInt("enrollId"));
				gradestu.setGradeId(rs.getInt("gradeId"));
				gradestu.setStuId(rs.getInt("stuId"));
				gradestu.setStuName(rs.getString("stuName"));
				gradestu.setMidScore(rs.getDouble("midScore"));
				gradestu.setFinalScore(rs.getDouble("finalScore"));
				gradestu.setAvgScore(rs.getDouble("avgScore"));
				list.add(gradestu);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}


		return list;
	}
	
	public String selectStuNameById(int id) {
		var stuName = "";
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call selectStuNameByID(?)}")) {
			cs.setInt(1, id);
			var rs = cs.executeQuery();
			while (rs.next()) {
				stuName = rs.getString("stuName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stuName;
	}
}
