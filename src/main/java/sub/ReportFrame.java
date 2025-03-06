package sub;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import dao.EnrollStuDao;
import dao.EnrollmentDao;
import dao.StudentDao;

import java.awt.BorderLayout;
import gui.TablePage;
import gui.TablePage.DataFetcher;
import gui.Paging.CountFetcher;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;

public class ReportFrame extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private static ReportFrame instance = null;
	private JPanel contentPane;
	private TablePage tablePageStuList;
	private Map<Integer, Class<?>> columnMapping = new HashMap<>();
	private int classID = 0;
	
	private JPanel panelPie;
	
	private JLabel lblTotalStudents_1;
	private JLabel lblTotalEnrollPass;
	private JLabel lblTotalEnrollFail;
	private JLabel lblTotalEnrollments_1;
	
	public static ReportFrame getInstance(int classId) {
	    if (instance == null || instance.classID != classId) {
	        instance = new ReportFrame(classId);
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
					ReportFrame frame = new ReportFrame(0);
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
	public ReportFrame(int classId) {
		setClosable(true);
		this.classID = classId;
		setTitle("Report");
		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 787, 489);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));

		JPanel panelOverall = new JPanel();
		contentPane.add(panelOverall, "overall");
		panelOverall.setLayout(null);

		JLabel lblNewLabel = new JLabel("Total Pass: ");
		lblNewLabel.setBounds(30, 94, 121, 25);
		panelOverall.add(lblNewLabel);

		JLabel lblTotalStudentFail = new JLabel("Total Fail:");
		lblTotalStudentFail.setBounds(30, 122, 121, 25);
		panelOverall.add(lblTotalStudentFail);

		JLabel lblTotalStudents = new JLabel("Total Students:");
		lblTotalStudents.setBounds(30, 21, 121, 25);
		panelOverall.add(lblTotalStudents);

		lblTotalStudents_1 = new JLabel("0");
		lblTotalStudents_1.setBounds(180, 21, 68, 25);
		panelOverall.add(lblTotalStudents_1);

		lblTotalEnrollPass = new JLabel("0");
		lblTotalEnrollPass.setBounds(180, 94, 68, 25);
		panelOverall.add(lblTotalEnrollPass);

		lblTotalEnrollFail = new JLabel("0");
		lblTotalEnrollFail.setBounds(180, 122, 68, 25);
		panelOverall.add(lblTotalEnrollFail);
		
		JLabel lblTotalEnrollments = new JLabel("Total Enrollments: ");
		lblTotalEnrollments.setBounds(30, 65, 121, 25);
		panelOverall.add(lblTotalEnrollments);
		
		lblTotalEnrollments_1 = new JLabel("0");
		lblTotalEnrollments_1.setBounds(180, 65, 68, 25);
		panelOverall.add(lblTotalEnrollments_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(30, 53, 539, 2);
		panelOverall.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(30, 157, 263, 2);
		panelOverall.add(separator_1);
		
		panelPie = new JPanel();
		panelPie.setBounds(30, 169, 263, 230);
		panelOverall.add(panelPie);
		panelPie.setLayout(new BorderLayout(0, 0));

		JPanel panelSpecific = new JPanel();
		contentPane.add(panelSpecific, "specific");
		panelSpecific.setLayout(new BorderLayout(0, 0));

		tablePageStuList = new TablePage(this::fetchDataStuList, this::countTotalRows);
		columnMapping.put(0, Integer.class);
		columnMapping.put(1, Integer.class);
		columnMapping.put(2, String.class);
		columnMapping.put(3, Boolean.class);
		columnMapping.put(4, Date.class);
		columnMapping.put(5, String.class);
		columnMapping.put(6, String.class);
		columnMapping.put(7, String.class);
		columnMapping.put(8, ImageIcon.class);
		columnMapping.put(9, String.class);
		columnMapping.put(10, Integer.class);
		tablePageStuList.setColumnNamesAndTypes(new String[] { "No", "Student ID", "Name", "Gender", "DoB", "Address",
				"Email", "Phone", "Picture", "Status", "Enroll Id"

		}, columnMapping);
		tablePageStuList.setTableStatus(false);
		tablePageStuList.setColumnVisibility(10, false);
		panelSpecific.add(tablePageStuList, BorderLayout.CENTER);
		tablePageStuList.resetTable();
		defineCardLayout();
	}

	public void defineCardLayout() {
    	CardLayout cardLayout = (CardLayout) contentPane.getLayout();
		if (classID == 0) {
			cardLayout.show(contentPane, "overall");
			overallStatistic();
			var arr = ratioStat();
			 // Create Dataset
	        DefaultPieDataset dataset = new DefaultPieDataset();
	        dataset.setValue("Passed", arr[1]);  // 75 students passed
	        dataset.setValue("Failed", arr[0]);  // 25 students failed

	        // Create Chart
	        JFreeChart pieChart = ChartFactory.createPieChart(
	                "Pass/Fail Ratio", // Chart Title
	                dataset,           // Dataset
	                true,              // Show Legend
	                true,              // Show Tooltips
	                false              // URLs?
	        );
	        
	    

	        // Customize Plot
	        PiePlot plot = (PiePlot) pieChart.getPlot();
	        plot.setSectionPaint("Passed", new Color(0, 153, 76)); // Green for Passed
	        plot.setSectionPaint("Failed", new Color(204, 0, 0));  // Red for Failed
	        plot.setSimpleLabels(true);
	        
	        // Set percentage labels
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}",
                    NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()));

	        // Add Chart to Panel
	        ChartPanel chartPanel = new ChartPanel(pieChart);
	        chartPanel.setPreferredSize(new Dimension(560, 370));
