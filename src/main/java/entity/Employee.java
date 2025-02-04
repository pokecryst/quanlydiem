package entity;

import java.sql.Date;
import java.time.LocalDate;

public class Employee {
	private int empId;
	private String empName;
	private Boolean empGender;
	private LocalDate empDob;
	private String empPhone;
	private String empAddress;
	private LocalDate empHireDate;
	private String empImage;

	public Employee() {};
	public Employee(int empId, String empName, Boolean empGender, LocalDate empDob, String empPhone, String empAddress, LocalDate empHireDate,
	    String empImage) {
		super();
		this.empId       = empId;
		this.empName     = empName;
		this.empGender   = empGender;
		this.empDob      = empDob;
		this.empPhone    = empPhone;
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

	public Boolean isEmpGender() {
		return empGender;
	}
	
	
	
	public void setEmpGender(Boolean empGender) {
		this.empGender = empGender;
	}

	public LocalDate getEmpDob() {
		return empDob;
	}

	public void setEmpDob(LocalDate empDob) {
		this.empDob = empDob;
	}

	public String getEmpAddress() {
		return empAddress;
	}

	public void setEmpAddress(String empAddress) {
		this.empAddress = empAddress;
	}

	public LocalDate getEmpHireDate() {
		return empHireDate;
	}

	public void setEmpHireDate(LocalDate empHireDate) {
		this.empHireDate = empHireDate;
	}

	public String getEmpImage() {
		return empImage;
	}

	public void setEmpImage(String empImage) {
		this.empImage = empImage;
	}
	
	
	public String getEmpPhone() {
		return empPhone;
	}
	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", empGender=" + empGender + ", empDob=" + empDob
				+ ", empPhone=" + empPhone + ", empAddress=" + empAddress + ", empHireDate=" + empHireDate
				+ ", empImage=" + empImage + "]";
	}






}
