package org.nucastrodata;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.FlowLayout;


/**
 * The Class TitlePanel.
 */
public class TitlePanel extends JPanel{

	/**
	 * Instantiates a new title panel.
	 *
	 * @param featureString the feature string
	 * @param panelString the panel string
	 */
	public TitlePanel(String featureString, String panelString){
		setLayout(new FlowLayout());

		JLabel label1 = new JLabel(featureString);
		JLabel label2 = new JLabel(" | ");
		JLabel label3 = new JLabel(panelString);

		add(label1);
		add(label2);
		add(label3);
	}
}