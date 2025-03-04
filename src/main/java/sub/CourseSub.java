package sub;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dao.ClassesDao;
import dao.CourseDao;
import dao.EmployeeDao;
import entity.Classes;
import entity.Course;
import entity.Employee;
import gui.TablePage;
import gui.TablePage.DataFetcher;
import gui.Paging.CountFetcher;

public class CourseSub extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel            lblNewLabel;
	private JLabel            lblCourseId;
	private JTextField        txtCourseId;
	private JLabel            lblCourseName;
	private JTextField        txtCourseName;
	private JLabel            lblCourseDesc;
	private JTextPane         txtCourseDesc;
	private JLabel            lblCourseDuration;
	private JTextField        txtCourseDura;
	private JButton           btnAddCourse;
	private JButton           btnUpdateCourse;
	private JButton           btnDeleteCourse;
	private JLabel            lblNewLabel_1;
	private JLabel            lblClassId;
	private JTextField        textField_3;
	private JLabel            lblNewLabel_2;
	private JTextField        textField_4;
	private JLabel            lblNewLabel_3;
	private JLabel            lblNewLabel_4;
	private JLabel            lblNewLabel_5;
	private JLabel            lblNewLabel_6;
	private JButton           btnAddClass;
	private JButton           btnAddUpdate;
	private JButton           btnDelClass;
	private JDateChooser      dateChooser;
	private JDateChooser      dateChooser_1;
	
	private JComboBox<Course> cbbCourse = new JComboBox<>();
	private JComboBox<Employee> cbbTeacher = new JComboBox<>();
	
	private CourseDao courseDao = new CourseDao();
	private EmployeeDao empDao = new EmployeeDao();
	private TablePage tablePageCourse;
	private TablePage tablePageClasses;
	private Map<Integer, Class<?>> columnMapping = new HashMap<>();
	private Map<Integer, Consumer<Object>> accountsMappings = new HashMap<>();



	/**
	 * Create the panel.
	 */
	public CourseSub() {
		setLayout(null);
		setBounds(0, 0, 892, 724);

		lblNewLabel = new JLabel("Course");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel.setBounds(10, 11, 578, 30);
		add(lblNewLabel);

		lblCourseId = new JLabel("Course ID:");
		lblCourseId.setBounds(598, 41, 90, 30);
		add(lblCourseId);

		txtCourseId = new JTextField();
		txtCourseId.setColumns(10);
		txtCourseId.setBounds(698, 41, 180, 30);
		add(txtCourseId);

		lblCourseName = new JLabel("Course Name:");
		lblCourseName.setBounds(598, 85, 90, 30);
		add(lblCourseName);

		txtCourseName = new JTextField();
		txtCourseName.setColumns(10);
		txtCourseName.setBounds(698, 82, 180, 30);
		add(txtCourseName);

		lblCourseDesc = new JLabel("Course DESC:");
		lblCourseDesc.setBounds(598, 154, 90, 30);
		add(lblCourseDesc);

		txtCourseDesc = new JTextPane();
		txtCourseDesc.setBorder(new LineBorder(new Color(192, 192, 192)));
		txtCourseDesc.setBounds(698, 126, 180, 85);
		add(txtCourseDesc);

		lblCourseDuration = new JLabel("Duration:");
		lblCourseDuration.setBounds(598, 222, 90, 30);
		add(lblCourseDuration);

		txtCourseDura = new JTextField();
		txtCourseDura.setColumns(10);
		txtCourseDura.setBounds(698, 222, 180, 30);
		add(txtCourseDura);

		btnAddCourse = new JButton("Add");
		btnAddCourse.addActionListener(this::btnAddCourseActionPerformed);
		btnAddCourse.setBounds(598, 281, 89, 30);
		add(btnAddCourse);

		btnUpdateCourse = new JButton("Update");
		btnUpdateCourse.addActionListener(this::btnUpdateCourseActionPerformed);
		btnUpdateCourse.setBounds(698, 281, 89, 30);
		add(btnUpdateCourse);

		btnDeleteCourse = new JButton("Delete");
		btnDeleteCourse.setBounds(797, 281, 89, 30);
		add(btnDeleteCourse);

		lblNewLabel_1 = new JLabel("Class");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_1.setBounds(10, 352, 578, 30);
		add(lblNewLabel_1);

		lblClassId = new JLabel("Class ID:");
		lblClassId.setBounds(598, 382, 90, 30);
		add(lblClassId);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(698, 382, 180, 30);
		add(textField_3);

		lblNewLabel_2 = new JLabel("Class Name:");
		lblNewLabel_2.setBounds(598, 423, 90, 30);
		add(lblNewLabel_2);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(698, 423, 180, 30);
		add(textField_4);

		lblNewLabel_3 = new JLabel("Start Date:");
		lblNewLabel_3.setBounds(598, 464, 90, 30);
		add(lblNewLabel_3);

		lblNewLabel_4 = new JLabel("End Date:");
		lblNewLabel_4.setBounds(598, 505, 90, 30);
		add(lblNewLabel_4);

		lblNewLabel_5 = new JLabel("Course ID:");
		lblNewLabel_5.setBounds(598, 547, 90, 30);
		add(lblNewLabel_5);

		lblNewLabel_6 = new JLabel("Emp ID:");
		lblNewLabel_6.setBounds(598, 588, 90, 30);
		add(lblNewLabel_6);

		btnAddClass = new JButton("Add");
		btnAddClass.addActionListener(this::btnAddClassActionPerformed);
		btnAddClass.setBounds(598, 637, 89, 30);
		add(btnAddClass);

		btnAddUpdate = new JButton("Update");
		btnAddUpdate.addActionListener(this::btnAddUpdateActionPerformed);
		btnAddUpdate.setBounds(698, 637, 89, 30);
		add(btnAddUpdate);

		btnDelClass = new JButton("Delete");
		btnDelClass.setBounds(797, 637, 89, 30);
		btnDelClass.addActionListener(this::btnDeleteClassActionPerformed);
		add(btnDelClass);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(698, 464, 180, 30);
		add(dateChooser);

		dateChooser_1 = new JDateChooser();
		dateChooser_1.setBounds(698, 505, 180, 30);
		add(dateChooser_1);

//		cbbCourse = new JComboBox();
//		
//		
//		loadCourseIdsIntoComboBox();
//
//		cbbTeacher = new JComboBox();
		
		tablePageCourse = new TablePage(
				this::loadCourse,
				this::countCourse
				);
		tablePageCourse.setBounds(20, 43, 546, 268);
		tablePageCourse.setColumnNamesAndTypes(
				new String[] { "Course ID", "Course Name", "Course Desc", "Course Duration"},
				Map.of(
			                0, Integer.class,			             
			                1, String.class,			              
			                3, String.class,
			                4, Integer.class
			                
					));
		accountsMappings = Map.of(
				0, value -> txtCourseId.setText(value.toString()),
				1, value -> txtCourseName.setText(value.toString()),
				2, value -> txtCourseDesc.setText(value.toString()),
				3, value -> txtCourseDura.setText(value.toString())	
				);
		tablePageCourse.setFieldMappings(accountsMappings);
		add(tablePageCourse);
		tablePageCourse.resetTable();
		
		tablePageClasses = new TablePage(
				this::loadClass,
				this::countClass);
		tablePageClasses.setBounds(20, 384, 546, 283);
		tablePageClasses.setColumnNamesAndTypes(
				new String[] {"Class ID", "Class Name", "Start Date", "End Date", "Course ID", "Emp ID"},
				Map.of(
						0, Integer.class, 1, String.class, 2, Date.class, 3, Date.class, 4, Integer.class, 5,
						Integer.class
			                
					));
		accountsMappings = Map.of(
				0, value -> textField_3.setText(value.toString()),
				1, value -> textField_4.setText(value.toString()),
				2, value -> helper.FieldsMapper.setDateChooser(dateChooser, value),
				3, value -> helper.FieldsMapper.setDateChooser(dateChooser_1, value),
				4, value -> cbbCourse.setSelectedItem(courseDao.selectCourseById((Integer) value)),
				5, value -> cbbTeacher.setSelectedItem(empDao.selectEmpByID((Integer) value))
				);
		tablePageClasses.setFieldMappings(accountsMappings);
		add(tablePageClasses);
		tablePageClasses.resetTable();
		
//		loadEmployeeIdsIntoComboBox();
		cbbCourse.setBounds(698, 547, 180, 30);
		cbbTeacher.setBounds(698, 588, 180, 30);
		setCbbCourse();
		add(cbbCourse);
		add(cbbTeacher);
		
		JButton btnRefresh = new JButton("Refresh All");
		btnRefresh.setBounds(30, 677, 89, 30);
		btnRefresh.addActionListener(e -> resetEverything());
		add(btnRefresh);
	}

	// Load employee ids into combo box
