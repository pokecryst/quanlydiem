package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import entity.Classes;
import entity.Student;
import service.ConnectDB;

public class StudentDao {
	
	public List<Student> selectAll(){
		List<Student> list = new ArrayList<Student>();
		try(
			var conn = ConnectDB.getCon();
			var cs = conn.prepareCall("{call selectStu()}");
			var rs = cs.executeQuery();
			){
			while(rs.next()) {
				var student = new Student();
				student.setStuId(rs.getInt("stuId"));
				student.setStuName(rs.getString("stuName"));
				student.setStuGender(rs.getBoolean("stuGender"));
				student.setStuDob(rs.getDate("stuDob").toLocalDate());
				student.setStuEmail(rs.getString("stuEmail"));
				student.setStuPhone(rs.getString("stuPhone"));
				student.setStuAddress(rs.getString("stuAddress"));
				student.setStuImage(rs.getString("stuImage"));

				list.add(student);
			}
		}catch (Exception e) {
              e.printStackTrace();
        }
		
		return list;
	}
	
	public void updateStudent(Student stu) {
	    try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call updateStu(?, ?, ?, ?, ?, ?, ?, ?)}")) {
	        cs.setString(1, stu.getStuName());
	        cs.setBoolean(2, stu.isStuGender());
	        cs.setDate(3, java.sql.Date.valueOf(stu.getStuDob()));
	        cs.setString(4, stu.getStuEmail());
	        cs.setString(5, stu.getStuPhone());
	        cs.setString(6, stu.getStuAddress());
	        cs.setString(7, stu.getStuImage());
	        cs.setInt(8, stu.getStuId());
	        cs.execute();
	        JOptionPane.showMessageDialog(null, "Student Updated Successfully");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Failed to Update Student");
	    }
	}
	
	public void insertStudent(Student stu) {
	    try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call createStu(?, ?, ?, ?, ?, ?, ?)}")) {
	        cs.setString(1, stu.getStuName());
	        cs.setBoolean(2, stu.isStuGender());
	        cs.setDate(3, java.sql.Date.valueOf(stu.getStuDob()));
	        cs.setString(4, stu.getStuEmail());
	        cs.setString(5, stu.getStuPhone());
	        cs.setString(6, stu.getStuAddress());
	        cs.setString(7, stu.getStuImage());
	        cs.execute();
	        JOptionPane.showMessageDialog(null, "Student Added Successfully");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Failed to Add Student");
	    }
	}
	
	public void deleteStudent(int stuId) {
	    try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call deleteStu(?)}")) {
	        cs.setInt(1, stuId);
	        cs.execute();
	        JOptionPane.showMessageDialog(null, "Student deleted successfully!");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Failed to delete student!");
	    }
	}

	
	public List<Student> pagingStudent(Integer currentPage, Integer numberOfRows) {
	    List<Student> list = new ArrayList<>();
	    try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call pagingStudent(?, ?)}")) {
	        cs.setInt(1, (currentPage == null) ? 1 : currentPage);
	        cs.setInt(2, numberOfRows);
	        try (var rs = cs.executeQuery()) {
	            while (rs.next()) {
	                var stu = new Student();
	                stu.setStuId(rs.getInt("stuId"));
	                stu.setStuName(rs.getString("stuName"));
	                stu.setStuGender(rs.getBoolean("stuGender"));
	                stu.setStuDob(rs.getDate("stuDob").toLocalDate());
	                stu.setStuEmail(rs.getString("stuEmail"));
	                stu.setStuPhone(rs.getString("stuPhone"));
	                stu.setStuAddress(rs.getString("stuAddress"));
	                stu.setStuImage(rs.getString("stuImage"));
	                list.add(stu);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	
	public List<Student> pagingSearchStudent(String searchName, Boolean searchGender, String searchEmail, String searchPhone, Integer currentPage, Integer numberOfRows) {
	    List<Student> list = new ArrayList<>();
	    
	    try (var conn = ConnectDB.getCon(); 
	         var cs = conn.prepareCall("{call pagingSearchStudent(?, ?, ?, ?, ?, ?)}")) {
	        
	    	
	        cs.setString(1, searchName);
	        cs.setBoolean(2, (searchGender == null) ? null : searchGender);
	        cs.setString(3, searchEmail);
	        cs.setString(4, searchPhone);
	        cs.setInt(5, currentPage);
	        cs.setInt(6, numberOfRows);
	        
	        try (var rs = cs.executeQuery()) {
	            while (rs.next()) {
	                var stu = new Student();
	                stu.setStuId(rs.getInt("stuId"));
	                stu.setStuName(rs.getString("stuName"));
	                stu.setStuGender(rs.getBoolean("stuGender"));
	                stu.setStuDob(rs.getDate("stuDob").toLocalDate());
	                stu.setStuEmail(rs.getString("stuEmail"));
	                stu.setStuPhone(rs.getString("stuPhone"));
	                stu.setStuAddress(rs.getString("stuAddress"));
	                stu.setStuImage(rs.getString("stuImage"));
	                list.add(stu);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return list;
	}
	
	public int countSearchStudent(String searchName, Boolean searchGender, String searchEmail, String searchPhone) {
	    int count = 0;
	    
	    try (var conn = ConnectDB.getCon(); 
	         var cs = conn.prepareCall("{call countSearchStudent(?, ?, ?, ?)}")) {	    	
	        // Set parameters, handling NULL values
	        cs.setObject(1, searchName);
	        cs.setObject(2, searchGender);
	        cs.setObject(3, searchEmail);
	        cs.setObject(4, searchPhone);
	        
	        try (var rs = cs.executeQuery()) {
	            if (rs.next()) {
	                count = rs.getInt(1); 
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return count;
	}


	
	public Integer countStudent() {
		var count = 0;

		try (var conn = ConnectDB.getCon();
				var cs = conn.prepareCall("{call countStudent()}");
				var rs = cs.executeQuery();
				) {
		
			if (rs.next()) {
				count = rs.getInt("total");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}
		
	
}
