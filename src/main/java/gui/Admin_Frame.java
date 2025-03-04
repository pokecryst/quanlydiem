package gui;

import java.awt.Color;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import sub.AcountSub;
import sub.CourseSub;
import sub.EmployeeSub;
import sub.SSub;
import sub.StudentSub;
import sub.TeacherSub;

public class Admin_frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel            contentPane;
	private JTabbedPane       tabbedPane;
	private JPanel            panel;
	private JPanel            panel_1;
	private JPanel            panel_2;
	private JPanel            panel_3;
	private JPanel            panel_4;
	private JPanel panel_5;


	public Admin_frame() {
		setTitle("AdminFrame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1180, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setBackground(new Color(255, 255, 255));
		tabbedPane.setPreferredSize(new Dimension(50, 50));
		tabbedPane.setBounds(0, 0, 1164, 729);
		contentPane.add(tabbedPane);

		panel_1 = new EmployeeSub();
		tabbedPane.addTab("Employee Manager",
		    new ImageIcon("D:\\Repos\\quanlydiem\\icon\\employee (1).png"), panel_1, null);

		panel = new AcountSub();
		panel.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Account Manager",
		    new ImageIcon("D:\\Repos\\quanlydiem\\icon\\user.png"), panel, null);

		panel_2 = new TeacherSub();
		tabbedPane.addTab("Teacher", new ImageIcon("D:\\Repos\\quanlydiem\\icon\\teacher.png"),
		    panel_2, null);

		panel_3 = new CourseSub();
		tabbedPane.addTab("Course & Class",
		    new ImageIcon("D:\\Repos\\quanlydiem\\icon\\online_course.png"), panel_3, null);

		panel_4 = new StudentSub();
		tabbedPane.addTab("Student",
		    new ImageIcon("D:\\Repos\\quanlydiem\\icon\\graduated (1).png"), panel_4, null);
		

	}
}
