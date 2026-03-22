package org.nucastrodata.element.elementman;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.ElementManDataStructure;
import org.nucastrodata.datastructure.util.ElementSimDataStructure;
import org.nucastrodata.enums.SimProperty;
import org.nucastrodata.export.copy.TextCopier;
import org.nucastrodata.export.save.TextSaver;
import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.nucastrodata.export.print.PrintableEditorPane;
import java.awt.*;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import java.text.*;

public class ElementManInfo2Panel extends JPanel implements ActionListener{

	private ElementManDataStructure ds;
	private MainDataStructure mds;
	private ElementManFrame frame;
	private PrintableEditorPane textPane;
	private JButton saveButtonText, saveButtonHTML, copyButton, printButton;

	public ElementManInfo2Panel(MainDataStructure mds, ElementManDataStructure ds, ElementManFrame frame){
		
		this.mds = mds;
		this.ds = ds;
		this.frame = frame;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.FILL, gap, TableLayoutConstants.PREFERRED, gap};

		setLayout(new TableLayout(column, row));
		
		textPane = new PrintableEditorPane();
		textPane.setEditable(false);
		textPane.setEditorKit(new HTMLEditorKit());
		textPane.setBackground(Color.white);
		
		JScrollPane sp = new JScrollPane(textPane);
		
		saveButtonText = new JButton("Save as Text File");
		saveButtonText.setFont(Fonts.buttonFont);
		saveButtonText.addActionListener(this);

		saveButtonHTML = new JButton("Save as HTML File");
		saveButtonHTML.setFont(Fonts.buttonFont);
		saveButtonHTML.addActionListener(this);
		
		printButton = new JButton("Print");
		printButton.setFont(Fonts.buttonFont);
		printButton.addActionListener(this);
		
