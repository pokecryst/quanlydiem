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
import service.ConnectDB;
import sub.AccountInfo;
import sub.AdminSubFrame;
import sub.MFrame_ClassManagePanel2;
import sub.ManageClass;
import sub.ReportFrame;
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

public class TestFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuBar menuBar;
    private JMenu mnNewMenu;
    private JMenuItem mntmTeacherAccInfo;

    private Account currentAcc  = new Account();
    private JDesktopPane desktopPane;
    private JMenuItem mntmPrintReport;

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
		setTitle("Score Management Software");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);
        setBounds(100, 100, 1300, 800);
        
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        mnNewMenu = new JMenu("File");
        mnNewMenu.setMnemonic('F');
        menuBar.add(mnNewMenu);

        mntmTeacherAccInfo = new JMenuItem("Account Info");
        mntmTeacherAccInfo.setMnemonic('A');
        mntmTeacherAccInfo.addActionListener(this::showInfo);
        mnNewMenu.add(mntmTeacherAccInfo);
        
        mntmPrintReport = new JMenuItem("Print Report");
        mntmPrintReport.setMnemonic('P');
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
//		JOptionPane.showMessageDialog(this, currentAcc.toString());
		var conn = ConnectDB.getCon();
		
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

	
//	protected void showReport(ActionEvent e) {
//		 try {
//	            // 1️⃣ Load and compile the Jasper template (.jrxml)	      
//			 InputStream reportStream = getClass().getClassLoader().getResourceAsStream("reports/students_report.jrxml");
//			 if (reportStream == null) {
//			     throw new RuntimeException("Report file not found in resources folder!");
//			 }
//			 JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
//
//	            // 2️⃣ Get Student Data
//	            List<Map<String, Object>> dataList = new ArrayList<>();
//	            var dao = new StudentDao();
//	            var students = dao.pagingStudent(1, 10); // Fetch 10 students (example)
//
//	            for (var stu : students) {
//	                Map<String, Object> studentData = new HashMap<>();
//	                studentData.put("stuId", stu.getStuId());
//	                studentData.put("stuName", stu.getStuName());
//	                studentData.put("stuGender", stu.isStuGender() ? "Male" : "Female");
//	                studentData.put("stuDob", java.sql.Date.valueOf(stu.getStuDob()).toString());
//	                studentData.put("stuEmail", stu.getStuEmail());
//	                studentData.put("stuPhone", stu.getStuPhone());
//	                studentData.put("stuAddress", stu.getStuAddress());
//
//	                // Handle student image
//	                if (stu.getStuImage() != null && new File(stu.getStuImage()).exists()) {
//	                    studentData.put("stuImage", stu.getStuImage()); // Pass file path
//	                } else {
//	                    studentData.put("stuImage", "default_image.png"); // Default image path
//	                }
//
//	                dataList.add(studentData);
//	            }
//
//	            // 3️⃣ Convert Data to JasperReports Data Source
//	            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);
//
//	            // 4️⃣ Fill the Report with Data
//	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
//
//	            // 5️⃣ Export to PDF
//	            JasperExportManager.exportReportToPdfFile(jasperPrint, "Student_Report.pdf");
//
//	           JOptionPane.showMessageDialog(null, "Student report generated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
//	        } catch (Exception ex) {
//	            ex.printStackTrace();
//	            JOptionPane.showMessageDialog(null, "Error generating student report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//	        }
//	}

}
