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
				list.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
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

	// update employee
	public void updateEmployee(Employee emp) {
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call updateEmp(?, ?, ?, ?, ?, ?, ?, ?)}")) {
			cs.setInt(1, emp.getEmpId());
			cs.setString(2, emp.getEmpName());
			cs.setBoolean(3, emp.isEmpGender());
			cs.setString(4, emp.getEmpPhone());
			cs.setString(5, emp.getEmpAddress());
			cs.setDate(6, java.sql.Date.valueOf(emp.getEmpHireDate()));
			cs.setDate(7, java.sql.Date.valueOf(emp.getEmpDob()));
			cs.setString(8, emp.getEmpImage());
			cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertEmployee(Employee emp) {
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call createEmp(?, ?, ?, ?, ?, ?, ?)}")) {
			cs.setString(1, emp.getEmpName());
			cs.setBoolean(2, emp.isEmpGender());
			cs.setDate(3, java.sql.Date.valueOf(emp.getEmpDob()));
			cs.setDate(4, java.sql.Date.valueOf(emp.getEmpHireDate()));
			cs.setString(5, emp.getEmpPhone());
			cs.setString(6, emp.getEmpAddress());
			cs.setString(7, emp.getEmpImage());
			cs.execute();
			JOptionPane.showMessageDialog(null, "Add Success");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Add Fail");
		}
	}
}




