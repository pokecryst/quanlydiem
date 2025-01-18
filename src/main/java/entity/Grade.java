package entity;

public class Grade {
	private int gradeId;
	private double midScore;
	private double finalScore;
	private double avgScore;

	public Grade() {}


	public Grade(int gradeId, double midScore, double finalScore) {
		super();
		this.gradeId = gradeId;
		this.midScore = midScore;
		this.finalScore = finalScore;
		this.avgScore = Math.round((midScore + finalScore * 2) / 3.0 * 100.0) / 100.0;
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
	

	public double getAvgScore() {
		return avgScore;
	}


	public void setAvgScore(double avgScore) {
		this.avgScore = avgScore;
	}


	@Override
	public String toString() {
		return "Grade [gradeId=" + gradeId + ", midScore=" + midScore + ", finalScore=" + finalScore + ", avgScore="
				+ avgScore + "]";
	}
	
	//average calc
	
	public double calcAvg(double midScore, double finalScore) {
		var average = Math.round((midScore + finalScore * 2) / 3.0 * 100.0) / 100.0;
        
		return average;
		
	}


	


}
