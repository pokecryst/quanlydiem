package helper;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class FieldsMapper {
	
	public static void setTextField(JTextField field, Object value) {
        if (value != null) field.setText(value.toString());
    }

    public static void setDateChooser(JDateChooser chooser, Object value) {
        if (value != null && value instanceof String) {
            chooser.setDate(parseDate((String) value));
        }
    }

    public static Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
