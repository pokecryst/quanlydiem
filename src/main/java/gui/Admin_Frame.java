package gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dao.AccountDao;
import dao.EmployeeDao;
import dao.EnrollStuDao;
import entity.Account;
import entity.Account.AccType;
import entity.Employee;
import gui.TablePage.DataFetcher;
import gui.Paging.CountFetcher;

public class Admin_Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel_1;
	private JTextField textEmail;
	private JLabel lblNewLabel_2;
	private JPasswordField passwordField;
	private JComboBox cbbType;
	private JLabel lblAccType;
	private JLabel lblEmpId;
	private JTextField txtEmpId;
	private JDateChooser dateChooser;
	private JLabel lblCreateAt;
	private JCheckBox chckbxActive;
	private JLabel lblBlockUser;
	private JButton btnNewButton;
	private JPanel panel;
	private JTabbedPane tabbedPane;
	private JPanel panel_1;
	private JButton btnLoad;
	private JPanel panel_2;
	private JLabel lblNewLabel;
	private JTextField textID;
	private JPanel panel_3;
	private JLabel lblEmailaccount;
	private JTextField txtEmail;
	private JLabel lblNewLabel_3;
	private JPasswordField passwordField_1;
	private JLabel lblAccType_1;
	private JComboBox<AccType> cbbType1;
	private JLabel lblEmpId_1;
	private JComboBox cbbEmpId;
	private JButton btnAddAccount;
	private JLabel lblBlockUser_1;
	private JCheckBox chckbxActive_1;
	private JScrollPane scrollPane_1;
	private JPanel panel_4;
	private JTable table_1;
	private JLabel lblAvatar;
	private JButton btnNewButton_1;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JLabel lblNewLabel_8;
	private JTextField txtempId;
	private JTextField txtEmpName;
	private JTextField txtEmpGender;
	private JTextField txtEmpPhone;
	private JLabel lblNewLabel_9;
	private JLabel lblNewLabel_10;
	private JTextField txtEmpAddress;
	private JButton btnUpdateEmp;
	private JButton btnNewButton_3;
	private JDateChooser dateChooser_HD;
	private JDateChooser dateChooser_DOB;

	private String filenew = null; // ten file moi
	private String fileold = null; // ten file cu
	private String dirnew = null; // duong dan file moi
	private String dirold = null; // duong dan file cu
	private JButton btnRefeshEmp;

	private Integer currentPage = 1; // trang hiện tại
	private Integer numberOfRows = 10; // số dòng hiển thị trên 1 trang
	private Integer totalRows = 0; // tổng số dòng (hàng) trong csdl
	private Double totalPage = 0.0; // tổng số trang
	
	private TablePage tablePageAccount;

	public Admin_Frame() {
		setTitle("Admin Frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 888, 686);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		AccType[] filteredTypes = { AccType.staff, AccType.teacher };

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 872, 647);
		contentPane.add(tabbedPane);

		panel_1 = new JPanel();
		tabbedPane.addTab("Account", null, panel_1, null);
		panel_1.setLayout(null);

		panel = new JPanel();
		panel.setBounds(10, 11, 374, 373);
		panel_1.add(panel);
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Update Account:", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 255)));
		panel.setLayout(null);

		lblNewLabel_1 = new JLabel("Email:");
		lblNewLabel_1.setBounds(10, 69, 70, 30);
		panel.add(lblNewLabel_1);

		textEmail = new JTextField();
		textEmail.setBounds(96, 69, 196, 30);
		panel.add(textEmail);
		textEmail.setColumns(10);

		lblNewLabel_2 = new JLabel("Password:");
		lblNewLabel_2.setBounds(10, 110, 70, 30);
		panel.add(lblNewLabel_2);

		lblAccType = new JLabel("Position:");
		lblAccType.setBounds(10, 151, 70, 30);
		panel.add(lblAccType);

		lblEmpId = new JLabel("Employee ID:");
		lblEmpId.setBounds(10, 192, 70, 30);
		panel.add(lblEmpId);

		passwordField = new JPasswordField();
		passwordField.setBounds(96, 110, 196, 30);
		panel.add(passwordField);

		cbbType = new JComboBox<AccType>();
		cbbType.setBounds(96, 151, 196, 30);
		panel.add(cbbType);
		cbbType.setModel(new DefaultComboBoxModel<>(filteredTypes));

		txtEmpId = new JTextField();
		txtEmpId.setEditable(false);
		txtEmpId.setBounds(96, 192, 196, 30);
		panel.add(txtEmpId);
		txtEmpId.setColumns(10);

		lblCreateAt = new JLabel("Create At:");
		lblCreateAt.setBounds(10, 233, 70, 30);
		panel.add(lblCreateAt);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(96, 233, 196, 30);
		panel.add(dateChooser);

		lblBlockUser = new JLabel("Active:");
		lblBlockUser.setBounds(10, 274, 70, 30);
		panel.add(lblBlockUser);

		chckbxActive = new JCheckBox("UnActive");
		chckbxActive.setBounds(96, 278, 196, 30);
		panel.add(chckbxActive);

		btnNewButton = new JButton("Update Account");
		btnNewButton.addActionListener(this::btnNewButtonActionPerformed);
		btnNewButton.setBounds(21, 315, 329, 47);
		panel.add(btnNewButton);

		lblNewLabel = new JLabel("Account ID:");
		lblNewLabel.setBounds(10, 28, 70, 30);
		panel.add(lblNewLabel);

		textID = new JTextField();
		textID.setColumns(10);
		textID.setBounds(96, 28, 196, 30);
		panel.add(textID);
//		loadDataAccount();

		btnLoad = new JButton("Refesh");
		btnLoad.addActionListener(this::btnLoadActionPerformed);
		btnLoad.setBounds(10, 395, 89, 23);

		panel_1.add(btnLoad);

		panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Add New Account:", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 255)));
		panel_3.setBounds(479, 58, 374, 303);
		panel_1.add(panel_3);
		panel_3.setLayout(null);

		lblEmailaccount = new JLabel("EmailAccount:");
		lblEmailaccount.setBounds(10, 29, 90, 30);
		panel_3.add(lblEmailaccount);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(124, 29, 196, 30);
		panel_3.add(txtEmail);

		lblNewLabel_3 = new JLabel("Password:");
		lblNewLabel_3.setBounds(10, 70, 70, 30);
		panel_3.add(lblNewLabel_3);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(124, 75, 196, 30);
		panel_3.add(passwordField_1);

		lblAccType_1 = new JLabel("Position:");
		lblAccType_1.setBounds(10, 111, 70, 30);
		panel_3.add(lblAccType_1);

		cbbType1 = new JComboBox<>();
		cbbType1.setBounds(124, 116, 196, 30);
		panel_3.add(cbbType1);
		cbbType1.setModel(new DefaultComboBoxModel<>(filteredTypes));

		lblEmpId_1 = new JLabel("Employee ID:");
		lblEmpId_1.setBounds(10, 152, 70, 30);
		panel_3.add(lblEmpId_1);

		cbbEmpId = new JComboBox<>();
		cbbEmpId.setBounds(124, 156, 196, 30);
		panel_3.add(cbbEmpId);
		loadEmpId();

		btnAddAccount = new JButton("Add New Account");
		btnAddAccount.addActionListener(this::btnAddAccountActionPerformed);
		btnAddAccount.setBounds(24, 247, 328, 45);
		panel_3.add(btnAddAccount);

		lblBlockUser_1 = new JLabel("Active:");
		lblBlockUser_1.setBounds(10, 193, 70, 30);
		panel_3.add(lblBlockUser_1);

		chckbxActive_1 = new JCheckBox("UnActive");
		chckbxActive_1.setBounds(124, 197, 196, 30);
		panel_3.add(chckbxActive_1);
		
		//Start paging
		tablePageAccount = new TablePage(this::loadDataAccount, this::countTotalRows);
		tablePageAccount.setColumnNamesAndTypes(
	            new String[]{
	                    "AccId", "Email", "Password", "Acc Type", "Created Date",
	                    "Emp Id", "Block User"

	                },
	                Map.of(
	                    0, Integer.class,
	                    1, String.class,
	                    2, String.class,
	                    3, AccType.class,
	                    4, LocalDate.class,
	                    5, Integer.class,
	                    6, Boolean.class
	                )
	            );
//		Map<Integer, Consumer<Object>> accountsMappings = Map.of(
//			    0, value -> textID.setText(value.toString()),
//			    1, value -> textEmail.setText(value.toString()),
//			    2, value -> passwordField.setText(value.toString()),
//			    3, value -> cbbType.setSelectedItem(value),
//			    4, value -> {
//			        try {
//			            dateChooser.setDate(new java.text.SimpleDateFormat("yyyy-MM-dd").parse(value.toString()));
//			        } catch (java.text.ParseException e1) {
//			            e1.printStackTrace();
//			        }
//			    },
//			    5, value -> txtEmpId.setText(value.toString()),
//			    6, value -> chckbxActive.setSelected((boolean) value)
//			);
		Map<Integer, Consumer<Object>> accountsMappings = Map.of(
			    0, value -> helper.FieldsMapper.setTextField(textID, value),
			    1, value -> helper.FieldsMapper.setTextField(textEmail, value),
			    2, value -> helper.FieldsMapper.setTextField(passwordField, value),
			    3, value -> cbbType.setSelectedItem(value),
			    4, value -> helper.FieldsMapper.setDateChooser(dateChooser, value),
			    5, value -> txtEmpId.setText(value.toString()),
			    6, value -> chckbxActive.setSelected((boolean) value)
			);
		tablePageAccount.setFieldMappings(accountsMappings);
		
		//End paging

		tablePageAccount.setBounds(10, 428, 843, 192);
		panel_1.add(tablePageAccount);

		panel_2 = new JPanel();
		tabbedPane.addTab("Employee", null, panel_2, null);
		panel_2.setLayout(null);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 412, 847, 161);
		panel_2.add(scrollPane_1);

		table_1 = new JTable();
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				table_1MouseClicked(e);
			}
		});

