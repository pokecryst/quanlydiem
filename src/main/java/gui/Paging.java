
package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Paging extends JPanel {

    private static final long serialVersionUID = 1L;
    private JButton btnPagingL;
    private JButton btnPagingR;
    private JTextField textField;
    private JButton btnFirst;
    private JButton btnLast;
    private JComboBox<String> cbbNumberOfRows;
    private Integer currentPage = 1; // trang hiện tại
    private Integer numberOfRows = 10; // số dòng hiển thị trên 1 trang
    private Integer totalRows = 0; // tổng số dòng (hàng) trong csdl
    private Double totalPages = 0.0; // tổng số trang
    private PagingListener pagingListener;
    private CountFetcher countFetcher;

    public interface PagingListener {
        void onPageChanged(int currentPage, int numberOfRows);
    }

    public interface CountFetcher {
      int countTotalRows();
  }
    
    public void setCountFetcher(CountFetcher countFetcher) {
        this.countFetcher = countFetcher;
       
    }

    public Paging(CountFetcher countFetcher) {
    	 	this.countFetcher = countFetcher;  // Ensure countFetcher is assigned
        setBackground(new Color(255, 255, 255));
        setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        var gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{64, 85, 47, 126, 47, 85, 0};
        gridBagLayout.rowHeights = new int[]{31, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        btnFirst = new JButton("First");
        btnFirst.addActionListener(this::btnFirstActionPerformed);
        var gbc_btnFirst = new GridBagConstraints();
        gbc_btnFirst.anchor = GridBagConstraints.WEST;
        gbc_btnFirst.insets = new Insets(0, 0, 5, 5);
        gbc_btnFirst.gridx = 1;
        gbc_btnFirst.gridy = 0;
        add(btnFirst, gbc_btnFirst);

        btnPagingL = new JButton("<");
        btnPagingL.addActionListener(this::btnPagingLActionPerformed);
        btnPagingL.setFont(new Font("Tahoma", Font.PLAIN, 18));
        var gbc_btnPagingL = new GridBagConstraints();
        gbc_btnPagingL.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnPagingL.insets = new Insets(0, 0, 5, 5);
        gbc_btnPagingL.gridx = 2;
        gbc_btnPagingL.gridy = 0;
        add(btnPagingL, gbc_btnPagingL);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setText("1");
        var gbc_textField = new GridBagConstraints();
        gbc_textField.anchor = GridBagConstraints.WEST;
        gbc_textField.insets = new Insets(0, 0, 5, 5);
        gbc_textField.gridx = 3;
        gbc_textField.gridy = 0;
        add(textField, gbc_textField);
        textField.setColumns(10);

        btnPagingR = new JButton(">");
        btnPagingR.addActionListener(this::btnPagingRActionPerformed);
        btnPagingR.setFont(new Font("Tahoma", Font.PLAIN, 18));
        var gbc_btnPagingR = new GridBagConstraints();
        gbc_btnPagingR.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnPagingR.insets = new Insets(0, 0, 5, 5);
        gbc_btnPagingR.gridx = 4;
        gbc_btnPagingR.gridy = 0;
        add(btnPagingR, gbc_btnPagingR);

        btnLast = new JButton("Last");
        btnLast.addActionListener(this::btnLastActionPerformed);
        var gbc_btnLast = new GridBagConstraints();
        gbc_btnLast.insets = new Insets(0, 0, 5, 0);
        gbc_btnLast.anchor = GridBagConstraints.WEST;
        gbc_btnLast.gridx = 5;
        gbc_btnLast.gridy = 0;
        add(btnLast, gbc_btnLast);

        cbbNumberOfRows = new JComboBox<>();
        cbbNumberOfRows.addActionListener(this::cbbNumberOfRowsActionPerformed);
        cbbNumberOfRows.setModel(new DefaultComboBoxModel<>(new String[]{"10", "50", "100"}));
        cbbNumberOfRows.setSelectedIndex(0);
        var gbc_cbbNumberOfRows = new GridBagConstraints();
        gbc_cbbNumberOfRows.fill = GridBagConstraints.HORIZONTAL;
        gbc_cbbNumberOfRows.insets = new Insets(0, 0, 0, 5);
        gbc_cbbNumberOfRows.gridx = 3;
        gbc_cbbNumberOfRows.gridy = 1;
        add(cbbNumberOfRows, gbc_cbbNumberOfRows);

        // **Fetch initial total rows and update pagination**
        updateTotalRowsFromDao();
    }

    public void setPagingListener(PagingListener listener) {
        this.pagingListener = listener;
    }
    public Integer getNumberOfRows() {

    	return this.numberOfRows;

    }

    public void updateNumberOfRows(Integer numberOfRows) {
    	this.numberOfRows =  numberOfRows;
    }

    public void updateTotalPages() {
    	this.totalPages = Math.ceil(totalRows.doubleValue()/numberOfRows);
    	textField.setText(String.valueOf(currentPage));
    }

    /** Updated to handle possible null exceptions **/
    public void updateTotalRowsFromDao() {
        if (countFetcher != null) {
            this.totalRows = countFetcher.countTotalRows();
            updateTotalPages();
        } else {
            System.err.println("CountFetcher is not set!");
            this.totalRows = 0;
        }
    }

//    public void setTotalRows(Integer totalRows) {
//    	this.totalRows = totalRows;
//    	updateTotalPages();
//    }

    public void onPageChanged(int currentPage, int numberOfRows) {
      this.currentPage = currentPage;
      this.numberOfRows = numberOfRows;
      updatePage();
  }


    private void btnFirstActionPerformed(ActionEvent e) {
        currentPage = 1;
        updatePage();
    }

    private void btnPagingLActionPerformed(ActionEvent e) {
        if (currentPage > 1) {
            currentPage--;
            updatePage();
        }
    }

    private void btnPagingRActionPerformed(ActionEvent e) {
        if (currentPage < totalPages) {
            currentPage++;
            updatePage();
        }
    }

    private void btnLastActionPerformed(ActionEvent e) {
        currentPage = totalPages.intValue();
        updatePage();
    }

    private void cbbNumberOfRowsActionPerformed(ActionEvent e) {
    	currentPage = 1;  // Reset to page 1 when rows per page changes
    	numberOfRows = Integer.parseInt((String) cbbNumberOfRows.getSelectedItem());
      updatePage();
    }

    private void updatePage() {
      textField.setText(String.valueOf(currentPage));
      if (pagingListener != null) {
          pagingListener.onPageChanged(currentPage, numberOfRows);
      }

      updateTotalRowsFromDao();
  }



}
