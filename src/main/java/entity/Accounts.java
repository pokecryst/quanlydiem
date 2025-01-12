package entity;

import java.util.Date;

public class Accounts {
	private enum AccType {
		ADMIN, STAFF, TEACHER
	}

	private int accId;
	private String accEmail;
	private String accPass;
	private AccType accType;
	private Date CreatedDate;

	public Accounts() {}

	public Accounts(int accId, String accEmail, String accPass, AccType accType, Date createdDate) {
		super();
		this.accId    = accId;
		this.accEmail = accEmail;
		this.accPass  = accPass;
		this.accType  = accType;
		CreatedDate   = createdDate;
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

	public Date getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(Date createdDate) {
		CreatedDate = createdDate;
	}

	@Override
	public String toString() {
		return "Accounts [accId=" + accId + ", accEmail=" + accEmail + ", accPass=" + accPass + ", accType=" + accType
		    + ", CreatedDate=" + CreatedDate + "]";
	}



}
