package sub;

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
import javax.swing.JComboBox;
import gui.Paging;
import gui.Paging.CountFetcher;
import gui.TablePage;
import gui.TablePage.DataFetcher;
import java.awt.FlowLayout;

public class ManageClass extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private static ManageClass instance = null;
	private int classId;
	private JTextField txtClassID;
	private JTextField txtClassName;
	private JDateChooser dateChooser = new JDateChooser();
	private JDateChooser dateChooser_1 = new JDateChooser();
	private JComboBox<Course> cbbCourse = new JComboBox<>();
	private JComboBox<Employee> cbbTeacher = new JComboBox<>();
	private Classes classInfo = new Classes();
	private ClassesDao classesDao = new ClassesDao();
	private TablePage tablePageRemoveStu;
	private TablePage tablePageAddStu;

	
	public static ManageClass getInstance() {
		if(instance == null) {
			instance = new ManageClass();
			
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
					ManageClass frame = new ManageClass();
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
	public ManageClass() {
//		var grade = new GradeDao().selectGradebyId(classId);
		
		setIconifiable(false);
		setClosable(true);
		setTitle("Manage Class");
		setBounds(100, 100, 501, 338);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel UpdateClassPanel = new JPanel();
		tabbedPane.addTab("Update Class Info", null, UpdateClassPanel, null);
		GridBagLayout gbl_UpdateClassPanel = new GridBagLayout();
		gbl_UpdateClassPanel.columnWidths = new int[] {0, 0, 10, 0};
		gbl_UpdateClassPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_UpdateClassPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE, 1.0};
		gbl_UpdateClassPanel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		UpdateClassPanel.setLayout(gbl_UpdateClassPanel);
		
		JLabel lblClassID = new JLabel("Class ID: ");
		GridBagConstraints gbc_lblClassID = new GridBagConstraints();
		gbc_lblClassID.anchor = GridBagConstraints.EAST;
		gbc_lblClassID.insets = new Insets(0, 0, 5, 5);
		gbc_lblClassID.gridx = 0;
		gbc_lblClassID.gridy = 0;
		UpdateClassPanel.add(lblClassID, gbc_lblClassID);
		
		txtClassID = new JTextField();
		txtClassID.setEnabled(false);
		GridBagConstraints gbc_txtClassID = new GridBagConstraints();
		gbc_txtClassID.anchor = GridBagConstraints.WEST;
		gbc_txtClassID.insets = new Insets(0, 0, 5, 5);
		gbc_txtClassID.gridx = 1;
		gbc_txtClassID.gridy = 0;
		UpdateClassPanel.add(txtClassID, gbc_txtClassID);
		txtClassID.setColumns(10);
		
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
		
		JButton btnUpdate = new JButton("Update");
		GridBagConstraints gbc_btnUpdate = new GridBagConstraints();
		gbc_btnUpdate.gridx = 3;
		gbc_btnUpdate.gridy = 6;
		btnUpdate.addActionListener(this::btnUpdateClassActionPerformed);
		
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
		UpdateClassPanel.add(btnUpdate, gbc_btnUpdate);
		
		
		JPanel ManageStudentsPanel = new JPanel();
		tabbedPane.addTab("Students", null, ManageStudentsPanel, null);
		ManageStudentsPanel.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.LEFT);
		ManageStudentsPanel.add(tabbedPane_1, BorderLayout.CENTER);
		
		JPanel panelRemoveStudents = new JPanel();
		tabbedPane_1.addTab("Remove Students", null, panelRemoveStudents, null);
		panelRemoveStudents.setLayout(new BorderLayout(0, 0));
		
		tablePageRemoveStu = new TablePage(
				this::fetchDataStuList,
       		 	this::countTotalRows
				);
		tablePageRemoveStu.setColumnNamesAndTypes(
	            new String[]{
	                "Action", "Student ID", "Name", "Gender", "DoB",
	                "Address", "Email", "Phone", "Picture", "Enroll Id"

	            },
	            Map.of(
	                0, Boolean.class,
	                1, Integer.class,
	                2, String.class,
	                3, Boolean.class,
	                4, Date.class,
	                5, String.class,
	                6, String.class,
	                7, String.class,
	                8, ImageIcon.class,
	                9, Integer.class
	            )
	        );
		tablePageRemoveStu.setTableStatus(true);
		tablePageRemoveStu.setEditableColumn(0);
		panelRemoveStudents.add(tablePageRemoveStu, BorderLayout.CENTER);
		
		JButton btnRemoveStu = new JButton("Remove");
		panelRemoveStudents.add(btnRemoveStu, BorderLayout.SOUTH);
		btnRemoveStu.addActionListener(this::btnRemoveStuActionPerformed);
		
		JPanel panelAddStudents = new JPanel();
		tabbedPane_1.addTab("Add Students", null, panelAddStudents, null);
		panelAddStudents.setLayout(new BorderLayout(0, 0));
		
		tablePageAddStu = new TablePage(
				this::fetchDataAddStudent,
       		 	this::countTotalRowsAddStudent
				);
		tablePageAddStu.setColumnNamesAndTypes(
	            new String[]{
	                "Action", "Student ID", "Name", "Gender", "DoB",
	                "Address", "Email", "Phone", "Picture"

	            },
	            Map.of(
	                0, Boolean.class,
	                1, Integer.class,
	                2, String.class,
	                3, Boolean.class,
	                4, Date.class,
	                5, String.class,
	                6, String.class,
	                7, String.class,
	                8, ImageIcon.class
	            )
	        );
		tablePageAddStu.setTableStatus(true);
		tablePageAddStu.setEditableColumn(0);
		panelAddStudents.add(tablePageAddStu, BorderLayout.CENTER);
		
		JButton btnAddStu = new JButton("Add");
		btnAddStu.addActionListener(this::btnAddStuActionPerformed);
		panelAddStudents.add(btnAddStu, BorderLayout.SOUTH);
		
		

	}
	
	@Override
	public void dispose() {
	    instance = null;
	    super.dispose();
	}
	
	 private void refreshClassDetails() {
	        var cl = new ClassesDao().selectByClassID(classId);
	        var course = new CourseDao().selectCourseById(cl.getCourseId());
	        var teacher = new EmployeeDao().selectEmpByID(cl.getTeachId());
	        
	        if (cl != null && course != null) {
	        	txtClassID.setText(String.valueOf(classId));
	            txtClassName.setText(String.valueOf(cl.getClassName()));
	            dateChooser.setDate(cl.getStartDate());	     
	            dateChooser_1.setDate(cl.getEndDate());
	            cbbCourse.setSelectedItem(course);
	            cbbTeacher.setSelectedItem(teacher);
	            
	        } else {
	        	txtClassID.setText("0");
	        }
	    }
	
	public void setClassId(int classId) {		
		this.classId = classId;
		this.classInfo = classesDao.selectByClassID(classId);
		tablePageRemoveStu.resetTable();
		tablePageAddStu.resetTable();
		refreshClassDetails();
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
		refreshClassDetails();
//		JOptionPane.showMessageDialog(null, items2);
	};
	
	private List<Object[]> fetchDataStuList(int currentPage, int numberOfRows) {
//    	var classInfo = selectClassByID();
      if (classInfo == null) {
          return List.of(); // Return an empty list if no class is selected
      }

        List<Object[]> list = new ArrayList<>();
        var enrollStuDao = new EnrollStuDao();
        var enrollments = enrollStuDao.pagingStudentList(currentPage, numberOfRows, classInfo);
//        var number = (currentPage - 1) * numberOfRows + 1;

        for(var en : enrollments) {
        	list.add(
              new Object[] {
            		  false,
                  en.getStudent().getStuId(),
                  en.getStudent().getStuName(),
                  en.getStudent().isstuGender(),
                  en.getStudent().getStuDob(),
                  en.getStudent().getStuAddress(),
                  en.getStudent().getStuEmail(),
                  en.getStudent().getStuPhone(),
                  (en.getStudent().getStuImage() == null) ?
                      new ImageIcon(
                          new BufferedImage(60, 60, BufferedImage.TYPE_INT_ARGB)
                      ) :
                      new ImageIcon(
                          new ImageIcon(en.getStudent().getStuImage())
                              .getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)
                      ),
                  en.getEnrollId()
              }
          );

       }
