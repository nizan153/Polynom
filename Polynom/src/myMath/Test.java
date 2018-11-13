package myMath;

/**
 * This class Test the program.
 * @author Nitzan and Rivka
 *
 */

public class Test {
	public static void main(String[] args) {
		Polynom p = new Polynom("0.2x^4-1.5x^3+3.0x^2-x-5");
		p.runGUI(-2, 6);
//		test1();
	}
	
	public static void test1() {
		Polynom_able p1 = new Polynom("3.7x^2-3x^3+3.12x+1");
		Polynom_able p2 = new Polynom("x^2-4");
		Polynom_able p3 = new Polynom();
		Polynom_able p4 = new Polynom(p2);
		
		
		
		// print Polynoms
		System.out.println("Polynom 1: " + p1.toString());
		System.out.println("Polynom 2: " + p2.toString());
		System.out.println("Polynom 3: " + p3.toString());
		System.out.println("Polynom 4: " + p4.toString());
		
		// isZero
		System.out.println("\n isZero: ");
		System.out.println("Check if p1 is zero: " + p1.isZero());
		System.out.println("Check if p3 is zero: " + p3.isZero());
		
		// add Monom
		System.out.println("\n add Monom");
		p3.add(new Monom(1.4, 3));
		System.out.println("add the monom 1.4x^3 to p3: " + p3.toString());
		p3.add(new Monom(4, 1));
		System.out.println("add the monom 4x to p3: " + p3.toString());
		p3.add(new Monom(5, 0));
		System.out.println("add the monom 5 to p3: " + p3.toString());
		
		// copy p1 into p3
		System.out.println("\n Copy polynom p1 to p3 ");
		System.out.println("before copy: " + p3);
		p3 = p1.copy();
		System.out.println("after copy: " + p3);

		// equal
		System.out.println("\n Equal: ");
		System.out.println("Check if p1 = p3 : " + p1.equals(p3));
		System.out.println("Check if p1 = p2 : " + p1.equals(p2));
		
		// f(x)
		System.out.println("\n f(x): ");
		System.out.println("p1: f(3.5)= " + p1.f(3.5));
		System.out.println("p2: f(3.5)= " + p2.f(3.5) );
		System.out.println("p3: f(3.5)= " + p3.f(3.5) );
		System.out.println("p4: f(3.5)= " + p4.f(3.5) );
		
		// p1 + p2 & p4 + p2
		System.out.println("\n add: p1 + p2");
		System.out.print("(" + p1 + ") + (" + p2 + ") = ");
		p1.add(p2);
		System.out.println(p1);
		System.out.println(" add: p4 + p2");
		System.out.print("(" + p4 + ") + (" + p2 + ") = ");
		p4.add(p2);
		System.out.println(p4);
		
		// p1 - p2 & p4 - 02
		System.out.println("\n substract: p1 - p2");
		System.out.print("(" + p1 + ") - (" + p2 + ") = ");
		p1.substract(p2);
		System.out.println(p1);
		System.out.println(" substract: p4 - p2");
		System.out.print("(" + p4 + ") - (" + p2 + ") = ");
		p4.substract(p2);
		System.out.println(p4);
		
		// p3 * p2 & p4 * p2
		System.out.println("\n multiply: p3 * p2");
		System.out.print("(" + p3  + ") * (" + p2 + ") = ");
		p3.multiply(p2);
		System.out.println(p3);
		System.out.println(" multiply: p4 * p2");
		System.out.print("(" + p4  + ") * (" + p2 + ") = ");
		p4.multiply(p2);
		System.out.println(p4);
		
		// derivative
		System.out.println("\n derivate");
		System.out.println("derivate of p1: " + p1.derivative());
		System.out.println("derivate of p2: " + p2.derivative());
		System.out.println("derivate of p3: " + p3.derivative());
		
		// root
		System.out.println("\n root");
		System.out.println("the root of " + p1 + " in the range [1,10] is " + p1.root(1, 5, 0.0001));
		System.out.println("the root of " + p2 + " in the range [0,7] is " + p2.root(0,7, 0.0001));
		System.out.println("the root of " + p3 + " in the range [-5,1] is " + p3.root(-5,1, 0.0001));
		
		// area
		System.out.println("\n area");
		System.out.println("the area of " + p1 + " in the range [-1,5] is " + p1.area(-1, 5, 0.05));
		System.out.println("the area of " + p2 + " in the range [-1,5] is " + p2.area(-1, 5, 0.05));
		System.out.println("the area of " + p3 + " in the range [-1,5] is " + p3.area(-1, 5, 0.05));
		
	}
}
