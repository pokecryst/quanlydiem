package entity;

public class Grade {
	private int gradeId;
	private double midScore;
	private double finalScore;

	public Grade() {}

	public Grade(int gradeId, double midScore, double finalScore) {
		super();
		this.gradeId    = gradeId;
		this.midScore   = midScore;
		this.finalScore = finalScore;
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

	@Override
	public String toString() {
		return "Grade [gradeId=" + gradeId + ", midScore=" + midScore + ", finalScore=" + finalScore + "]";
	};


}