//
        return list;
    }
	
	private Integer countTotalRows() {
//		var classInfo = selectClassByID();
      if (classInfo == null) {
          return 0; // Return an empty list if no class is selected
      }

      var enrollStuDao = new EnrollStuDao();
      return enrollStuDao.countStudentList(classInfo); // Only counting total rows here
  }
	
	private List<Object[]> fetchDataAddStudent(int currentPage, int numberOfRows) {
//    	var classInfo = selectClassByID();
      if (classInfo == null) {
          return List.of(); // Return an empty list if no class is selected
      }

        List<Object[]> list = new ArrayList<>();
        var enrollStuDao = new EnrollStuDao();
        var enrollments = enrollStuDao.pagingStudentNotInClass(currentPage, numberOfRows, classInfo);
//        var number = (currentPage - 1) * numberOfRows + 1;

        for(var en : enrollments) {
        	list.add(
              new Object[] {
            		  false,
                  en.getStudent().getStuId(),
                  en.getStudent().getStuName(),
                  en.getStudent().isstuGender(),
                  en.getStudent().getStuDob(),
                  en.getStudent().getStuAddress(),
                  en.getStudent().getStuEmail(),
                  en.getStudent().getStuPhone(),
                  (en.getStudent().getStuImage() == null) ?
                      new ImageIcon(
                          new BufferedImage(60, 60, BufferedImage.TYPE_INT_ARGB)
                      ) :
                      new ImageIcon(
                          new ImageIcon(en.getStudent().getStuImage())
                              .getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)
                      )
              }
          );

       }
