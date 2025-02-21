package dao;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import entity.Grade;
import entity.Student;
import service.ConnectDB;

public class GradeDao {
	
	public double calcAvg(double midScore, double finalScore) {
		var average = Math.round((midScore + finalScore * 2) / 3.0 * 100.0) / 100.0;
        
		return average;
		
	}
	
	public Double calcAveStrInput(String midScore, String finalScore) {
		var mid = Double.parseDouble(midScore);
		var fin = Double.parseDouble(finalScore);
		var average = Math.round((mid + fin * 2) / 3.0 * 100.0) / 100.0;
        
		return average;
		
		
	}
	
	public List<Grade> selectPaging(int currentpage, int numberofrows) {
		List<Grade> list = new ArrayList<>();

		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call paging(?,?)}");) {
			cs.setInt(1, currentpage);
			cs.setInt(2, numberofrows);
			var rs = cs.executeQuery();
			// while result set still can continue, takes the value from columns to put into
			// model entry
			while (rs.next()) {
				var grade = new Grade();
				grade.setGradeId(rs.getInt("gradeId"));
				grade.setMidScore(rs.getDouble("midScore"));
				grade.setFinalScore(rs.getDouble("finalScore"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	

	public void insert(Grade grade, Student stu, int empId) {
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call createGrade(?,?,?,?)}")) {
			cs.setDouble(1, grade.getMidScore());
			cs.setDouble(2, grade.getFinalScore());
			cs.setInt(3, stu.getStuId());
			cs.setInt(4, empId);
			if (cs.executeUpdate() > 0) {
				JOptionPane.showMessageDialog(null, "Grade Successfully Added", "Message",
						JOptionPane.INFORMATION_MESSAGE);
			};
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Grade grade) {
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call updateGrade(?,?,?,?)}")) {
			cs.setDouble(1, grade.getMidScore());
			cs.setDouble(2, grade.getFinalScore());
			cs.setDouble(3, grade.getAvgScore());
			cs.setInt(4, grade.getGradeId());
			cs.executeUpdate();
//			if (cs.executeUpdate() > 0) {
//				JOptionPane.showMessageDialog(null, "Grade Successfully Updated", "Message",
//						JOptionPane.INFORMATION_MESSAGE);
//			};
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Grade> select() {
		List<Grade> list = new ArrayList<>();
		try (var conn = ConnectDB.getCon();
				var cs = conn.prepareCall("{call selectGrade}");
				var rs = cs.executeQuery();) {
			while (rs.next()) {
				var grade = new Grade();
				grade.setGradeId(rs.getInt("gradeId"));
				grade.setMidScore(rs.getDouble("midScore"));
				grade.setFinalScore(rs.getDouble("finalScore"));
				list.add(grade);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Grade selectGradebyId(int id) {
		Grade grade = new Grade();
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call selectGradeByID(?)}");
				
			)
		{	
			cs.setInt(1, id);
			var rs = cs.executeQuery();
			while(rs.next()){
				grade.setGradeId(rs.getInt("gradeId"));
				grade.setMidScore(rs.getDouble("midScore"));
				grade.setFinalScore(rs.getDouble("finalScore"));
				grade.setAvgScore(rs.getDouble("avgScore"));
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return grade;
	}

}
