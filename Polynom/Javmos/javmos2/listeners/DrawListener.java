package javmos2.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javmos2.JavmosGUI;
import javmos2.components.JavmosPanel;
import javmos2.components.functions.*;

public class DrawListener implements ActionListener {

    private final JavmosGUI gui;
    private final JavmosPanel panel;

    public DrawListener(JavmosGUI gui, JavmosPanel panel) {
        this.gui = gui;
        this.panel = panel;
        
    }
    public JavmosPanel getPanel() {
    	return this.panel;
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        try {      
            if (gui.getEquationField().contains("sin")) {
                panel.setFunction(new Sine(gui, gui.getEquationField()));
            } else if (gui.getEquationField().contains("cos")) {
                panel.setFunction(new Cosine(gui, gui.getEquationField()));
            } else if (gui.getEquationField().contains("tan")) {
                panel.setFunction(new Tangent(gui, gui.getEquationField()));
            } else if (gui.getEquationField().contains("log") ||gui.getEquationField().contains("ln")) {
                panel.setFunction(new Logarithmic(gui, gui.getEquationField()));
            } else if (gui.getEquationField().isEmpty() == false) {
            	String s = gui.getEquationField();
            	Polynomial p = new Polynomial(gui, s);
            	panel.setFunction(p);
            	if(panel.getFunction() instanceof Polynomial) {
            		gui.setFirstDerivativeLabel(((Polynomial)panel.getFunction()).getFirstDerivative());
            		gui.setAreaAboveX(Double.toString(((Polynomial)panel.getFunction()).areaAbove(gui.getMinDomain(), gui.getMaxDomain(), 0.0001)));
            		gui.setAreaUnderX(Double.toString(((Polynomial)panel.getFunction()).areaUnder(gui.getMinDomain(), gui.getMaxDomain(), 0.0001)));
            	}
            }	
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Function Error", JOptionPane.ERROR_MESSAGE);
        }
        panel.repaint();
    }
    
    public void draw() {
    	try {      
            if (gui.getEquationField().contains("sin")) {
                panel.setFunction(new Sine(gui, gui.getEquationField()));
            } else if (gui.getEquationField().contains("cos")) {
                panel.setFunction(new Cosine(gui, gui.getEquationField()));
            } else if (gui.getEquationField().contains("tan")) {
                panel.setFunction(new Tangent(gui, gui.getEquationField()));
            } else if (gui.getEquationField().contains("log") ||gui.getEquationField().contains("ln")) {
                panel.setFunction(new Logarithmic(gui, gui.getEquationField()));
            } else if (gui.getEquationField().isEmpty() == false) {
            	String s = gui.getEquationField();
            	Polynomial p = new Polynomial(gui, s);
            	panel.setFunction(p);
            	if(panel.getFunction() instanceof Polynomial) {
            		gui.setFirstDerivativeLabel(((Polynomial)panel.getFunction()).getFirstDerivative());
            		gui.setAreaAboveX(Double.toString(((Polynomial)panel.getFunction()).areaAbove(gui.getMinDomain(), gui.getMaxDomain(), 0.0001)));
            		gui.setAreaUnderX(Double.toString(((Polynomial)panel.getFunction()).areaUnder(gui.getMinDomain(), gui.getMaxDomain(), 0.0001)));
            	}
            }	
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Function Error", JOptionPane.ERROR_MESSAGE);
        }
        panel.repaint();
    }
}
