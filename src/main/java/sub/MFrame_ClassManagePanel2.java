
package sub;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import dao.ClassesDao;
import dao.CourseDao;
import dao.EmployeeDao;
import dao.EnrollStuDao;
import dao.EnrollmentDao;
import dao.GradeDao;
import dao.GradeStuDao;
import entity.Account;
import entity.Classes;
import entity.Grade;
import entity.Student;
import gui.TablePage;
import helper.Regex;
import service.ConnectDB;
import sub.UpdateGrade;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MFrame_ClassManagePanel2 extends JPanel {

    private static final long serialVersionUID = 1L;
    private JPanel contentQLD_GV;
    private JDesktopPane desktopPane;
    private JSplitPane splitPane;
    private JPanel panelLeft;
    private JScrollPane scrollPane;
    private JPanel panelRight;
    private JTabbedPane tabbedPane;
    private JPanel panelOveralInfo;
    private JTree tree;
    private JMenuItem mntmTeacherAccInfo;
    private JPanel panelInfo;
    private JLabel lblClass;
    private JLabel lblClName;
    private JLabel lblNewLabel;
    private JLabel lblStuCounts;
    private JLabel lblNewLabel_2;
    private JLabel lblNewLabel_3;
    private JLabel lblNewLabel_4;
    private JLabel lblStartDate;
    private JLabel lblEndDate;
    private JLabel lblCourseName;

    private Integer currentPage = 1; // trang hiện tại
    private Integer numberOfRows = 10; // số dòng hiển thị trên 1 trang
    private Integer totalRows = 0; // tổng số dòng (hàng) trong csdl
    private Double totalPage = 0.0; // tổng số trang
    private int classId;
    private Account currentAcc = new Account();
    private GradeStuDao gradestuDao = new GradeStuDao();

    private TablePage tablePageStuList;
    private TablePage tablePageScoList;
    private JPanel panelAction;
    private JPanel panelActionStaff;
    private JLabel lblUpdateFor;
    private JTextField txtInputGradeID;
    private JButton btnUpdateScore;
    private JButton btnManageClass;
    private JPanel panelCard;
    private JLabel lblClassID;
    private JLabel lblClassID_2;
    private JButton btnAddNewClass;
    private JLabel lblAssignedTeacher;
    private JLabel lblATeacherName;
    
    private Map<Integer, Class<?>> columnMapping = new HashMap<>();
    private DefaultMutableTreeNode root = new DefaultMutableTreeNode("Classes");
    private JTextField txtStudent;
    private JLabel lblMidScore;
    private JTextField txtMidScore;
    private JLabel lblFinalScore;
    private JTextField txtFinalScore;


   

    public MFrame_ClassManagePanel2(Account acc) {
    	currentAcc = acc;
    	
    	setLayout(new BorderLayout(0, 0));
    	
    	 // Create and add content panel
        contentQLD_GV = new JPanel();
        contentQLD_GV.setBorder(null);
        contentQLD_GV.setLayout(new BorderLayout(0, 0));
        contentQLD_GV.setBackground(new Color(255, 255, 255));
        add(contentQLD_GV);

        // Desktop pane for split layout
        desktopPane = new JDesktopPane();
        contentQLD_GV.add(desktopPane, BorderLayout.CENTER);
        desktopPane.setLayout(new BorderLayout(0, 0));
		
        splitPane = new JSplitPane();
        splitPane.setContinuousLayout(true);
        desktopPane.add(splitPane, BorderLayout.CENTER);

        panelLeft = new JPanel();
        splitPane.setLeftComponent(panelLeft);
        panelLeft.setLayout(new BorderLayout(0, 0));

        scrollPane = new JScrollPane();
        panelLeft.add(scrollPane, BorderLayout.CENTER);

        tree = new JTree();
        tree.addTreeSelectionListener(this::treeValueChanged);

        populateTree();

        panelRight = new JPanel();
        splitPane.setRightComponent(panelRight);
        panelRight.setLayout(new BorderLayout(0, 0));

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        panelRight.add(tabbedPane, BorderLayout.CENTER);

        tablePageStuList = new TablePage(
        		 this::fetchDataStuList,
        		 this::countTotalRows
        		);
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
        tablePageStuList.setColumnNamesAndTypes(
            new String[]{
                "No", "Student ID", "Name", "Gender", "DoB",
                "Address", "Email", "Phone", "Picture", "Status","Enroll Id"

            },
            columnMapping
        );
        tablePageStuList.setTableStatus(false);
        tablePageStuList.setColumnVisibility(10, false);

        tabbedPane.addTab("Student List", null, tablePageStuList, null);

        tablePageScoList = new TablePage(
       		 this::fetchDataScoList,
       		 this::countTotalRows
      		);

        tablePageScoList.setColumnNamesAndTypes(
            new String[]{
                "No", "Student ID", "Name", "Mid Score", "Final Score",
                "Average Score", "Grade Id"
            },
            Map.of(
                0, Integer.class,
                1, Integer.class,
                2, String.class,
                3, Double.class,
                4, Double.class,
                5, Double.class,
                6, Integer.class
            )
        );
        tablePageScoList.setColumnWidths(6);
        tablePageScoList.setColumnVisibility(6, false);
        Map<Integer, Consumer<Object>> accountsMappings = Map.of(
        		1, value -> helper.FieldsMapper.setTextField(txtStudent, "StuID " + value + " - " + gradestuDao.selectStuNameById((int)value)),
			    3, value -> helper.FieldsMapper.setTextField(txtMidScore, value),
			    4, value -> helper.FieldsMapper.setTextField(txtFinalScore, value),
        		6, value -> helper.FieldsMapper.setTextField(txtInputGradeID, value)	    
			);
        tablePageScoList.setFieldMappings(accountsMappings);
        
       
        tabbedPane.addTab("Score List", null, tablePageScoList, null);
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedComponent() == tablePageScoList) {
                tablePageScoList.resetTable();  // Ensures data is fetched and displayed
            }
        });

        panelOveralInfo = new JPanel();
        panelRight.add(panelOveralInfo, BorderLayout.NORTH);
        panelOveralInfo.setLayout(new GridLayout(0, 2, 0, 0));

        panelInfo = new JPanel();
        panelInfo.setBorder(new EmptyBorder(0, 10, 10, 0));
        panelOveralInfo.add(panelInfo);
        var gbl_panelInfo = new GridBagLayout();
        gbl_panelInfo.columnWidths = new int[]{56, 79, 0};
        gbl_panelInfo.rowHeights = new int[]{25, 0, 25, 25, 0};
        gbl_panelInfo.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gbl_panelInfo.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        panelInfo.setLayout(gbl_panelInfo);

        lblClass = new JLabel("Class name: ");
        var gbc_lblClass = new GridBagConstraints();
        gbc_lblClass.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblClass.insets = new Insets(0, 0, 5, 5);
        gbc_lblClass.gridx = 0;
        gbc_lblClass.gridy = 0;
        panelInfo.add(lblClass, gbc_lblClass);

        lblClName = new JLabel("");
        var gbc_lblClName = new GridBagConstraints();
        gbc_lblClName.insets = new Insets(0, 0, 5, 0);
        gbc_lblClName.anchor = GridBagConstraints.WEST;
        gbc_lblClName.gridx = 1;
        gbc_lblClName.gridy = 0;
        panelInfo.add(lblClName, gbc_lblClName);

        lblNewLabel = new JLabel("Student counts:");
        var gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 1;
        panelInfo.add(lblNewLabel, gbc_lblNewLabel);

        lblStuCounts = new JLabel("0");
        var gbc_lblStuCounts = new GridBagConstraints();
        gbc_lblStuCounts.insets = new Insets(0, 0, 5, 0);
        gbc_lblStuCounts.gridx = 1;
        gbc_lblStuCounts.gridy = 1;
        panelInfo.add(lblStuCounts, gbc_lblStuCounts);

        lblNewLabel_2 = new JLabel("Start date: ");
        var gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_2.gridx = 0;
        gbc_lblNewLabel_2.gridy = 2;
        panelInfo.add(lblNewLabel_2, gbc_lblNewLabel_2);

        lblStartDate = new JLabel("");
        var gbc_lblStartDate = new GridBagConstraints();
        gbc_lblStartDate.insets = new Insets(0, 0, 5, 0);
        gbc_lblStartDate.gridx = 1;
        gbc_lblStartDate.gridy = 2;
        panelInfo.add(lblStartDate, gbc_lblStartDate);

        lblNewLabel_3 = new JLabel("End date: ");
        var gbc_lblNewLabel_3 = new GridBagConstraints();
        gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_3.gridx = 0;
        gbc_lblNewLabel_3.gridy = 3;
        panelInfo.add(lblNewLabel_3, gbc_lblNewLabel_3);

        lblEndDate = new JLabel("");
        var gbc_lblEndDate = new GridBagConstraints();
        gbc_lblEndDate.insets = new Insets(0, 0, 5, 0);
        gbc_lblEndDate.gridx = 1;
        gbc_lblEndDate.gridy = 3;
        panelInfo.add(lblEndDate, gbc_lblEndDate);

        lblNewLabel_4 = new JLabel("Course:");
        var gbc_lblNewLabel_4 = new GridBagConstraints();
        gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_4.insets = new Insets(0, 0, 0, 5);
        gbc_lblNewLabel_4.gridx = 0;
        gbc_lblNewLabel_4.gridy = 4;
        panelInfo.add(lblNewLabel_4, gbc_lblNewLabel_4);

        lblCourseName = new JLabel("");
        var gbc_lblCourseName = new GridBagConstraints();
        gbc_lblCourseName.gridx = 1;
        gbc_lblCourseName.gridy = 4;
        panelInfo.add(lblCourseName, gbc_lblCourseName);
        
        panelCard = new JPanel();
        panelOveralInfo.add(panelCard);
        panelCard.setLayout(new CardLayout(0, 0));
        
        panelAction = new JPanel();
        panelCard.add(panelAction, "teacher");
        GridBagLayout gbl_panelAction = new GridBagLayout();
        gbl_panelAction.columnWidths = new int[]{0, 0, 0};
        gbl_panelAction.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gbl_panelAction.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_panelAction.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panelAction.setLayout(gbl_panelAction);
        
        lblUpdateFor = new JLabel("Update Score of ");
        GridBagConstraints gbc_lblUpdateFor = new GridBagConstraints();
        gbc_lblUpdateFor.insets = new Insets(0, 0, 5, 5);
        gbc_lblUpdateFor.anchor = GridBagConstraints.EAST;
        gbc_lblUpdateFor.gridx = 0;
        gbc_lblUpdateFor.gridy = 0;
        panelAction.add(lblUpdateFor, gbc_lblUpdateFor);
        
        txtStudent = new JTextField();
        txtStudent.setEditable(false);
        GridBagConstraints gbc_txtStudent = new GridBagConstraints();
        gbc_txtStudent.anchor = GridBagConstraints.WEST;
        gbc_txtStudent.insets = new Insets(0, 0, 5, 0);
        gbc_txtStudent.gridx = 1;
        gbc_txtStudent.gridy = 0;
        panelAction.add(txtStudent, gbc_txtStudent);
        txtStudent.setColumns(10);
        
        lblMidScore = new JLabel("Mid Score: ");
        GridBagConstraints gbc_lblMidScore = new GridBagConstraints();
        gbc_lblMidScore.anchor = GridBagConstraints.EAST;
        gbc_lblMidScore.insets = new Insets(0, 0, 5, 5);
        gbc_lblMidScore.gridx = 0;
        gbc_lblMidScore.gridy = 1;
        panelAction.add(lblMidScore, gbc_lblMidScore);
        
        txtMidScore = new JTextField();
        txtMidScore.setColumns(10);
        GridBagConstraints gbc_txtMidScore = new GridBagConstraints();
        gbc_txtMidScore.anchor = GridBagConstraints.WEST;
        gbc_txtMidScore.insets = new Insets(0, 0, 5, 0);
        gbc_txtMidScore.gridx = 1;
        gbc_txtMidScore.gridy = 1;
        panelAction.add(txtMidScore, gbc_txtMidScore);
        
        lblFinalScore = new JLabel("Final score: ");
        GridBagConstraints gbc_lblFinalScore = new GridBagConstraints();
        gbc_lblFinalScore.anchor = GridBagConstraints.EAST;
        gbc_lblFinalScore.insets = new Insets(0, 0, 5, 5);
        gbc_lblFinalScore.gridx = 0;
        gbc_lblFinalScore.gridy = 2;
        panelAction.add(lblFinalScore, gbc_lblFinalScore);
        
        txtFinalScore = new JTextField();
        txtFinalScore.setColumns(10);
        GridBagConstraints gbc_txtFinalScore = new GridBagConstraints();
        gbc_txtFinalScore.anchor = GridBagConstraints.WEST;
        gbc_txtFinalScore.insets = new Insets(0, 0, 5, 0);
        gbc_txtFinalScore.gridx = 1;
        gbc_txtFinalScore.gridy = 2;
        panelAction.add(txtFinalScore, gbc_txtFinalScore);
        
        btnUpdateScore = new JButton("Update Score");
        btnUpdateScore.addActionListener(this::btnUpdateScoreActionPerformed);
        
        txtInputGradeID = new JTextField();
        txtInputGradeID.setVisible(false);
        txtInputGradeID.setEditable(false);
        txtInputGradeID.setText("0");
        GridBagConstraints gbc_txtInputGradeID = new GridBagConstraints();
        gbc_txtInputGradeID.insets = new Insets(0, 0, 5, 5);
        gbc_txtInputGradeID.anchor = GridBagConstraints.WEST;
        gbc_txtInputGradeID.gridx = 0;
        gbc_txtInputGradeID.gridy = 3;
        panelAction.add(txtInputGradeID, gbc_txtInputGradeID);
        txtInputGradeID.setColumns(10);
        
        
        GridBagConstraints gbc_btnUpdateScore = new GridBagConstraints();
        gbc_btnUpdateScore.insets = new Insets(0, 0, 5, 0);
        gbc_btnUpdateScore.anchor = GridBagConstraints.WEST;
        gbc_btnUpdateScore.gridx = 1;
        gbc_btnUpdateScore.gridy = 3;
        panelAction.add(btnUpdateScore, gbc_btnUpdateScore);
        
        panelActionStaff = new JPanel();
        panelCard.add(panelActionStaff, "staff");
        
        GridBagLayout gbl_panelActionStaff = new GridBagLayout();
        gbl_panelActionStaff.columnWidths = new int[]{0, 0, 0};
        gbl_panelActionStaff.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gbl_panelActionStaff.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_panelActionStaff.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panelActionStaff.setLayout(gbl_panelActionStaff);
        
        lblClassID = new JLabel("Class ID: ");
        lblClassID.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblClassID = new GridBagConstraints();
        gbc_lblClassID.insets = new Insets(0, 0, 5, 5);
        gbc_lblClassID.gridx = 0;
        gbc_lblClassID.gridy = 0;
        panelActionStaff.add(lblClassID, gbc_lblClassID);
        
        lblClassID_2 = new JLabel("");
        lblClassID_2.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblClassID_2 = new GridBagConstraints();
        gbc_lblClassID_2.anchor = GridBagConstraints.WEST;
        gbc_lblClassID_2.insets = new Insets(0, 0, 5, 0);
        gbc_lblClassID_2.gridx = 1;
        gbc_lblClassID_2.gridy = 0;
        panelActionStaff.add(lblClassID_2, gbc_lblClassID_2);
        
        btnManageClass = new JButton("Manage Class");
        btnManageClass.addActionListener(this::btnManageClassActionPerformed);
        
        lblAssignedTeacher = new JLabel("Assigned Teacher: ");
        lblAssignedTeacher.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblAssignedTeacher = new GridBagConstraints();
        gbc_lblAssignedTeacher.insets = new Insets(0, 0, 5, 5);
        gbc_lblAssignedTeacher.gridx = 0;
        gbc_lblAssignedTeacher.gridy = 1;
        panelActionStaff.add(lblAssignedTeacher, gbc_lblAssignedTeacher);
        
        lblATeacherName = new JLabel("");
        lblATeacherName.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblATeacherName = new GridBagConstraints();
        gbc_lblATeacherName.anchor = GridBagConstraints.WEST;
        gbc_lblATeacherName.insets = new Insets(0, 0, 5, 0);
        gbc_lblATeacherName.gridx = 1;
        gbc_lblATeacherName.gridy = 1;
        panelActionStaff.add(lblATeacherName, gbc_lblATeacherName);
        
        
        GridBagConstraints gbc_btnManageClass = new GridBagConstraints();
        gbc_btnManageClass.insets = new Insets(0, 0, 0, 5);
        gbc_btnManageClass.gridx = 0;
        gbc_btnManageClass.gridy = 4;
        panelActionStaff.add(btnManageClass, gbc_btnManageClass);
        
        btnAddNewClass = new JButton("Add New Class");
        GridBagConstraints gbc_btnAddNewClass = new GridBagConstraints();
        gbc_btnAddNewClass.gridx = 1;
        gbc_btnAddNewClass.gridy = 4;
        btnAddNewClass.addActionListener(this::btnAddClassActionPerformed);
        panelActionStaff.add(btnAddNewClass, gbc_btnAddNewClass);
        
        defineCardLayout();
        
    }
     
    
