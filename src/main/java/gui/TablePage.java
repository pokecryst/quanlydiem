package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import dao.EnrollStuDao;
import gui.Paging.CountFetcher;

public class TablePage extends JPanel implements Paging.PagingListener {

    private static final long serialVersionUID = 1L;
    private Paging paging;
    private DataFetcher dataFetcher;
    private Paging.CountFetcher countFetcher;
    private JTable table;
    private DefaultTableModel tableModel;
    private String[] columnNames;
    private Map<Integer, Class<?>> columnTypes = new HashMap<>();
    private Map<Integer, Consumer<Object>> fieldMappings;
	

    // Functional interfaces for dynamic data and count fetching
    public interface DataFetcher {
    	List<Object[]> fetchData(Integer currentPage, Integer numberOfRows);

    }



    public TablePage(DataFetcher dataFetcher, Paging.CountFetcher countFetcher) {
        this.dataFetcher = dataFetcher;
        this.countFetcher = countFetcher;
        setLayout(new BorderLayout(0, 0));

        // Initialize the Paging component with CountFetcher
        paging = new Paging(countFetcher);
        paging.setPagingListener(this);
        add(paging, BorderLayout.SOUTH);
        paging.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

     // Default empty column setup (modifiable later)
        columnNames = new String[]{"Column 1", "Column 2"};
        columnTypes.put(0, String.class);
        columnTypes.put(1, String.class);

        // Initialize table model
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                return columnTypes.getOrDefault(column, Object.class);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Set to false for sorting stability
            }
        };

        // Create the table with sorting
        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setRowSorter(new TableRowSorter<>(tableModel)); // Enables sorting
        table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e,  JTable table, Map<Integer, Consumer<Object>> fieldMappings) {
				tableMouseClicked(e, table, fieldMappings);
			}
		});
        add(new JScrollPane(table), BorderLayout.CENTER);
       
        // Fetch initial data and total pages

        onPageChanged(1, paging.getNumberOfRows());  // Load the first page of data
        paging.updateTotalRowsFromDao();
    }
//
//    abstract void tableMousePressed(MouseEvent e);
//    abstract void deleteRow(ActionEvent actionevent1);




	/**
     * Event triggered when the page or number of rows changes.
     */
    @Override
    public void onPageChanged(int currentPage, int numberOfRows) {
        // Fetch data from the provided data fetcher
    		var data = dataFetcher.fetchData(currentPage, numberOfRows);
        updateTable(data);

    }
    
    //click
    
    protected void tableMouseClicked(MouseEvent e, JTable table, Map<Integer, Consumer<Object>> fieldMappings) {
        int row = table.getSelectedRow();
        fieldMappings.forEach((columnIndex, action) -> {
            Object value = table.getValueAt(row, columnIndex);
            action.accept(value);
        });
    }


    //Dynamically set the columns' name
    public void setColumnNamesAndTypes(String[] columnNames, Map<Integer, Class<?>> columnTypes) {
      this.columnNames = columnNames;
      this.columnTypes = columnTypes;
      tableModel.setColumnIdentifiers(columnNames);
      tableModel.fireTableStructureChanged();
  }


    /**
     * Resets the table data and pagination state.
     */
    public void resetTable() {
        paging.updateTotalRowsFromDao();
//        onPageChanged(1, paging.getNumberOfRows());
        paging.onPageChanged(1, paging.getNumberOfRows());
    }
    
    //Disble or Enable the ability to interact with table
    public void setTableStatus(boolean status) {
    	table.setRowSelectionAllowed(status);
    }

    /**
     * Clears and updates the table with the provided data.
     */
    private void updateTable(List<Object[]> data) {

//    	DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0)
    	tableModel.setRowCount(0); // Clear existing rows before adding new data
      for (Object[] row : data) { // Ensure it's properly handled as an array
          tableModel.addRow(row);
      }
      table.setRowHeight(60);

      var picturePathIndex = -1;

      for(var i = 0; i < table.getColumnCount(); i++) {
      	if("Picturepath".equals(table.getColumnName(i))) {
      		picturePathIndex = i;
//      		System.out.println(picturePathIndex);
      		break;
      	}

      }
      if(picturePathIndex != -1) {
      	table.getColumnModel().getColumn(picturePathIndex).setMinWidth(0);
      	table.getColumnModel().getColumn(picturePathIndex).setMaxWidth(0);
      	table.getColumnModel().getColumn(picturePathIndex).setWidth(0);
      }


    }
    
    
}
