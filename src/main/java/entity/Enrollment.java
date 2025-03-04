package entity;

import java.sql.Date;

public class Enrollment {
	private int enrollId;
	private Date enrollDate;
	private int stuId;
	private int classId;

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

	public int getStuId() {
		return stuId;
	}

	public void setStuId(int stuId) {
		this.stuId = stuId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	@Override
	public String toString() {
		return "Enrollment [enrollId=" + enrollId + ", enrollDate=" + enrollDate + ", stuId=" + stuId + ", classId="
				+ classId + "]";
	}






}