//	        setContentPane(chartPanel);
	        panelPie.add(chartPanel);
    	}else {
    		cardLayout.show(contentPane, "specific");
    	}
    }
	
	private void overallStatistic() {
		var enrollDao = new EnrollmentDao();
		var stuDao = new StudentDao();
		Integer totalEnroll = enrollDao.countEnroll();
		Integer totalFail = enrollDao.countFail();
		Integer totalPass = enrollDao.countPass();
//		double ratioFail = ((double) totalFail / totalEnroll) * 100;
//		double ratioPass = 100 - ratioFail;
//		String formattedRatioFail = String.format("%.1f%%", ratioFail);
//		String formattedRatioPass = String.format("%.1f%%", ratioPass);
		
		lblTotalStudents_1.setText(stuDao.countStudent().toString());
		lblTotalEnrollments_1.setText(totalEnroll.toString());
		lblTotalEnrollFail.setText(totalFail.toString());
		lblTotalEnrollPass.setText(totalPass.toString());
		
//		lblRatioFail.setText(formattedRatioFail);
//		lblRatioPass.setText(formattedRatioPass);
		
	}
	
	private double[] ratioStat(){
		double[] arr = new double[2];
	
		var enrollDao = new EnrollmentDao();
//		Integer totalEnroll = enrollDao.countEnroll();
		Integer totalFail = enrollDao.countFail();
		Integer totalPass = enrollDao.countPass();
		Integer totalFinished = totalFail + totalPass;
//		double ratioFail = ((double) totalFail / totalEnroll) * 100;
//		double ratioPass = 100 - ratioFail;
		arr[0] = ((double) totalFail / totalFinished) * 100;
		arr[1] = 100 - arr[0];
		return arr;
	}

	private List<Object[]> fetchDataStuList(int currentPage, int numberOfRows) {

		List<Object[]> list = new ArrayList<>();
		var enrollStuDao = new EnrollStuDao();
		var enrollments = enrollStuDao.pagingStudentList(currentPage, numberOfRows, classID);
		var number = (currentPage - 1) * numberOfRows + 1;

		for (var en : enrollments) {
			list.add(new Object[] { number++, en.getStudent().getStuId(), en.getStudent().getStuName(),
					en.getStudent().isStuGender(), java.sql.Date.valueOf(en.getStudent().getStuDob()),
					en.getStudent().getStuAddress(), en.getStudent().getStuEmail(), en.getStudent().getStuPhone(),
					(en.getStudent().getStuImage() == null)
							? new ImageIcon(new BufferedImage(60, 60, BufferedImage.TYPE_INT_ARGB))
							: new ImageIcon(new ImageIcon(en.getStudent().getStuImage()).getImage()
									.getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
					(en.isPassStatus())? "Pass" : "Fail", en.getEnrollId() });

		}
//
		return list;
	}

	private Integer countTotalRows() {
		var enrollStuDao = new EnrollStuDao();
		return enrollStuDao.countStudentList(classID); // Only counting total rows here
	}
}
