package org.nucastrodata.info;
import java.awt.*;
import javax.swing.*;

import org.nucastrodata.Cina;

import java.awt.event.*;


/**
 * The Class AboutFrame.
 */
public class AboutFrame extends JFrame{

    /**
     * Instantiates a new about frame.
     */
    public AboutFrame(){
    	Container c = getContentPane();
        setSize(349, 400);
        setVisible(true); 
        c.setLayout(new BorderLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        addWindowListener(new WindowAdapter(){
	        public void windowClosing(WindowEvent we) {
	            setVisible(false);
	            dispose();
		    } 	
        });
        JTextArea helpTextArea = new JTextArea(getAboutString());
      	helpTextArea.setEditable(false);
		helpTextArea.setLineWrap(true);
		helpTextArea.setWrapStyleWord(true);
      	JScrollPane sp = new JScrollPane(helpTextArea
  								, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
  								, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(200, 100));
        helpTextArea.setCaretPosition(0);
		c.add(sp, BorderLayout.CENTER);
		
        c.validate();
    }
    
    /**
     * Gets the about string.
     *
     * @return the about string
     */
    private String getAboutString(){
    	String string = "";
    	string = "A Computational Infrastructure for Nuclear Astrophysics\n\n";
    	string += "A program in the Rate Generator calculates the thermonuclear reaction rate as a ";
		string += "function of temperature from pointwise cross sections and S-factors as a ";
		string += "function of energy. The reaction rate at a given temperature is the product ";
		string += "of the cross section and relative velocity averaged over the ";
		string += "Maxwell-Boltzmann relative energy distribution at that temperature. We ";
		string += "interpolate and/or extrapolate the original input data set using cubic ";
		string += "splines and polynomials to improve the numerical integration. The program ";
		string += "automatically searches for narrow resonances and treats these differently ";
		string += "from smoothly varying energy intervals. Typically, 5,000~30,000 interpolated ";
		string += "data points are numerically integrated using a Riemann sum approach. The ";
		string += "integration is repeated typically for 60 temperature values over a range ";
		string += "determined by calculating the Gamow window for the input data set. The ";
		string += "program has been tested by comparing its results to those using 3 similar ";
		string += "codes, as well as by integrating functions that can be solved analytically. ";
		string += "The results of those tests will be made available upon request.\n\n";
 		string += "The parameterizer program in the Rate Generator determines a set of ";
		string += "REACLIB-format parameters to represent a pointwise reaction rate as a ";
		string += "function of temperature.  Parameters are determined using the ";
		string += "Levenberg-Marquardt method for nonlinear parameter estimation from W. H. ";
		string += "Press et al. 'Numerical Recipes in FORTRAN', 2nd edition, 1992.  This ";
		string += "method calculates new parameters from starting parameters by reducing ";
		string += "the chisquared between the parameterized function and the pointwise ";
		string += "data.  Starting parameters may be provided by the user or randomly ";
		string += "selected from a built-in set.  This program also has the ability to ";
		string += "divide the pointwise data into intervals to improve fits and direct ";
		string += "parameter sets to represent individual resonances in the reaction rate. ";
		string += "The Levenberg-Marquardt method is iteratively called until an exit ";
		string += "condition is satisfied.  Implemented exit conditions include chisquared ";
		string += "and maximum percent different below a user-defined level, too many ";
		string += "consecutive failures, and too many iterations.  The interval over the ";
		string += "lowest temperature range is fit first, and additional intervals are ";
		string += "added when the fit meets user-defined conditions.  A parallel version of ";
		string += "the Levenberg-Marquardt method is also available that executes multiple ";
		string += "MARQUARDT routines with a random selection of starting parameters.  Fits ";
		string += "that do not converge after a reasonable time are terminated and ";
		string += "additional fits with different starting parameters are started.  ";
		string += "Numerous fits with different starting parameters continue to run until ";
		string += "an exit condition above is satisfied.  Additional parameter sets are ";
		string += "added when all fits appear unsuccessful.  The program also provides ";
		string += "warnings when reaction rates are not well behaved when extrapolated to ";
		string += "0.01 to 10 T9.  Potential solutions to this problem will be in a future ";
		string += "version of this program. \n\n";
    	string += "Project Leader: Michael Smith\n\n";
    	string += "FORTRAN Developers: Jason Scott, Kyungyuk Chae, and Chase Hard\n\n";
    	string += "Java Developer: Eric Lingerfelt\n\n";
    	string += "Programming Consultants: Dale Visser, Caroline Nesaraja, and Richard Meyer \n\n";
    	string += "Sponsor: The Nuclear Data Program and Low Energy Nuclear Physics Program of the Division of Nuclear Science in the U.S. Dept. of Energy Office of Science - under contract number DE-AC05-00OR22725 with Oak Ridge National Laboratory, managed by UT-Battelle, LLC.\n\n";
    	string += "For further information, contact coordinator@nucastrodata.org.";
    	string += "\n\n\u00A9 copyright 2004";
    	return string;	
    }
    
}

