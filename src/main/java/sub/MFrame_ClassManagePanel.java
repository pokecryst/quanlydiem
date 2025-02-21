
package sub;

import java.awt.BorderLayout;
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
import java.util.List;
import java.util.Map;

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
import dao.EnrollStuDao;
import dao.GradeDao;
import dao.GradeStuDao;
import entity.Classes;
import entity.Student;
import gui.TablePage;
import service.ConnectDB;
import sub.UpdateGrade;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JDesktopPane;
import java.awt.CardLayout;

public class MFrame_ClassManagePanel extends JPanel {

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

    private TablePage tablePageStuList;
    private TablePage tablePageScoList;
    private JPanel panelAction;
    private JLabel lblInputGradeID;
    private JTextField txtInputGradeID;
    private JButton btnUpdateScore;
    private JPanel panelCard;


    public MFrame_ClassManagePanel() {
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

        var root = new DefaultMutableTreeNode("Classes");
        var dao = new ClassesDao();
        dao.selectAll().forEach(cla -> {
            root.add(new DefaultMutableTreeNode(cla.getClassName()));
        });

        tree.setModel(new DefaultTreeModel(root));
        scrollPane.setViewportView(tree);

        panelRight = new JPanel();
        splitPane.setRightComponent(panelRight);
        panelRight.setLayout(new BorderLayout(0, 0));

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        panelRight.add(tabbedPane, BorderLayout.CENTER);

        tablePageStuList = new TablePage(
        		 this::fetchDataStuList,
        		 this::countTotalRows
        		);

        tablePageStuList.setColumnNamesAndTypes(
            new String[]{
                "No", "Student ID", "Name", "Gender", "DoB",
                "Address", "Email", "Phone", "Picture", "Enroll Id"

            },
            Map.of(
                0, Integer.class,
                1, Integer.class,
                2, String.class,
                3, Boolean.class,
                4, Date.class,
                5, String.class,
                6, String.class,
                7, String.class,
                8, ImageIcon.class,
                9, Integer.class
            )
        );
        tablePageStuList.setTableStatus(false);

        tabbedPane.addTab("Student List", null, tablePageStuList, null);

        tablePageScoList = new TablePage(
       		 this::fetchDataScoList,
       		 this::countTotalRows
      		);

        tablePageScoList.setColumnNamesAndTypes(
            new String[]{
                "No", "Name", "Mid Score", "Final Score",
                "Average Score", "Grade Id"
            },
            Map.of(
                0, Integer.class,
                1, String.class,
                2, Double.class,
                3, Double.class,
                4, Double.class,
                5, Integer.class
            )
        );
        
       
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
        panelInfo.setBorder(null);
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

        lblClName = new JLabel("placeholder name");
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

        lblStuCounts = new JLabel("placeholder num");
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

        lblStartDate = new JLabel("New label");
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

        lblEndDate = new JLabel("New label");
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

        lblCourseName = new JLabel("New label");
        var gbc_lblCourseName = new GridBagConstraints();
        gbc_lblCourseName.gridx = 1;
        gbc_lblCourseName.gridy = 4;
        panelInfo.add(lblCourseName, gbc_lblCourseName);
        
        panelCard = new JPanel();
        panelOveralInfo.add(panelCard);
        panelCard.setLayout(new CardLayout(0, 0));
        
        panelAction = new JPanel();
        panelOveralInfo.add(panelAction);
        
        GridBagLayout gbl_panelAction = new GridBagLayout();
        gbl_panelAction.columnWidths = new int[]{0, 0, 0};
        gbl_panelAction.rowHeights = new int[]{0, 0, 0};
        gbl_panelAction.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_panelAction.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        panelAction.setLayout(gbl_panelAction);
        
        lblInputGradeID = new JLabel("Input Grade ID: ");
        GridBagConstraints gbc_lblInputGradeID = new GridBagConstraints();
        gbc_lblInputGradeID.insets = new Insets(0, 0, 5, 5);
        gbc_lblInputGradeID.anchor = GridBagConstraints.EAST;
        gbc_lblInputGradeID.gridx = 0;
        gbc_lblInputGradeID.gridy = 0;
        panelAction.add(lblInputGradeID, gbc_lblInputGradeID);
        
        txtInputGradeID = new JTextField();
        GridBagConstraints gbc_txtInputGradeID = new GridBagConstraints();
        gbc_txtInputGradeID.anchor = GridBagConstraints.WEST;
        gbc_txtInputGradeID.insets = new Insets(0, 0, 5, 0);
        gbc_txtInputGradeID.gridx = 1;
        gbc_txtInputGradeID.gridy = 0;
        panelAction.add(txtInputGradeID, gbc_txtInputGradeID);
        txtInputGradeID.setColumns(10);
        
        btnUpdateScore = new JButton("Update Score");
        btnUpdateScore.addActionListener(this::btnUpdateScoreActionPerformed);
        
        
        GridBagConstraints gbc_btnUpdateScore = new GridBagConstraints();
        gbc_btnUpdateScore.anchor = GridBagConstraints.WEST;
        gbc_btnUpdateScore.gridx = 1;
        gbc_btnUpdateScore.gridy = 1;
        panelAction.add(btnUpdateScore, gbc_btnUpdateScore);
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
                  en.getStudent().isstuGender(),
                  en.getStudent().getStuDob(),
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
    
    protected void btnUpdateScoreActionPerformed(ActionEvent e) {
		var gradeid =  Integer.parseInt(txtInputGradeID.getText());
		var conn = ConnectDB.getCon();
		if(helper.Valid.checkScoreExists(conn, gradeid)) {
//			JOptionPane.showMessageDialog(null, "Grade ID exists");
			var f = UpdateGrade.getInstance();
			if(!f.isVisible()) {
				f.setVisible(true);
				desktopPane.add(f);
			}			
			
		}else {
			JOptionPane.showMessageDialog(null, "Grade ID doesn't exists");
		}
		
	}


    protected void treeValueChanged(TreeSelectionEvent e) {
        var classInfo = selectClassInfoByClassName();
        
        if (classInfo == null) {
            System.err.println("No class selected or data not found.");
            return; // Exit if no class is selected
        }

        // Update class details
        var courseDao = new CourseDao();
        var enrollStuDao = new EnrollStuDao();

        lblClName.setText(classInfo.getClassName());
        lblStartDate.setText(classInfo.getStartDate().toString());
        lblEndDate.setText(classInfo.getEndDate().toString());
        lblStuCounts.setText(String.valueOf(enrollStuDao.countStudentList(classInfo)));
        lblCourseName.setText(courseDao.selectCourseById(classInfo.getCourseId()).getCourseName());

        // Refresh the table based on the selected tab
        if (tabbedPane.getSelectedComponent() == tablePageScoList) {
            tablePageScoList.resetTable();  // Fetches new data for the Score List tab
        } else if (tabbedPane.getSelectedComponent() == tablePageStuList) {
            tablePageStuList.resetTable();  // Fetches new data for the Student List tab
        }
    }



}
