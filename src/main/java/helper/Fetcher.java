package helper;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import dao.ClassesDao;
import dao.EnrollStuDao;
import entity.ClassStatus;
import entity.Classes;

public class Fetcher {
	
	public <T> T fetchDao(Class<T> daoType) {
        if (daoType.equals(EnrollStuDao.class)) {
            return daoType.cast(new EnrollStuDao());
        } else if (daoType.equals(ClassesDao.class)) {
            return daoType.cast(new ClassesDao());
        } else {
            throw new IllegalArgumentException("Unsupported DAO type: " + daoType.getName());
        }
    }
	
	public static void setIcon(JTabbedPane tabbedPane, JPanel panel, String imagePath, String tabTitle) {
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        tabbedPane.addTab(tabTitle, resizedIcon, panel, null);
    }
	
	public static <T extends getID> int getSelectedId(JComboBox<T> comboBox) {
	    T selectedItem = (T) comboBox.getSelectedItem();
	    return (selectedItem != null) ? selectedItem.getID() : -1;  // Return -1 if nothing is selected
	}

	
}
