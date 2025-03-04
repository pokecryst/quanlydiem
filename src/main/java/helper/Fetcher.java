package helper;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import dao.ClassesDao;
import dao.EnrollStuDao;

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
}
