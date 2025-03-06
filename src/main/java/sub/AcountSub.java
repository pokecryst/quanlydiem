package sub;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import dao.AccountDao;
import dao.CourseDao;
import dao.EmployeeDao;
import dao.EnrollStuDao;
import dao.GradeStuDao;
import entity.Account;
import entity.Course;
import entity.Employee;
import gui.TablePage;
import gui.TablePage.DataFetcher;
import gui.Paging.CountFetcher;

public class AcountSub extends JPanel {

	private static final long serialVersionUID = 1L;
	private TablePage tablePageAccount;
	private JLabel            lblAccId;
	private JLabel            lblAccMail;
	private JLabel            lblAccPass;
	private JLabel            lblRoleId;
	private JLabel            lblAccStatus;
	private JLabel            lblEmpId;
	private JTextField        txtAccId;
	private JTextField        txtAccmail;
	private JPasswordField    passwordField;
	private JComboBox         cbbRoleId;
	private JCheckBox         chckStatusAcc;
	private JComboBox<Integer> cbbEmpId = new JComboBox<>();
	private JLabel  lblTitle;
	private JButton btnAddAcc;
	private JButton btnEditAccount;
	private JButton btnDeleteAccount;
	private JButton           btnNewButton;

	/**
	 * Create the panel.
	 */
	public AcountSub() {
		setLayout(null);
		setBounds(0, 0, 892, 724);

		lblAccId = new JLabel("Account ID");
		lblAccId.setBounds(10, 402, 150, 30);
		add(lblAccId);

		lblAccMail = new JLabel("Email Account");
		lblAccMail.setBounds(10, 452, 150, 30);
		add(lblAccMail);

		lblAccPass = new JLabel("Password");
		lblAccPass.setBounds(10, 502, 150, 30);
		add(lblAccPass);

		lblRoleId = new JLabel("Role ID");
		lblRoleId.setBounds(512, 402, 150, 30);
		add(lblRoleId);

		lblAccStatus = new JLabel("Status Account");
		lblAccStatus.setBounds(512, 452, 150, 30);
		add(lblAccStatus);

		lblEmpId = new JLabel("Emp ID");
		lblEmpId.setBounds(512, 502, 150, 30);
		add(lblEmpId);

		txtAccId = new JTextField();
		txtAccId.setEditable(false);
		txtAccId.setBounds(170, 402, 200, 30);
		add(txtAccId);
		txtAccId.setColumns(10);

		txtAccmail = new JTextField();
		txtAccmail.setColumns(10);
		txtAccmail.setBounds(170, 452, 200, 30);
		add(txtAccmail);

		passwordField = new JPasswordField();
		passwordField.setBounds(170, 502, 200, 30);
		add(passwordField);

		cbbRoleId = new JComboBox<>(new DefaultComboBoxModel<>(new Integer[] { 1, 2, 3 }));
		cbbRoleId.setBounds(672, 406, 200, 30);
		add(cbbRoleId);

		chckStatusAcc = new JCheckBox("Active");
		chckStatusAcc.setBounds(672, 452, 200, 30);
		add(chckStatusAcc);

		cbbEmpId.setBounds(672, 502, 200, 30);
		add(cbbEmpId);
		loadEmpId();
		cbbEmpId.addActionListener(e -> {
			var selectedEmpId = (Integer) cbbEmpId.getSelectedItem();
			if (selectedEmpId != null) {
				// Fetch the role ID corresponding to the selected employee ID
				var     dao    = new EmployeeDao();
				var roleId = dao.getRoleIdByEmpId(selectedEmpId);
				if (roleId != null) {
					cbbRoleId.setSelectedItem(roleId);
				}
			}
		});
		
		lblTitle = new JLabel("Account");
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(10, 11, 872, 30);
		add(lblTitle);

		btnAddAcc = new JButton("Add Account");
		btnAddAcc.addActionListener(this::btnAddAccActionPerformed);
		btnAddAcc.setBounds(682, 573, 200, 100);
		add(btnAddAcc);

		btnEditAccount = new JButton("Edit Account");
		btnEditAccount.addActionListener(this::btnEditAccountActionPerformed);
		btnEditAccount.setBounds(10, 573, 200, 100);
		add(btnEditAccount);

		btnDeleteAccount = new JButton("Delete Account");
		btnDeleteAccount.setBounds(342, 573, 200, 100);
		add(btnDeleteAccount);

		btnNewButton = new JButton("Clear Field");
		btnNewButton.addActionListener(this::btnNewButtonActionPerformed);
		btnNewButton.setBounds(10, 368, 120, 23);
		add(btnNewButton);
		
		tablePageAccount = new TablePage(
	       		 this::LoadDataAcc,
	       		 this::countTotalRows
	      		);
		 tablePageAccount.setBounds(20, 51, 852, 303);
			tablePageAccount.setColumnNamesAndTypes(
	            new String[]{
	                "Account ID", " Email", "Password", "Role ID", "Created Date",
	                "Status", "Employee ID"
	            },
	            Map.of(
	                0, Integer.class,
	                1, String.class,
	                2, String.class,
	                3, Integer.class,
	                4, Date.class,
	                5, Boolean.class,
	                6, Integer.class
	            )
				);
			tablePageAccount.setTableStatus(true);
			Map<Integer, Consumer<Object>> accountsMappings = Map.of(
	        		0, value -> helper.FieldsMapper.setTextField(txtAccId, value),
				    1, value -> helper.FieldsMapper.setTextField(txtAccmail, value),
				    2, value -> helper.FieldsMapper.setTextField(passwordField, value),
				    3, value -> cbbRoleId.setSelectedItem(value),
				    5, value -> helper.FieldsMapper.setCheckBoxValue(chckStatusAcc, value),
				    6, value -> handleCurrentEmp(value)
				);
			tablePageAccount.setFieldMappings(accountsMappings);
			tablePageAccount.setOnTableClickListener(() -> {
			    cbbRoleId.setEnabled(false); // Disable role selection when table row is clicked
			});
		
		
		add(tablePageAccount);
		
		tablePageAccount.resetTable();
//		loadEmpId();
	}

