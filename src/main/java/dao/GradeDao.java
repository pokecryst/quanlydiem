package dao;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import entity.Grade;
import entity.Student;
import service.ConnectDB;

public class GradeDao {
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
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call updateGrade(?,?,?)}")) {
			cs.setDouble(1, grade.getMidScore());
			cs.setDouble(2, grade.getFinalScore());
			cs.setInt(3, grade.getGradeId());
			if (cs.executeUpdate() > 0) {
				JOptionPane.showMessageDialog(null, "Grade Successfully Updated", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
			;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Grade> select(){
		List<Grade> list = new ArrayList<>();
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call selectGrade}"); var rs = cs.executeQuery();) {
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

	public void delete(Grade grade) {
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call deleteGrade(?)}");) {
			cs.setInt(1, grade.getGradeId());
			if (cs.executeUpdate() > 0) {
				JOptionPane.showMessageDialog(null, "Delete successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
			;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
