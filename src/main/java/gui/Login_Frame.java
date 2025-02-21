
package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import dao.AccountDao;
import entity.Account;

public class Login_Frame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                var frame = new Login_Frame();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);// cho ve giua man hinh
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public Login_Frame() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        var panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel.setBounds(50, 50, 325, 150);
        contentPane.add(panel);
        panel.setLayout(null);

        var lblUsername = new JLabel("Username:");
        lblUsername.setBounds(10, 30, 80, 25);
        panel.add(lblUsername);

        usernameField = new JTextField();
        usernameField.setBounds(100, 30, 200, 25);
        panel.add(usernameField);
        usernameField.setColumns(10);

        var lblPassword = new JLabel("Password:");
        lblPassword.setBounds(10, 70, 80, 25);
        panel.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 70, 200, 25);
        panel.add(passwordField);

        var btnLogin = new JButton("Login");
        btnLogin.addActionListener(this::btnLoginActionPerformed);
        btnLogin.setBounds(100, 110, 200, 25);
        panel.add(btnLogin);
    }
  	protected void btnLoginActionPerformed(ActionEvent e) {
  		var username = usernameField.getText();
      var password = new String(passwordField.getPassword());

      var accountDao = new AccountDao();
      if (accountDao.validateAccount(username, password)) {
          JOptionPane.showMessageDialog(this, "Welcome Back my Partner");
//          var acc = new Account();
          var account = accountDao.selectOneAccount(username);
          if(account.getRoleId() == 1) {
        	  var AF = new Admin_Frame();
              AF.setVisible(true);
              AF.setLocationRelativeTo(null);
              dispose();
          }else if(account.getRoleId() == 2) {
        	  var MF = new TestFrame(account);
        	  MF.setVisible(true);
              MF.setLocationRelativeTo(null);
              dispose();
          }else {
        	  var MF = new TestFrame(account);
        	  MF.setVisible(true);
              MF.setLocationRelativeTo(null);
              dispose();
          }
          
      } else {
    	 
        JOptionPane.showMessageDialog(this, "Login failed");
      }
  	}
}
