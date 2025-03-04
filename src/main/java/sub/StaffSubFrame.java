package sub;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import entity.Account;
import sub.AcountSub;
import sub.CourseSub;
import sub.EmployeeSub;
import sub.StudentSub;
import sub.TeacherSub;

public class StaffSubFrame extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTabbedPane tabbedPane;

    public StaffSubFrame(Account acc) {
        setLayout(null);
//        setPreferredSize(new Dimension(1180, 768));
        setPreferredSize(new Dimension(1500, 900));
        setBorder(new EmptyBorder(5, 5, 5, 5));

        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setPreferredSize(new Dimension(50, 50));
        tabbedPane.setBounds(0, 0, 1164, 729);
        add(tabbedPane);

        JPanel panelTeacher = new TeacherSub();
        helper.Fetcher.setIcon(tabbedPane, panelTeacher, "D:\\Repos\\quanlydiem\\icon\\teacher.png", "Teacher");

        JPanel panelCourse = new CourseSub();
        helper.Fetcher.setIcon(tabbedPane, panelCourse, "D:\\Repos\\quanlydiem\\icon\\online-course.png", "Course & Class");

        JPanel panelStudent = new StudentSub();
        helper.Fetcher.setIcon(tabbedPane, panelStudent, "D:\\Repos\\quanlydiem\\icon\\graduated (1).png", "Student");

        MFrame_ClassManagePanel2 panelClassManger = new MFrame_ClassManagePanel2(acc);
        helper.Fetcher.setIcon(tabbedPane, panelClassManger, "D:\\Repos\\quanlydiem\\icon\\classroom.png", "Class Manager");
    }
    
    
}
