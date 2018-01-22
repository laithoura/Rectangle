package form;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.Rectangle;
import dialog.DialogAddRectagle;
import dialog.DialogUpdateRectagle;
import interfaces.CallBackListener;
import interfaces.CloseFormListener;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;

public class frmMain extends JFrame implements ActionListener, CallBackListener,CloseFormListener{

	private static final long serialVersionUID = 1L;
	
	private final String[] COLUMNS = {"Width","Height","Area"};
	private final int ASCENDING_SORT = 0;
	private final int DESCENDING_SORT = 1;
	
	private final int SORTBY_WIDHT = 0;
	private final int SORTBY_HEIGHT = 1;
	private final int SORTBY_AREA = 2;
	
	private static int index = -1;
	private Rectangle recUpdating;
	private float total;
	private DialogAddRectagle dAddRectangle;
	private DialogUpdateRectagle dUpdateRectable;
	
	private DefaultTableModel tableModel;
	private JTable tbViewRectangle;	
	private JPanel pContainer;
	private JPanel pBanner;
	private JPanel pBody;
	private JScrollPane scrollTable;
		
	private JButton btnNew;
	private JButton btnDelete;
	private JButton btnUpdate;
	private ArrayList<Rectangle> recs = new ArrayList<>();
	
	private JPanel pFooter;
	private JTextField txtTotalArea;
	private JLabel lblTotalArea;
	private JPanel pSortData;
	private JLabel lblSortBy;
	private JComboBox<String> cboSortBy;
	private JPanel pSortMode;
	private JRadioButton rdAscending;
	private JRadioButton rdDescending;