//	private void loadEmployeeIdsIntoComboBox() {
//		var dao         = new EmployeeDao();
//		var employeeIds = dao.selectEmployee().stream().filter(emp -> emp.getRoleId() == 2).map(Employee::getEmpId)
//		    .toArray();
//		cbbTeacher.setModel(new DefaultComboBoxModel<>(employeeIds));
//	}

	// Load course ids into combo box
//	private void loadCourseIdsIntoComboBox() {
//		var dao       = new CourseDao();
//		var courseIds = dao.selectAll().stream().map(Course::getCourseId).toArray();
//		cbbCourse.setModel(new DefaultComboBoxModel<>(courseIds));
//	}
	
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
		
		resetEverything();
//		JOptionPane.showMessageDialog(null, items2);
	};

public List<Object[]> loadCourse(int currentPage, int numberOfRows) {
		
		List<Object[]> list = new ArrayList<>();
		var dao  = new CourseDao();	
		for (var c : dao.pagingCourse(currentPage, numberOfRows))
		list.add(
				new Object[] {
						c.getCourseId(),
						c.getCourseName(),
						c.getCourseDesc(),
						c.getCourseDuration()}
				
				);
		return list;
	}

	
	public int countCourse() {
		return new CourseDao().countCourse();
	}

	public List<Object[]> loadClass(int currentPage, int numberOfRows) {
	    List<Object[]> list = new ArrayList<>();
	    var dao = new ClassesDao(); 

	    for (var cl : dao.pagingClass(currentPage, numberOfRows)) {
	        list.add(new Object[] {
	            cl.getClassId(),     
	            cl.getClassName(),   
	            cl.getStartDate(),   
	            cl.getEndDate(),     
	            cl.getCourseId(),    
	            cl.getTeachId()       
	        });
	    }
	    return list;
	}
	
	public int countClass() {
		return new ClassesDao().countClass();
	}

	// event when click on button add course
	protected void btnAddCourseActionPerformed(ActionEvent e) {
		var course = new Course();
		course.setCourseName(txtCourseName.getText());
		course.setCourseDesc(txtCourseDesc.getText());
		course.setCourseDuration(Integer.parseInt(txtCourseDura.getText()));
		var dao = new CourseDao();
		dao.insertCourse(course);
		resetCourse();

	}

