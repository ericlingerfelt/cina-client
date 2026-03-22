package org.nucastrodata.dialogs;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import org.nucastrodata.dialogs.worker.OpenDialogWorker;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

public class ProgressBarDialog extends JDialog implements ActionListener, WindowListener{

	private JTextArea progressBarTextArea;
    private JProgressBar progressBar;
    private JButton cancelButton;
    private Frame frame;
    private SwingWorker<Void, Void> worker;

	public ProgressBarDialog(Frame frame, String initialString, SwingWorker<Void, Void> worker){
	
		super(frame, "Please wait...", Dialog.ModalityType.APPLICATION_MODAL);
		this.frame = frame;
		this.worker = worker;
		
		addWindowListener(this);
    	setSize(700, 400);
		setLocationRelativeTo(frame);
		
		progressBarTextArea = new JTextArea("", 5, 30);
		progressBarTextArea.setLineWrap(true);
		progressBarTextArea.setWrapStyleWord(true);
		progressBarTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(progressBarTextArea);

		String progressBarString = initialString;
		
		progressBarTextArea.setText(progressBarString);
		progressBarTextArea.setCaretPosition(0);
		
		progressBar = new JProgressBar();
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		
		double[] column = {10, TableLayoutConstants.FILL, 10};
		double[] row = {10, TableLayoutConstants.FILL
						, 10, TableLayoutConstants.PREFERRED
						, 10, TableLayoutConstants.PREFERRED, 10};
		getContentPane().setLayout(new TableLayout(column, row));
		getContentPane().add(sp,     		"1, 1, f, f");
		getContentPane().add(progressBar,  	"1, 3, f, c");
		getContentPane().add(cancelButton,  "1, 5, c, c");
	
	}
	
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==cancelButton){
			worker.cancel(true);
			close();
		}
	}
	
	public void appendText(String string){
		progressBarTextArea.append(string);
		progressBarTextArea.setCaretPosition(progressBarTextArea.getText().length());
	}
	
	public void setMaximum(int maximum){
		progressBar.setMaximum(maximum);
	}
	
	public void setCurrentValue(int currentValue){
		progressBar.setValue(currentValue);
	}
	
	public void open(){
		OpenDialogWorker worker = new OpenDialogWorker(this, frame);
		worker.execute();
	}

	public void close(){
		setVisible(false);
		dispose();
	}

	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {
		worker.cancel(true);
	}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}

}