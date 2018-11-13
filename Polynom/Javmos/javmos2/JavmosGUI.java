package javmos2;

import javmos2.enums.RootType;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.Format;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javmos2.listeners.DomainRangeListener;
import javmos2.listeners.DrawListener;
import javmos2.listeners.PointClickListener;
import javmos2.listeners.QuitListener;
import javmos2.listeners.ZoomListener;
import javmos2.components.JavmosPanel;
import javmos2.components.functions.Polynomial;

public final class JavmosGUI {

    // Create GUI's constants
    private final int PLANE_PANEL_WIDTH = 700;
    private final int PLANE_PANEL_HEIGHT = 700;
    private final int CONTROL_PANEL_WIDTH = PLANE_PANEL_WIDTH / 2;
    private final int CONTROL_PANEL_HEIGHT = PLANE_PANEL_HEIGHT;
    private final int CONTROL_PANEL_ITEMS = 9;
    private final double MIN_DOMAIN;
    private final double MAX_DOMAIN;
    private final double MIN_RANGE = -PLANE_PANEL_HEIGHT / 2;
    private final double MAX_RANGE = PLANE_PANEL_HEIGHT / 2;
    private final double DOMAIN_STEP = 1; // X axis 
    private final double RANGE_STEP = 2; // Y axis 
    private final double ZOOM = 50;
    // Create GUI's container components
    private final JFrame frame = new JFrame("Javmos v1.0"); 
    private final JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)); //chart
    private final JavmosPanel javmosPanel = new JavmosPanel(this); 
    private final JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)); //control
    private final JPanel domainStepPanel = new JPanel(new GridLayout(2, 1));  // X axis range
    private final JPanel rangeStepPanel = new JPanel(new GridLayout(2, 1));  // Y axis range
    // Create GUI's control components
    private final JTextField equationField = new JTextField("Enter Polynom Here");						// polynom field
    private final JTextField domainStepField = new JTextField(Double.toString(DOMAIN_STEP));			// X axis range
    private final JTextField minDomainField;															// X axis start range
    private final JTextField maxDomainField;															// X axis end range
    private final JTextField rangeStepField = new JTextField(Double.toString(RANGE_STEP));				// Y axis
    private final JTextField minRangeField = new JTextField(Double.toString(MIN_RANGE));				// Y axis start range
    private final JTextField maxRangeField = new JTextField(Double.toString(MAX_RANGE));				// Y axis end range
    private final JLabel firstDerivativeLabel = new JLabel("First Derivative", SwingConstants.CENTER);	// first derivative label
    private final JLabel areaUnderLabel = new JLabel("Area Under x", SwingConstants.CENTER);			// area under x label
    private final JLabel areaAboveLabel = new JLabel("Area Above x", SwingConstants.CENTER);			// area above x label
    private final JLabel pointLabel = new JLabel("Click a Point to Display", SwingConstants.CENTER);	// cut/crit points label
    private final JLabel zoomLabel = new JLabel("x" + ZOOM, SwingConstants.CENTER);						// zoom number label
    private final JLabel domainLabel = new JLabel("X RANGE", SwingConstants.CENTER);						// X range label
    private final JLabel rangeLabel = new JLabel("Y RANGE", SwingConstants.CENTER);						// Y range label
    private final JButton zoomOutButton = new JButton("ZOOM \nOUT");									// zoom out button
    private final JButton zoomInButton = new JButton("ZOOM \nIN");										// zoom in button
    private final JButton quitButton = new JButton("QUIT");												// quit button
    // Create GUI's listeners
    private final DrawListener drawListener = new DrawListener(this, javmosPanel);
    private final ZoomListener zoomListener = new ZoomListener(this, javmosPanel);
    private final DomainRangeListener domainRangeListener = new DomainRangeListener(this, javmosPanel);
    private final QuitListener quitListener = new QuitListener();
    // Create GUI's colors
    private final Color inputBackgroundColor = Color.LIGHT_GRAY;//input color(range)
    private final Color displayBackgroundColor = Color.BLACK;	//labels color
    private final Color displayForegroundColor = Color.WHITE;	//font color

    // Class constructor
    public JavmosGUI() {
    	MIN_DOMAIN = -50;
    	MAX_DOMAIN = 50;
    	minDomainField = new JTextField(Double.toString(MIN_DOMAIN));
    	maxDomainField = new JTextField(Double.toString(MAX_DOMAIN));
        // Add the plane and control panels to the main panel
        mainPanel.add(createJavmosPanel());
        mainPanel.add(createControlPanel());
        // Configure the frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setContentPane(mainPanel);
        frame.pack();
    }
    
    public JavmosGUI(String pol) {
    	MIN_DOMAIN = -50;
    	MAX_DOMAIN = 50;
    	minDomainField = new JTextField(Double.toString(MIN_DOMAIN));
    	maxDomainField = new JTextField(Double.toString(MAX_DOMAIN));
    	equationField.setText(pol);
        // Add the plane and control panels to the main panel
        mainPanel.add(createJavmosPanel());
        mainPanel.add(createControlPanel());
        // Configure the frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setContentPane(mainPanel);
        frame.pack();
        drawListener.draw();
    }
    
    public JavmosGUI(String pol, double minX, double maxX) {
    	MIN_DOMAIN = minX;
    	MAX_DOMAIN = maxX;
    	minDomainField = new JTextField(Double.toString(MIN_DOMAIN));
    	maxDomainField = new JTextField(Double.toString(MAX_DOMAIN));
    	equationField.setText(pol);
        // Add the plane and control panels to the main panel
        mainPanel.add(createJavmosPanel());
        mainPanel.add(createControlPanel());
        // Configure the frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setContentPane(mainPanel);
        frame.pack();
        drawListener.draw();
    }

    // Creates the GUI's Javmos panel
    private JPanel createJavmosPanel() {
        // Configure the Javmos panel
        javmosPanel.setPreferredSize(new Dimension(PLANE_PANEL_WIDTH, PLANE_PANEL_HEIGHT));
        javmosPanel.addMouseListener(new PointClickListener(this));
        // Return the configured panel
        return javmosPanel;
    }

    // Creates the GUI's control panel
    private JPanel createControlPanel() {
        // Configure the control panel
        controlPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH, CONTROL_PANEL_HEIGHT));
        // Configure control panel's components
        configureComponent(equationField, CONTROL_PANEL_WIDTH, CONTROL_PANEL_HEIGHT / CONTROL_PANEL_ITEMS, Color.WHITE, 20, drawListener, "", Color.LIGHT_GRAY, Color.BLUE);
        configureComponent(firstDerivativeLabel, CONTROL_PANEL_WIDTH, CONTROL_PANEL_HEIGHT / CONTROL_PANEL_ITEMS, Color.WHITE, 20, null, "", displayBackgroundColor, Color.WHITE);
        configureComponent(areaUnderLabel, CONTROL_PANEL_WIDTH, CONTROL_PANEL_HEIGHT / CONTROL_PANEL_ITEMS, Color.WHITE, 20, null, "", displayBackgroundColor, Color.WHITE);
        configureComponent(areaAboveLabel, CONTROL_PANEL_WIDTH, CONTROL_PANEL_HEIGHT / CONTROL_PANEL_ITEMS, Color.WHITE, 20, null, "", displayBackgroundColor, displayForegroundColor);
        configureComponent(pointLabel, CONTROL_PANEL_WIDTH, CONTROL_PANEL_HEIGHT / CONTROL_PANEL_ITEMS, Color.WHITE, 20, null, "", displayBackgroundColor, displayForegroundColor);
        configureComponent(zoomOutButton, CONTROL_PANEL_WIDTH / 3, CONTROL_PANEL_HEIGHT / CONTROL_PANEL_ITEMS, Color.WHITE, 15, zoomListener, "-", displayBackgroundColor, displayForegroundColor);
        configureComponent(zoomLabel, CONTROL_PANEL_WIDTH / 3, CONTROL_PANEL_HEIGHT / CONTROL_PANEL_ITEMS, Color.WHITE, 25, null, "", displayBackgroundColor, displayForegroundColor);
        configureComponent(zoomInButton, CONTROL_PANEL_WIDTH / 3, CONTROL_PANEL_HEIGHT / CONTROL_PANEL_ITEMS, Color.WHITE, 15, zoomListener, "+", displayBackgroundColor, displayForegroundColor);
        configureComponent(minDomainField, CONTROL_PANEL_WIDTH / 3, CONTROL_PANEL_HEIGHT / CONTROL_PANEL_ITEMS, Color.BLACK, 25, domainRangeListener, "MIN_DOMAIN", inputBackgroundColor, displayBackgroundColor);
        configureComponent(domainLabel, CONTROL_PANEL_WIDTH / 3, (CONTROL_PANEL_HEIGHT / CONTROL_PANEL_ITEMS) / 2, Color.WHITE, 20, null, "", displayBackgroundColor, displayForegroundColor);
        configureComponent(domainStepField, CONTROL_PANEL_WIDTH / 3, (CONTROL_PANEL_HEIGHT / CONTROL_PANEL_ITEMS) / 2, Color.BLACK, 20, domainRangeListener, "DOMAIN_STEP", inputBackgroundColor, displayBackgroundColor);
        configureComponent(maxDomainField, CONTROL_PANEL_WIDTH / 3, CONTROL_PANEL_HEIGHT / CONTROL_PANEL_ITEMS, Color.BLACK, 25, domainRangeListener, "MAX_DOMAIN", inputBackgroundColor, displayBackgroundColor);
        configureComponent(minRangeField, CONTROL_PANEL_WIDTH / 3, CONTROL_PANEL_HEIGHT / CONTROL_PANEL_ITEMS, Color.BLACK, 25, domainRangeListener, "MIN_RANGE", inputBackgroundColor, displayBackgroundColor);
        configureComponent(rangeLabel, CONTROL_PANEL_WIDTH / 3, (CONTROL_PANEL_HEIGHT / CONTROL_PANEL_ITEMS) / 2, Color.WHITE, 20, null, "", displayBackgroundColor, displayForegroundColor);
        configureComponent(rangeStepField, CONTROL_PANEL_WIDTH / 3, (CONTROL_PANEL_HEIGHT / CONTROL_PANEL_ITEMS) / 2, Color.BLACK, 20, domainRangeListener, "RANGE_STEP", inputBackgroundColor, displayBackgroundColor);
        configureComponent(maxRangeField, CONTROL_PANEL_WIDTH / 3, CONTROL_PANEL_HEIGHT / CONTROL_PANEL_ITEMS, Color.BLACK, 25, domainRangeListener, "MAX_RANGE", inputBackgroundColor, displayBackgroundColor);
        configureComponent(quitButton, CONTROL_PANEL_WIDTH, CONTROL_PANEL_HEIGHT / CONTROL_PANEL_ITEMS, Color.WHITE, 40, quitListener, "", displayBackgroundColor, displayForegroundColor);
        // Add step components to respective panels
        domainStepPanel.add(domainLabel);
        domainStepPanel.add(domainStepField);
        rangeStepPanel.add(rangeLabel);
        rangeStepPanel.add(rangeStepField);
        // Add control panel components
        controlPanel.add(equationField);
        controlPanel.add(firstDerivativeLabel);
        controlPanel.add(areaAboveLabel);
        controlPanel.add(areaUnderLabel);
        controlPanel.add(pointLabel);
        controlPanel.add(zoomOutButton);
        controlPanel.add(zoomLabel);
        controlPanel.add(zoomInButton);
        controlPanel.add(minDomainField);
        controlPanel.add(domainStepPanel);
        controlPanel.add(maxDomainField);
        controlPanel.add(minRangeField);
        controlPanel.add(rangeStepPanel);
        controlPanel.add(maxRangeField);
        controlPanel.add(quitButton);
        // Return the configured panel
        return controlPanel;
    }

    // Configures various properties of JComponents
    private void configureComponent(JComponent component, int width, int height, Color border, int fontSize, ActionListener listener, String actionCommand, Color background, Color foreground) {
        component.setPreferredSize(new Dimension(width, height));
        component.setBorder(new LineBorder(border, 1));
        component.setFont(new Font("Arial Bold", Font.BOLD, fontSize));
        component.setOpaque(true);
        component.setBackground(background);
        component.setForeground(foreground);
        if (component instanceof JButton) {
            ((JButton) component).addActionListener(listener);
            ((JButton) component).setActionCommand(actionCommand);
        } else if (component instanceof JTextField) {
            ((JTextField) component).setHorizontalAlignment(SwingConstants.CENTER);
            ((JTextField) component).addActionListener(listener);
            ((JTextField) component).setActionCommand(actionCommand);
        }
    }

    public int getPlaneWidth() {
        return PLANE_PANEL_WIDTH;
    }

    public int getPlaneHeight() {
        return PLANE_PANEL_HEIGHT;
    }

    public String getEquationField() {
        return equationField.getText();
    }

    public double getZoom() {
        return Double.parseDouble(zoomLabel.getText().substring(1));
    }

    public double getMinDomain() {
        return Double.parseDouble(minDomainField.getText());
    }

    public double getMaxDomain() {
        return Double.parseDouble(maxDomainField.getText());
    }

    public double getMinRange() {
        return Double.parseDouble(minRangeField.getText());
    }

    public double getMaxRange() {
        return Double.parseDouble(maxRangeField.getText());
    }

    public double getDomainStep() {
        return Double.parseDouble(domainStepField.getText());
    }

    public double getRangeStep() {
        return Double.parseDouble(rangeStepField.getText());
    }

    public void setFirstDerivativeLabel(String firstDerivative) {
        firstDerivativeLabel.setText("f'(x) = " + firstDerivative);
    }
    
    public void setAreaAboveX(String areaAbove) {
    	double d = Double.parseDouble(areaAbove);
    	areaAboveLabel.setText("Area Above x: " + new DecimalFormat("#.##").format(d));
    }
    
    public void setAreaUnderX(String areaUnder) {
    	double d = Double.parseDouble(areaUnder);
    	areaUnderLabel.setText("Area under x:" + new DecimalFormat("#.##").format(d));
    }

    public void setZoom(double zoom) {
        this.zoomLabel.setText("x" + zoom);
    }

    public void setPointLabel(String pointLabel, RootType rootType) {
        this.pointLabel.setForeground(rootType.getPointColor());
        this.pointLabel.setText(pointLabel);
    }
    
    public static void main(String[] args) {
//		JavmosGUI j = new JavmosGUI("0.2x^4-1.5x^3+3.0x^2-x-5", -2, 6);
	}
}
