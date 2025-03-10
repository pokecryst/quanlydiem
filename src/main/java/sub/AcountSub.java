package sub;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.AccountDao;
import dao.CourseDao;
import dao.EmployeeDao;
import dao.EnrollStuDao;
import dao.GradeStuDao;
import dao.StudentDao;
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

		cbbRoleId = new JComboBox<>(new DefaultComboBoxModel<>(new Integer[] { 0, 1, 2, 3 }));
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
		btnAddAcc.setBounds(475, 588, 150, 85);
		add(btnAddAcc);

		btnEditAccount = new JButton("Edit Account");
		btnEditAccount.addActionListener(this::btnEditAccountActionPerformed);
		btnEditAccount.setBounds(56, 588, 168, 85);
		add(btnEditAccount);

		btnDeleteAccount = new JButton("Delete Account");
		btnDeleteAccount.setBounds(263, 588, 168, 85);
		btnDeleteAccount.addActionListener(this::btnDeleteAccountActionPerformed);
		add(btnDeleteAccount);

		btnNewButton = new JButton("Refresh");
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
			tablePageAccount.setColumnVisibility(2, false);
			tablePageAccount.setOnTableClickListener(() -> {
			    cbbRoleId.setEnabled(false);
			    txtAccId.setEnabled(false);
			});
		
		
		add(tablePageAccount);
		
		JButton btnSearchAcc = new JButton("Search Account");
		btnSearchAcc.setBounds(668, 588, 150, 85);
		btnSearchAcc.addActionListener(this::searchBtnAction);
		add(btnSearchAcc);
		
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
		cbbEmpId.addItem(0);
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
          		    (acc.getAccCreateDate() != null) ? java.sql.Date.valueOf(acc.getAccCreateDate()) : null,

          		    acc.getAccStatus(),
          		    acc.getEmpId()
              }
          );


       }
        return list;
	}
	
	public List<Object[]> searchDataAccount(int currentPage, int numberOfRows) {
	    // Get search parameters from UI components
	    var searchAccId = (txtAccId.getText().isEmpty()) ? null : Integer.parseInt(txtAccId.getText());
	    var searchAccEmail = (txtAccmail.getText().isEmpty()) ? null : txtAccmail.getText();
	    var searchRoleId = ((Integer)cbbRoleId.getSelectedItem() == 0) ? null : (Integer) cbbRoleId.getSelectedItem();
	    var searchStatus = (chckStatusAcc.isSelected()) ? true : null;
	    var searchEmpId = ((Integer)cbbEmpId.getSelectedItem() == 0) ? null : (Integer) cbbEmpId.getSelectedItem();

	    List<Object[]> list = new ArrayList<>();
	    var dao = new AccountDao();
	    var accounts = dao.pagingSearchAccounts(searchAccEmail, searchStatus, searchAccId, searchRoleId, searchEmpId, currentPage, numberOfRows);

	    for (var acc : accounts) {
        	list.add(
              new Object[] {
            		acc.getAccId(),
            		acc.getAccEmail(),
            		acc.getAccPass(),
          		    acc.getRoleId(),
          		  (acc.getAccCreateDate() != null) ? java.sql.Date.valueOf(acc.getAccCreateDate()) : null,
          		    acc.getAccStatus(),
          		    acc.getEmpId()
              }
	        		);
	    }
	    return list;
	}
	
	protected void searchBtnAction (ActionEvent e) {
		 // Update the data fetcher and count fetcher in the existing table instance
	    tablePageAccount.setDataFetcher(this::searchDataAccount);
	    tablePageAccount.setCountFetcher(this::countTotalRowsSearchAccount);
	    
	    // Refresh the table to apply the new search parameters
	    tablePageAccount.resetTable();
	    
	    JOptionPane.showMessageDialog(null, "Search completed!");
	}

	
	private Integer countTotalRows() {
    	
      var accDao = new AccountDao();
      return accDao.countAcc(); // Only counting total rows here
  }
	
	private Integer countTotalRowsSearchAccount() {
	    var searchAccEmail = (txtAccmail.getText().isEmpty()) ? null : txtAccmail.getText();
	    var searchStatus = chckStatusAcc.isSelected() ? true : null;
	    var searchAccId = (txtAccId.getText().isEmpty()) ? null : Integer.parseInt(txtAccId.getText());
	    var searchRoleId = (cbbRoleId.getSelectedItem() == null) ? null : (Integer) cbbRoleId.getSelectedItem();
	    var searchEmpId = (cbbEmpId.getSelectedItem() == null) ? null : (Integer) cbbEmpId.getSelectedItem();

	    var dao = new AccountDao();
	    return dao.countSearchAccounts(searchAccEmail, searchStatus, searchAccId, searchRoleId, searchEmpId);
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
	
	protected void btnDeleteAccountActionPerformed(ActionEvent e) {
	    // Ensure an account is selected
	    if (txtAccId.getText().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Please select an account to delete!");
	        return;
	    }

	    // Parse Account ID
	    int accId = Integer.parseInt(txtAccId.getText());

	    // Confirm deletion
	    int confirm = JOptionPane.showConfirmDialog(null, 
	        "Are you sure you want to delete this account?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

	    if (confirm == JOptionPane.YES_OPTION) {
	        var dao = new AccountDao(); // Assuming you have an AccountDao class
	        dao.deleteAccount(accId);
	        resetEverything(); 
	    }
	}


	protected void btnNewButtonActionPerformed(ActionEvent e) {
		resetEverything();
		
	}
	
	protected void resetEverything() {
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
				txtAccId.setEnabled(true);
				
				tablePageAccount.setDataFetcher(this::LoadDataAcc);
				tablePageAccount.setCountFetcher(this::countTotalRows);
				tablePageAccount.resetTable();
	}
}
