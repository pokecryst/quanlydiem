package entity;

import java.time.LocalDate;

public class EnrollStu {
	private int enrollId;
	private LocalDate enrollDate;
	private boolean passStatus;
	private int stuId;
	private Student student;
	private int classId;

	public EnrollStu() {}

	public EnrollStu(int enrollId, LocalDate enrollDate, boolean passStatus, int stuId, Student student, int classId) {
		super();
		this.enrollId = enrollId;
		this.enrollDate = enrollDate;
		this.passStatus = passStatus;
		this.stuId = stuId;
		this.student = student;
		this.classId = classId;
	}



	public int getEnrollId() {
		return enrollId;
	}

	public void setEnrollId(int enrollId) {
		this.enrollId = enrollId;
	}

	public LocalDate getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(LocalDate enrollDate) {
		this.enrollDate = enrollDate;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getStuId() {
		return stuId;
	}

	public void setStuId(int stuId) {
		this.stuId = stuId;
	}
	
	

	public boolean isPassStatus() {
		return passStatus;
	}

	public void setPassStatus(boolean passStatus) {
		this.passStatus = passStatus;
	}

	@Override
	public String toString() {
		return "EnrollStu [enrollId=" + enrollId + ", enrollDate=" + enrollDate + ", passStatus=" + passStatus
				+ ", stuId=" + stuId + ", student=" + student + ", classId=" + classId + "]";
	}

	
	





}