		copyButton = new JButton("Copy");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButtonText);
		buttonPanel.add(saveButtonHTML);
		buttonPanel.add(copyButton);
		buttonPanel.add(printButton);

		add(sp, "0, 1, f, f");
		add(buttonPanel, "0, 3, c, c");
	
	}
	
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==saveButtonText){
			TextSaver.saveText(getTextString(), frame, mds);
		}else if(ae.getSource()==saveButtonHTML){
			TextSaver.saveTextHTML(textPane.getText(), frame, mds);
		}else if(ae.getSource()==copyButton){
			TextCopier.copyText(getTextString());
		}else if(ae.getSource()==printButton){
			textPane.print();
		}
	}
	
	private String getTextString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("Element Synthesis Simulation Report\n\n");
		Iterator<String> itr = ds.getSimMapSelected().keySet().iterator();
		while(itr.hasNext()){
			ElementSimDataStructure esds = ds.getSimMapSelected().get(itr.next());
			sb.append("Element Synthesis Simulation\t" + esds.getPath() + "\n");
			Iterator<SimProperty> itrInner = esds.getPropMap().keySet().iterator();
			while(itrInner.hasNext()){
				SimProperty prop = itrInner.next();
				String value = esds.getPropMap().get(prop);
				value = value.equals("Y") ? "Yes": value;
				value = value.equals("N") ? "No": value;
				if(mds.isMasterUser() && prop==SimProperty.THERMO_PATH){
					sb.append(prop.toString() 
							+ "\t" 
							+ value
							+ "\n");
				}else if(prop==SimProperty.CREATION_DATE){
					sb.append(prop.toString() 
								+ "\t" 
								+ new SimpleDateFormat().format(getCalendar(value).getTime(), new StringBuffer(), new FieldPosition(0)) 
								+ "\n");
				}else if(prop==SimProperty.Al26_TYPE){
					if(!value.equals("")){
						sb.append(prop.toString() 
							+ "\t" 
							+ (value.equals("METASTABLE") ? "metastable + ground": "only stable")
							+ "\n");
					}else{
						sb.append(prop.toString() 
							+ "\tN/A\n");
					}
				}else if(prop==SimProperty.SCALE_FACTOR){
					if(!esds.getPropMap().get(SimProperty.REACTION_RATE).trim().equals("")){
						sb.append(prop.toString() 
							+ "\t" 
							+ value
							+ "\n");
					}
				}else if(prop==SimProperty.PARAMS){
					if(!esds.getPropMap().get(SimProperty.REACTION_RATE).trim().equals("")){
						sb.append(prop.toString() 
							+ "\t" 
							+ getFormattedParameters(value, true)
							+ "\n");
					}
				}else if(prop==SimProperty.REACTION_RATE){
					if(!value.trim().equals("")){
						sb.append(prop.toString() 
							+ "\t" 
							+ value
							+ "\n");
					}
				}else if(prop==SimProperty.LIB_DIR){
					value = value.equals("") ? "N/A": value;
					sb.append(prop.toString() 
						+ "\t" 
						+ value
						+ "\n");
				}else if(prop==SimProperty.LOOPING_TYPE){
					value = value.equals("") ? "N/A": value;
					sb.append(prop.toString() 
						+ "\t" 
						+ value
						+ "\n");
				}else{
					value = value.equals("") ? "N/A": value;
					sb.append(prop.toString() 
						+ "\t" 
						+ value
						+ "\n");
				}
			}
			sb.append("\n\n");
		}
		return sb.toString();
	}
	
	private String getInfoReport(){
	
		StringBuilder sb = new StringBuilder();
	
		sb.append("<html><body>");
		sb.append("<b>Element Synthesis Simulation Report</b><br><br>");
		Iterator<String> itr = ds.getSimMapSelected().keySet().iterator();
		while(itr.hasNext()){
			ElementSimDataStructure esds = ds.getSimMapSelected().get(itr.next());
			sb.append("<table border=\"1\"><tr><td><b>Element Synthesis Simulation</b></td><td><b>" + esds.getPath() + "</b></td></tr>");
			Iterator<SimProperty> itrInner = esds.getPropMap().keySet().iterator();
			while(itrInner.hasNext()){
				SimProperty prop = itrInner.next();
				String value = esds.getPropMap().get(prop);
				value = value.equals("Y") ? "Yes": value;
				value = value.equals("N") ? "No": value;
				if(prop==SimProperty.THERMO_PATH && mds.isMasterUser()){
					sb.append("<tr><td><b>" 
							+ prop.toString() 
							+ "</b></td><td>" 
							+ value
							+ "</td></tr>");
				}else if(prop==SimProperty.CREATION_DATE){
					sb.append("<tr><td><b>"
								+ prop.toString() 
								+ "</b></td><td>" 
								+ new SimpleDateFormat().format(getCalendar(value).getTime(), new StringBuffer(), new FieldPosition(0)) 
								+ "</td></tr>");
				}else if(prop==SimProperty.Al26_TYPE){
					if(!value.equals("")){
						sb.append("<tr><td><b>"
							+ prop.toString() 
							+ "</b></td><td>" 
							+ (value.equals("METASTABLE") ? "metastable + ground": "only stable")
							+ "</td></tr>");
					}else{
						sb.append("<tr><td><b>"
							+ prop.toString() 
							+ "</b></td><td>N/A</td></tr>");
					}
				}else if(prop==SimProperty.SCALE_FACTOR){
					if(!esds.getPropMap().get(SimProperty.REACTION_RATE).trim().equals("")){
						sb.append("<tr><td><b>"
							+ prop.toString() 
							+ "</b></td><td>" 
							+ value
							+ "</td></tr>");
					}
				}else if(prop==SimProperty.PARAMS){
					if(!esds.getPropMap().get(SimProperty.REACTION_RATE).trim().equals("")){
						sb.append("<tr><td><b>"
							+ prop.toString() 
							+ "</b></td><td>" 
							+ getFormattedParameters(value, true)
							+ "</td></tr>");
					}
				}else if(prop==SimProperty.REACTION_RATE){
					if(!value.trim().equals("")){
						sb.append("<tr><td><b>"
							+ prop.toString() 
							+ "</b></td><td>" 
							+ value
							+ "</td></tr>");
					}
				}else if(prop==SimProperty.NOTES){
					value = value.equals("") ? "N/A": value;
					sb.append("<tr><td><b>"
						+ prop.toString() 
						+ "</b></td><td>" 
						+ value
						+ "</td></tr>");
				}else if(prop==SimProperty.LIB_DIR){
					value = value.equals("") ? "N/A": value;
					sb.append("<tr><td><b>"
						+ prop.toString() 
						+ "</b></td><td>" 
						+ value
						+ "</td></tr>");
				}else if(prop==SimProperty.LOOPING_TYPE){
					value = value.equals("") ? "N/A": value;
					sb.append("<tr><td><b>"
						+ prop.toString() 
						+ "</b></td><td>" 
						+ value
						+ "</td></tr>");
				}else{
					value = value.equals("") ? "N/A": value;
					sb.append("<tr><td><b>"
						+ prop.toString() 
						+ "</b></td><td>" 
						+ value
						+ "</td></tr>");
				}
			}
			sb.append("</table><br>");
		}
		return sb.toString();
	}
	
	private String getFormattedParameters(String params, boolean useHtml){
		String[] array = params.split(",");
		String string = "";
		int counter = 1;
		for(String param: array){
			string += "a(" + counter + ") = " + NumberFormats.getFormattedParameter(Double.valueOf(param));
			if(useHtml){
				string += "<br>";
			}else{
				string += "\n";
			}
			counter++;
		}
		if(useHtml){
			return string.substring(0, string.lastIndexOf("<br>"));
		}
		return string.substring(0, string.lastIndexOf("\n"));
	}
	
	private Calendar getCalendar(String string){
		Calendar calendar = Calendar.getInstance();
		String day = string.split(" ")[0];
		String time = string.split(" ")[1];
		int year = Integer.valueOf(day.split("-")[0]);
		int month = Integer.valueOf(day.split("-")[1])-1;
		int date = Integer.valueOf(day.split("-")[2]);
		int hourOfDay = Integer.valueOf(time.split(":")[0]);
		int minute = Integer.valueOf(time.split(":")[1]);
		int second = Integer.valueOf(time.split(":")[2]);
		calendar.set(year, month, date, hourOfDay, minute, second);
		return calendar;
	}
	
	public void setCurrentState(){
		textPane.setText(getInfoReport());
		textPane.setCaretPosition(0);
	}

	public void getCurrentState(){}
}

