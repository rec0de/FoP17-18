package matrix;

public class IntegerArithmetic implements Arithmetic<Integer> {

	@Override
	public Integer zero() {
		return 0;
	}

	@Override
	public Integer add(Integer a, Integer b) {
		return a + b;
	}

	@Override
	public Integer mul(Integer a, Integer b) {
		return a * b;
	}

	@Override
	public Integer parse(String s) {
		return Integer.parseInt(s);
	}
	
}