//    public void getCurrentAccRole(Account currentAcc) {
//    	accId = currentAcc.getAccId();
//    	role = currentAcc.getRoleId();
//    }
    
    public void defineCardLayout() {
    	CardLayout cardLayout = (CardLayout) panelCard.getLayout();
    	if(currentAcc.getRoleId() == 2) {
			cardLayout.show(panelCard, "teacher");
    	}else {
    		cardLayout.show(panelCard, "staff");
    	}
    }
    
    
    public void populateTree() {
    	
    	 root.removeAllChildren();
    	 
         var dao = new ClassesDao();

			switch (currentAcc.getRoleId()) {
			case 1:
				categorizeTree();
				break;
			case 2:
				dao.selectByTeachID(currentAcc.getEmpId()).forEach(cla -> {
					root.add(new DefaultMutableTreeNode(cla.getClassName()));
				});
				break;
			case 3:
				dao.selectAll().forEach(cla -> {
					root.add(new DefaultMutableTreeNode(cla.getClassName()));
				});
				break;
			default:
				break;
			}
			

         tree.setModel(new DefaultTreeModel(root));
         scrollPane.setViewportView(tree);
    }
    
    private void categorizeTree() {
    	var dao = new ClassesDao();
    	DefaultMutableTreeNode enrollingNode = new DefaultMutableTreeNode("Enrolling");
	    DefaultMutableTreeNode inProgressNode = new DefaultMutableTreeNode("In Progress");
	    DefaultMutableTreeNode finishedNode = new DefaultMutableTreeNode("Finished");
	    
	    dao.selectAll().forEach(cla -> {
	        DefaultMutableTreeNode classNode = new DefaultMutableTreeNode(cla.getClassName());
	        
	        switch (cla.getClStatId()) {
	            case 1: // Enrolling
	                enrollingNode.add(classNode);	         
	                break;
	            case 2: // In Progress
	                inProgressNode.add(classNode);
	                break;
	            case 3: // Finished
	                finishedNode.add(classNode);
	                break;
	            default:
	                System.err.println("Unknown class status ID: " + cla.getClStatId());
	                break;
	        }
	    });

	    // Only add parent nodes if they have children
	    if (enrollingNode.getChildCount() > 0) root.add(enrollingNode);
	    if (inProgressNode.getChildCount() > 0) root.add(inProgressNode);
	    if (finishedNode.getChildCount() > 0) root.add(finishedNode);
    
    }
    

    private Classes selectClassInfoByClassName() {
    	var classInfo = new Classes();
    	var node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
    	if (node == null) {
        System.err.println("No node selected in the tree.");
        return null;
    }
      var className = (String) node.getUserObject();
      var classesDao = new ClassesDao();
      classInfo = classesDao.selectByClassName(className);

      return classInfo;
    }
