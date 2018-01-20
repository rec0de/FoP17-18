package matrix;

public interface Arithmetic<T> {
	/**
	 * returns the representation of zero
	 * @return the representation of zero
	 */
	T zero();
	
	/**
	 * Returns the result of the addition of a and b
	 * @param a
	 * @param b
	 * @return
	 */
	T add(T a, T b);
	
	/**
	 * Returns the result of the multiplication of a and b
	 * @param a
	 * @param b
	 * @return
	 */
	T mul(T a, T b);
	
	/**
	 * Parse a given string
	 * @param s
	 * @return
	 */
	T parse(String s);
	
}
