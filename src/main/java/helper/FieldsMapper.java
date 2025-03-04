package helper;

import java.util.Date;
import java.awt.Image;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class FieldsMapper {
	
	public static void setTextField(JTextField field, Object value) {
        if (value != null) field.setText(value.toString());
    }
	
	public static void setGenderRadio(JRadioButton male, JRadioButton female, Object value) {
		if(value!= null) {
			var bool = (boolean) value;
			if(bool) {
				male.setSelected(true);
			}else {
				female.setSelected(true);
			}
		}
	}

	public static void setDateChooser(JDateChooser chooser, Object value) {
	    if (value == null) {
	        chooser.setDate(null);
	        return;
	    }

	    if (value instanceof java.util.Date) {
	        chooser.setDate((java.util.Date) value);
	    } 
	    else if (value instanceof java.sql.Date) {
	        chooser.setDate(new java.util.Date(((java.sql.Date) value).getTime()));
	    } 
	    else if (value instanceof java.time.LocalDate) {
	        chooser.setDate(java.sql.Date.valueOf((LocalDate) value));
	    } 
	    else if (value instanceof Timestamp) {
	        chooser.setDate(new java.util.Date(((Timestamp) value).getTime()));
	    }
	    else if (value instanceof String) {
	        // Try parsing from a String format (e.g., "yyyy-MM-dd")
	        try {
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            chooser.setDate(sdf.parse((String) value));
	        } catch (ParseException e) {
	            e.printStackTrace(); // Handle parsing errors
	        }
	    } 
	    else {
	        System.out.println("Unsupported date format: " + value.getClass().getName());
	    }
	}
    
	public static void setImageLabel(JLabel label, Object value) {
		if (value != null && value instanceof ImageIcon) {
			ImageIcon icon = (ImageIcon) value;
			label.setIcon(new ImageIcon(icon.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH)));
		}else {
			label.setIcon(null);
		}
		
	}
    
	public static void setComboBoxValue(JComboBox comboBox, Object value) {
		if (value != null)
			comboBox.setSelectedItem(value);
	}

    public static Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
	public static void setCheckBoxValue(JCheckBox checkBox, Object value) {
		if (value != null)
			checkBox.setSelected((boolean) value);
	}
    
	public LocalDate parseLocalDate(String dateStr) {
		try {
			return LocalDate.parse(dateStr);
		} catch (DateTimeParseException ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
