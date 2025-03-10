package sub;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private JPanel panelAccount = null;
    private static final int ACCOUNT_TAB_INDEX = 1;
    
    private JPanel panelTeacher = null;
    private static final int TEACHER_TAB_INDEX = 2;
    
    private JPanel panelCourse = null;
    private static final int COURSE_TAB_INDEX = 3;
    
    private JPanel panelStudent = null;
    private static final int STUDENT_TAB_INDEX = 4;
    
    private MFrame_ClassManagePanel2 panelClassManager = null;
    private static final int CLASS_MANAGER_TAB_INDEX = 5;

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
        setIcon(tabbedPane, panelEmployee, "D:\\Repos\\quanlydiem\\icon\\employee (1).png", "Employee");
       
        setIcon(tabbedPane, panelAccount, "D:\\Repos\\quanlydiem\\icon\\user.png", "Account");
      
        setIcon(tabbedPane, panelTeacher, "D:\\Repos\\quanlydiem\\icon\\teacher.png", "Teacher");

        setIcon(tabbedPane, panelCourse, "D:\\Repos\\quanlydiem\\icon\\online-course.png", "Course & Class");

        setIcon(tabbedPane, panelStudent, "D:\\Repos\\quanlydiem\\icon\\graduated (1).png", "Student");

        setIcon(tabbedPane, panelClassManager, "D:\\Repos\\quanlydiem\\icon\\classroom.png", "Class Manager");

        
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
            	 if (tabbedPane.getSelectedIndex() == ACCOUNT_TAB_INDEX && panelAccount == null) {
            		 panelAccount = new AcountSub(); // Load only when clicked
                     tabbedPane.setComponentAt(ACCOUNT_TAB_INDEX, panelAccount);
                     
            	 }
            	// Lazy load Teacher tab
                 else if (tabbedPane.getSelectedIndex() == TEACHER_TAB_INDEX && panelTeacher == null) {
                     panelTeacher = new TeacherSub();
                     tabbedPane.setComponentAt(TEACHER_TAB_INDEX, panelTeacher);
                     System.out.println("Loaded Teacher Panel");
                 }

                 // Lazy load Course tab
                 else if (tabbedPane.getSelectedIndex() == COURSE_TAB_INDEX && panelCourse == null) {
                     panelCourse = new CourseSub();
                     tabbedPane.setComponentAt(COURSE_TAB_INDEX, panelCourse);
                     System.out.println("Loaded Course Panel");
                 }

                 // Lazy load Student tab
                 else if (tabbedPane.getSelectedIndex() == STUDENT_TAB_INDEX && panelStudent == null) {
                     panelStudent = new StudentSub();
                     tabbedPane.setComponentAt(STUDENT_TAB_INDEX, panelStudent);
                     System.out.println("Loaded Student Panel");
                 }

                 // Lazy load Class Manager tab
                 else if (tabbedPane.getSelectedIndex() == CLASS_MANAGER_TAB_INDEX ) {
                     panelClassManager = new MFrame_ClassManagePanel2(acc);
                 	panelClassManager.populateTree();
                     tabbedPane.setComponentAt(CLASS_MANAGER_TAB_INDEX, panelClassManager);
                     System.out.println("Loaded Class Manager Panel");
                 }
            	
        
                
            }
        });
        setVisible(true);
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
