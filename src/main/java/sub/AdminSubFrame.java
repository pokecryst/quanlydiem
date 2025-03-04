package sub;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import entity.Account;
import sub.AcountSub;
import sub.CourseSub;
import sub.EmployeeSub;
import sub.StudentSub;
import sub.TeacherSub;

public class AdminSubFrame extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTabbedPane tabbedPane;

    public AdminSubFrame(Account acc) {
        setLayout(null);
//        setPreferredSize(new Dimension(1180, 768));
        setPreferredSize(new Dimension(1500, 900));
        setBorder(new EmptyBorder(5, 5, 5, 5));

        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setPreferredSize(new Dimension(50, 50));
        tabbedPane.setBounds(0, 0, 1164, 729);
        add(tabbedPane);
        
        JPanel panelEmployee = new EmployeeSub();
        setIcon(tabbedPane, panelEmployee, "D:\\Repos\\quanlydiem\\icon\\employee (1).png", "Employee Manager");

        JPanel panelAccount = new AcountSub();
        panelAccount.setBackground(Color.WHITE);
        setIcon(tabbedPane, panelAccount, "D:\\Repos\\quanlydiem\\icon\\user.png", "Account Manager");

        JPanel panelTeacher = new TeacherSub();
        setIcon(tabbedPane, panelTeacher, "D:\\Repos\\quanlydiem\\icon\\teacher.png", "Teacher");

        JPanel panelCourse = new CourseSub();
        setIcon(tabbedPane, panelCourse, "D:\\Repos\\quanlydiem\\icon\\online-course.png", "Course & Class");

        JPanel panelStudent = new StudentSub();
        setIcon(tabbedPane, panelStudent, "D:\\Repos\\quanlydiem\\icon\\graduated (1).png", "Student");

        MFrame_ClassManagePanel2 panelClassManger = new MFrame_ClassManagePanel2(acc);
        setIcon(tabbedPane, panelClassManger, "D:\\Repos\\quanlydiem\\icon\\classroom.png", "Class Manager");

        
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tabbedPane.getSelectedIndex();
                if (selectedIndex == 6) {
                	panelClassManger.populateTree();
                }
            }
        });
    }
    
    private void setIcon(JTabbedPane tabbedPane, JPanel panel, String imagePath, String tabTitle) {
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        tabbedPane.addTab(tabTitle, resizedIcon, panel, null);
    }

    private void setIcon(JTabbedPane tabbedPane, MFrame_ClassManagePanel2 panel, String imagePath, String tabTitle) {
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        tabbedPane.addTab(tabTitle, resizedIcon, panel, null);
    }

}
