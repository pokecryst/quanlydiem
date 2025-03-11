package popup;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import dao.ClassesDao;
import dao.CourseDao;
import dao.EmployeeDao;
import dao.EnrollStuDao;
import dao.EnrollmentDao;
import dao.GradeDao;
import entity.Classes;
import entity.Course;
import entity.Employee;
import entity.Grade;
import helper.Regex;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import com.toedter.calendar.JDateChooser;

import component.Paging;
import component.TablePage;
import component.Paging.CountFetcher;
import component.TablePage.DataFetcher;

import javax.swing.JComboBox;

import java.awt.FlowLayout;

public class AddClass extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private static AddClass instance = null;
	private int classId;
	private JTextField txtClassName;
	private JDateChooser dateChooser = new JDateChooser();
	private JDateChooser dateChooser_1 = new JDateChooser();
	private JComboBox<Course> cbbCourse = new JComboBox<>();
	private JComboBox<Employee> cbbTeacher = new JComboBox<>();
	private Classes classInfo = new Classes();
	private ClassesDao classesDao = new ClassesDao();

	
	public static AddClass getInstance() {
		if(instance == null) {
			instance = new AddClass();
			
		}
		return instance;
		
	}
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddClass frame = new AddClass();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddClass() {
//		var grade = new GradeDao().selectGradebyId(classId);
		
		setIconifiable(false);
		setClosable(true);
		setTitle("Add Class");
		setBounds(100, 100, 501, 338);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		JPanel UpdateClassPanel = new JPanel();
		getContentPane().add(UpdateClassPanel, BorderLayout.CENTER);
		GridBagLayout gbl_UpdateClassPanel = new GridBagLayout();
		gbl_UpdateClassPanel.columnWidths = new int[] {0, 0, 10, 0};
		gbl_UpdateClassPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_UpdateClassPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE, 1.0};
		gbl_UpdateClassPanel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		UpdateClassPanel.setLayout(gbl_UpdateClassPanel);
		
		JLabel lblClassName = new JLabel("Class Name: ");
		GridBagConstraints gbc_lblClassName = new GridBagConstraints();
		gbc_lblClassName.anchor = GridBagConstraints.EAST;
		gbc_lblClassName.insets = new Insets(0, 0, 5, 5);
		gbc_lblClassName.gridx = 0;
		gbc_lblClassName.gridy = 1;
		UpdateClassPanel.add(lblClassName, gbc_lblClassName);
		
		txtClassName = new JTextField();
		GridBagConstraints gbc_txtClassName = new GridBagConstraints();
		gbc_txtClassName.insets = new Insets(0, 0, 5, 5);
		gbc_txtClassName.anchor = GridBagConstraints.WEST;
		gbc_txtClassName.gridx = 1;
		gbc_txtClassName.gridy = 1;
		UpdateClassPanel.add(txtClassName, gbc_txtClassName);
		txtClassName.setColumns(10);
		
		
		
		
		JLabel lblStartDate = new JLabel("Start Date:");
		GridBagConstraints gbc_lblStartDate = new GridBagConstraints();
		gbc_lblStartDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblStartDate.gridx = 0;
		gbc_lblStartDate.gridy = 2;
		UpdateClassPanel.add(lblStartDate, gbc_lblStartDate);
		
		dateChooser.setDateFormatString("dd/MM/yyyy");
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.gridx = 1;
		gbc_dateChooser.gridy = 2;
		dateChooser.addPropertyChangeListener("date", evt -> {
			Date start = dateChooser.getDate();
			if(start != null) {
				//set the minimum as start which means it cannot be before start
				dateChooser_1.setMinSelectableDate(start);
				//người dùng chọn ngày kết thúc trước ngày bắt đầu thì sẽ thế này
				Date end = dateChooser_1.getDate();
				if (end != null && end.before(start)) {
					dateChooser_1.setDate(start);
				}
			}else {
				
				dateChooser_1.setMinSelectableDate(null);
			}
		});
		
		UpdateClassPanel.add(dateChooser, gbc_dateChooser);
		
		JLabel lblTeacher = new JLabel("Assigned Teacher: ");
		GridBagConstraints gbc_lblTeacher = new GridBagConstraints();
		gbc_lblTeacher.anchor = GridBagConstraints.EAST;
		gbc_lblTeacher.insets = new Insets(0, 0, 5, 5);
		gbc_lblTeacher.gridx = 2;
		gbc_lblTeacher.gridy = 2;
		UpdateClassPanel.add(lblTeacher, gbc_lblTeacher);
		
		
		GridBagConstraints gbc_cbbCourse = new GridBagConstraints();
		gbc_cbbCourse.anchor = GridBagConstraints.WEST;
		gbc_cbbCourse.insets = new Insets(0, 0, 5, 0);
		gbc_cbbCourse.gridx = 3;
		gbc_cbbCourse.gridy = 1;
		
		GridBagConstraints gbc_cbbTeacher = new GridBagConstraints();
		gbc_cbbTeacher.anchor = GridBagConstraints.WEST;
		gbc_cbbTeacher.insets = new Insets(0, 0, 5, 0);
		gbc_cbbTeacher.gridx = 3;
		gbc_cbbTeacher.gridy = 2;
		
		setCbbCourse();
		UpdateClassPanel.add(cbbCourse, gbc_cbbCourse);
		UpdateClassPanel.add(cbbTeacher, gbc_cbbTeacher);
		
		JLabel lblEndDate = new JLabel("End Date:");
		GridBagConstraints gbc_lblEndDate = new GridBagConstraints();
		gbc_lblEndDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblEndDate.gridx = 0;
		gbc_lblEndDate.gridy = 3;
		UpdateClassPanel.add(lblEndDate, gbc_lblEndDate);
		
		JLabel lblCourse = new JLabel("Course: ");
		GridBagConstraints gbc_lblCourse = new GridBagConstraints();
		gbc_lblCourse.anchor = GridBagConstraints.EAST;
		gbc_lblCourse.insets = new Insets(0, 0, 5, 5);
		gbc_lblCourse.gridx = 2;
		gbc_lblCourse.gridy = 1;
		UpdateClassPanel.add(lblCourse, gbc_lblCourse);
		
		JButton btnAdd = new JButton("Add");
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.gridx = 3;
		gbc_btnAdd.gridy = 6;
		btnAdd.addActionListener(this::btnAddClassActionPerformed);
		
		dateChooser_1.setDateFormatString("dd/MM/yyyy");
		GridBagConstraints gbc_dateChooser_1 = new GridBagConstraints();
		gbc_dateChooser_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooser_1.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser_1.gridx = 1;
		gbc_dateChooser_1.gridy = 3;
