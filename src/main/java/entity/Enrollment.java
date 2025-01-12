package entity;

import java.sql.Date;

public class Enrollment {
	private int enrollId;
	private Date enrollDate;

	public Enrollment() {}

	public Enrollment(int enrollId, Date enrollDate) {
		super();
		this.enrollId   = enrollId;
		this.enrollDate = enrollDate;
	}

	public int getEnrollId() {
		return enrollId;
	}

	public void setEnrollId(int enrollId) {
		this.enrollId = enrollId;
	}

	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}


	@Override
	public String toString() {
		return "Enrollment [enrollId=" + enrollId + ", enrollDate=" + enrollDate + "]";
	}





}
