package entity;

import java.time.LocalDate;

public class EnrollStu {
	private int enrollId;
	private LocalDate enrollDate;
	private int stuId;
	private Student student;
	private int classId;

	public EnrollStu() {}

	public EnrollStu(int enrollId, LocalDate enrollDate) {
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

	@Override
	public String toString() {
		return "EnrollStu [enrollId=" + enrollId + ", enrollDate=" + enrollDate + ", stuId=" + stuId + ", student="
				+ student + ", classId=" + classId + "]";
	}

	





}
