package entity;

import java.time.LocalDate;

public class Account {

	private int    accId;
	private String accEmail;
	private String accPass;
	private LocalDate accCreateDate;
	private Boolean   accStatus;
	private int     empId;
	private int roleId;


	public Account() {
	}

	public Account(String accEmail, String accPass, Boolean accStatus, int empId, int roleId) {
		this.accEmail  = accEmail;
		this.accPass   = accPass;
		this.accStatus = accStatus;
		this.empId     = empId;
		this.roleId    = roleId;
		
	}
	
	
	public Account(int accId, String accEmail, String accPass, LocalDate accCreateDate, Boolean accStatus, int empId,
			int roleId) {
		super();
		this.accId = accId;
		this.accEmail = accEmail;
		this.accPass = accPass;
		this.accCreateDate = accCreateDate;
		this.accStatus = accStatus;
		this.empId = empId;
		this.roleId = roleId;
	}

	public int getAccId() {
		return accId;
	}

	public void setAccId(int accId) {
		this.accId = accId;
	}

	public String getAccEmail() {
		return accEmail;
	}

	public void setAccEmail(String accEmail) {
		this.accEmail = accEmail;
	}

	public String getAccPass() {
		return accPass;
	}

	public void setAccPass(String accPass) {
		this.accPass = accPass;
	}

	public LocalDate getAccCreateDate() {
		return accCreateDate;
	}

	public void setAccCreateDate(LocalDate accCreateDate) {
		this.accCreateDate = accCreateDate;
	}

	public Boolean getAccStatus() {
		return accStatus;
	}

	public void setAccStatus(Boolean accStatus) {
		this.accStatus = accStatus;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}
	
	

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "Account [accId=" + accId + ", accEmail=" + accEmail + ", accPass=" + accPass + ", accCreateDate="
				+ accCreateDate + ", accStatus=" + accStatus + ", empId=" + empId + ", roleId=" + roleId + "]";
	}





}
