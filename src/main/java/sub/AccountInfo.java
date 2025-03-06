package sub;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import dao.EmployeeDao;
import entity.Account;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.Dimension;

public class AccountInfo extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPasswordField passwordFieldCurrent;
	private JPasswordField passwordFieldNew;
	private JPasswordField passwordFieldNew_1;
	private static AccountInfo instance = null;
	private Account acc;
	private JLabel lblAccId_1;
    private JLabel lblUsername_1;
    private JLabel lblEmail_1;
    private JLabel lblPicture;

	
	
	public static AccountInfo getInstance() {
		if (instance == null) {
			instance = new AccountInfo();

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
					AccountInfo frame = new AccountInfo();
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
	public AccountInfo() {
	
		setPreferredSize(new Dimension(450, 300));
		setResizable(false);
		setTitle("Account Info");
		setClosable(true);
		
	    setResizable(true);
		setBounds(100, 100, 450, 300);
		
		JPanel panel = new JPanel();
		panel.setMaximumSize(new Dimension(450, 300));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblPicture = new JLabel("");
		lblPicture.setHorizontalAlignment(SwingConstants.CENTER);
		lblPicture.setOpaque(true);
		lblPicture.setBackground(new Color(255, 128, 0));
		lblPicture.setBounds(10, 10, 121, 121);
		panel.add(lblPicture);
		
		JLabel lblAccId = new JLabel("Account Id: ");
		lblAccId.setBounds(141, 10, 71, 21);
		panel.add(lblAccId);
		
		JLabel lblUsername = new JLabel("Name: ");
		lblUsername.setBounds(141, 41, 71, 21);
		panel.add(lblUsername);
		
		JLabel lblEmail = new JLabel("Email: ");
		lblEmail.setBounds(141, 72, 71, 21);
		panel.add(lblEmail);
		
		JLabel lblChangePassword = new JLabel("Change Password");
		lblChangePassword.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblChangePassword.setBounds(141, 103, 106, 21);
		panel.add(lblChangePassword);
		
		JLabel lblCurPass = new JLabel("Current Password:");
		lblCurPass.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCurPass.setBounds(141, 134, 106, 21);
		panel.add(lblCurPass);
		
		passwordFieldCurrent = new JPasswordField();
		passwordFieldCurrent.setBounds(258, 135, 137, 19);
		panel.add(passwordFieldCurrent);
		
		JLabel lblNewPass = new JLabel("New Password:");
		lblNewPass.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewPass.setBounds(141, 165, 106, 21);
		panel.add(lblNewPass);
		
		passwordFieldNew = new JPasswordField();
		passwordFieldNew.setBounds(258, 164, 137, 19);
		panel.add(passwordFieldNew);
		
		JLabel lblRetypeNewPassword = new JLabel("Retype new password:");
		lblRetypeNewPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRetypeNewPassword.setBounds(121, 196, 126, 21);
		panel.add(lblRetypeNewPassword);
		
		passwordFieldNew_1 = new JPasswordField();
		passwordFieldNew_1.setBounds(258, 193, 137, 19);
		panel.add(passwordFieldNew_1);
		
		JButton btnChangePass = new JButton("Update");
		btnChangePass.setBounds(286, 226, 85, 21);
		btnChangePass.addActionListener(this::btnChangePassActionPerformed);
		panel.add(btnChangePass);
		
		lblAccId_1 = new JLabel("");
		lblAccId_1.setBounds(218, 10, 71, 21);
		panel.add(lblAccId_1);
		
		lblUsername_1 = new JLabel("");
		lblUsername_1.setBounds(218, 41, 161, 21);
		panel.add(lblUsername_1);
		
		lblEmail_1 = new JLabel("");
		lblEmail_1.setBounds(222, 72, 184, 21);
		panel.add(lblEmail_1);
		
		
//		JOptionPane.showMessageDialog(this, this.getWidth());
//        JOptionPane.showMessageDialog(this, this.getHeight());

	}
	@Override
	public void dispose() {
	    instance = null;
	    super.dispose();
	}
	
	public void setAccount(Account acc) {
		this.acc = acc;
		refreshDetails();
	}
	
	protected void btnChangePassActionPerformed(ActionEvent e) {
		
		var accdao = new dao.AccountDao();
		var currentPass = new String(passwordFieldCurrent.getPassword());
		var newPass = new String(passwordFieldNew.getPassword());
		var newPass1 = new String(passwordFieldNew_1.getPassword());

		if (!accdao.validatePass(currentPass, acc)) {
		    JOptionPane.showMessageDialog(this, acc.getAccPass());
		    return;
		}

		if (!newPass.equals(newPass1)) {
			JOptionPane.showMessageDialog(this, "New password does not match");
			return;
		}

		acc.setAccPass(newPass);

	
	
		accdao.updateAccount(acc);
		

		JOptionPane.showMessageDialog(this, "Password updated successfully");
		passwordFieldCurrent.setText("");
		passwordFieldNew.setText("");
		passwordFieldNew_1.setText("");
		
	}
	
	private void refreshDetails() {
		var empdao = new EmployeeDao();
		var emp = empdao.selectEmpByID(acc.getEmpId());
		var image =
		(emp.getEmpImage() == null) ? new ImageIcon(new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB))
				: new ImageIcon(new ImageIcon(emp.getEmpImage()).getImage().getScaledInstance(80, 80,
						Image.SCALE_SMOOTH));
		
		lblAccId_1.setText(String.valueOf(acc.getAccId()));
        lblUsername_1.setText(emp.getEmpName());
        lblEmail_1.setText(acc.getAccEmail());
        lblPicture.setIcon(image);
//        helper.FieldsMapper.setImageLabel(lblAccId_1, acc);
    }
	
}
