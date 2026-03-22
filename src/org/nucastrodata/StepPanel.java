package org.nucastrodata;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.FlowLayout;


/**
 * The Class StepPanel.
 */
public class StepPanel extends JPanel{
	
	/**
	 * Instantiates a new step panel.
	 *
	 * @param currentStepString the current step string
	 * @param totalStepString the total step string
	 */
	public StepPanel(String currentStepString, String totalStepString){
		setLayout(new FlowLayout());

		JLabel label1 = new JLabel("Step ");
		JLabel label2 = new JLabel(currentStepString);
		JLabel label3 = new JLabel(" of ");
		JLabel label4 = new JLabel(totalStepString);

		add(label1);
		add(label2);
		add(label3);
		add(label4);
	}
}