package javmos2.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import javmos2.JavmosGUI;
import javmos2.components.JavmosPanel;
import javmos2.components.functions.Polynomial;

public class DomainRangeListener implements ActionListener {

    private final JavmosPanel panel;
    private final JavmosGUI gui;

    public DomainRangeListener(JavmosGUI gui,JavmosPanel panel) {
        this.panel = panel;
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            Double.parseDouble(((JTextField) event.getSource()).getText());

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, ((JTextField) event.getSource()).getText() + " is an invalid domain or range value", "Domain/Range Error", JOptionPane.ERROR_MESSAGE);
        }

        if(panel.getFunction() instanceof Polynomial) {
        	gui.setAreaAboveX(Double.toString(((Polynomial)panel.getFunction()).areaAbove(gui.getMinDomain(), gui.getMaxDomain(), 0.0001)));
        	gui.setAreaUnderX(Double.toString(((Polynomial)panel.getFunction()).areaUnder(gui.getMinDomain(), gui.getMaxDomain(), 0.0001)));
        }
        panel.repaint();
    }
}
