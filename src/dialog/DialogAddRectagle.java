package dialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.Rectangle;
import interfaces.CallBackListener;
import interfaces.CloseFormListener;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JLabel;

public class DialogAddRectagle extends JDialog implements ActionListener {

	private Rectangle rec;
	private JButton btnAdd;
	private JButton btnClear;
	private final JPanel pBody = new JPanel();
	private JTextField txtWidth;
	private JTextField txtHeight;
	private CallBackListener backListener;
	private CloseFormListener closeFormListener;
	
	public void setAddFormListener( CloseFormListener closeFormListener) {
		this.closeFormListener = closeFormListener;
	}
	public void setCallBackListener(CallBackListener backListener) {
		this.backListener = backListener;
	}
	
	public DialogAddRectagle() {
		setTitle("Add Rectagle");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {			
			e.printStackTrace();
		}
		setResizable(false);
		setSize(301, 119);
		setLocationRelativeTo(null);
		//setBounds(100, 100, 301, 118);
		getContentPane().setLayout(new BorderLayout());
		pBody.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(pBody, BorderLayout.CENTER);
		pBody.setLayout(null);
		
		txtWidth = new JTextField();
		txtWidth.setBounds(72, 11, 110, 20);
		pBody.add(txtWidth);
		txtWidth.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Width");
		lblNewLabel.setBounds(16, 14, 46, 14);
		pBody.add(lblNewLabel);
		{
			txtHeight = new JTextField();
			txtHeight.setColumns(10);
			txtHeight.setBounds(72, 42, 110, 20);
			pBody.add(txtHeight);
		}
		{
			JLabel lblHeight = new JLabel("Height");
			lblHeight.setBounds(16, 45, 46, 14);
			pBody.add(lblHeight);
		}
		{
			btnAdd = new JButton("Add");
			btnAdd.setBounds(192, 10, 77, 23);
			pBody.add(btnAdd);
			btnAdd.setActionCommand("OK");
			getRootPane().setDefaultButton(btnAdd);
		}
		{
			btnClear = new JButton("Clear");
			btnClear.setBounds(192, 40, 77, 23);
			pBody.add(btnClear);
			btnClear.setActionCommand("Cancel");
		}
		
		/* Register Event to Button */
		btnAdd.addActionListener(this);
		btnClear.addActionListener(this);
		
		/* Set Call back to Main Form when the Dialog was Close */
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeFormListener.setCloseFormListener(DialogAddRectagle.this);
			}
		});	
		
		txtHeight.setText("");
		txtWidth.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnAdd) {
			rec = new Rectangle();
			rec.setHeigth(Float.parseFloat(txtHeight.getText().trim()));
			rec.setWidth(Float.parseFloat(txtWidth.getText().trim()));			
			backListener.callBack(this, rec);
		}else if(e.getSource() == btnClear) {
			txtHeight.setText("");
			txtWidth.setText("");
		}
	}
}
