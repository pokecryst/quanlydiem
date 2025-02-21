package sub;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import dao.GradeDao;
import entity.Grade;
import helper.Regex;

import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdateGrade extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtGradeMid;
	private JTextField txtGradeFinal;
	private JTextField txtGradeAvg;
	private JTextField txtGradeId;
	private static UpdateGrade instance = null;
	private int gradeId;

	
	public static UpdateGrade getInstance() {
		if(instance == null) {
			instance = new UpdateGrade();
			
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
					UpdateGrade frame = new UpdateGrade();
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
	public UpdateGrade() {
//		var grade = new GradeDao().selectGradebyId(gradeId);
		
		setIconifiable(false);
		setClosable(true);
		setTitle("Update Grade");
		setBounds(100, 100, 501, 338);
		getContentPane().setLayout(null);
		
		JLabel lblGradeID = new JLabel("Grade ID:");
		lblGradeID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGradeID.setBounds(116, 44, 83, 23);
		getContentPane().add(lblGradeID);
		
		JLabel lblGradeMid = new JLabel("Mid Grade:");
		lblGradeMid.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGradeMid.setBounds(116, 77, 83, 23);
		getContentPane().add(lblGradeMid);
		
		JLabel lblGradeFinal = new JLabel("Final Grade:");
		lblGradeFinal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGradeFinal.setBounds(116, 110, 83, 23);
		getContentPane().add(lblGradeFinal);
		
		JLabel lblGradeAvg = new JLabel("Avg Grade:");
		lblGradeAvg.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGradeAvg.setBounds(116, 143, 83, 23);
		getContentPane().add(lblGradeAvg);
		
		txtGradeMid = new JTextField();
//		txtGradeMid.setText(String.valueOf(grade.getMidScore()));
		txtGradeMid.setBounds(232, 81, 96, 19);
		getContentPane().add(txtGradeMid);
		txtGradeMid.setColumns(10);
		
		txtGradeFinal = new JTextField();
//		txtGradeFinal.setText(String.valueOf(grade.getFinalScore()));
		txtGradeFinal.setColumns(10);
		txtGradeFinal.setBounds(232, 114, 96, 19);
		getContentPane().add(txtGradeFinal);
		
//		var average = (grade.getMidScore()+grade.getFinalScore()*2)/3;
		txtGradeAvg = new JTextField();
//		txtGradeAvg.setText(String.valueOf(Math.round(average * 100.0) / 100.0));
		txtGradeAvg.setEditable(false);
		txtGradeAvg.setColumns(10);
		txtGradeAvg.setBounds(232, 147, 96, 19);
		getContentPane().add(txtGradeAvg);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(this::btnUpdateActionPerformed);
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdate.setBounds(114, 191, 214, 19);
		getContentPane().add(btnUpdate);
		
		txtGradeId = new JTextField();
		txtGradeId.setEditable(false);
		txtGradeId.setColumns(10);
		txtGradeId.setBounds(232, 48, 96, 19);
		getContentPane().add(txtGradeId);

	}
	
	@Override
	public void dispose() {
	    instance = null;
	    super.dispose();
	}
	
	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
		 refreshGradeDetails();
	}
	
	 private void refreshGradeDetails() {
	        var grade = new GradeDao().selectGradebyId(gradeId);
	        
	        if (grade != null) {
	        	txtGradeId.setText(String.valueOf(gradeId));
	            txtGradeMid.setText(String.valueOf(grade.getMidScore()));
	            txtGradeFinal.setText(String.valueOf(grade.getFinalScore()));
//	            var average = (grade.getMidScore() + grade.getFinalScore() * 2) / 3.0;
	            txtGradeAvg.setText(String.valueOf(grade.calcAvg(grade.getMidScore(), grade.getFinalScore())));
	        } else {
	        	txtGradeId.setText("0");
	            txtGradeMid.setText("0.0");
	            txtGradeFinal.setText("0.0");
	            txtGradeAvg.setText("0.0");
	        }
	    }
	 
	 protected void btnUpdateActionPerformed(ActionEvent e) {
		 var grade = new Grade();
		 var dao = new GradeDao();
		 var midCheck = helper.Valid.checkRegex2(Regex.DOUBLE, txtGradeMid.getText()); 
		 var finalCheck = helper.Valid.checkRegex2(Regex.DOUBLE, txtGradeFinal.getText());
		 
		 if(midCheck && finalCheck) {
			 grade.setMidScore(Double.parseDouble(txtGradeMid.getText()));
			 grade.setFinalScore(Double.parseDouble(txtGradeFinal.getText()));
			 grade.setAvgScore(dao.calcAveStrInput(txtGradeMid.getText(), txtGradeFinal.getText()));
			 grade.setGradeId(Integer.parseInt(txtGradeId.getText()));
			 dao.update(grade);
			 JOptionPane.showMessageDialog(null, "Grade Updated");
			 instance.dispose();
			
			
		 }else {
			 JOptionPane.showMessageDialog(null, "Invalid Input");
		 }
		 
		 
		 
		 
	 }
}

