package myMath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javmos2.JavmosGUI;
import myMath.Monom;
/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Nitzan and Rivka
 *
 */
public class Polynom implements Polynom_able{

	private ArrayList<Monom> arrayListPolynom;

	/**
	 * Constructs an empty arraylist of monom 
	 * 
	 */
	public Polynom() {
		// initialize empty arraylist
		this.arrayListPolynom = new ArrayList<Monom>();
	}

	/**
	 * 
	 * constructs arraylist from string to monom
	 * @param str Polynom represents in the form a_1X^b_1+....+a_nX^b_n
	 * @throws  RuntimeException for invalid string
	 */
	public Polynom(String str) {
		double cof = 0;
		int pow = 0;
		arrayListPolynom = new ArrayList<>();



		str = str.toLowerCase();
		str = str.replaceAll("\\s+", ""); //remove spaces
		str = str.replaceAll("\\*", ""); //remove *

		// Check if string not empty
		if(str.length() == 0)
			throw new RuntimeException("String can't be empty");
		
		// Check if all power are positive
		for (int i = 0; i < str.length(); i++) {
			if(str.charAt(i) == '^' && str.charAt(i+1) == '-')
				throw new RuntimeException("Polynom can't have negative power");
		}

		// Change all - into +- and split + to array of strings
		str =str.replaceAll("-", "+-");
		String[] part = str.split("\\+");
		try {
			// Loop to initialize the arraylist with monom
			for (int i = 0; i < part.length; i++) {
				if(part[i].isEmpty() || (part[i].length() == 1 && part[i].charAt(0) == '-'))
					i++;
				// add monom with power 0 
				if(!part[i].contains("x")) {
					cof = Double.parseDouble(part[i].substring(0, part[i].length()));
					arrayListPolynom.add(new Monom(cof, 0));
				}
				else {
					for (int j = 0; j < part[i].length(); j++) {
						if(part[i].charAt(j) == 'x') { // check if x exists
							if(part[i].contains("^")) { // check if there is power(^) after x
								if (part[i].charAt(0) == 'x') //if there is no coefficient and + before x then coefficient is 1 
									cof = 1;
								else if (part[i].charAt(0) == '-' && part[i].charAt(1) == 'x') //else if there is no coefficient and - before x then coefficient is -1
									cof = -1;
								else // else there is coefficient different from +/-1
									cof = Double.parseDouble(part[i].substring(0, j));
								pow = Integer.parseInt(part[i].substring(j+2));
								arrayListPolynom.add(new Monom(cof, pow));
							}
							else { // if string doesn't have power(^) after x than the power is 0
								if (part[i].charAt(0) == 'x') //if there is no coefficient and + before x then coefficient is 1
									cof = 1;
								else if (part[i].charAt(0) == '-' && part[i].charAt(1) == 'x') // else if there is no coefficient and + before x then coefficient is -1
									cof = -1;
								else // else there is coefficient different from +/-1
									cof = Double.parseDouble(part[i].substring(0, j));
								arrayListPolynom.add(new Monom(cof, 1));
							}
						}
					}
				}
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Invalid input of Polynom");
		}
		
		// if there is zeros remove them and sort by high power to low
		removeZero();
		sort();
	}

	/**
	 * 
	 * Construct copy polynom to new polynom
	 * @param pl copeid polynom
	 */
	public Polynom(Polynom_able pl) {
		this(pl.copy().toString());
	}

	/**
	 * Sort the polynom from higher power to lower
	 */
	private void sort() {
		Collections.sort(arrayListPolynom, new Monom_Comperator());
	}

	/**
	 * 
	 * @return the size of the polynom
	 */
	private int size() {
		return arrayListPolynom.size();
	}

	/**
	 * This method calculate the result of given variable in the polynom
	 * @param x variable to place in the polynom
	 * @return the result of polynom
	 */
	@Override
	public double f(double x) {
		double result = 0;
		Iterator<Monom> monIt = iteretor();

		// Loop get the next monom in arraylist and call f(x) from monom and add it to result
		while(monIt.hasNext()) {
			result += monIt.next().f(x);
		}

		return result;
	}

	/**
	 * addition 2 polynoms
	 * @param p1 Polynom to add to current polynom
	 * @throws RuntimeException if p1 is not a polynom
	 */
	@Override
	public void add(Polynom_able p1) {
		Iterator<Monom> monIt;

		// check if p1 is polynom and add polynom to current polynom
		if(p1 instanceof Polynom) {
			monIt = p1.iteretor();
			while(monIt.hasNext()) {
				add(monIt.next());
			}
		}
		else
			throw new RuntimeException("p1 is not Polynom");

		removeZero();
		sort();
	}

	/**
	 * adding a monom to polynom
	 * @param m1 Monom to add to current polynom
	 */
	@Override
	public void add(Monom m1) {
		Iterator<Monom> monIt = iteretor();

		// loop to find monom in the polynom with the same power of given to add  
		while(monIt.hasNext()) {
			Monom m = monIt.next();
			if(new Monom_Comperator().compare(m, m1) == 0) {
				m1.add(m);
				arrayListPolynom.set(arrayListPolynom.indexOf(m), m1);
				removeZero();
				sort();
				return;
			}
		}

		// add monom to polynom with new power
		arrayListPolynom.add(m1);
		removeZero();
		sort();

	}

	/**
	 * Substract 2 polynoms
	 * @param p1 Polynom to substract to current polynom
	 * @throws RuntimeException if p1 is not a polynom
	 */
	@Override
	public void substract(Polynom_able p1) {
		Iterator<Monom> it = p1.iteretor();
		while(it.hasNext()) {
			Monom m=it.next();
			m.multyply(new Monom(-1, 0));
			this.add(m);

		}
	}

	/**
	 * Multiply 2 polynoms
	 * @param p1 Polynom to multiply to current polynom
	 * @throws RuntimeException if p1 is not a polynom
	 */
	@Override
	public void multiply(Polynom_able p1) {
		Iterator<Monom> monIt = iteretor();
		Iterator<Monom> p1It;
		Polynom tmpPol = new Polynom();

		if(p1 instanceof Polynom) {			
			while(monIt.hasNext()) {
				p1It = ((Polynom)p1).iteretor();
				Monom m1 = monIt.next();
				while(p1It.hasNext()) {
					Monom m2 = p1It.next();
					tmpPol.add(new Monom(m1.get_coefficient()*m2.get_coefficient(), m1.get_power()+m2.get_power()));
				}
			}
		}
		else
			throw new RuntimeException("p1 is not polynom");

		arrayListPolynom.clear();
		add(tmpPol);
		removeZero();
		sort();
	}

	/**
	 * If polynom has monoms with coefficient 0 it removes them
	 */
	private void removeZero() {
		Iterator<Monom> monIt = iteretor();

		while(monIt.hasNext()) {
			if(monIt.next().get_coefficient() == 0)
				monIt.remove();
		}
	}

	/**
	 * Compare 2 polyonom
	 * @param p1 polynom to compare
	 * @throws RuntimeException if p1 is not polynom 
	 */
	@Override
	public boolean equals(Polynom_able p1) {
		Iterator<Monom> monIt = iteretor();		

		//check if monom in the polynom equal to monom in current polynom
		if(p1 instanceof Polynom) {
			this.sort();
			((Polynom) p1).sort();
			Iterator<Monom> p1It = p1.iteretor();
			if(this.arrayListPolynom.size() != ((Polynom)p1).arrayListPolynom.size())
				return false;
			while(monIt.hasNext()) {
				Monom m = monIt.next();
				Monom m2 = p1It.next();
				if(new Monom_Comperator().compare(m, m2) != 0)
					return false;
				if(m.get_coefficient() != m2.get_coefficient())
					return false;
			}
		}
		else
			throw new RuntimeException("p1 is not Polynom");

		return true;
	}

	/**
	 * @return true if arraylist size is 0 or null
	 */
	@Override
	public boolean isZero() {
		return size() == 0 || arrayListPolynom == null ? true : false;
	} 

	/**
	 * find the root between x0 and x1 within the range of epsilon
	 * @param x0 start point
	 * @param x1 end point
	 * @param eps step (positive) value
	 * @return the root between x0 and x1
	 * @throws RuntimeException if polynom doesn't have roots between x0 and x1 
	 */
	@Override
	public double root(double x0, double x1, double eps) {
		double xmid=0;
		double xR = Math.max(x0, x1);
		double xL = Math.min(x0, x1);
		
		if(this.f(x0)*this.f(x1) <= 0) {
			do {
				xmid = (xR+xL)/2;
				if(f(xL)*f(xmid) >0)
					xL = xmid;
				else
					xR = xmid;
			}while(Math.abs(this.f(xmid)) > eps);
		}
		else {
			if(f(xR) == 0)
				return xR;
			if(f(xL) == 0)
				return xL;
			
			throw new RuntimeException("Error");
			
		}
		
		return xmid;
	}

	/**
	 * create a deep copy of this Polynum
	 * @return deep copy of the polynom
	 */
	@Override
	public Polynom_able copy() {
		return new Polynom(toString());
	}

	/**
	 * Compute a new Polynom which is the derivative of this Polynom
	 * @return polynom after computing derivative
	 */
	@Override
	public Polynom_able derivative() {
		Polynom_able p = new Polynom();
		Iterator<Monom> monIt = iteretor();

		if(isZero())
			return null;

		while(monIt.hasNext()) {
			p.add(monIt.next().derivative());

		}

		return p;
	}

	/**
	 * Compute Riemann's Integral over this Polynom starting from x0, till x1 using eps size steps
	 * @param x0 start point
	 * @param x1 end point
	 * @param eps width of the rectangle 
	 * @return the approximated area above the x-axis below this Polynom and between the [x0,x1] range
	 */
	@Override
	public double area(double x0, double x1, double eps) {
		double sumOfArea = 0;
		double xR = Math.max(x0, x1);
		double xL = Math.min(x0, x1);

		while (xR > xL) {
			if(f(xL) > 0)
				sumOfArea += eps * f(xL);
			xL += eps;
		}

		return sumOfArea;
	}
	
	/**
	 * Compute Riemann's Integral over this Polynom starting from x0, till x1 using eps size steps
	 * @param x0 start point
	 * @param x1 end point
	 * @param eps width of the rectangle 
	 * @return the approximated area UNDER the x-axis below this Polynom and between the [x0,x1] range
	 */
	public double areaUnderX(double x0, double x1, double eps) {
		double sumOfArea = 0;
		double xR = Math.max(x0, x1);
		double xL = Math.min(x0, x1);

		while (xR > xL) {
			if(f(xL) < 0)
				sumOfArea += eps * f(xL);
			xL += eps;
		}

		return Math.abs(sumOfArea);
	}
	
	/**
	 * returns the iterator of polynom
	 * @return iterator of monom for this polynom
	 */
	@Override
	public Iterator<Monom> iteretor() {
		Iterator<Monom> monIt = new Iterator<Monom>() {

			private int currentIndex = 0;

			@Override
			public Monom next() {
				return arrayListPolynom.get(currentIndex++);
			}

			@Override
			public boolean hasNext() {
				return currentIndex < size() && arrayListPolynom.get(currentIndex) != null;
			}

			@Override
			public void remove() {
				arrayListPolynom.remove(currentIndex-1);
			}
		};

		return monIt;
	}	

	/**
	 * return String a representation of this Polynom
	 * @return String a representation of this Polynom
	 */
	@Override
	public String toString() {
		String str = "";
		Iterator<Monom> it = arrayListPolynom.iterator();

		if(isZero())
			return "0";

		while(it.hasNext()) {
			Monom m = it.next();
			str += m.toString();
			str += "+";
		}

		str = str.replace("+-", "-");
		str = str.substring(0, str.length()-1);

		return str;
	}
	
	/**
	 * get polynom and runs a gui of the polynom
	 * @param pol 
	 */
	public void runGUI() {
		JavmosGUI gui = new JavmosGUI(this.toString());
	}
	
	/**
	 * get a polynom and x0, x1 the range of the polynom to draw
	 * @param pol
	 * @param x0
	 * @param x1
	 */
	public void runGUI(double x0, double x1) {
		JavmosGUI gui = new JavmosGUI(this.toString(), Math.min(x0, x1), Math.max(x0, x1));
	}
}