	public void handleCurrentEmp(Object value) {
		
		var currentEmpId = (Integer) value;
		var dao = new EmployeeDao();
		var empIds = dao.getEmpIdsWithoutAccount();
		cbbEmpId.removeAllItems();
		if (currentEmpId != null) {
			cbbEmpId.addItem(currentEmpId);
			for (Integer empId : empIds) {
				cbbEmpId.addItem(empId);
			}
			cbbEmpId.setSelectedItem(currentEmpId);
		}
	}

	// Load data to combobox
	public void loadEmpId() {
		var dao    = new EmployeeDao();
		var empIds = dao.getEmpIdsWithoutAccount();
		cbbEmpId.removeAllItems();
		for (Integer empId : empIds) {
			cbbEmpId.addItem(empId);
		}
	}
	
	// event combobox

	// Load data to table
	public List<Object[]> LoadDataAcc(int currentPage, int numberOfRows) {
		 List<Object[]> list = new ArrayList<>();
		 	var dao = new AccountDao();
	        var accList = dao.pagingAccount(currentPage, numberOfRows);
		
		for(var acc : accList) {
        	list.add(
              new Object[] {
            		acc.getAccId(),
            		acc.getAccEmail(),
            		acc.getAccPass(),
          		    acc.getRoleId(),
          		    (acc.getAccCreateDate() != null)? Date.valueOf(acc.getAccCreateDate()): null,
          		    acc.getAccStatus(),
          		    acc.getEmpId()
              }
          );


       }
        return list;
	}
	
	private Integer countTotalRows() {
    	
      var accDao = new AccountDao();
      return accDao.countAcc(); // Only counting total rows here
  }

	// button add account
	protected void btnAddAccActionPerformed(ActionEvent e) {
		var acc = new Account();
		acc.setAccEmail(txtAccmail.getText());
		acc.setAccPass(new String(passwordField.getPassword()));
		acc.setRoleId((Integer) cbbRoleId.getSelectedItem());
		acc.setAccStatus(chckStatusAcc.isSelected());
		acc.setEmpId((Integer) cbbEmpId.getSelectedItem());

		var dao = new AccountDao();
		dao.insertAccount(acc);

		// clear data in textfield
		txtAccId.setText("");
		txtAccmail.setText("");
		passwordField.setText("");
		cbbRoleId.setSelectedIndex(0);
		cbbEmpId.setSelectedIndex(0);
		chckStatusAcc.setSelected(false);

		// reset data
		loadEmpId();
		tablePageAccount.resetTable();
	}

	// button edit account
	protected void btnEditAccountActionPerformed(ActionEvent e) {
		if (txtAccId.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please choose a row to update");
			return;
		}

		var acc = new Account();
		acc.setAccId(Integer.parseInt(txtAccId.getText()));
		acc.setAccEmail(txtAccmail.getText());
		acc.setAccPass(new String(passwordField.getPassword())); // Convert char[] to String
		acc.setRoleId((Integer) cbbRoleId.getSelectedItem());
		acc.setEmpId((Integer) cbbEmpId.getSelectedItem());
		acc.setAccStatus(chckStatusAcc.isSelected());

		var dao = new AccountDao();
		dao.updateAccount(acc);

		// clear data in textfield
		txtAccId.setText("");
		txtAccmail.setText("");
		passwordField.setText("");
		cbbRoleId.setSelectedIndex(0);
		cbbEmpId.setSelectedIndex(0);
		chckStatusAcc.setSelected(false);

		// reset data
		loadEmpId();
		tablePageAccount.resetTable();
	}

	protected void btnNewButtonActionPerformed(ActionEvent e) {
		// clear data in textfield
		txtAccId.setText("");
		txtAccmail.setText("");
		passwordField.setText("");
		cbbRoleId.setSelectedIndex(0);
		cbbEmpId.setSelectedIndex(0);
		chckStatusAcc.setSelected(false);

		// reset data
		loadEmpId();
		cbbRoleId.setEnabled(true);
		tablePageAccount.resetTable();
	}
}
