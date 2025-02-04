package entity;

import java.time.LocalDate;

public class Account {

	// create enum for account type
	public enum AccType {
		admin, teacher, staff
	}

	private int    accId;
	private String accEmail;
	private String accPass;
	private AccType accType;
	private LocalDate accCreateDate;
	private Boolean   accStatus;
	private int     empId;


	public Account() {
	}

	public Account(String accEmail, String accPass, AccType accType, Boolean accStatus, int empId) {
		this.accEmail  = accEmail;
		this.accPass   = accPass;
		this.accType   = accType;
		this.accStatus = accStatus;
		this.empId     = empId;
	}

	public Account(int accId, String accEmail, String accPass, AccType accType, LocalDate accCreateDate,
	    Boolean accStatus, int empId) {
		this.accId         = accId;
		this.accEmail      = accEmail;
		this.accPass       = accPass;
		this.accType       = accType;
		this.accCreateDate = accCreateDate;
		this.accStatus     = accStatus;
		this.empId         = empId;
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

	public AccType getAccType() {
		return accType;
	}

	public void setAccType(AccType accType) {
		this.accType = accType;
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

	@Override
	public String toString() {
		return "Account [accId=" + accId + ", accEmail=" + accEmail + ", accPass=" + accPass + ", accType=" + accType
		    + ", accCreateDate=" + accCreateDate + ", accStatus=" + accStatus + ", empId=" + empId + "]";
	}



}