//		dateChooser_1.addPropertyChangeListener("date", evt ->{
//			Date start = dateChooser.getDate();
//		});
		
		UpdateClassPanel.add(dateChooser_1, gbc_dateChooser_1);
		UpdateClassPanel.add(btnAdd, gbc_btnAdd);
		
	}
	
	@Override
	public void dispose() {
	    instance = null;
	    super.dispose();
	}
	
		
	public void setCbbCourse() {
		var courseDao = new CourseDao();
		var courseTeach = new EmployeeDao();
		
		List<Course> items = courseDao.selectAll();
		List<Employee> items2 = courseTeach.selectTeacher();
		for (Course item : items) {
		    this.cbbCourse.addItem(item);
		}
		for (Employee item : items2) {
			this.cbbTeacher.addItem(item);
		}
//		JOptionPane.showMessageDialog(null, items2);
	};
	
	 
	 protected void btnAddClassActionPerformed(ActionEvent e) {
		 var cl = new Classes();
		 var dao = new ClassesDao();
		 var clName = helper.Valid.checkRegex2(Regex.CLASSNAME, txtClassName.getText());
		 
		 if(clName) {
			 cl.setClassId(classId);
			 cl.setClassName(txtClassName.getText());
			 cl.setStartDate(new java.sql.Date(dateChooser.getDate().getTime()));
			 cl.setEndDate(new java.sql.Date(dateChooser_1.getDate().getTime()));
			 cl.setCourseId(((Course) cbbCourse.getSelectedItem()).getCourseId());
			 cl.setTeachId(((Employee) cbbTeacher.getSelectedItem()).getEmpId());
			 dao.add(cl);
			 JOptionPane.showMessageDialog(null, "Class Added");
			 instance.dispose();
			
			
		 }else {
			 JOptionPane.showMessageDialog(null, "Invalid Input");
		 }
	 }
}

