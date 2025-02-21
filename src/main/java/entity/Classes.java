package entity;

import java.sql.Date;

public class Classes {
	private int classId;
	private String className;
	private Date startDate;
	private Date endDate;
	private int courseId;
	private int empId;

	public Classes() {};

	

	public Classes(int classId, String className, Date startDate, Date endDate, int courseId, int empId) {
		super();
		this.classId = classId;
		this.className = className;
		this.startDate = startDate;
		this.endDate = endDate;
		this.courseId = courseId;
		this.empId = empId;
	}



	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
	

	public int getTeachId() {
		return empId;
	}



	public void setTeachId(int empId) {
		this.empId = empId;
	}



	@Override
	public String toString() {
		return "Classes [classId=" + classId + ", className=" + className + ", startDate=" + startDate + ", endDate="
				+ endDate + ", courseId=" + courseId + ", teachId=" + empId + "]";
	}



	


}
