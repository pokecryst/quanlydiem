package entity;

public class GradeStu {
	private String  stuName;
	private int gradeId;
	private double midScore;
	private double finalScore;
	private double avgScore;
	private int enrollId;

	public GradeStu(String stuName, int gradeId, double midScore, double finalScore, double avgScore, int enrollId) {
		super();
		this.stuName    = stuName;
		this.gradeId    = gradeId;
		this.midScore   = midScore;
		this.finalScore = finalScore;
		this.avgScore = avgScore;
		this.enrollId   = enrollId;
	}
	public GradeStu() {}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public int getGradeId() {
		return gradeId;
	}
	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}
	public double getMidScore() {
		return midScore;
	}
	public void setMidScore(double midScore) {
		this.midScore = midScore;
	}
	public double getFinalScore() {
		return finalScore;
	}
	public void setFinalScore(double finalScore) {
		this.finalScore = finalScore;
	}
	public int getEnrollId() {
		return enrollId;
	}
	public void setEnrollId(int enrollId) {
		this.enrollId = enrollId;
	}
	public double getAvgScore() {
		return avgScore;
	}
	public void setAvgScore(double avgScore) {
		this.avgScore = avgScore;
	}
	@Override
	public String toString() {
		return "GradeStu [stuName=" + stuName + ", gradeId=" + gradeId + ", midScore=" + midScore + ", finalScore="
		    + finalScore + ", avgScore=" + avgScore + ", enrollId=" + enrollId + "]";
	}

	




}
