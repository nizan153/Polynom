package javmos2.enums;

import java.awt.Color;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashSet;
import javmos2.JavmosGUI;
import javmos2.components.Point;
import javmos2.components.functions.Function;
import javmos2.components.functions.Polynomial;

public enum RootType {

    X_INTERCEPT(Color.GREEN, FunctionType.ORIGINAL, FunctionType.FIRST_DERIVATIVE),
    CRITICAL_POINT(Color.RED, FunctionType.FIRST_DERIVATIVE, FunctionType.SECOND_DERIVATIVE);

    public final String rootName = name();
    public final Color rootColor;
    public final int ATTEMPTS = 15;
    public final FunctionType functionOne;
    public final FunctionType functionTwo;

    RootType(Color color, FunctionType one, FunctionType two) {
        rootColor = color;
        functionOne = one;
        functionTwo = two;
    }

    public Color getPointColor() {
        return rootColor;
    }

    public String getPointName() {
        return rootName;
    }

    protected Double newtonsMethod(Function function, double guess, int attempts) {
        if (attempts == 0 || Double.isNaN(guess)) {
            return null;
        } else {
            double root = guess - function.getValueAt(guess, functionOne) / function.getValueAt(guess, functionTwo);
            if (Math.abs(guess - root) <= 0.00001) {
                return root;
            } else {
                return newtonsMethod(function, root, attempts - 1);
            }
        }
    }
    
    public java.util.HashSet<Point> getRoots(JavmosGUI gui, Function function, double minDomain, double maxDomain) {
        DecimalFormat f = new DecimalFormat("#.###");
        HashSet<Point> temp = new HashSet<>();
        f.setRoundingMode(RoundingMode.HALF_DOWN);
        for (double x = minDomain; x <= maxDomain; x += 1) {
            Double xTest = newtonsMethod(function, x, ATTEMPTS);
            if (xTest != null) {
                double xA = Double.parseDouble(f.format(xTest));
                double yA = Double.parseDouble(f.format(function.getValueAt(xA, FunctionType.ORIGINAL)));
                if (yA == -0) {
                    yA = 0;
                }
                Point root = new Point(gui, this, xA, yA);
                temp.add(root);
            }
        }
        return temp;
    }
}
