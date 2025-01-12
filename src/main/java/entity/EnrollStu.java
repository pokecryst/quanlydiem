package entity;

import java.sql.Date;

public class EnrollStu {
	private int enrollId;
	private Date enrollDate;
	private Student student;

	public EnrollStu() {}

	public EnrollStu(int enrollId, Date enrollDate) {
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

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Override
	public String toString() {
		return "Enrollment [enrollId=" + enrollId + ", enrollDate=" + enrollDate + "]";
	}





}
