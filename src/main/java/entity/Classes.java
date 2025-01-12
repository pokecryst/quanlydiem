package entity;

import java.util.Date;

public class Classes {
	private int classId;
	private String className;
	private Date startDate;
	private Date endDate;
	private int courseId;

	public Classes() {};

	public Classes(int classId, String className, Date startDate, Date endDate, int courseId) {
		super();
		this.classId   = classId;
		this.className = className;
		this.startDate = startDate;
		this.endDate   = endDate;
		this.courseId = courseId;
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

	@Override
	public String toString() {
		return "Class [classId=" + classId + ", className=" + className + ", startDate=" + startDate + ", endDate="
		    + endDate + "]";
	}


}
