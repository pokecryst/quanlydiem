package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import entity.Employee;
import service.ConnectDB;

public class EmployeeDao {
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

	public void insertEmployee(Employee emp) {
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call createEmp(?, ?, ?, ?, ?, ?, ?)}")) {
			cs.setString(1, emp.getEmpName());
			cs.setBoolean(2, emp.getEmpGender());
			cs.setDate(3, java.sql.Date.valueOf(emp.getEmpDob()));
			cs.setString(4, emp.getEmpPhone());
			cs.setString(5, emp.getEmpAddress());
			cs.setString(6, emp.getEmpImage());
			cs.setInt(7, emp.getRoleId());
			cs.execute();
			JOptionPane.showMessageDialog(null, "Add Success");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Add Fail");
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




