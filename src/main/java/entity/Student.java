package entity;

import java.time.LocalDate;

public class Student {
	private int     stuId;
	private String  stuName;
	private boolean stuGender;
	private LocalDate    stuDob;
	private String  stuEmail;
	private String  stuPhone;
	private String  stuAddress;
	private String  stuImage;


	public Student() {};
	public Student(int stuId, String stuName, boolean stuGender, LocalDate stuDob, String stuEmail, String stuPhone,
	    String stuAddress, String stuImage) {
		super();
		this.stuId      = stuId;
		this.stuName    = stuName;
		this.stuGender     = stuGender;
		this.stuDob     = stuDob;
		this.stuEmail   = stuEmail;
		this.stuPhone   = stuPhone;
		this.stuAddress = stuAddress;
		this.stuImage   = stuImage;
	}

	public int getStuId() {
		return stuId;
	}
	public void setStuId(int stuId) {
		this.stuId = stuId;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public boolean isStuGender() {
		return stuGender;
	}
	public void setStuGender(boolean stuGender) {
		this.stuGender = stuGender;
	}
	public LocalDate getStuDob() {
		return stuDob;
	}
	public void setStuDob(LocalDate stuDob) {
		this.stuDob = stuDob;
	}
	public String getStuEmail() {
		return stuEmail;
	}
	public void setStuEmail(String stuEmail) {
		this.stuEmail = stuEmail;
	}
	public String getStuPhone() {
		return stuPhone;
	}
	public void setStuPhone(String stuPhone) {
		this.stuPhone = stuPhone;
	}
	public String getStuAddress() {
		return stuAddress;
	}
	public void setStuAddress(String stuAddress) {
		this.stuAddress = stuAddress;
	}
	public String getStuImage() {
		return stuImage;
	}
	public void setStuImage(String stuImage) {
		this.stuImage = stuImage;
	}

	@Override
	public String toString() {
		return "Student [stuId=" + stuId + ", stuName=" + stuName + ", stuGender=" + stuGender + ", stuDob=" + stuDob
		    + ", stuEmail=" + stuEmail + ", stuPhone=" + stuPhone + ", stuAddress=" + stuAddress + ", stuImage=" + stuImage
		    + "]";
	}




}
