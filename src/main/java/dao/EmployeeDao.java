package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import entity.Employee;
import service.ConnectDB;

public class EmployeeDao {
	private static final String EMP_PREFIX = "2";
	private static final Map<String, Integer> hireCount = new HashMap<>();
	
	public static Integer generateEmpId(int year, int month) {
		var monthStr = String.format("%02d", month);
		var yearStr = String.format("%02d", year % 100);
		
		String key = yearStr + monthStr;
		
		// Increment student count for this year-month
		hireCount.put(key, hireCount.getOrDefault(key, 0) + 1);
        int empNumber = hireCount.get(key);

        // Format student number to be three digits (001, 002, etc.)
        Integer finalId = Integer.parseInt(EMP_PREFIX + yearStr + monthStr + String.format("%03d", empNumber));
        // Construct final student ID
        return finalId;
		
	}
	
	public List<Employee> selectEmployee() {
		List<Employee> list = new ArrayList<>();
		try (var conn = ConnectDB.getCon();
				var cs = conn.prepareCall("{call selectEmp()}");
				var rs = cs.executeQuery())
		{

			while (rs.next()) {
				var emp = new Employee();
				emp.setEmpId(rs.getInt("empId"));
				emp.setEmpName(rs.getString("empName"));
				emp.setEmpGender(rs.getBoolean("empGender"));
				emp.setEmpDob(rs.getDate("empDob").toLocalDate());
				emp.setEmpPhone(rs.getString("EmpPhone"));
				emp.setEmpAddress(rs.getString("empAddress"));
				emp.setEmpHireDate(rs.getDate("empHireDate").toLocalDate());
				emp.setEmpImage(rs.getString("empImage"));
				emp.setRoleId(rs.getInt("roleId"));
				list.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public Employee selectEmpByID(int id) {
	    Employee emp = null;
	    try (var conn = ConnectDB.getCon();
	         var cs = conn.prepareCall("{call selectEmpByID(?)}")) {

	        cs.setInt(1, id);
	        try (var rs = cs.executeQuery()) {
	            if (rs.next()) {
	                emp = new Employee();
	                emp.setEmpId(rs.getInt("empId"));
	                emp.setEmpName(rs.getString("empName"));
	                emp.setEmpGender(rs.getBoolean("empGender"));
	                emp.setEmpDob(rs.getDate("empDob").toLocalDate());
	                emp.setEmpPhone(rs.getString("empPhone"));
	                emp.setEmpAddress(rs.getString("empAddress"));
	                emp.setEmpHireDate(rs.getDate("empHireDate").toLocalDate());
	                emp.setEmpImage(rs.getString("empImage"));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return emp;  // Will be null if no record found
	}
	
	public String selectEmpNameById(int id) {
		var empName = "";
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call selectEmpNameById(?)}")) {
			cs.setInt(1, id);
			var rs = cs.executeQuery();
			while (rs.next()) {
				empName = rs.getString("empName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empName;
	}

//	get employee id without account
	public List<Integer> getEmpIdsWithoutAccount() {
		List<Integer> listEmpId = new ArrayList<>();
		try (var conn = ConnectDB.getCon();
		    var cs = conn.prepareCall("{call getEmpIdsWithoutAccount()}");
		    var rs = cs.executeQuery()) {
			while (rs.next()) {
				var empId = (rs.getInt("empId"));
				listEmpId.add(empId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listEmpId;
	}
	
	public List<Employee> selectTeacher(){
		List<Employee> list = new ArrayList<>();
        try (var conn = ConnectDB.getCon();
                var cs = conn.prepareCall("{call selectTeacher()}");
                var rs = cs.executeQuery())
        {
        	while (rs.next()) {
				var emp = new Employee();
				emp.setEmpId(rs.getInt("empId"));
				emp.setEmpName(rs.getString("empName"));
				emp.setEmpGender(rs.getBoolean("empGender"));
				emp.setEmpDob(rs.getDate("empDob").toLocalDate());
				emp.setEmpPhone(rs.getString("EmpPhone"));
				emp.setEmpAddress(rs.getString("empAddress"));
				emp.setEmpHireDate(rs.getDate("empHireDate").toLocalDate());
				emp.setEmpImage(rs.getString("empImage"));
				list.add(emp);
			}
                
	}catch (SQLException e) {
        e.printStackTrace();
    }
        return list;
	}

	// update employee
	public void updateEmployee(Employee emp) {
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call updateEmp(?, ?, ?, ?, ?, ?, ?, ?)}")) {
			cs.setString(1, emp.getEmpName());
			cs.setBoolean(2, emp.getEmpGender());
			cs.setDate(3, java.sql.Date.valueOf(emp.getEmpDob()));
			cs.setString(4, emp.getEmpPhone());
			cs.setString(5, emp.getEmpAddress());
			cs.setString(6, emp.getEmpImage());
			cs.setInt(7, emp.getRoleId());
			cs.setInt(8, emp.getEmpId());

			cs.execute();
			JOptionPane.showMessageDialog(null, "Update Success");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertEmployee(Employee emp, int year, int month) {
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call createEmp(?, ?, ?, ?, ?, ?, ?, ?)}")) {
			cs.setInt(1, generateEmpId(year, month));
			cs.setString(2, emp.getEmpName());
			cs.setBoolean(3, emp.getEmpGender());
			cs.setDate(4, java.sql.Date.valueOf(emp.getEmpDob()));
			cs.setString(5, emp.getEmpPhone());
			cs.setString(6, emp.getEmpAddress());
			cs.setString(7, (emp.getEmpImage() != null) ? emp.getEmpImage(): "images/a.jpg");
			cs.setInt(8, emp.getRoleId());
			cs.execute();
			JOptionPane.showMessageDialog(null, "Add Success");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Add Fail");
		}
	}
	
	public void deleteEmployee(int empId) {
	    try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call deleteEmp(?)}")) {
	        cs.setInt(1, empId);
	        cs.execute();
	       
	        JOptionPane.showMessageDialog(null, "Deleted successfully!");
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Failed! Check if Employee is being assigned to any Accounts or Classes");
	    }
	}

	
	// test function
		public Integer getRoleIdByEmpId(Integer empId) {
			var sql = "{call GetRoleIdByEmpId(?)}";
			try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall(sql)) {
				cs.setInt(1, empId);
				try (var rs = cs.executeQuery()) {
					if (rs.next()) {
						return rs.getInt("roleId");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		//paging
		public List<Employee> pagingTeacher(Integer currentPage, Integer numberOfRows) {
			List<Employee> list = new ArrayList<>();
			try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call pagingTeacher(?, ?)}")) {
				cs.setInt(1, currentPage);
				cs.setInt(2, numberOfRows);
				try (var rs = cs.executeQuery()) {
					while (rs.next()) {
						var emp = new Employee();
						emp.setEmpId(rs.getInt("empId"));
						emp.setEmpName(rs.getString("empName"));
						emp.setEmpGender(rs.getBoolean("empGender"));
						emp.setEmpDob(rs.getDate("empDob").toLocalDate());
						emp.setEmpPhone(rs.getString("EmpPhone"));
						emp.setEmpAddress(rs.getString("empAddress"));
						emp.setEmpHireDate(rs.getDate("empHireDate").toLocalDate());
						emp.setEmpImage(rs.getString("empImage"));
						emp.setRoleId(rs.getInt("roleId"));
						list.add(emp);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return list;
		}
		
		public Integer countTeacher() {
			var count = 0;

			try (var conn = ConnectDB.getCon();
					var cs = conn.prepareCall("{call countTeacher()}");
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
		
		public List<Employee> pagingEmp(Integer currentPage, Integer numberOfRows) {
			List<Employee> list = new ArrayList<>();
			try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call pagingEmp(?, ?)}")) {
				cs.setInt(1, currentPage);
				cs.setInt(2, numberOfRows);
				try (var rs = cs.executeQuery()) {
					while (rs.next()) {
						var emp = new Employee();
						emp.setEmpId(rs.getInt("empId"));
						emp.setEmpName(rs.getString("empName"));
						emp.setEmpGender(rs.getBoolean("empGender"));
						emp.setEmpDob(rs.getDate("empDob").toLocalDate());
						emp.setEmpPhone(rs.getString("EmpPhone"));
						emp.setEmpAddress(rs.getString("empAddress"));
						emp.setEmpHireDate(rs.getDate("empHireDate").toLocalDate());
						emp.setEmpImage(rs.getString("empImage"));
						emp.setRoleId(rs.getInt("roleId"));
						list.add(emp);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return list;
		}
		
		public Integer countEmp() {
			var count = 0;

			try (var conn = ConnectDB.getCon();
					var cs = conn.prepareCall("{call countEmp()}");
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




