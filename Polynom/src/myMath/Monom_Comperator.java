package myMath;

import java.util.Comparator;

/**
 * This class compare which power is higher between 2 monoms
 * @author Nitzan and Rivka
 *
 */
public class Monom_Comperator implements Comparator<Monom> {

	/**
	 * compare the power of 2 monoms
	 * @param arg0 first monon to compare power
	 * @param arg1 second monom to compare power
	 * @return 0 if power of monoms equals or positive number if arg0 power higher then arg1 or negative number if arg1 power higher than arg0 
	 */
	@Override
	public int compare(Monom arg0, Monom arg1) {
		return arg1.get_power() - arg0.get_power();
	}
}
