package entity;

public class Course {
	private int courseId;
	private String courseName;
	private String courseDesc;
	private int courseDuration;

	public Course() {};

	public Course(int courseId, String courseName, String courseDesc, int courseDuration) {
		super();
		this.courseId       = courseId;
		this.courseName     = courseName;
		this.courseDesc     = courseDesc;
		this.courseDuration = courseDuration;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseDesc() {
		return courseDesc;
	}

	public void setCourseDesc(String courseDesc) {
		this.courseDesc = courseDesc;
	}

	public int getCourseDuration() {
		return courseDuration;
	}

	public void setCourseDuration(int courseDuration) {
		this.courseDuration = courseDuration;
	}

//	@Override
//	public String toString() {
//		return "Course [courseId=" + courseId + ", courseName=" + courseName + ", courseDesc=" + courseDesc
//		    + ", courseDuration=" + courseDuration + "]";
//	}
	
	@Override
	public String toString() {
		return courseId + " - " + courseName;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Course other = (Course) obj;
	    return this.courseId == other.courseId;
	}

}
