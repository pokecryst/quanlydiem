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

import dao.GradeStuDao;
import dao.StudentDao;
import entity.Account;
import popup.AccountInfo;
import popup.ManageClass;
import popup.ReportFrame;
import service.ConnectDB;
import sub.AdminSubFrame;
import sub.MFrame_ClassManagePanel2;
import sub.StaffSubFrame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuBar menuBar;
    private JMenu mnNewMenu;
    private JMenuItem mntmTeacherAccInfo;
    private JMenuItem mntmPrintReport;

    private Account currentAcc  = new Account();
    private JDesktopPane desktopPane;
    

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
	public MainFrame(Account acc) {
		setCurrentAcc(acc);
		
		setBackground(new Color(255, 255, 255));
		setTitle("Score Management Software");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);
        setBounds(100, 100, 1175, 790);
        
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        mnNewMenu = new JMenu("File");
        mnNewMenu.setMnemonic('F');
        menuBar.add(mnNewMenu);

        mntmTeacherAccInfo = new JMenuItem("Account Info");
        mntmTeacherAccInfo.setMnemonic('A');
        mntmTeacherAccInfo.addActionListener(this::showInfo);
        mnNewMenu.add(mntmTeacherAccInfo);
        
        mntmPrintReport = new JMenuItem("Report");
        mntmPrintReport.setMnemonic('R');
        mntmPrintReport.addActionListener(this::showReport);
        mnNewMenu.add(mntmPrintReport);
        
        
        contentPane = new JPanel();
        contentPane.setBorder(null);
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.setBackground(Color.RED);
        setContentPane(contentPane);
        
        desktopPane = new JDesktopPane();
        contentPane.add(desktopPane, BorderLayout.CENTER);
        desktopPane.setLayout(new BorderLayout(0, 0));
        
        if(acc.getRoleId()==1) {
        	AdminSubFrame mainPanel = new AdminSubFrame(acc);
            mainPanel.setBackground(new Color(255, 255, 255));
            desktopPane.add(mainPanel, BorderLayout.CENTER);
        }else if(acc.getRoleId()==2) {
        		mntmPrintReport.setVisible(false);
        	 MFrame_ClassManagePanel2 mainPanel = new MFrame_ClassManagePanel2(acc);
             mainPanel.setBackground(new Color(255, 255, 255));
             desktopPane.add(mainPanel, BorderLayout.CENTER);
        }else {
        	StaffSubFrame mainPanel = new StaffSubFrame(acc);
            mainPanel.setBackground(new Color(255, 255, 255));
            desktopPane.add(mainPanel, BorderLayout.CENTER);
        }
       
		
	}
	
	public void setCurrentAcc(Account acc) {
		this.currentAcc = acc;
	}
	
	
	protected void showInfo(ActionEvent e) {		
			var f = AccountInfo.getInstance();
			
			f.setAccount(currentAcc);
			if(!f.isVisible()) {
				f.setVisible(true);
				desktopPane.add(f);
			}
			 f.toFront(); 
		        f.moveToFront(); 
			
		
	}
	
	protected void showReport(ActionEvent e) {
		var f =  ReportFrame.getInstance(0);
		
		if(!f.isVisible()) {
			f.setVisible(true);
			desktopPane.add(f);
		}
		 f.toFront(); 
	        f.moveToFront(); 
	}

	
}