	private ButtonGroup btnGroupSort = new ButtonGroup();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					frmMain frame = new frmMain();
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
	public frmMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(503,327);
		setResizable(false);
		setLocationRelativeTo(null);
		//setBounds(100, 100, 450, 300);	
		initControlls();
		addControllToFrame();
		initRectangleList();	
		registerEvent();
	}

	private void registerEvent() {
		btnNew.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnDelete.addActionListener(this);	
		
		/*Register when Dialog is being worked*/		
		dAddRectangle.setCallBackListener(this);
		dUpdateRectable.setCallBackListener(this);
		
		/*Register when Dialog is closed*/
		dAddRectangle.setAddFormListener(this);
		dUpdateRectable.setUpdateFormListener(this);		
			
		/* Sort Part */
		rdAscending.addActionListener(this);
		rdDescending.addActionListener(this);
		cboSortBy.addActionListener(this);
		
	}

	private void initRectangleList() {
		
		recs.add(new Rectangle(1, 2));
		recs.add(new Rectangle(5, 6));
		recs.add(new Rectangle(11, 12));
		recs.add(new Rectangle(9, 10));
		recs.add(new Rectangle(3, 4));
		recs.add(new Rectangle(7, 8));
		
		/*Sort By Width => Ascending sort*/
		SortRecs(ASCENDING_SORT, SORTBY_WIDHT);
		sumTotalArea();
				
		tbViewRectangle.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD,14));
		tbViewRectangle.setFont(new Font("Khmer OS System", Font.LAYOUT_LEFT_TO_RIGHT, 12));
		tbViewRectangle.setRowHeight(25);			
		
		/*TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tbModel);
		tbRectangle.setRowSorter(sorter);
		
		ArrayList<RowSorter.SortKey> sortkeys  = new ArrayList<>();
		sortkeys.add(new RowSorter.SortKey(2, SortOrder.DESCENDING));
		sortkeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
		sorter.setSortKeys(sortkeys);
		*/
	}

	private void initControlls() {
		
		dAddRectangle = new DialogAddRectagle();
		dUpdateRectable = new DialogUpdateRectagle();
		
		pSortMode = new JPanel();
		pSortData = new JPanel();
				
		rdAscending = new JRadioButton("Ascending");
		rdDescending = new JRadioButton("Descending");	
		rdAscending.setSelected(true);
		
		pContainer = new JPanel();
		pContainer.setAlignmentY(0.0f);
		pContainer.setAlignmentX(0.0f);
		pContainer.setBorder(new EmptyBorder(2, 2, 2, 2));
		pContainer.setLayout(new BorderLayout(0, 0));
		setContentPane(pContainer);		
		
		scrollTable = new JScrollPane();
		btnNew = new JButton("New");
		btnUpdate = new JButton("Update");
		btnDelete = new JButton("Delete");
		
		txtTotalArea = new JTextField(10);	
		txtTotalArea.setEditable(false);
		lblTotalArea = new JLabel("Total Area");
		
		pFooter = new JPanel();	
		pBanner = new JPanel();		
		pBody = new JPanel();		
		pBody.setLayout(new BorderLayout(0, 0));		
		
		tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(COLUMNS);
		tbViewRectangle = new JTable(tableModel);
		scrollTable.setViewportView(tbViewRectangle);
		
	}

	private void addControllToFrame() {
				
		pSortMode.add(rdAscending);		
		pSortMode.add(rdDescending);
		
		btnGroupSort.add(rdAscending);
		btnGroupSort.add(rdDescending);
				
		pBanner.add(pSortMode);
		pBanner.add(pSortData);
		
		lblSortBy = new JLabel("Sort By");
		pSortData.add(lblSortBy);
		
		cboSortBy = new JComboBox<String>();
		cboSortBy.setModel(new DefaultComboBoxModel<String>(new String[] {"Width", "Height", "Area"}));
		cboSortBy.setMinimumSize(new Dimension(40, 20));
		pSortData.add(cboSortBy);
		pBanner.add(btnNew);		
		pBanner.add(btnUpdate);		
		pBanner.add(btnDelete);
		pBody.add(scrollTable, BorderLayout.CENTER);		
		
		pContainer.add(pBody, BorderLayout.CENTER);
		pContainer.add(pBanner, BorderLayout.NORTH);
			
		pContainer.add(pFooter, BorderLayout.SOUTH);		
		
		pFooter.add(lblTotalArea);				
		pFooter.add(txtTotalArea);		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == rdAscending) {
			if(rdAscending.isSelected()) SortRecs(ASCENDING_SORT,cboSortBy.getSelectedIndex());		
		}
		else if(e.getSource() == rdDescending) {
			if(rdDescending.isSelected()) SortRecs(DESCENDING_SORT,cboSortBy.getSelectedIndex());				
		}		
		else if(e.getSource() == cboSortBy) {		
			SortRecs(rdAscending.isSelected()?ASCENDING_SORT:DESCENDING_SORT, cboSortBy.getSelectedIndex());						
		}else if(e.getSource() == btnNew) {
			dAddRectangle.setVisible(true);
		}else if(e.getSource() == btnUpdate) {
			
			index = tbViewRectangle.getSelectedRow();			
			if(index != -1) {
				recUpdating = recs.get(index);				
				dUpdateRectable.setRecToUpdateDialog(recs.get(index));
				dUpdateRectable.setVisible(true);				
			}
		}else if(e.getSource() == btnDelete) {
			if(tbViewRectangle.getRowCount()>0) {
				int ind = tbViewRectangle.getSelectedRow();
				if(ind != -1) {
					if(recs.get(ind) != recUpdating) {
						recs.remove(ind);
						tableModel.removeRow(ind);
						sumTotalArea();
						if(recs.size() <= 1) 
							enableSortControl(false);						
					}else {
						JOptionPane.showMessageDialog(frmMain.this, "This row is locked.","Message Locked",JOptionPane.INFORMATION_MESSAGE);
					}
				}				
			}			
		}
	}
	
	private void enableSortControl(boolean ena) {
		rdAscending.setEnabled(ena);
		rdDescending.setEnabled(ena);
		cboSortBy.setEnabled(ena);		
	}

	private void SortRecs(int sortMode, int sortBy) {		
						
		if(recs.size() <=1) return;
		
		if(sortBy == SORTBY_WIDHT) {
			if(sortMode == ASCENDING_SORT) {
				Collections.sort(recs, new Comparator<Rectangle>() {
				    @Override
				    public int compare(Rectangle r1, Rectangle r2) {
				    	if(r1.getWidth() > r2.getWidth()) return 1;
				        if (r1.getWidth() < r2.getWidth()) return -1;
				        return 0;
				    }
				});		
			}else {
				Collections.sort(recs, new Comparator<Rectangle>() {
				    @Override
				    public int compare(Rectangle r1, Rectangle r2) {
				    	if(r1.getWidth() < r2.getWidth()) return 1;
				        if (r1.getWidth() > r2.getWidth()) return -1;
				        return 0;
				    }
				});		
			}
		}else if(sortBy == SORTBY_HEIGHT) {
			if(sortMode == ASCENDING_SORT) {
				Collections.sort(recs, new Comparator<Rectangle>() {
				    @Override
				    public int compare(Rectangle r1, Rectangle r2) {
				    	if(r1.getHeigth() > r2.getHeigth()) return 1;
				        if (r1.getHeigth() < r2.getHeigth()) return -1;
				        return 0;
				    }
				});	
			}else {
				Collections.sort(recs, new Comparator<Rectangle>() {
				    @Override
				    public int compare(Rectangle r1, Rectangle r2) {
				    	if(r1.getHeigth() < r2.getHeigth()) return 1;
				        if (r1.getHeigth() > r2.getHeigth()) return -1;
				        return 0;
				    }
				});	
			}
		}else if(sortBy == SORTBY_AREA) {
			if(sortMode == ASCENDING_SORT) {
				Collections.sort(recs, new Comparator<Rectangle>() {
				    @Override
				    public int compare(Rectangle r1, Rectangle r2) {
				    	if(r1.getArea() > r2.getArea()) return 1;
				        if (r1.getArea() < r2.getArea()) return -1;
				        return 0;
				    }
				});	
			}else {
				Collections.sort(recs, new Comparator<Rectangle>() {
				    @Override
				    public int compare(Rectangle r1, Rectangle r2) {
				    	if(r1.getArea() < r2.getArea()) return 1;
				        if (r1.getArea() > r2.getArea()) return -1;
				        return 0;
				    }
				});	
			}
		}				
		refreshTableModel();		
	}

	private void refreshTableModel() {		
		if (tableModel.getRowCount() > 0) {
		    for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
		        tableModel.removeRow(i);
		    }
		}
		
		for(Rectangle rec:recs) {
			Object[] row = {rec.getWidth(),rec.getHeigth(),rec.getArea()}; 
			tableModel.addRow(row);
		}
	}

	@Override
	public void callBack(Object object, Rectangle r) {
		if(object != null && r != null) {
			if(object == dAddRectangle) {
				/* Add rec into ArrayList */
				recs.add(r);			
				refreshTable(r);
				
				/* IF rows are more than one, I enable Sort Part */
				if(recs.size()>1) enableSortControl(true);
				
			}else if(object == dUpdateRectable) {
				int index = recs.indexOf(recUpdating);
				recs.set(index, r);
				tableModel.setValueAt(r.getWidth(),index, 0);
				tableModel.setValueAt(r.getHeigth(),index, 1);
				tableModel.setValueAt(r.getArea(),index, 2);
				sumTotalArea();									
			}	
			if(recs.size()>1)
				SortRecs(rdAscending.isSelected()?ASCENDING_SORT:DESCENDING_SORT, cboSortBy.getSelectedIndex());		
		}			
	}
	
	private void refreshTable(Rectangle rec) {
		Object[] row = {rec.getWidth(),rec.getHeigth(),rec.getArea()};
		tableModel.addRow(row);	
		sumTotalArea();
	}
	
	private void sumTotalArea() {
		total = 0.0f;
		for(int i=0;i<recs.size();i++) {
			total += recs.get(i).getArea();
		}
		txtTotalArea.setText(total+"");
	}

	@Override
	public void setCloseFormListener(Object object) {		
		if(object == dUpdateRectable) {
			/* Release lock of updating records */
			recUpdating = null;
		}else if(object == dAddRectangle) {			
			
		}										
	}
	
}
