package helper;

public class Regex {
	public static final String INTNUM = "[0-9]+$";
	public static final String DOUBLE = "^\\d+(\\.\\d+)?$";
	public static final String PHONE = "[0-9]{1,10}+$";
	public static final String STRING = "[\\p{L}\\s]{1,50}$";
	public static final String BOOL = "[0-1]";
	public static final String DATE = "^\\d{4}[-/]\\d{2}[-/]\\d{2}$";
	
	//custom regex
	public static final String CLASSNAME = "^[A-Za-z]{2}\\d{2}$";
}
