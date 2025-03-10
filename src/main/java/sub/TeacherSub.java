package sub;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dao.AccountDao;
import dao.EmployeeDao;
import entity.Employee;
import gui.TablePage;
import gui.TablePage.DataFetcher;
import gui.Paging.CountFetcher;

public class TeacherSub extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel             lblNewLabel;
	private JButton            btnNewButton;
	private JLabel             lblEmpImage;
	private JLabel             lblEmpId;
	private JTextField         txtEmpId;
	private JTextField         txtEmpName;
	private JLabel             lblEmpName;
	private JLabel             lblEmpAddress;
	private JTextField         txtEmpAddress;
	private JLabel             lblEmpGender;
	private JRadioButton       rdbtnMale;
	private JRadioButton       rdbtnFemale;
	private JButton            btnNewButton_1;
	private JLabel             lblDob;
	private JDateChooser       dcDob;
	private JTextField         txtEmpPhone;
	private JLabel             lblEmpPhone;
	private JLabel             lblHireDate;
	private JDateChooser       dcHD;
	private JButton            btnUpdateEmp;
	private JButton            btnDeleteEmp;
	private JButton            btnAddEmp;
	private TablePage          tablePageTeacher;
	private Map<Integer, Class<?>> columnMapping = new HashMap<>();
	private final ButtonGroup buttonGroup      = new ButtonGroup();

	private String filenew = null; // ten file moi
	private String fileold = null; // ten file cu
	private String dirnew  = null; // duong dan file moi
	private String dirold  = null; // duong dan file cu

	/**
	 * Create the panel.
	 */
	public TeacherSub() {
		setLayout(null);
		setBounds(0, 0, 892, 724);

		lblNewLabel = new JLabel("Teacher");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel.setBounds(10, 11, 872, 24);
		add(lblNewLabel);
		
		btnNewButton = new JButton("Refresh");
		btnNewButton.addActionListener(this::btnNewButtonActionPerformed);
		btnNewButton.setBounds(10, 351, 120, 23);
		add(btnNewButton);

		lblEmpImage = new JLabel("");
		lblEmpImage.setOpaque(true);
		lblEmpImage.setForeground(Color.WHITE);
		lblEmpImage.setBackground(Color.CYAN);
		lblEmpImage.setBounds(10, 385, 160, 160);
		add(lblEmpImage);

		lblEmpId = new JLabel("EmployeeId");
		lblEmpId.setBounds(180, 385, 110, 30);
		add(lblEmpId);

		txtEmpId = new JTextField();
		txtEmpId.setColumns(10);
		txtEmpId.setBounds(300, 385, 150, 30);
		add(txtEmpId);

		txtEmpName = new JTextField();
		txtEmpName.setColumns(10);
		txtEmpName.setBounds(300, 426, 150, 30);
		add(txtEmpName);

		lblEmpName = new JLabel("Employee Name");
		lblEmpName.setBounds(180, 426, 110, 30);
		add(lblEmpName);

		lblEmpAddress = new JLabel("Address");
		lblEmpAddress.setBounds(180, 467, 111, 30);
		add(lblEmpAddress);

		txtEmpAddress = new JTextField();
		txtEmpAddress.setColumns(10);
		txtEmpAddress.setBounds(300, 467, 150, 30);
		add(txtEmpAddress);

		lblEmpGender = new JLabel("Gender");
		lblEmpGender.setBounds(180, 515, 110, 30);
		add(lblEmpGender);

		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setBounds(300, 515, 64, 30);
		buttonGroup.add(rdbtnMale);
		add(rdbtnMale);

		rdbtnFemale = new JRadioButton("FeMale");
		rdbtnFemale.setBounds(366, 515, 84, 30);
		buttonGroup.add(rdbtnFemale);
		add(rdbtnFemale);

		btnNewButton_1 = new JButton("Change Image");
		btnNewButton_1.addActionListener(this::btnNewButton_1ActionPerformed);
		btnNewButton_1.setBounds(10, 545, 160, 23);
		add(btnNewButton_1);

		lblDob = new JLabel("Dob");
		lblDob.setBounds(560, 385, 110, 30);
		add(lblDob);

		dcDob = new JDateChooser();
		dcDob.setBounds(680, 385, 150, 30);
		add(dcDob);

		txtEmpPhone = new JTextField();
		txtEmpPhone.setColumns(10);
		txtEmpPhone.setBounds(680, 426, 150, 30);
		add(txtEmpPhone);

		lblEmpPhone = new JLabel("Phone");
		lblEmpPhone.setBounds(560, 426, 110, 30);
		add(lblEmpPhone);

		lblHireDate = new JLabel("HireDate");
		lblHireDate.setBounds(560, 467, 110, 30);
		add(lblHireDate);

		dcHD = new JDateChooser();
		dcHD.setEnabled(false);
		dcHD.setBounds(680, 467, 150, 30);
		add(dcHD);

		btnUpdateEmp = new JButton("Update");
		btnUpdateEmp.addActionListener(this::btnUpdateEmpActionPerformed);
		btnUpdateEmp.setBounds(10, 589, 200, 100);
		add(btnUpdateEmp);

		btnDeleteEmp = new JButton("Delete");
		btnDeleteEmp.setBounds(346, 589, 200, 100);
		btnDeleteEmp.addActionListener(this::btnDeleteEmpActionPerformed);
		add(btnDeleteEmp);

		btnAddEmp = new JButton("Add");
		btnAddEmp.addActionListener(this::btnAddEmpActionPerformed);
		btnAddEmp.setBounds(682, 589, 200, 100);
		add(btnAddEmp);
		
		tablePageTeacher = new TablePage(

	       		 this::loadDataTeacher,
	       		 this::countTotalRows
	      		);
		
		tablePageTeacher.setBounds(10, 45, 872, 293);
		columnMapping.put(0, Integer.class);
		columnMapping.put(1, String.class);
		columnMapping.put(2, Boolean.class);
		columnMapping.put(3, String.class);
		columnMapping.put(4, String.class);
		columnMapping.put(5, Date.class);
		columnMapping.put(6, Date.class);
		columnMapping.put(7, Integer.class);
		columnMapping.put(8, ImageIcon.class);
		columnMapping.put(9, String.class);
		tablePageTeacher.setColumnNamesAndTypes(
	            new String[]{
	                "Employee ID", "Name", "Gender", "Phone", "Adress",
	                "Hire Date", "DOB", "Role ID", "Image", "Image Path"
	            },
	            columnMapping
				);
			tablePageTeacher.setTableStatus(true);
			Map<Integer, Consumer<Object>> accountsMappings = Map.of(
	        		0, value -> helper.FieldsMapper.setTextField(txtEmpId, value),
	        		1, value -> helper.FieldsMapper.setTextField(txtEmpName, value),
	        		2, value -> helper.FieldsMapper.setGenderRadio(rdbtnMale, rdbtnFemale, value),
	        		3, value -> helper.FieldsMapper.setTextField(txtEmpPhone, value),
	        		4, value -> helper.FieldsMapper.setTextField(txtEmpAddress, value),
	        		6, value -> helper.FieldsMapper.setDateChooser(dcDob, value),
	        		8, value -> helper.FieldsMapper.setImageLabel(lblEmpImage, value),
	        		9, value -> fileold = value.toString()
				);
			tablePageTeacher.setFieldMappings(accountsMappings);
			tablePageTeacher.setColumnVisibility(9, false);
			add(tablePageTeacher);
			tablePageTeacher.resetTable();
	}

	public List<Object[]> loadDataTeacher(int currentPage, int numberOfRows) {
		List<Object[]> list = new ArrayList<>();
		var dao = new EmployeeDao();
		var teacher = dao.pagingTeacher(currentPage, numberOfRows);
		
		for (var emp : teacher) {
			list.add(new Object[] {
					emp.getEmpId(),
					emp.getEmpName(),
					emp.getEmpGender(),
					emp.getEmpPhone(),
					emp.getEmpAddress(),
					java.sql.Date.valueOf(emp.getEmpHireDate()),
					java.sql.Date.valueOf(emp.getEmpDob()),
					emp.getRoleId(),
					(emp.getEmpImage() == null) ? new ImageIcon(new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB))
							: new ImageIcon(new ImageIcon(emp.getEmpImage()).getImage().getScaledInstance(80, 80,
									Image.SCALE_SMOOTH)),
					emp.getEmpImage() });
		}
		
		return list;
	}
	
	private Integer countTotalRows() {
 
		var	dao = new EmployeeDao();
	      return dao.countTeacher(); // Only counting total rows here
	  }
	
	private boolean checkValidField() {
		var check = false;
		var name = helper.Valid.checkRegex2(helper.Regex.STRING, txtEmpName.getText());
		var address = helper.Valid.checkRegex2(helper.Regex.STRING, txtEmpAddress.getText());
		var phone = helper.Valid.checkRegex2(helper.Regex.PHONE, txtEmpPhone.getText());
		if (name && address && phone) {
			
			check = true;
		}
		return check;
	}

	protected void btnUpdateEmpActionPerformed(ActionEvent e) {
		if (txtEmpId.getText().isBlank()) {
			JOptionPane.showMessageDialog(null, "ID is required");
			return;
		}
		
		if(checkValidField()) {
			var emp = new Employee();
			emp.setEmpId(Integer.parseInt(txtEmpId.getText()));
			emp.setEmpName(txtEmpName.getText());
			emp.setEmpAddress(txtEmpAddress.getText());
			emp.setEmpPhone(txtEmpPhone.getText());
			emp.setEmpDob(dcDob.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
			emp.setEmpGender(rdbtnMale.isSelected());
			emp.setRoleId(2);
			if (filenew != null) {
				dirnew = System.getProperty("user.dir") + "\\images";
				var pathold = Paths.get(dirold);
				var pathnew = Paths.get(dirnew);
				try {
					Files.copy(pathold, pathnew.resolve(pathold.getFileName()), StandardCopyOption.REPLACE_EXISTING);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				// sau do cap nhat lai duong dan hinh moi trong csdl
				emp.setEmpImage("images/" + filenew);
			} else {
				emp.setEmpImage(fileold);
			}

			var dao = new EmployeeDao();
			dao.updateEmployee(emp);
		}else {
			JOptionPane.showMessageDialog(null, "Invalid field");
		}
		
		
		resetEverything();
	}

	protected void btnNewButton_1ActionPerformed(ActionEvent e) {
		var chooser = new JFileChooser("D:\\Repos\\quanlydiem\\images");
		chooser.setDialogTitle("Choose a picture");
		chooser.setAcceptAllFileFilterUsed(false); // ko cho chon all file
		chooser.setFileFilter(new FileNameExtensionFilter("Image (jpg, png, gif)", "jpg", "png", "gif"));
		var result = chooser.showOpenDialog(null); // mo cua so chon file
		if (result == JFileChooser.APPROVE_OPTION) {
			var file = chooser.getSelectedFile();
			if (file.length() > 5 * 1024 * 1024) {
				JOptionPane.showMessageDialog(null, "File size must be less than 5MB");
				return;
			}
			filenew = file.getName();         // lay ten file moi ra
			dirold  = file.getAbsolutePath(); // lay duong dan file cu ra

			lblEmpImage.setIcon(new ImageIcon(
			    new ImageIcon(file.getAbsolutePath()).getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH)));
		}
	}

	protected void btnAddEmpActionPerformed(ActionEvent e) {
		var dao = new EmployeeDao();
		var emp = new Employee();
		emp.setEmpName(txtEmpName.getText());
		emp.setEmpAddress(txtEmpAddress.getText());
		emp.setEmpPhone(txtEmpPhone.getText());
		emp.setEmpDob(dcDob.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
		emp.setEmpGender(rdbtnMale.isSelected());
		emp.setRoleId(2);

		if (filenew != null) {
			dirnew = System.getProperty("user.dir") + "\\images";
			var pathold = Paths.get(dirold);
			var pathnew = Paths.get(dirnew);
			try {
				Files.copy(pathold, pathnew.resolve(pathold.getFileName()), StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			// sau do cap nhat lai duong dan hinh moi trong csdl
			emp.setEmpImage("images/" + filenew);
		} else {
			emp.setEmpImage(fileold);
			
		}
		
		dao.insertEmployee(emp);
		
		resetEverything();
	
	}
	
	protected void btnDeleteEmpActionPerformed(ActionEvent e) {
	    // Ensure an employee is selected
	    if (txtEmpId.getText().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Please select an employee to delete!");
	        return;
	    }

	    // Parse Employee ID
	    int empId = Integer.parseInt(txtEmpId.getText());

	    // Confirm deletion
	    int confirm = JOptionPane.showConfirmDialog(null, 
	        "Are you sure you want to delete this employee?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

	    if (confirm == JOptionPane.YES_OPTION) {
	        var dao = new EmployeeDao(); // Assuming you have an EmployeeDao class
	        dao.deleteEmployee(empId);
	        resetEverything(); 
	    }
	}
	
	protected void btnNewButtonActionPerformed(ActionEvent e) {
		resetEverything();
	}
	
	protected void resetEverything() {
		lblEmpImage.setIcon(null);

		txtEmpId.setText("");
		txtEmpName.setText("");
		buttonGroup.clearSelection();
		txtEmpPhone.setText("");
		txtEmpAddress.setText("");
		dcHD.setDate(null);
		dcDob.setDate(null);

		tablePageTeacher.resetTable();
	}
}