//    private ArrayList<Integer> increasingNumber(Integer numberOfRows) {
//    	var list = new ArrayList<Integer>();
//    	if(numberOfRows==null) {
//    		return list;
//    	}
//    	for (var i = 0; i < numberOfRows; i++) {
//    		list.add(i+1);
//    	}
//    	return list;
//    }

    private List<Object[]> fetchDataStuList(int currentPage, int numberOfRows) {
    	var classInfo = selectClassInfoByClassName();
      if (classInfo == null) {
          return List.of(); // Return an empty list if no class is selected
      }

        List<Object[]> list = new ArrayList<>();
        var enrollStuDao = new EnrollStuDao();
        var enrollments = enrollStuDao.pagingStudentList(currentPage, numberOfRows, classInfo);
        var number = (currentPage - 1) * numberOfRows + 1;

        for(var en : enrollments) {
        	list.add(
              new Object[] {
                  number++,
                  en.getStudent().getStuId(),
                  en.getStudent().getStuName(),
                  en.getStudent().isStuGender(),
                  java.sql.Date.valueOf(en.getStudent().getStuDob()),
                  en.getStudent().getStuAddress(),
                  en.getStudent().getStuEmail(),
                  en.getStudent().getStuPhone(),
                  (en.getStudent().getStuImage() == null) ?
                      new ImageIcon(
                          new BufferedImage(60, 60, BufferedImage.TYPE_INT_ARGB)
                      ) :
                      new ImageIcon(
                          new ImageIcon(en.getStudent().getStuImage())
                              .getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)
                      ),
                  (en.isPassStatus())? "Pass" : "Fail",
                  en.getEnrollId()
              }
          );

       }
