package sub;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;

import dao.ClassesDao;
import dao.CourseDao;
import dao.EmployeeDao;
import dao.EnrollStuDao;
import dao.StudentDao;
import entity.Employee;
import entity.Student;
import gui.TablePage;
import gui.TablePage.DataFetcher;
import gui.Paging.CountFetcher;
import java.awt.event.ActionListener;

public class StudentSub extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel            lblNewLabel;
	private JLabel            lblPicture;
	private JLabel            lblStuId;
	private JLabel            lblStuName;
	private JTextField        txtStuID;
	private JTextField        txtStuName;
	private JLabel            lblStuGender;
	private JRadioButton      rdbtnMale;
	private JRadioButton      rdbtnFemale;
	private JLabel            lblStuDob;
	private JDateChooser      dcStuDob;
	private JTextField        txtStuEmail;
	private JLabel            lblStuEmail;
	private JLabel            lblStuPhone;
	private JTextField        txtPhone;
	private JTextField        txtAddress;
	private JLabel            lblStuAddress;
	private JButton           btnChangeImg;
	private JButton           btnStuAdd;
	private JButton           btnStuUpdate;
	private JButton           btnStuDelete;
	private final ButtonGroup buttonGroup      = new ButtonGroup();
	private TablePage tablePageStu;
	private TablePage tablePageEnroll;
	private Map<Integer, Class<?>> columnMapping = new HashMap<>();
	private Map<Integer, Consumer<Object>> accountsMappings = new HashMap<>();
	private int currentStuId = 0;
	
	private String filenew = null; // ten file moi
	private String fileold = null; // ten file cu
	private String dirnew  = null; // duong dan file moi
	private String dirold  = null; // duong dan file cu


	/**
	 * Create the panel.
	 */
	public StudentSub() {
		setLayout(null);
		setBounds(0, 0, 892, 724);

		lblNewLabel = new JLabel("Student");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel.setBounds(10, 11, 872, 29);
		add(lblNewLabel);

		lblPicture = new JLabel("");
		lblPicture.setOpaque(true);
		lblPicture.setBackground(Color.CYAN);
		lblPicture.setBounds(10, 342, 130, 160);
		add(lblPicture);

		lblStuId = new JLabel("Student ID:");
		lblStuId.setBounds(150, 342, 100, 30);
		add(lblStuId);

		lblStuName = new JLabel("Student Name:");
		lblStuName.setBounds(150, 383, 100, 30);
		add(lblStuName);

		txtStuID = new JTextField();
		txtStuID.setColumns(10);
		txtStuID.setBounds(260, 342, 120, 30);
		add(txtStuID);

		txtStuName = new JTextField();
		txtStuName.setColumns(10);
		txtStuName.setBounds(260, 388, 120, 30);
		add(txtStuName);

		lblStuGender = new JLabel("Gender:");
		lblStuGender.setBounds(150, 424, 100, 30);
		add(lblStuGender);

		rdbtnMale = new JRadioButton("Male");
		buttonGroup.add(rdbtnMale);
		rdbtnMale.setBounds(260, 428, 52, 30);
		add(rdbtnMale);

		rdbtnFemale = new JRadioButton("Female");
		buttonGroup.add(rdbtnFemale);
		rdbtnFemale.setBounds(321, 428, 59, 30);
		add(rdbtnFemale);

		lblStuDob = new JLabel("DOB:");
		lblStuDob.setBounds(150, 465, 100, 30);
		add(lblStuDob);

		dcStuDob = new JDateChooser();
		dcStuDob.setBounds(260, 465, 120, 30);
		add(dcStuDob);

		txtStuEmail = new JTextField();
		txtStuEmail.setColumns(10);
		txtStuEmail.setBounds(260, 513, 120, 30);
		add(txtStuEmail);

		lblStuEmail = new JLabel("Email:");
		lblStuEmail.setBounds(150, 513, 100, 30);
		add(lblStuEmail);

		lblStuPhone = new JLabel("Phone:");
		lblStuPhone.setBounds(150, 554, 100, 30);
		add(lblStuPhone);

		txtPhone = new JTextField();
		txtPhone.setColumns(10);
		txtPhone.setBounds(260, 554, 120, 30);
		add(txtPhone);

		txtAddress = new JTextField();
		txtAddress.setColumns(10);
		txtAddress.setBounds(260, 595, 120, 30);
		add(txtAddress);

		lblStuAddress = new JLabel("Address:");
		lblStuAddress.setBounds(150, 595, 100, 30);
		add(lblStuAddress);

		btnChangeImg = new JButton("Change Image");
		btnChangeImg.addActionListener(this::btnChangeStuImgActionPerformed);
		btnChangeImg.setBounds(10, 513, 130, 41);
		add(btnChangeImg);

		btnStuAdd = new JButton("Add");
		btnStuAdd.addActionListener(this::btnAddStuActionPerformed);
		btnStuAdd.setBounds(10, 651, 110, 30);
		add(btnStuAdd);

		btnStuUpdate = new JButton("Update");
		btnStuUpdate.addActionListener(this::btnUpdateStuActionPerformed);
		btnStuUpdate.setBounds(130, 651, 110, 30);
		add(btnStuUpdate);

		btnStuDelete = new JButton("Delete");
		btnStuDelete.addActionListener(this::btnDeleteStuActionPerformed);
		btnStuDelete.setBounds(260, 651, 110, 30);
		add(btnStuDelete);
		
		tablePageStu = new TablePage(
				this::loadDataStudent,
				this::countTotalRows
				);
		tablePageStu.setBounds(10, 51, 872, 271);
		columnMapping.put(0, Integer.class);    
		columnMapping.put(1, String.class);      
		columnMapping.put(2, Boolean.class);    
		columnMapping.put(3, Date.class);        
		columnMapping.put(4, String.class);      
		columnMapping.put(5, String.class);      
		columnMapping.put(6, String.class);     
		columnMapping.put(7, ImageIcon.class);  
		columnMapping.put(8, String.class);      

		tablePageStu.setColumnNamesAndTypes(
		    new String[]{
		        "Student ID", "Name", "Gender", "DOB", "Email",
		        "Phone", "Address", "Image", "Image Path"
		    },
		    columnMapping
		);
			tablePageStu.setTableStatus(true);
			accountsMappings = Map.of(
	        		0, value -> {
	        	        helper.FieldsMapper.setTextField(txtStuID, value);
	        	        currentStuId = Integer.parseInt(value.toString());
	        	        tablePageEnroll.resetTable();
	        	    },
	        		1, value -> helper.FieldsMapper.setTextField(txtStuName, value),
	        		2, value -> helper.FieldsMapper.setGenderRadio(rdbtnMale, rdbtnFemale, value),
	        		3, value -> helper.FieldsMapper.setDateChooser(dcStuDob, value),
	        		4, value -> helper.FieldsMapper.setTextField(txtStuEmail, value),
	        		5, value -> helper.FieldsMapper.setTextField(txtPhone, value),
	        		6, value -> helper.FieldsMapper.setTextField(txtAddress, value),
	        		7, value -> helper.FieldsMapper.setImageLabel(lblPicture, value),
	        		8, value -> fileold = value.toString()	
				);
			tablePageStu.setFieldMappings(accountsMappings);
			tablePageStu.setColumnVisibility(8, false);
		
			add(tablePageStu);
			tablePageStu.resetTable();
		
		
		JLabel lblEnroll = new JLabel("Enrollment List");
		lblEnroll.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEnroll.setBounds(450, 342, 120, 30);
		add(lblEnroll);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(this::btnRefreshActionPerformed);
		btnRefresh.setBounds(450, 651, 130, 30);
		add(btnRefresh);
		
		tablePageEnroll = new TablePage(
				this::loadEnrollData,
				this::countTotalRowsEnroll
				);
		tablePageEnroll.setBounds(450, 376, 432, 265);
		tablePageEnroll.setColumnNamesAndTypes(
	            new String[]{
	                "Enroll Id", "Enroll Date", "Class Name", "Course Name", "Stu ID", "Status"
	            },
	            Map.of(
	                0, Integer.class,
	                1, Date.class,
	                2, String.class,
	                3, String.class,
	                4, Integer.class,
	                5, Boolean.class
	            )
	        );
		
		add(tablePageEnroll);
//		tablePageStu.setOnTableClickListener(() -> {
//			
//			tablePageEnroll.resetTable();
//		});


	}
	
	public List<Object[]> loadDataStudent(int currentPage, int numberOfRows) {
	    List<Object[]> list = new ArrayList<>();
	    var dao = new StudentDao();
	    var students = dao.pagingStudent(currentPage, numberOfRows);

	    for (var stu : students) {
	        list.add(new Object[] {
	            stu.getStuId(),                        
	            stu.getStuName(),                      
	            stu.isStuGender(),                    
	            java.sql.Date.valueOf(stu.getStuDob()),
	            stu.getStuEmail(),                    
	            stu.getStuPhone(),                    
	            stu.getStuAddress(),                  
	            (stu.getStuImage() == null) ? 
	                new ImageIcon(new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB)) : // Default blank image
	                new ImageIcon(new ImageIcon(stu.getStuImage()).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)), // Student Image
	            stu.getStuImage()                      // Image Path (Stored as String)
	        });
	    }
	    return list;
	}
	
	public List<Object[]> loadEnrollData(int currentPage, int numberOfRows) {
	    List<Object[]> list = new ArrayList<>();
	    var dao = new EnrollStuDao();
	    var enrollList = dao.pagingEnrollListOfStu(currentPage, numberOfRows, currentStuId);

		
	    for (var en : enrollList) {
	    	var cl = new ClassesDao().selectByClassID(en.getClassId());
	        list.add(new Object[] {
	            en.getEnrollId(),
	            java.sql.Date.valueOf(en.getEnrollDate()),
	            cl.getClassName(),
	            new CourseDao().selectCourseNameById(cl),
	            en.getStuId(),
	            en.isPassStatus()
	        });
	    }
	    return list;
	}

	
	private Integer countTotalRows() {
		 
		var	dao = new StudentDao();
	      return dao.countStudent(); 
	  }
	
	private Integer countTotalRowsEnroll() {
		
		var	dao = new EnrollStuDao();
	      return dao.countEnrollListOfStu(currentStuId); 
	  }

	// event button update employee
	protected void btnUpdateStuActionPerformed(ActionEvent e) {
	    if (txtStuID.getText().isBlank()) {
	        JOptionPane.showMessageDialog(null, "ID is required");
	        return;
	    }

	    var stu = new Student();
	    stu.setStuId(Integer.parseInt(txtStuID.getText()));
	    stu.setStuName(txtStuName.getText());
	    stu.setStuAddress(txtAddress.getText());
	    stu.setStuPhone(txtPhone.getText());
	    stu.setStuDob(dcStuDob.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
	    stu.setStuGender(rdbtnMale.isSelected());
	    stu.setStuEmail(txtStuEmail.getText());

	    if (filenew != null) {
	        dirnew = System.getProperty("user.dir") + "\\images";
	        var pathold = Paths.get(dirold);
	        var pathnew = Paths.get(dirnew);
	        try {
	            Files.copy(pathold, pathnew.resolve(pathold.getFileName()), StandardCopyOption.REPLACE_EXISTING);
	        } catch (Exception e2) {
	            e2.printStackTrace();
	        }
	        // Update the new image path in the database
	        stu.setStuImage("images/" + filenew);
	    } else {
	        stu.setStuImage(fileold);
	    }

	    var dao = new StudentDao();
	    dao.updateStudent(stu);
	    resetEverything();
	}

	// event button change image

	protected void btnChangeStuImgActionPerformed(ActionEvent e) {
	    var chooser = new JFileChooser("D:\\Repos\\quanlydiem\\images");
	    chooser.setDialogTitle("Choose a picture");
	    chooser.setAcceptAllFileFilterUsed(false); // Don't allow selecting all file types
	    chooser.setFileFilter(new FileNameExtensionFilter("Image (jpg, png, gif)", "jpg", "png", "gif"));

	    var result = chooser.showOpenDialog(null); // Open file chooser dialog
	    if (result == JFileChooser.APPROVE_OPTION) {
	        var file = chooser.getSelectedFile();
	        
	        // Validate file size (max 5MB)
	        if (file.length() > 5 * 1024 * 1024) {
	            JOptionPane.showMessageDialog(null, "File size must be less than 5MB");
	            return;
	        }

	        filenew = file.getName();         // Get new file name
	        dirold  = file.getAbsolutePath(); // Get the full path of the selected file

	        // Set the selected image to the student picture label
	        lblPicture.setIcon(new ImageIcon(
	            new ImageIcon(file.getAbsolutePath()).getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH)));
	    }
	}


	// button add Employee

	protected void btnAddStuActionPerformed(ActionEvent e) {
	    var dao = new StudentDao();
	    var stu = new Student();

	    // Set student attributes from input fields
	    stu.setStuName(txtStuName.getText());
	    stu.setStuAddress(txtAddress.getText());
	    stu.setStuPhone(txtPhone.getText());
	    stu.setStuDob(dcStuDob.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
	    stu.setStuGender(rdbtnMale.isSelected());
	    stu.setStuEmail(txtStuEmail.getText());

	    // Handle student image selection and copying
	    if (filenew != null) {
	        dirnew = System.getProperty("user.dir") + "\\images";
	        var pathold = Paths.get(dirold);
	        var pathnew = Paths.get(dirnew);
	        try {
	            Files.copy(pathold, pathnew.resolve(pathold.getFileName()), StandardCopyOption.REPLACE_EXISTING);
	        } catch (Exception e2) {
	            e2.printStackTrace();
	        }
	        // Update the new image path in the database
	        stu.setStuImage("images/" + filenew);
	    } else {
	        stu.setStuImage(fileold);
	    }

	    dao.insertStudent(stu); // Insert student into the database
	    resetEverything(); // Reset fields after adding
	}
	
	protected void btnDeleteStuActionPerformed(ActionEvent e) {
	    if (txtStuID.getText().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Please select a student to delete!");
	        return;
	    }

	    int stuId = Integer.parseInt(txtStuID.getText());

	    int confirm = JOptionPane.showConfirmDialog(null, 
	        "Are you sure you want to delete this student?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

	    if (confirm == JOptionPane.YES_OPTION) {
	        var dao = new StudentDao();
	        dao.deleteStudent(stuId);
	        resetEverything(); // Reset the form after deletion
	    }
	}


	// button clear field

	protected void btnRefreshActionPerformed(ActionEvent e) {
		resetEverything();
	}
	
	protected void resetEverything() {
		lblPicture.setIcon(null);

	    txtStuID.setText("");
	    txtStuName.setText("");
	    rdbtnMale.setSelected(false);
	    rdbtnFemale.setSelected(false);
	    txtPhone.setText("");
	    txtAddress.setText("");
	    txtStuEmail.setText("");
	    dcStuDob.setDate(null);

		tablePageStu.resetTable();
	}
}