//		loadDataEmployee();
		scrollPane_1.setViewportView(table_1);

		panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "add employee", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 255)));
		panel_4.setBounds(10, 11, 759, 328);
		panel_2.add(panel_4);
		panel_4.setLayout(null);

		lblAvatar = new JLabel("");
		lblAvatar.setOpaque(true);
		lblAvatar.setBackground(new Color(0, 255, 255));
		lblAvatar.setBounds(20, 20, 200, 200);
		panel_4.add(lblAvatar);

		btnNewButton_1 = new JButton("Update Iamge");
		btnNewButton_1.addActionListener(this::btnNewButton_1ActionPerformed);
		btnNewButton_1.setBounds(20, 231, 144, 30);
		panel_4.add(btnNewButton_1);

		lblNewLabel_4 = new JLabel("Employee ID");
		lblNewLabel_4.setBounds(238, 20, 108, 30);
		panel_4.add(lblNewLabel_4);

		lblNewLabel_5 = new JLabel("Employee Name");
		lblNewLabel_5.setBounds(238, 61, 108, 30);
		panel_4.add(lblNewLabel_5);

		lblNewLabel_6 = new JLabel("Employee Gender");
		lblNewLabel_6.setBounds(238, 102, 108, 30);
		panel_4.add(lblNewLabel_6);

		lblNewLabel_7 = new JLabel("Employee DOB");
		lblNewLabel_7.setBounds(238, 143, 108, 30);
		panel_4.add(lblNewLabel_7);

		lblNewLabel_8 = new JLabel("Employee Phone");
		lblNewLabel_8.setBounds(238, 184, 108, 30);
		panel_4.add(lblNewLabel_8);

		txtempId = new JTextField();
		txtempId.setEditable(false);
		txtempId.setBounds(356, 20, 160, 30);
		panel_4.add(txtempId);
		txtempId.setColumns(10);

		txtEmpName = new JTextField();
		txtEmpName.setColumns(10);
		txtEmpName.setBounds(356, 61, 160, 30);
		panel_4.add(txtEmpName);

		txtEmpGender = new JTextField();
		txtEmpGender.setColumns(10);
		txtEmpGender.setBounds(356, 102, 160, 30);
		panel_4.add(txtEmpGender);

		txtEmpPhone = new JTextField();
		txtEmpPhone.setColumns(10);
		txtEmpPhone.setBounds(356, 184, 160, 30);
		panel_4.add(txtEmpPhone);

		lblNewLabel_9 = new JLabel("Employee Address");
		lblNewLabel_9.setBounds(238, 225, 108, 30);
		panel_4.add(lblNewLabel_9);

		lblNewLabel_10 = new JLabel("Hire Date:");
		lblNewLabel_10.setBounds(238, 266, 108, 30);
		panel_4.add(lblNewLabel_10);

		txtEmpAddress = new JTextField();
		txtEmpAddress.setColumns(10);
		txtEmpAddress.setBounds(356, 225, 160, 30);
		panel_4.add(txtEmpAddress);

		btnUpdateEmp = new JButton("Update Info");
		btnUpdateEmp.addActionListener(this::btnUpdateEmpActionPerformed);

		btnUpdateEmp.setBounds(554, 63, 143, 50);
		panel_4.add(btnUpdateEmp);

		btnNewButton_3 = new JButton("Add New Employee");
		btnNewButton_3.addActionListener(this::btnNewButton_3ActionPerformed);
		btnNewButton_3.setBounds(554, 196, 143, 50);
		panel_4.add(btnNewButton_3);

		dateChooser_HD = new JDateChooser();
		dateChooser_HD.setBounds(356, 266, 160, 30);
		panel_4.add(dateChooser_HD);

		dateChooser_DOB = new JDateChooser();
		dateChooser_DOB.setBounds(356, 143, 160, 30);
		panel_4.add(dateChooser_DOB);

		btnRefeshEmp = new JButton("Refesh");
		btnRefeshEmp.addActionListener(this::btnRefeshEmpActionPerformed);
		btnRefeshEmp.setBounds(10, 378, 89, 23);
		panel_2.add(btnRefeshEmp);

	}

	public void loadEmpId() {
		var dao = new EmployeeDao();
		var empIds = dao.getEmpIdsWithoutAccount();
		cbbEmpId.removeAllItems();
		for (var empId : empIds) {
			cbbEmpId.addItem(empId);
		}
	}

