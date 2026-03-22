package org.nucastrodata.element.elementviz.abundtable;

import java.awt.Point;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;

import javax.swing.*;

import org.nucastrodata.Fonts;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.datastructure.util.NucSimDataStructure;
import org.nucastrodata.element.elementviz.util.ElementVizIsotopePoint;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

public class ElementVizAbundTableTablePanel extends JPanel implements ActionListener{

	private ElementVizDataStructure ds;
	private JButton saveButton, copyButton;
	private JTextArea tableTextArea;
	private JScrollPane sp;
	private JPanel buttonPanel;
	private DecimalFormat df;

	public ElementVizAbundTableTablePanel(ElementVizDataStructure ds) {
	
		this.ds = ds;
	
		df = new DecimalFormat("0.0000000000E0");
	
		saveButton = new JButton("Save");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
		
      	copyButton = new JButton("Copy");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this); 
	
		tableTextArea = new JTextArea();
		tableTextArea.setFont(Fonts.textFontFixedWidth);
		tableTextArea.setEditable(false);

      	sp = new JScrollPane(tableTextArea);
	
      	buttonPanel = new JPanel();
		double[] columnButton = {TableLayoutConstants.PREFERRED, 10, TableLayoutConstants.PREFERRED};
		double[] rowButton = {TableLayoutConstants.PREFERRED};
		buttonPanel.setLayout(new TableLayout(columnButton, rowButton));
		buttonPanel.add(saveButton,     "0, 0, c, c");
		buttonPanel.add(copyButton,  	"2, 0, c, c");
	
		double[] column = {10, TableLayoutConstants.FILL, 10};
		double[] row = {10, TableLayoutConstants.FILL, 10, TableLayoutConstants.PREFERRED, 10};
		setLayout(new TableLayout(column, row));
		add(sp,     		"1, 1, f, f");
		add(buttonPanel,  	"1, 3, c, c");

	}

	public void setCurrentState(){
	
		TreeMap<ElementVizIsotopePoint, TreeMap<String, Float>> map = new TreeMap<ElementVizIsotopePoint, TreeMap<String, Float>>();
	
		for(int i=0; i<ds.getNucSimDataStructureArray().length; i++){
	
			NucSimDataStructure nsds = ds.getNucSimDataStructureArray()[i];
			
			String name = nsds.getNucSimName();	
			float[] abundArray = nsds.getAbundTimestepDataStructureArray()[0].getAbundArray();
			int[] indexArray = nsds.getAbundTimestepDataStructureArray()[0].getIndexArray();
			
			for(int j=0; j<abundArray.length; j++){
			
				int index = indexArray[j];
				float abund = abundArray[j];
				if(index != -1){
					Point isotope = nsds.getZAMapArray()[index];
					String label = nsds.getIsotopeLabelMapArray()[index];
					ElementVizIsotopePoint ip = new ElementVizIsotopePoint((int)isotope.getX(), (int)isotope.getY(), label);
					
					if(!map.containsKey(ip)){
						TreeMap<String, Float> tempMap = new TreeMap<String, Float>(new Comparator<String>(){
						
							public int compare(String name1, String name2) {
				                
				                String[] name1Array = name1.split(" ");
				                String firstName1 = name1Array[0];
				                String lastName1 = name1Array[1];
				                String[] lastName1Array = lastName1.split("_");
				                int zone1 = Integer.valueOf(lastName1Array[1].substring(0, lastName1Array[1].length()-1));
				                
				                String[] name2Array = name2.split(" ");
				                String firstName2 = name2Array[0];
				                String lastName2 = name2Array[1];
				                String[] lastName2Array = lastName2.split("_");
				                int zone2 = Integer.valueOf(lastName2Array[1].substring(0, lastName2Array[1].length()-1));
				                
				                if(!firstName1.equals(firstName2)){
				                	return firstName1.compareTo(firstName2);
				                }
				                
				                if(zone1 > zone2){		
				                	return 1;
				                }
				                
				                if(zone1 < zone2){
				                	return -1;
				                }
				                
				                return 0;
				   
				            }
						
						});
						map.put(ip, tempMap);
					}
					
					TreeMap<String, Float> runMap = map.get(ip);
					runMap.put(name, abund);
				}
			}
			
		}
	
		tableTextArea.setText("");
		if(map.size()==0){
			tableTextArea.append("None of the selected isotopes are present in the selected simulations.");
		}
		
		Iterator<ElementVizIsotopePoint> itrPoint = map.keySet().iterator();
		while(itrPoint.hasNext()){
			ElementVizIsotopePoint ip = itrPoint.next();
			TreeMap<String, Float> runMap = map.get(ip);
			tableTextArea.append("Simulation vs. Final Abundance for " + ip.toString() + "\n\n");
			Iterator<String> itrRun = runMap.keySet().iterator();
			while(itrRun.hasNext()){
				String name = itrRun.next();
				float abund = runMap.get(name);
				tableTextArea.append(name + "\t" + df.format(abund) + "\n");
			}
			tableTextArea.append("\n\n");
			
		}
		
		tableTextArea.setCaretPosition(0);
	
	}

	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getSource()==saveButton){
		
			TextSaver.saveText(tableTextArea.getText(), this);
		
		}else if(ae.getSource()==copyButton){
		
			TextCopier.copyText(tableTextArea.getText());
		
		}

	}

}
