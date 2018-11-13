
package myMath;
/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Nitzan
 *
 */
public class Monom implements function{

	private double _coefficient;
	private int _power;
	
	/**
	 * Constructor: set a as coefficient and b as power
	 * @param a coefficient of monom
	 * @param b power of monom
	 * @throws RuntimeException if power is negative
	 */
	public Monom(double a, int b){
		if(b < 0)
			throw new RuntimeException("Power can't be negative");
		this.set_coefficient(a);
		this.set_power(b);
	}
	
	/**
	 * 
	 * Constructor copy a monom to new monom
	 * @param ot monom to copy
	 */
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}
	
	/**
	 * 
	 * @return power of monom
	 */
	public int get_power() {
		return this._power;
	}
	
	/**
	 * @return coefficient of monom 
	 */
	public double get_coefficient() {
		return this._coefficient;
	}
	
	/**
	 * 
	 * @param a the coefficient of monom
	 */
	private void set_coefficient(double a){
		this._coefficient = a;
	}
	
	/**
	 * 
	 * @param p the power of the monom
	 */
	private void set_power(int p) {
		this._power = p;
	}
	
	/**
	 * return the result of given x to place as x in monom
	 * @param x variable to place in monom
	 * @return result of x placed in monom
	 */
	@Override
	public double f(double x) {
		return this.get_coefficient() * Math.pow(x, this.get_power());
	} 
	
	/**
	 * add the new monom to current monom
	 * @param m new monom to add to current monom
	 */
	public void add(Monom m) {
		set_coefficient(this._coefficient + m._coefficient);
	}
	
	/**
	 * derivative current monom
	 * @return the derivative of monom
	 */
	public Monom derivative() {
		return this._power == 0 ? new Monom(0, 0) : new Monom(this._coefficient*this._power, this._power-1);   
	}
	
	/**
	 * String a representation of this Monom
	 * @return a String representation of this Monom
	 */
	@Override
	public String toString() {
		String str = "";
		
		if(_coefficient == 0)
			return "";
		
		if(_power == 0)
			str += _coefficient;
		else if(_power == 1) {
			if(_coefficient == 1)
				str += "x";
			else if(_coefficient == -1)
				str += "-x";
			else
				str += _coefficient + "x";
		}
		else {
			if(_coefficient == 1)
				str += "x^" + _power;
			else if(_coefficient == -1)
				str += "-x^" + _power;
			else
				str += _coefficient + "x^" + _power;
		}
		
		return str;
	}
}