// load account in table
	public List<Object[]> loadDataAccount(int currentPage, int numberOfRows) {
		List<Object[]> list = new ArrayList<>();
		var dao = new AccountDao();
		var accList = dao.pagingAccount(currentPage, numberOfRows);

		for (var acc : accList) {
			list.add(new Object[] { acc.getAccId(), acc.getAccEmail(), acc.getAccPass(), acc.getAccType(),
					acc.getAccCreateDate(), acc.getEmpId(), acc.getAccStatus() }

			);
		}
		
		
		return list;

	}
	
	private Integer countTotalRows() {
      var accdao = new AccountDao();
      return accdao.countAcc(); // Only counting total rows here
  }
	
	 protected void tableMouseClicked(MouseEvent e, JTable table, Map<Integer, Consumer<Object>> fieldMappings) {
	        int row = table.getSelectedRow();
	        fieldMappings.forEach((columnIndex, action) -> {
	            Object value = table.getValueAt(row, columnIndex);
	            action.accept(value);
	        });
	    }

// load employee in table
	public void loadDataEmployee() {
		var model = new DefaultTableModel() {
			@Override
			public Class<?> getColumnClass(int column) {
				return switch (column) {
				case 0 -> Integer.class; // empId
				case 1 -> String.class; // empName
				case 2 -> Boolean.class; // empGender
				case 3 -> String.class; // empPhone
				case 4 -> String.class; // empAddress
				case 5 -> String.class; // empHireDate
				case 6 -> String.class; // empDOB
				case 7 -> ImageIcon.class; // empImage
				default -> String.class;
				};
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return switch (column) {
				case 7 -> false;
				default -> true;
				};
			}
		};
		model.addColumn("empId");
		model.addColumn("empName");
		model.addColumn("empGender");
		model.addColumn("empPhone");
		model.addColumn("empAddress");
		model.addColumn("empHireDate");
		model.addColumn("empDOB");
		model.addColumn("empImage"); // Store image path as String
		var dao = new EmployeeDao();
		dao.selectEmployee().forEach(emp -> {
			model.addRow(new Object[] { emp.getEmpId(), emp.getEmpName(), emp.isEmpGender(), emp.getEmpPhone(),
					emp.getEmpAddress(), emp.getEmpHireDate(), emp.getEmpDob(),
					(emp.getEmpImage() == null) ? new ImageIcon(new BufferedImage(60, 60, BufferedImage.TYPE_INT_ARGB))
							: new ImageIcon(new ImageIcon(emp.getEmpImage()).getImage().getScaledInstance(60, 60,
									Image.SCALE_SMOOTH)) });
		});
		table_1.setModel(model);
		table_1.setRowHeight(60);

	}

	protected void btnLoadActionPerformed(ActionEvent e) {
		tablePageAccount.resetTable();
//		var model = new DefaultTableModel();
//		model.addColumn("accId");
//		model.addColumn("accEmail");
//		model.addColumn("accPass");
//		model.addColumn("accType");
//		model.addColumn("accCreateDate");
//		model.addColumn("empId");
//		model.addColumn("BlockUser");
//		var dao = new AccountDao();
//		dao.selectAccount().forEach(acc -> model.addRow(new Object[] { acc.getAccId(), acc.getAccEmail(),
//				acc.getAccPass(), acc.getAccType(), acc.getAccCreateDate(), acc.getEmpId(), acc.getAccStatus() }));
//		table.setModel(model);

	}

	protected void btnNewButtonActionPerformed(ActionEvent e) {
		if (textID.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please choose a row to update");
			return;
		}

		var acc = new Account();
		acc.setAccId(Integer.parseInt(textID.getText()));
		acc.setAccEmail(textEmail.getText());
		acc.setAccPass(new String(passwordField.getPassword())); // Convert char[] to String
		acc.setAccType((AccType) cbbType.getSelectedItem());
		acc.setEmpId(Integer.parseInt(txtEmpId.getText()));
		acc.setAccCreateDate(dateChooser.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
		acc.setAccStatus(chckbxActive.isSelected());
		var dao = new AccountDao();
		dao.updateAccount(acc);

	}

	protected void btnAddAccountActionPerformed(ActionEvent e) {
		var acc = new Account();
		acc.setAccEmail(txtEmail.getText());
		acc.setAccPass(new String(passwordField_1.getPassword()));
		acc.setAccType((AccType) cbbType1.getSelectedItem());
		acc.setEmpId((Integer) cbbEmpId.getSelectedItem());
		acc.setAccStatus(chckbxActive_1.isSelected());

		var dao = new AccountDao();
		dao.insertAccount(acc);
		loadEmpId();
	}

	// ----------------Employee------------------------------
	protected void table_1MouseClicked(MouseEvent e) {
		var row = table_1.getSelectedRow();
		txtempId.setText(table_1.getValueAt(row, 0).toString());
		txtEmpName.setText(table_1.getValueAt(row, 1).toString());
		txtEmpGender.setText(table_1.getValueAt(row, 2).toString());
		txtEmpPhone.setText(table_1.getValueAt(row, 3).toString());
		txtEmpAddress.setText(table_1.getValueAt(row, 4).toString());
		try {
			dateChooser_DOB
					.setDate(new java.text.SimpleDateFormat("yyyy-MM-dd").parse(table_1.getValueAt(row, 5).toString()));
		} catch (java.text.ParseException e2) {
			e2.printStackTrace();
		}

		try {
			dateChooser_HD
					.setDate(new java.text.SimpleDateFormat("yyyy-MM-dd").parse(table_1.getValueAt(row, 6).toString()));
		} catch (java.text.ParseException e2) {
			e2.printStackTrace();
		}

		lblAvatar.setIcon(new ImageIcon(
				(((ImageIcon) table_1.getValueAt(row, 7)).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH))));

	}

	// button change image
	protected void btnNewButton_1ActionPerformed(ActionEvent e) {
		var chooser = new JFileChooser("C:\\Users\\ADMIN\\eclipse-workspace\\project_exam\\images");
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
			filenew = file.getName(); // lay ten file moi ra
			dirold = file.getAbsolutePath(); // lay duong dan file cu ra

			lblAvatar.setIcon(new ImageIcon(
					new ImageIcon(file.getAbsolutePath()).getImage().getScaledInstance(253, 184, Image.SCALE_SMOOTH)));
		}
	}

	protected void btnNewButton_3ActionPerformed(ActionEvent e) {
		var dao = new EmployeeDao();
		var emp = new Employee();
		emp.setEmpName(txtEmpName.getText());
		emp.setEmpAddress(txtEmpAddress.getText());
		emp.setEmpPhone(txtEmpPhone.getText());
		emp.setEmpHireDate(dateChooser_HD.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
		emp.setEmpDob(dateChooser_DOB.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
		emp.setEmpGender(Boolean.parseBoolean(txtEmpGender.getText()));

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
		lblAvatar.setIcon(null);

		txtempId.setText("");
		txtEmpName.setText("");
		txtEmpGender.setText("");
		txtEmpPhone.setText("");
		txtEmpAddress.setText("");
		dateChooser_HD.setDate(null);
		dateChooser_DOB.setDate(null);
	}

	protected void btnUpdateEmpActionPerformed(ActionEvent e) {
		if (txtempId.getText().isBlank()) {
			JOptionPane.showMessageDialog(null, "ID is required");
			return;
		}
		var emp = new Employee();
		emp.setEmpId(Integer.parseInt(txtempId.getText()));
		emp.setEmpName(txtEmpName.getText());
		emp.setEmpAddress(txtEmpAddress.getText());
		emp.setEmpPhone(txtEmpPhone.getText());
		emp.setEmpHireDate(dateChooser_HD.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
		emp.setEmpDob(dateChooser_DOB.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
		emp.setEmpGender(Boolean.parseBoolean(txtEmpGender.getText()));
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
		lblAvatar.setIcon(null);

		txtempId.setText("");
		txtEmpName.setText("");
		txtEmpGender.setText("");
		txtEmpPhone.setText("");
		txtEmpAddress.setText("");
		dateChooser_HD.setDate(null);
		dateChooser_DOB.setDate(null);
	}

	protected void btnRefeshEmpActionPerformed(ActionEvent e) {
		loadDataEmployee();
	}
}