//
        return list;
    }
    private List<Object[]> fetchDataScoList(int currentPage, int numberOfRows) {
    	var classInfo = selectClassInfoByClassName();
      if (classInfo == null) {
          return List.of(); // Return an empty list if no class is selected
      }

        List<Object[]> list = new ArrayList<>();
        var gradeStuDao = new GradeStuDao();
        var gradeList = gradeStuDao.pagingGradeStu(currentPage, numberOfRows, classInfo);
        var number = (currentPage - 1) * numberOfRows + 1;

        for(var gra : gradeList) {
        	list.add(
              new Object[] {
                  number++,
                  gra.getStuId(),
                  gra.getStuName(),
                  gra.getMidScore(),
                  gra.getFinalScore(),
                  gra.getAvgScore(),
                  gra.getGradeId()

              }
          );

       }
//
        return list;
    }

    private Integer countTotalRows() {
    	var classInfo = selectClassInfoByClassName();
      if (classInfo == null) {
          return 0; // Return an empty list if no class is selected
      }

      var enrollStuDao = new EnrollStuDao();
      return enrollStuDao.countStudentList(classInfo); // Only counting total rows here
  }
    
//    protected void btnManageClassActionPerformed(ActionEvent e) {
//    	if(helper.Valid.checkRegex2(Regex.INTNUM, txtInputClassID.getText())) {
//    		var classid =  classId;
//    		var conn = ConnectDB.getCon();
//    		if(helper.Valid.checkClassExists(conn, classid)) {
//    			var f = ManageClass.getInstance();
//    			
//    			f.setClassId(classid);
//    			if(!f.isVisible()) {
//    				f.setVisible(true);
//    				desktopPane.add(f);
//    				
//    				 // Add an internal frame listener to perform actions when the frame is closed
//                    f.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
//                        @Override
//                        public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
//                            // Reset the table when the frame is closed
//                        	populateTree();
//                        }
//                    });
//    			}
//    			 f.toFront(); 
//    		        f.moveToFront(); 
//    			
//    		}else {
//    			JOptionPane.showMessageDialog(null, "Class ID doesn't exists");
//    		}
//    	}else{
//    		JOptionPane.showMessageDialog(null, "Invalid Input");
//    	};
//    		}
    protected void btnManageClassActionPerformed(ActionEvent e) {

    		var conn = ConnectDB.getCon();
    		if(helper.Valid.checkClassExists(conn, classId)) {
    			var f = ManageClass.getInstance();
    			
    			f.setClassId(classId);
    			if(!f.isVisible()) {
    				f.setVisible(true);
    				desktopPane.add(f);
    				
    				 // Add an internal frame listener to perform actions when the frame is closed
                    f.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
                        @Override
                        public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                            // Reset the table when the frame is closed
                        	populateTree();
                        }
                    });
    			}
    			 f.toFront(); 
    		        f.moveToFront(); 
    			
    		}else {
    			JOptionPane.showMessageDialog(null, "Class ID doesn't exists");
    		}
    	
    	}
    protected void btnAddClassActionPerformed(ActionEvent e) {
    	
    			var f = AddClass.getInstance();
    			
    			if(!f.isVisible()) {
    				f.setVisible(true);
    				desktopPane.add(f);
		
    				 // Add an internal frame listener to perform actions when the frame is closed
                    f.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
                        @Override
                        public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                            // Reset the table when the frame is closed
                        	populateTree();
                        }
                    });
    			}
    			 f.toFront(); 
    		        f.moveToFront(); 
    	
    		}
    	
    protected void btnUpdateScoreActionPerformed(ActionEvent e) {
    	var grade = new Grade();
		 var dao = new GradeDao();
		 var enrollId = dao.selectEnrollIDByGradeID(Integer.parseInt(txtInputGradeID.getText()));
		 var enrollDao = new EnrollmentDao();
		 var midCheck = helper.Valid.checkRegex2(Regex.DOUBLE, txtMidScore.getText()); 
		 var finalCheck = helper.Valid.checkRegex2(Regex.DOUBLE, txtFinalScore.getText());
		 
		 if(midCheck && finalCheck) {
			 grade.setMidScore(Double.parseDouble(txtMidScore.getText()));
			 grade.setFinalScore(Double.parseDouble(txtFinalScore.getText()));
			 grade.setAvgScore(dao.calcAveStrInput(txtMidScore.getText(), txtFinalScore.getText()));
			 grade.setGradeId(Integer.parseInt(txtInputGradeID.getText()));
			 dao.update(grade);
			 if(dao.calcAveStrInput(txtMidScore.getText(), txtFinalScore.getText()) >= 5.0) {
				 enrollDao.updateStatus(true, enrollId);
			 }else {
				 enrollDao.updateStatus(false, enrollId);
			 }
			 
			 JOptionPane.showMessageDialog(null, "Grade Updated");
			 tablePageScoList.resetTable();
			 tablePageStuList.resetTable();
			
			
		 }else {
			 JOptionPane.showMessageDialog(null, "Invalid Input");
		 }
    }
   
