package org.nucastrodata;
import javax.swing.plaf.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*; 


/**
 * The Class CheckBoxIcon.
 */
public class CheckBoxIcon implements Icon, UIResource, Serializable {

   /**
    * Gets the control size.
    *
    * @return the control size
    */
   protected int getControlSize(){return 13;}

   /* (non-Javadoc)
    * @see javax.swing.Icon#paintIcon(java.awt.Component, java.awt.Graphics, int, int)
    */	
   public void paintIcon(Component c, Graphics g, int x, int y){

      JCheckBox cb = (JCheckBox)c;
      ButtonModel model = cb.getModel();
      int controlSize = getControlSize();
      boolean drawCheck = model.isSelected();

      if(model.isEnabled()){
         if(model.isPressed() && model.isArmed()){
            g.setColor(UIManager.getColor("MyCheckBox.checkClickBackground"));
	    	g.fillRect( x, y, controlSize-1, controlSize-1);
         }else{
            g.setColor(UIManager.getColor("MyCheckBox.checkBackground"));
            g.fillRect( x, y, controlSize-1, controlSize-1);
         }
         g.setColor(UIManager.getColor("MyCheckBox.checkForeground"));
      }else{
         g.setColor(UIManager.getColor("MyCheckBox.checkDisabled"));
         g.drawRect( x, y, controlSize-1, controlSize-1);
      }
      if(model.isSelected()){drawCheck(c, g, x, y);}
   }

   /**
    * Draw check.
    *
    * @param c the c
    * @param g the g
    * @param x the x
    * @param y the y
    */	
   protected void drawCheck(Component c, Graphics g, int x, int y){
   	
      int controlSize = getControlSize();
      
      g.fillRect(x+3, y+5, 2, controlSize-8);
      g.drawLine(x+(controlSize-4), y+3, x+5, y+(controlSize-6));
      g.drawLine(x+(controlSize-4), y+4, x+5, y+(controlSize-5));
   }

   /* (non-Javadoc)
    * @see javax.swing.Icon#getIconWidth()
    */
   public int getIconWidth(){return getControlSize();}
   
   /* (non-Javadoc)
    * @see javax.swing.Icon#getIconHeight()
    */
   public int getIconHeight(){return getControlSize();}

 }

