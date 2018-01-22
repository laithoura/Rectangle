package dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.Rectangle;
import interfaces.CallBackListener;
import interfaces.CloseFormListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class DialogUpdateRectagle extends JDialog implements ActionListener{
	
	private Rectangle rec;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtHeight;
	private JTextField txtWidth;
	private JButton btnUpdate;
	private JButton btnCancel;

	private CallBackListener backListener;
	private CloseFormListener closeFormListener;
	
	public void setUpdateFormListener(CloseFormListener closeFormListener) {
		this.closeFormListener = closeFormListener;
	}
	
	public void setCallBackListener(CallBackListener backListener) {
		this.backListener = backListener;
	}
	
	public void setRecToUpdateDialog(Rectangle rec) {
		this.rec = rec;
		if(rec != null) {
			txtHeight.setText(rec.getHeigth()+"");
			txtWidth.setText(rec.getWidth()+"");			
		}		
	}
	
	public DialogUpdateRectagle() {
		setTitle("Update Rectangle");
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {			
			e.printStackTrace();
		}
		setResizable(false);
		setSize(292,124);
		setLocationRelativeTo(null);
		//setBounds(100, 100, 292, 124);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel label = new JLabel("Width");
		label.setBounds(10, 15, 46, 14);
		contentPanel.add(label);
		
		JLabel label_1 = new JLabel("Height");
		label_1.setBounds(10, 46, 46, 14);
		contentPanel.add(label_1);
		
		txtHeight = new JTextField();
		txtHeight.setColumns(10);
		txtHeight.setBounds(66, 43, 110, 20);
		contentPanel.add(txtHeight);
		
		txtWidth = new JTextField();
		txtWidth.setColumns(10);
		txtWidth.setBounds(66, 12, 110, 20);
		contentPanel.add(txtWidth);
		
		btnUpdate = new JButton("Update");
		btnUpdate.setActionCommand("OK");
		btnUpdate.setBounds(186, 11, 77, 23);
		contentPanel.add(btnUpdate);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setActionCommand("Cancel");
		btnCancel.setBounds(186, 41, 77, 23);
		contentPanel.add(btnCancel);
		
		btnCancel.addActionListener(this);
		btnUpdate.addActionListener(this);
		
		/* Set Call back to Main Form when the Dialog was Close */
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeFormListener.setCloseFormListener(DialogUpdateRectagle.this);				
			}
		});		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnUpdate) {
			rec.setHeigth(Float.parseFloat(txtHeight.getText().trim()));
			rec.setWidth(Float.parseFloat(txtWidth.getText().trim()));
			backListener.callBack(this, rec);
		}else if(e.getSource() == btnCancel) {
			txtWidth.setText(rec.getWidth()+"");
			txtHeight.setText(rec.getHeigth()+"");
		}
	}
}

