package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.toedter.calendar.JDateChooser;

public class Staff_frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel            contentPane;
	private JTabbedPane       tabbedPane;
	private JPanel            panel;
	private JPanel            panel_1;
	private JScrollPane       scrollPane;
	private JLabel            avatar_Teacher;
	private JLabel            lblEmpId;
	private JLabel            lblEmpName;
	private JLabel            lblEmpGender;
	private JLabel            lblEmpDob;
	private JTextField        txtEmpId;
	private JTextField        txtEmpName;
	private JRadioButton      rdbtnEmpMale;
	private JRadioButton      rdbtnEmpFemale;
	private JDateChooser      dcEmpDob;
	private JLabel            lblEmpPhone;
	private JTextField        textField;
	private JLabel            lblEmpPhone_1;
	private JLabel            lblEmpHD;
	private JTextField        textField_1;
	private JDateChooser      dcEmpHD;
	private JButton           btnAddEmp;
	private JButton           btnEmpEdit;
	private JButton           btnNewButton;
	private JTable            table;
	private JLabel            lblNewLabel;
	private JPanel            panel_2;
	private JScrollPane       scrollPaneCourse;
	private JScrollPane       scrollPaneClass;
	private JLabel            lblNewLabel_1;
	private JLabel            lblNewLabel_2;
	private JLabel            lblCourseId;
	private JTable            tableCourse;
	private JTable            tableClass;
	private JTextField        txtCourseId;
	private JLabel            lblCourseName;
	private JTextField        txtCourseName;
	private JLabel            lblCourseDesc;
	private JTextPane         textPane;
	private JLabel            lblCourseDuration;
	private JTextField        txtCourseDura;
	private JButton           btnAddCourse;
	private JButton           btnUpdateCourse;
	private JButton           btnDeleteCourse;
	private JLabel            lblClassId;
	private JLabel            lblNewLabel_4;
	private JLabel            lblNewLabel_5;
	private JLabel            lblNewLabel_6;
	private JLabel            lblNewLabel_7;
	private JLabel            lblNewLabel_8;
	private JTextField        textField_2;
	private JTextField        textField_3;
	private JTextField        textField_4;
	private JTextField        textField_5;
	private JTextField        textField_6;
	private JTextField        textField_7;
	private JButton           btnAddCourse_1;
	private JButton           btnAddCourse_2;
	private JButton           btnAddCourse_3;
	private JScrollPane       scrollPane_1;
	private JLabel            lblNewLabel_3;
	private JTable            table_1;
	private JLabel            lblNewLabel_9;
	private JButton           btnNewButton_1;
	private JLabel            lblStuId;
	private JLabel            lblStuName;
	private JLabel            lblStuGender;
	private JLabel            lblStuDob;
	private JLabel            lblStuEmail;
	private JLabel            lblStuPhone;
	private JTextField        txtStuId;
	private JTextField        txtStuName;
	private JTextField        txtStuEmail;
	private JLabel            lblStuAddress;
	private JTextField        txtStuPhone;
	private JTextField        txtStuAddress;
	private JDateChooser      dcStuDob;
	private JRadioButton      rdbtnNewRadioButton;
	private JRadioButton      rdbtnMale;
	private JButton           btnStuDelete;
	private JButton           btnStuUpdate;
	private JButton           btnStuAdd;
	private JLabel            lblEnrollId;
	private JLabel            lblEnrollDate;
	private JLabel            lblEnrollStuId;
	private JLabel            lblEnrollClass;
	private JScrollPane       scrollPane_2;
	private JTextField        textField_8;
	private JTextField        textField_9;
	private JTextField        textField_10;
	private JTextField        textField_11;
	private JButton           btnNewButton_2;
	private JButton           btnNewButton_3;


	public Staff_frame() {
		setTitle("STAFF");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1144, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setBounds(0, 0, 1128, 729);
		contentPane.add(tabbedPane);

		panel = new JPanel();
		tabbedPane.addTab("Teacher", new ImageIcon("D:\\Repos\\quanlydiem\\icon\\teacher.png"),
		    panel, null);
		panel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 35, 872, 279);
		panel.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		avatar_Teacher = new JLabel("");
		avatar_Teacher.setOpaque(true);
		avatar_Teacher.setBackground(new Color(0, 255, 255));
		avatar_Teacher.setHorizontalAlignment(SwingConstants.LEFT);
		avatar_Teacher.setBounds(10, 388, 130, 160);
		panel.add(avatar_Teacher);

		lblEmpId = new JLabel("ID Employee:");
		lblEmpId.setBounds(188, 388, 90, 30);
		panel.add(lblEmpId);

		lblEmpName = new JLabel("Full name");
		lblEmpName.setBounds(188, 438, 90, 30);
		panel.add(lblEmpName);

		lblEmpGender = new JLabel("Gender");
		lblEmpGender.setBounds(188, 488, 90, 30);
		panel.add(lblEmpGender);

		lblEmpDob = new JLabel("DOB");
		lblEmpDob.setBounds(188, 538, 90, 30);
		panel.add(lblEmpDob);

		txtEmpId = new JTextField();
		txtEmpId.setBounds(288, 388, 150, 30);
		panel.add(txtEmpId);
		txtEmpId.setColumns(10);

		txtEmpName = new JTextField();
		txtEmpName.setColumns(10);
		txtEmpName.setBounds(288, 438, 150, 30);
		panel.add(txtEmpName);

		rdbtnEmpMale = new JRadioButton("male");
		rdbtnEmpMale.setBounds(288, 488, 68, 30);
		panel.add(rdbtnEmpMale);

		rdbtnEmpFemale = new JRadioButton("female");
		rdbtnEmpFemale.setBounds(370, 488, 68, 30);
		panel.add(rdbtnEmpFemale);

		dcEmpDob = new JDateChooser();
		dcEmpDob.setBounds(288, 538, 150, 30);
		panel.add(dcEmpDob);

		lblEmpPhone = new JLabel("Emp Phone");
		lblEmpPhone.setBounds(531, 388, 90, 30);
		panel.add(lblEmpPhone);

		textField = new JTextField();
		textField.setBounds(631, 388, 150, 30);
		panel.add(textField);
		textField.setColumns(10);

		lblEmpPhone_1 = new JLabel("Address");
		lblEmpPhone_1.setBounds(531, 438, 90, 30);
		panel.add(lblEmpPhone_1);

		lblEmpHD = new JLabel("Hire Date");
		lblEmpHD.setBounds(531, 488, 90, 30);
		panel.add(lblEmpHD);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(631, 438, 150, 30);
		panel.add(textField_1);

		dcEmpHD = new JDateChooser();
		dcEmpHD.setBounds(631, 488, 150, 30);
		panel.add(dcEmpHD);

		btnAddEmp = new JButton("Add New");
		btnAddEmp.setBounds(10, 591, 120, 40);
		panel.add(btnAddEmp);

		btnEmpEdit = new JButton("Edit");
		btnEmpEdit.setBounds(223, 591, 120, 40);
		panel.add(btnEmpEdit);

		btnNewButton = new JButton("Delete");
		btnNewButton.setBounds(631, 591, 120, 40);
		panel.add(btnNewButton);

		lblNewLabel = new JLabel("Teacher");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel.setBounds(10, 11, 872, 24);
		panel.add(lblNewLabel);

		panel_2 = new JPanel();
		tabbedPane.addTab("Course & Class",
		    new ImageIcon("D:\\Repos\\quanlydiem\\icon\\online_course.png"), panel_2, null);
		panel_2.setLayout(null);

		scrollPaneCourse = new JScrollPane();
		scrollPaneCourse.setBounds(10, 47, 578, 270);
		panel_2.add(scrollPaneCourse);

		tableCourse = new JTable();
		scrollPaneCourse.setViewportView(tableCourse);

		scrollPaneClass = new JScrollPane();
		scrollPaneClass.setBounds(10, 388, 578, 285);
		panel_2.add(scrollPaneClass);

		tableClass = new JTable();
		scrollPaneClass.setViewportView(tableClass);

		lblNewLabel_1 = new JLabel("Class");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 358, 578, 30);
		panel_2.add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("Course");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_2.setBounds(10, 17, 578, 30);
		panel_2.add(lblNewLabel_2);

		lblCourseId = new JLabel("Course ID:");
		lblCourseId.setBounds(598, 47, 90, 30);
		panel_2.add(lblCourseId);

		txtCourseId = new JTextField();
		txtCourseId.setBounds(698, 47, 180, 30);
		panel_2.add(txtCourseId);
		txtCourseId.setColumns(10);

		lblCourseName = new JLabel("Course Name:");
		lblCourseName.setBounds(598, 91, 90, 30);
		panel_2.add(lblCourseName);

		txtCourseName = new JTextField();
		txtCourseName.setColumns(10);
		txtCourseName.setBounds(698, 88, 180, 30);
		panel_2.add(txtCourseName);

		lblCourseDesc = new JLabel("Course DESC:");
		lblCourseDesc.setBounds(598, 160, 90, 30);
		panel_2.add(lblCourseDesc);

		textPane = new JTextPane();
		textPane.setBorder(new LineBorder(new Color(192, 192, 192)));
		textPane.setBounds(698, 132, 180, 85);
		panel_2.add(textPane);

		lblCourseDuration = new JLabel("Course Duration:");
		lblCourseDuration.setBounds(598, 228, 90, 30);
		panel_2.add(lblCourseDuration);

		txtCourseDura = new JTextField();
		txtCourseDura.setBounds(698, 228, 180, 30);
		panel_2.add(txtCourseDura);
		txtCourseDura.setColumns(10);

		btnAddCourse = new JButton("Add");
		btnAddCourse.setBounds(598, 287, 89, 30);
		panel_2.add(btnAddCourse);

		btnUpdateCourse = new JButton("Update");
		btnUpdateCourse.setBounds(698, 287, 89, 30);
		panel_2.add(btnUpdateCourse);

		btnDeleteCourse = new JButton("Delete");
		btnDeleteCourse.setBounds(797, 287, 89, 30);
		panel_2.add(btnDeleteCourse);

		lblClassId = new JLabel("Class ID:");
		lblClassId.setBounds(598, 388, 90, 30);
		panel_2.add(lblClassId);

		lblNewLabel_4 = new JLabel("Class Name:");
		lblNewLabel_4.setBounds(598, 429, 90, 30);
		panel_2.add(lblNewLabel_4);

		lblNewLabel_5 = new JLabel("Start Date:");
		lblNewLabel_5.setBounds(598, 470, 90, 30);
		panel_2.add(lblNewLabel_5);

		lblNewLabel_6 = new JLabel("End Date:");
		lblNewLabel_6.setBounds(598, 511, 90, 30);
		panel_2.add(lblNewLabel_6);

		lblNewLabel_7 = new JLabel("Course ID:");
		lblNewLabel_7.setBounds(598, 553, 90, 30);
		panel_2.add(lblNewLabel_7);

		lblNewLabel_8 = new JLabel("Emp ID:");
		lblNewLabel_8.setBounds(598, 594, 90, 30);
		panel_2.add(lblNewLabel_8);

		textField_2 = new JTextField();
		textField_2.setBounds(698, 388, 180, 30);
		panel_2.add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(698, 429, 180, 30);
		panel_2.add(textField_3);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(698, 470, 180, 30);
		panel_2.add(textField_4);

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(698, 511, 180, 30);
		panel_2.add(textField_5);

		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(698, 553, 180, 30);
		panel_2.add(textField_6);

		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(698, 594, 180, 30);
		panel_2.add(textField_7);

		btnAddCourse_1 = new JButton("Add");
		btnAddCourse_1.setBounds(598, 643, 89, 30);
		panel_2.add(btnAddCourse_1);

		btnAddCourse_2 = new JButton("Add");
		btnAddCourse_2.setBounds(698, 643, 89, 30);
		panel_2.add(btnAddCourse_2);

		btnAddCourse_3 = new JButton("Add");
		btnAddCourse_3.setBounds(797, 643, 89, 30);
		panel_2.add(btnAddCourse_3);

		panel_1 = new JPanel();
		tabbedPane.addTab("Student Manager",
		    new ImageIcon("D:\\Repos\\quanlydiem\\icon\\graduated (1).png"), panel_1, null);
		panel_1.setLayout(null);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 41, 872, 263);
		panel_1.add(scrollPane_1);

		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);

		lblNewLabel_3 = new JLabel("Student");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(10, 11, 872, 29);
		panel_1.add(lblNewLabel_3);

		lblNewLabel_9 = new JLabel("");
		lblNewLabel_9.setOpaque(true);
		lblNewLabel_9.setBackground(new Color(0, 255, 255));
		lblNewLabel_9.setBounds(10, 342, 130, 160);
		panel_1.add(lblNewLabel_9);

		btnNewButton_1 = new JButton("Change Image");
		btnNewButton_1.setBounds(10, 513, 130, 41);
		panel_1.add(btnNewButton_1);

		lblStuId = new JLabel("Student ID:");
		lblStuId.setBounds(150, 342, 100, 30);
		panel_1.add(lblStuId);

		lblStuName = new JLabel("Student Name:");
		lblStuName.setBounds(150, 383, 100, 30);
		panel_1.add(lblStuName);

		lblStuGender = new JLabel("Gender:");
		lblStuGender.setBounds(150, 424, 100, 30);
		panel_1.add(lblStuGender);

		lblStuDob = new JLabel("DOB:");
		lblStuDob.setBounds(150, 465, 100, 30);
		panel_1.add(lblStuDob);

		lblStuEmail = new JLabel("Email:");
		lblStuEmail.setBounds(150, 513, 100, 30);
		panel_1.add(lblStuEmail);

		lblStuPhone = new JLabel("Phone:");
		lblStuPhone.setBounds(150, 554, 100, 30);
		panel_1.add(lblStuPhone);

		txtStuId = new JTextField();
		txtStuId.setBounds(260, 342, 120, 30);
		panel_1.add(txtStuId);
		txtStuId.setColumns(10);

		txtStuName = new JTextField();
		txtStuName.setColumns(10);
		txtStuName.setBounds(260, 388, 120, 30);
		panel_1.add(txtStuName);

		txtStuEmail = new JTextField();
		txtStuEmail.setColumns(10);
		txtStuEmail.setBounds(260, 513, 120, 30);
		panel_1.add(txtStuEmail);

		lblStuAddress = new JLabel("Address:");
		lblStuAddress.setBounds(150, 595, 100, 30);
		panel_1.add(lblStuAddress);

		txtStuPhone = new JTextField();
		txtStuPhone.setBounds(260, 554, 120, 30);
		panel_1.add(txtStuPhone);
		txtStuPhone.setColumns(10);

		txtStuAddress = new JTextField();
		txtStuAddress.setColumns(10);
		txtStuAddress.setBounds(260, 595, 120, 30);
		panel_1.add(txtStuAddress);

		dcStuDob = new JDateChooser();
		dcStuDob.setBounds(260, 465, 120, 30);
		panel_1.add(dcStuDob);

		rdbtnNewRadioButton = new JRadioButton("Female");
		rdbtnNewRadioButton.setBounds(321, 428, 59, 30);
		panel_1.add(rdbtnNewRadioButton);

		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setBounds(260, 428, 52, 30);
		panel_1.add(rdbtnMale);

		btnStuDelete = new JButton("Delete");
		btnStuDelete.setBounds(260, 651, 110, 30);
		panel_1.add(btnStuDelete);

		btnStuUpdate = new JButton("Update");
		btnStuUpdate.setBounds(130, 651, 110, 30);
		panel_1.add(btnStuUpdate);

		btnStuAdd = new JButton("Add");
		btnStuAdd.setBounds(10, 651, 110, 30);
		panel_1.add(btnStuAdd);

		lblEnrollId = new JLabel("EnRoll ID:");
		lblEnrollId.setBounds(684, 636, 80, 30);
		panel_1.add(lblEnrollId);

		lblEnrollDate = new JLabel("Enroll Date:");
		lblEnrollDate.setBounds(450, 636, 80, 30);
		panel_1.add(lblEnrollDate);

		lblEnrollStuId = new JLabel("Student ID:");
		lblEnrollStuId.setBounds(684, 595, 80, 30);
		panel_1.add(lblEnrollStuId);

		lblEnrollClass = new JLabel("Class ID:");
		lblEnrollClass.setBounds(450, 595, 80, 30);
		panel_1.add(lblEnrollClass);

		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(450, 364, 432, 201);
		panel_1.add(scrollPane_2);

		textField_8 = new JTextField();
		textField_8.setBounds(540, 595, 106, 30);
		panel_1.add(textField_8);
		textField_8.setColumns(10);

		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(540, 636, 106, 30);
		panel_1.add(textField_9);

		textField_10 = new JTextField();
		textField_10.setColumns(10);
		textField_10.setBounds(776, 595, 106, 30);
		panel_1.add(textField_10);

		textField_11 = new JTextField();
		textField_11.setColumns(10);
		textField_11.setBounds(776, 636, 106, 30);
		panel_1.add(textField_11);

		btnNewButton_2 = new JButton("New button");
		btnNewButton_2.setBounds(450, 677, 89, 23);
		panel_1.add(btnNewButton_2);

		btnNewButton_3 = new JButton("New button");
		btnNewButton_3.setBounds(793, 677, 89, 23);
		panel_1.add(btnNewButton_3);
	}
}
