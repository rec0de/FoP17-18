package matrix;

public class FloatArithmetic implements Arithmetic<Float> {

	@Override
	public Float zero() {
		return 0f;
	}

	@Override
	public Float add(Float a, Float b) {
		return a + b;
	}

	@Override
	public Float mul(Float a, Float b) {
		return a * b;
	}

	@Override
	public Float parse(String s) {
		return Float.parseFloat(s);
	}
	
}
