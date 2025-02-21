package gui;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entity.Account;
import sub.MFrame_ClassManagePanel;
import sub.MFrame_ClassManagePanel2;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TestFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuBar menuBar;
    private JMenu mnNewMenu;
    private JMenuItem mntmTeacherAccInfo;
    private Account currentAcc  = new Account();

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					TestFrame frame = new TestFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public TestFrame(Account acc) {
		setCurrentAcc(acc);
		
		setBackground(new Color(255, 255, 255));
		setTitle("Test Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 616);
        
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        mnNewMenu = new JMenu("File");
        mnNewMenu.setMnemonic('F');
        menuBar.add(mnNewMenu);

        mntmTeacherAccInfo = new JMenuItem("Account Info");
        mntmTeacherAccInfo.addActionListener(this::showInfo);
        mnNewMenu.add(mntmTeacherAccInfo);
        
        contentPane = new JPanel();
        contentPane.setBorder(null);
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.setBackground(Color.RED);
        setContentPane(contentPane);
        
        
        MFrame_ClassManagePanel2 mainPanel = new MFrame_ClassManagePanel2(acc);
        mainPanel.setBackground(new Color(255, 255, 255));
//        mainPanel.getCurrentAccRole(acc);
        contentPane.add(mainPanel, BorderLayout.CENTER);
		
	}
	
	public void setCurrentAcc(Account acc) {
		this.currentAcc = acc;
	}
	
	
	protected void showInfo(ActionEvent e) {
		JOptionPane.showMessageDialog(this, currentAcc.toString());
	}

}