// event when click on button update course
	protected void btnUpdateCourseActionPerformed(ActionEvent e) {
		var course = new Course();
		course.setCourseId(Integer.parseInt(txtCourseId.getText()));
		course.setCourseName(txtCourseName.getText());
		course.setCourseDesc(txtCourseDesc.getText());
		course.setCourseDuration(Integer.parseInt(txtCourseDura.getText()));
		var dao = new CourseDao();
		dao.updateCourse(course);
		resetCourse();
	}

//	event when click on button add class
	protected void btnAddClassActionPerformed(ActionEvent e) {
		var classes = new Classes();
		classes.setClassName(textField_4.getText());
		classes.setStartDate(new java.sql.Date(dateChooser.getDate().getTime()));
		classes.setEndDate(new java.sql.Date(dateChooser_1.getDate().getTime()));
		classes.setCourseId((Integer) cbbCourse.getSelectedItem());
		JOptionPane.showMessageDialog(null, cbbCourse.getSelectedItem());
		classes.setTeachId((Integer) cbbTeacher.getSelectedItem());
		JOptionPane.showMessageDialog(null, cbbTeacher.getSelectedItem());
		var dao = new ClassesDao();
		dao.addClass(classes);
		resetClasses();
	}

// event when click on button update class
	protected void btnAddUpdateActionPerformed(ActionEvent e) {
		var classes = new Classes();
		classes.setClassId(Integer.parseInt(textField_3.getText()));
		classes.setClassName(textField_4.getText());
		classes.setStartDate(new java.sql.Date(dateChooser.getDate().getTime()));
		classes.setEndDate(new java.sql.Date(dateChooser_1.getDate().getTime()));
		JOptionPane.showMessageDialog(null, cbbCourse.getSelectedItem());
		classes.setCourseId((Integer) cbbCourse.getSelectedItem());
		JOptionPane.showMessageDialog(null, cbbTeacher.getSelectedItem());
		classes.setTeachId((Integer) cbbTeacher.getSelectedItem());

		var dao = new ClassesDao();
		dao.update(classes);
		resetClasses();

	}
	// event when click on button delete class
	protected void btnDeleteClassActionPerformed(ActionEvent e) {
	    if (textField_3.getText().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Please select a class to delete!");
	        return;
	    }

	    int classId = Integer.parseInt(textField_3.getText());

	    int confirm = JOptionPane.showConfirmDialog(
	        null, 
	        "Are you sure you want to delete this class?", 
	        "Confirm Deletion", 
	        JOptionPane.YES_NO_OPTION
	    );

	    if (confirm == JOptionPane.YES_OPTION) {
	        var dao = new ClassesDao();
	        dao.delete(classId);
	        resetEverything(); // Refresh the form and table
	    }
	}

	
	protected void resetEverything() {
	    // Clear Course input fields
	    txtCourseId.setText("");
	    txtCourseName.setText("");
	    txtCourseDesc.setText("");
	    txtCourseDura.setText("");

	    // Clear Class input fields
	    textField_3.setText(""); 
	    textField_4.setText(""); 
	    dateChooser.setDate(null);
	    dateChooser_1.setDate(null);

	    // Reset ComboBoxes
	    cbbCourse.setSelectedIndex(0);
	    cbbTeacher.setSelectedIndex(0); 

	    // Reset tables
	    tablePageCourse.resetTable();
	    tablePageClasses.resetTable();
	}
	
	protected void resetClasses() {
	    // Clear Class input fields
	    textField_3.setText(""); 
	    textField_4.setText(""); 
	    dateChooser.setDate(null);
	    dateChooser_1.setDate(null);

	    // Reset ComboBoxes
	    cbbCourse.setSelectedIndex(0);
	    cbbTeacher.setSelectedIndex(0); 

	    // Reset tables	  
	    tablePageClasses.resetTable();
	}
	
	protected void resetCourse() {
	    // Clear Course input fields
	    txtCourseId.setText("");
	    txtCourseName.setText("");
	    txtCourseDesc.setText("");
	    txtCourseDura.setText("");


	    // Reset tables
	    tablePageCourse.resetTable();
	    
	}
}
