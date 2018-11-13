package javmos2.components.functions;

import java.util.Arrays;
import javmos2.JavmosGUI;
import javmos2.enums.FunctionType;

public class Polynomial extends Function {

	public final double[] coefficients;
    public final int[] degrees;

    //Separates polynomial by degress and coefficients
    public Polynomial(JavmosGUI gui, String function) {
        super(gui);
        function = function.replace(" ", "");
        function = function.replace("f(x)=", "");
        function = function.replace("f'(x)=", "");
        function = function.replace("f''(x)=", "");
        function = function.replace("y=", "");
        function = function.replace("-", "-*");
        String[] polynomialArray = function.split("[+-]");

        if (polynomialArray[0].equals("")) {
            polynomialArray = Arrays.copyOfRange(polynomialArray, 1, polynomialArray.length);
        }

        degrees = new int[polynomialArray.length];
        coefficients = new double[polynomialArray.length];

        //Catches polynomials without coefficients
        for (int x = 0; x < polynomialArray.length; x++) {
            String replace = polynomialArray[x].replace("*", "-");
            if (replace.contains("^")) {
                degrees[x] = Integer.parseInt(replace.split("\\^")[1]);
                switch (replace.split("\\^")[0]) {
                    case "x":
                        coefficients[x] = 1;
                        break;
                    case "-x":
                        coefficients[x] = -1;
                        break;
                    default:
                        coefficients[x] = Double.parseDouble(replace.split("\\^")[0].replace("x", ""));
                        break;
                }
            } else if (replace.contains("x")) {
                degrees[x] = 1;
                switch (replace) {
                    case "x":
                        coefficients[x] = 1;
                        break;
                    case "-x":
                        coefficients[x] = -1;
                        break;
                    default:
                        coefficients[x] = Double.parseDouble(replace.replace("x", ""));
                        break;
                }
            } else {
                degrees[x] = 0;
                coefficients[x] = Double.parseDouble(replace);
            }
        }
    }
    
//	Polynom p;
////    public final double[] coefficients;
////    public final int[] degrees;
//
//    //Separates polynomial by degress and coefficients
//    public Polynomial(JavmosGUI gui, String function) {
//        super(gui);
//        
//        function = function.replaceAll("\\s+", "");
//        function = function.replace("f(x)=", "");
//        function = function.replace("f'(x)=", "");
//
//        p = new Polynom(function);
//    }
//    
//    public Polynomial(JavmosGUI gui, Polynom_able op) {
//    	super(gui);
//    	
//    	p = new Polynom(op);
//    }
//       
//    public Polynom_able getPolynom() {
//    	return p;
//    }
//    
//    public Polynom_able getDerivative() {
//    	return p.derivative();
//    }
//    
//    public double area(double x0, double x1, double eps) {
//    	return p.area(x0, x1, eps);
//    }
//    
//    public double areaUnder(double x0, double x1, double eps) {
//    	return p.areaUnderX(x0, x1, eps);
//    }


    
    //Returns the first derivative of the polynomial
//    @Override
//    public String getFirstDerivative() {
//        String equation = "";
//        String sign = "";
//
//        for (int x = 0; x < degrees.length; x++) {
//            if (coefficients[x] >= 0 & x != 0) {
//                sign = "+";
//            }
//            if (degrees[x] >= 3) {
//                equation += sign + coefficients[x] * degrees[x] + "x^" + (degrees[x] - 1);
//            } else if (degrees[x] == 2) {
//                equation += sign + coefficients[x] * degrees[x] + "x";
//            } else if (degrees[x] == 1) {
//                if (coefficients[x] != 0) {
//                    equation += sign + coefficients[x];
//                }
//            } else {
//                equation += "";
//            }
//            sign = "";
//        }
//        return "f'(x)=" + equation;
//    }
    
    public double f(double x) {
    	double y = 0;
    	
    	for (int i = 0; i < coefficients.length; i++) {
			y += coefficients[i] * Math.pow(x, degrees[i]);
		}
    	
    	return y;
    }
    
    public double areaAbove(double x0, double x1, double eps) {
    	double sumOfArea = 0;

		while (x1 > x0) {
			if(f(x0) > 0)
				sumOfArea += eps * f(x0);
			x0 += eps;
		}

		return sumOfArea;
    }
    
    public double areaUnder(double x0, double x1, double eps) {
    	double sumOfArea = 0;

		while (x1 > x0) {
			if(f(x0) < 0)
				sumOfArea += eps * f(x0);
			x0 += eps;
		}

		return Math.abs(sumOfArea);
    	
    }
    //Returns the value of y at each point of x
    @Override
    public double getValueAt(double x, FunctionType functionType) {
    	double temp = 0.0;
        switch (functionType.ordinal()) {
            case 0:
                for (int i = 0; i < degrees.length; i++) {
                    temp += coefficients[i] * Math.pow(x, degrees[i]);
                }
                return temp;
            case 1:
                Polynomial first = new Polynomial(gui, getFirstDerivative());
                return first.getValueAt(x, FunctionType.ORIGINAL);
            case 2:
                Polynomial second = new Polynomial(gui, getSecondDerivative());
                return second.getValueAt(x, FunctionType.ORIGINAL);
            default:
                Polynomial fir = new Polynomial(gui, getFirstDerivative());
                Polynomial third = new Polynomial(gui, fir.getSecondDerivative());
                return third.getValueAt(x, FunctionType.ORIGINAL);
        } 
//        switch (functionType.ordinal()) {
//            case 0:
//                return p.f(x);
//            case 1:
//            	Polynomial first =  new Polynomial(gui, p.derivative());
//                return first.getValueAt(x, FunctionType.ORIGINAL);
//            case 2: 
//            	Polynomial second =  new Polynomial(gui, p.derivative().derivative());
//                return second.getValueAt(x, FunctionType.ORIGINAL);
//            default:
//            	return p.f(x);
//        }
    }

	@Override
	public String getFirstDerivative() {
		String equation = "";
        String sign = "";

        for (int x = 0; x < degrees.length; x++) {
            if (coefficients[x] >= 0 & x != 0) {
                sign = "+";
            }
            if (degrees[x] >= 3) {
                equation += sign + coefficients[x] * degrees[x] + "x^" + (degrees[x] - 1);
            } else if (degrees[x] == 2) {
                equation += sign + coefficients[x] * degrees[x] + "x";
            } else if (degrees[x] == 1) {
                if (coefficients[x] != 0) {
                    equation += sign + coefficients[x];
                }
            } else {
                equation += "";
            }
            sign = "";
        }
        return equation;
	}
	
	//Returns the second derivative of the polynomial
    @Override
    public String getSecondDerivative() {
        String equation = "";
        String sign = "";

        for (int x = 0; x < degrees.length; x++) {
            if (coefficients[x] >= 0 & x != 0) {
                sign = "+";
            }
            if (degrees[x] >= 4) {
                equation += sign + coefficients[x] * degrees[x] * (degrees[x] - 1) + "x^" + (degrees[x] - 2);
            } else if (degrees[x] == 3) {
                equation += sign + coefficients[x] * degrees[x] * (degrees[x] - 1) + "x";
            } else if (degrees[x] == 2) {
                if (coefficients[x] != 0) {
                    equation += sign + coefficients[x] * degrees[x];
                }
            } else {
                equation += "";
            }
            sign = "";
        }
        return equation;
    }
}