//    protected void btnUpdateScoreActionPerformed(ActionEvent e) {
//    	
//    	if(helper.Valid.checkRegex2(Regex.INTNUM, txtInputGradeID.getText())) {
//    		var gradeid =  Integer.parseInt(txtInputGradeID.getText());
//    		var conn = ConnectDB.getCon();
//    		if(helper.Valid.checkScoreExists(conn, gradeid)) {
//
//    			var f = UpdateGrade.getInstance();
//    			f.setGradeId(gradeid);
//    			if(!f.isVisible()) {
//    				f.setVisible(true);
//    				desktopPane.add(f);
//    				
//    				 // Add an internal frame listener to perform actions when the frame is closed
//                    f.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
//                        @Override
//                        public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
//                            // Reset the table when the frame is closed
//                        	tablePageScoList.resetTable();
//                        }
//                    });
//    			}
//    			 f.toFront(); 
//    		        f.moveToFront(); 
//    			
//    		}else {
//    			JOptionPane.showMessageDialog(null, "Grade ID doesn't exists");
//    		}
//    	}else{
//    		JOptionPane.showMessageDialog(null, "Invalid Input");
//    	};
//		
//		
//		
//	}


    protected void treeValueChanged(TreeSelectionEvent e) {
        var classInfo = selectClassInfoByClassName();
        
        
        if (classInfo == null) {
            System.err.println("No class selected or data not found.");
            return; // Exit if no class is selected
        }

        // Update class details
        var courseDao = new CourseDao();
        var teachDao = new EmployeeDao();
        var enrollStuDao = new EnrollStuDao();
        classId = classInfo.getClassId();
        
        lblClName.setText(classInfo.getClassName());
        lblStartDate.setText(classInfo.getStartDate() != null ? classInfo.getStartDate().toString() : "N/A");
        lblEndDate.setText(classInfo.getEndDate() != null ? classInfo.getEndDate().toString() : "N/A");
        lblStuCounts.setText(String.valueOf(enrollStuDao.countStudentList(classInfo)));
        lblCourseName.setText(courseDao.selectCourseById(classInfo.getCourseId()).getCourseName());
        lblClassID_2.setText(String.valueOf(classInfo.getClassId()));
//        txtInputClassID.setText(String.valueOf(classInfo.getClassId()));
        
        var teachId = classInfo.getTeachId();
        var teachName = teachDao.selectEmpNameById(teachId);
        lblATeacherName.setText(teachId + " - " + teachName);

        // Refresh the table based on the selected tab
        if (tabbedPane.getSelectedComponent() == tablePageScoList) {
            tablePageScoList.resetTable();  // Fetches new data for the Score List tab
        } else if (tabbedPane.getSelectedComponent() == tablePageStuList) {
            tablePageStuList.resetTable();  // Fetches new data for the Student List tab
        }
    }



}
