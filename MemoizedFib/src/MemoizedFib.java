import gui.GUI;

import java.lang.StackOverflowError;
import java.math.BigInteger;
import java.util.*;

// small program showing the benefits of Dynamic Programming (memoization) to recursion

public class MemoizedFib {
	
	static ArrayList<BigInteger> memo = new ArrayList<BigInteger>(1); // will remember solutions
	
	public static BigInteger fib(int number) { // for finding a fibonacci number at the nth position
		if (memo.size() >= number) { // the memo has the nth fibonacci number
			return memo.get(number - 1); // return the nth fibonacci number
		}
		if (number == 1 || number == 2) { // the first and second fibonacci numbers are "1", so just return "1" if either of them is the parameter
			return BigInteger.ONE;
		}
		BigInteger closestFibNumber = fib(number - 1); // find the (nth - 1) fibonacci number
		BigInteger fibNumber = closestFibNumber.add(fib(number - 2)); // add the (nth - 2) fibonacci number to the (nth - 1) fibonacci number to get the nth fibonacci number
		memo.add(fibNumber); // make sure to record the nth fibonacci number (that's the point!)
		return fibNumber;
	}
	
	public static void main (String[] args) {
		memo.add(BigInteger.ONE); // record the first fibonacci number
		memo.add(BigInteger.ONE); // record the second fibonacci number
		GUI g = new GUI();
		int nth = 0;
		while (nth != -1) { // for querying multiple times
			try {
				String response = g.get("Please enter the nth Fibonacci number you'd like to calculate!");
				if (response == null) { // user hit "Cancel"
					g.give("Thank you, and goodbye!");
					System.exit(0);
				}
				nth = Integer.parseInt(response);
				if (nth <= 0) { // an invalid nth position
					g.give("\"n\" must be positive!");
					continue;
				}
				BigInteger fibNumber = fib(nth);
				g.give("Number " + nth + " in the Fibonacci sequence is " + fibNumber + ".");				
			} catch (NumberFormatException nfe) { // an invalid number was entered
				g.giveThrowable(nfe);
				g.give("Please input a valid number!\n"
					 + "(Ensure it's an integer, and not too big (i.e., > 2,147,483,647))");
			} catch (StackOverflowError soe) {
				g.giveThrowable(soe);
				g.give("We've gone too deep (recursively)!");
			}
		}
	}
}