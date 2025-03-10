package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

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
				classes.setClStatId(rs.getInt("clStatId"));

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
						classes.setTeachId(rs.getInt("empId"));
						classes.setClStatId(rs.getInt("clStatId"));
					}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classes;
	}
	
	public Classes selectByClassID(int classId) {

		var classes = new Classes();

		try(
				var conn = ConnectDB.getCon();
				var cs = conn.prepareCall("{call selectClassByID(?)}");

				){
				cs.setInt(1, classId);
				var rs = cs.executeQuery();
					if (rs.next()) {
						classes.setClassId(rs.getInt("classId"));
						classes.setClassName(rs.getString("className"));
						classes.setStartDate(rs.getDate("startDate"));
						classes.setEndDate(rs.getDate("endDate"));
						classes.setCourseId(rs.getInt("courseId"));
						classes.setTeachId(rs.getInt("empId"));
						classes.setClStatId(rs.getInt("clStatId"));
					}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classes;
	}
	
	public List<Classes> selectByTeachID(int empId) {
	    var list = new ArrayList<Classes>();

	    try (
	        var conn = ConnectDB.getCon();
	        var cs = conn.prepareCall("{call selectClassesByTeachID(?)}")
	    ) {
	        cs.setInt(1, empId);
	        var rs = cs.executeQuery();
	        
	        while (rs.next()) {  
	            var classes = new Classes();
	            classes.setClassId(rs.getInt("classId"));
	            classes.setClassName(rs.getString("className"));
	            classes.setStartDate(rs.getDate("startDate"));
	            classes.setEndDate(rs.getDate("endDate"));
	            classes.setCourseId(rs.getInt("courseId"));
	            classes.setTeachId(rs.getInt("empId"));
	            classes.setClStatId(rs.getInt("clStatId"));
	            
	            list.add(classes);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return list;
	}

	
	public void update(Classes classes) {
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call updateClass(?,?,?,?,?,?,?)}");) {
			cs.setString(1, classes.getClassName());
			cs.setDate(2, classes.getStartDate());
			cs.setDate(3, classes.getEndDate());
			cs.setInt(4, classes.getCourseId());
			cs.setInt(5, classes.getTeachId());
			cs.setInt(6, classes.getClStatId());
			cs.setInt(7, classes.getClassId());
			cs.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void add(Classes classes) {
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call createClass(?,?,?,?,?)}");) {
			cs.setString(1, classes.getClassName());
			cs.setDate(2, classes.getStartDate());
			cs.setDate(3, classes.getEndDate());
			cs.setInt(4, classes.getCourseId());
			cs.setInt(5, classes.getTeachId());		
			cs.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int classId) {
	    try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call deleteClass(?)}")) {
	        cs.setInt(1, classId);
	        cs.execute();
	        JOptionPane.showMessageDialog(null, "Class deleted successfully!");
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Failed to delete class!");
	    }
	}


	public List<Classes> pagingClass(int currentPage, int numberOfRows) {
	    List<Classes> list = new ArrayList<>();
	    try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call pagingClass(?, ?)}")) {
	        cs.setInt(1, currentPage);
	        cs.setInt(2, numberOfRows);
	        var rs = cs.executeQuery();
	        while (rs.next()) {
	            var classes = new Classes();
	            classes.setClassId(rs.getInt("classId"));
	            classes.setClassName(rs.getString("className"));
	            classes.setStartDate(rs.getDate("startDate"));
	            classes.setEndDate(rs.getDate("endDate"));
	            classes.setCourseId(rs.getInt("courseId"));
	            classes.setTeachId(rs.getInt("empId"));
	            classes.setClStatId(rs.getInt("clStatId"));

	            list.add(classes);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	
	public int countClass() {
		var count = 0;
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call countClass()}")) {
			var rs = cs.executeQuery();
			if (rs.next()) {
				count = rs.getInt("total");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	//khanh
	public void addClass(Classes classes) {
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call createClass(?,?,?,?,?)}");) {
			cs.setString(1, classes.getClassName());
			cs.setDate(2, classes.getStartDate());
			cs.setDate(3, classes.getEndDate());
			cs.setInt(4, classes.getCourseId());
			JOptionPane.showMessageDialog(null, classes.getCourseId());
			cs.setInt(5, classes.getTeachId());
			JOptionPane.showMessageDialog(null, classes.getTeachId());
			cs.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	


}
