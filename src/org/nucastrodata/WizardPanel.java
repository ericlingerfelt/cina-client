package org.nucastrodata;
import java.awt.*;
import javax.swing.*;

import org.nucastrodata.StepPanel;
import org.nucastrodata.TitlePanel;


/**
 * The Class WizardPanel.
 */
public class WizardPanel extends JPanel{

	/**
	 * Adds the wizard panel.
	 *
	 * @param featureString the feature string
	 * @param panelString the panel string
	 * @param currentStepString the current step string
	 * @param totalStepString the total step string
	 */
	public void addWizardPanel(String featureString, String panelString, String currentStepString, String totalStepString){
		setLayout(new BorderLayout());
		JPanel topPanel = new JPanel(new BorderLayout());
		
		TitlePanel cinaTitlePanel = new TitlePanel(featureString, panelString);
		StepPanel cinaStepPanel = new StepPanel(currentStepString, totalStepString);
		
		topPanel.add(cinaTitlePanel, BorderLayout.WEST);
		topPanel.add(cinaStepPanel, BorderLayout.EAST);
		add(topPanel, BorderLayout.NORTH);
	}
}