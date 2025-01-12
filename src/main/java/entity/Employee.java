package entity;

import java.sql.Date;

public class Employee {
	private int empId;
	private String empName;
	private boolean empGender;
	private Date empDob;
	private String empAddress;
	private Date empHireDate;
	private String empImage;

	public Employee() {};
	public Employee(int empId, String empName, boolean empGender, Date empDob, String empAddress, Date empHireDate,
	    String empImage) {
		super();
		this.empId       = empId;
		this.empName     = empName;
		this.empGender   = empGender;
		this.empDob      = empDob;
		this.empAddress  = empAddress;
		this.empHireDate = empHireDate;
		this.empImage    = empImage;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public boolean isEmpGender() {
		return empGender;
	}

	public void setEmpGender(boolean empGender) {
		this.empGender = empGender;
	}

	public Date getEmpDob() {
		return empDob;
	}

	public void setEmpDob(Date empDob) {
		this.empDob = empDob;
	}

	public String getEmpAddress() {
		return empAddress;
	}

	public void setEmpAddress(String empAddress) {
		this.empAddress = empAddress;
	}

	public Date getEmpHireDate() {
		return empHireDate;
	}

	public void setEmpHireDate(Date empHireDate) {
		this.empHireDate = empHireDate;
	}

	public String getEmpImage() {
		return empImage;
	}

	public void setEmpImage(String empImage) {
		this.empImage = empImage;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", empGender=" + empGender + ", empDob=" + empDob
		    + ", empAddress=" + empAddress + ", empHireDate=" + empHireDate + ", empImage=" + empImage + "]";
	}





}