//
        return list;
    }
	
	private Integer countTotalRowsAddStudent() {
//		var classInfo = selectClassByID();
      if (classInfo == null) {
          return 0; // Return an empty list if no class is selected
      }

      var enrollStuDao = new EnrollStuDao();
      return enrollStuDao.countStudentNotInClass(classInfo); // Only counting total rows here
  }
	
	private void resetAllTable() {
		 tablePageAddStu.resetTable();
         tablePageRemoveStu.resetTable();
	}
	 

	 protected void btnRemoveStuActionPerformed(ActionEvent e) {
		 List<Object> checkedRows = tablePageRemoveStu.checkedRows(0, 9);
//		 JOptionPane.showMessageDialog(null, "Checked rows: " + checkedRows);
         var enDao = new EnrollmentDao();
         
         for (Object obj : checkedRows) {
             var enrollId = (int) obj;
                enDao.delete(enrollId);
         }
         JOptionPane.showMessageDialog(null, "Student Removed");
         resetAllTable();
	 }
	 
	 protected void btnAddStuActionPerformed(ActionEvent e) {
		 List<Object> checkedRows = tablePageAddStu.checkedRows(0, 1);
//		 JOptionPane.showMessageDialog(null, "Checked rows: " + checkedRows);
         var enDao = new EnrollmentDao();
         
         for (Object obj : checkedRows) {
             var stuId = (int) obj;
                enDao.add(stuId, classId);
         }
         JOptionPane.showMessageDialog(null, "Student Added");
         resetAllTable();
	 }
	 
	 protected void btnUpdateClassActionPerformed(ActionEvent e) {
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

			 dao.update(cl);
			 JOptionPane.showMessageDialog(null, "Class Updated");
			 instance.dispose();
			
			
		 }else {
			 JOptionPane.showMessageDialog(null, "Invalid Input");
		 }
	 }
}

