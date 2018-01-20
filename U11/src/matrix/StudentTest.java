/**
 * 
 */
package matrix;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author Nils Rollshausen
 *
 */
class StudentTest {

	@Test
	public void testConstructor() {
		Matrix<Integer> matrix = new Matrix<Integer>(3, 2, new IntegerArithmetic());
		
		assertEquals(matrix.getColumns(), 2);
		assertEquals(matrix.getRows(), 3);
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 2; j++) {
				assertEquals(matrix.getCell(i, j), new Integer(0));
			}
		}
		
		int height = (int) Math.floor(Math.random()*512);
		int width = (int) Math.floor(Math.random()*512);
		Matrix<Float> matrix2 = new Matrix<Float>(height, width, new FloatArithmetic());
		
		assertEquals(matrix2.getColumns(), width);
		assertEquals(matrix2.getRows(), height);
		
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				assertEquals(matrix2.getCell(i, j), new Float(0));
			}
		}
	}
	
	@Test
	public void testGetCell() {
		Matrix<Integer> a = new Matrix<Integer>(4, 4, new IntegerArithmetic());
		
		a.setCell(0, 0, 3);
		a.setCell(0, 1, 4);
		a.setCell(0, 2, 5);
		a.setCell(0, 3, 6);
		a.setCell(1, 0, 7);
		a.setCell(1, 1, 8);
		a.setCell(1, 2, 9);
		a.setCell(1, 3, 10);
		a.setCell(2, 0, 11);
		a.setCell(2, 1, 12);
		a.setCell(2, 2, 13);
		a.setCell(2, 3, 14);
		a.setCell(3, 0, 15);
		a.setCell(3, 1, 16);
		a.setCell(3, 2, 17);
		a.setCell(3, 3, 18);
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				assertEquals(a.getCell(i, j), new Integer(3 + i*4 + j));
			}
		}
	}
	
	@Test
	public void testSetCell() {
		Matrix<Float> a = new Matrix<Float>(100, 100, new FloatArithmetic());
		
		for(int i = 0; i < 1000; i++) {
			int row = (int) Math.floor(Math.random()*100);
			int col = (int) Math.floor(Math.random()*100);
			float value = (float) Math.random()*1000;
			a.setCell(row, col, value);
			assertEquals(a.getCell(row, col), new Float(value));
		}
	}
	
	@Test
	public void testAdd() {
		IntegerArithmetic arith = new IntegerArithmetic();
		Matrix<Integer> a = new Matrix<Integer>(2, 2, arith);
		Matrix<Integer> b = new Matrix<Integer>(2, 2, arith);
		Matrix<Integer> c = new Matrix<Integer>(3, 2, arith);
		Matrix<Integer> d = new Matrix<Integer>(2, 3, arith);
		Matrix<Integer> e = new Matrix<Integer>(2, 3, arith);
		
		Matrix<Integer> r1 = new Matrix<Integer>(2, 2, arith);
		Matrix<Integer> r2 = new Matrix<Integer>(2, 3, arith);
		
		a.setCell(0, 0, 100);
		a.setCell(0, 1, 200);
		a.setCell(1, 0, 300);
		a.setCell(1, 1, 400);
		
		b.setCell(0, 0, 10);
		b.setCell(0, 1, 20);
		b.setCell(1, 0, 30);
		b.setCell(1, 1, 40);
		
		r1.setCell(0, 0, 110);
		r1.setCell(0, 1, 220);
		r1.setCell(1, 0, 330);
		r1.setCell(1, 1, 440);
		
		d.setCell(0, 0, 3);
		d.setCell(0, 1, 14);
		d.setCell(0, 2, 15);
		d.setCell(1, 0, 9);
		d.setCell(1, 1, 26);
		d.setCell(1, 2, 5);
		
		e.setCell(0, 0, 3);
		e.setCell(0, 1, 5);
		e.setCell(0, 2, 8);
		e.setCell(1, 0, 9);
		e.setCell(1, 1, 7);
		e.setCell(1, 2, 9);
		
		r2.setCell(0, 0, 6);
		r2.setCell(0, 1, 19);
		r2.setCell(0, 2, 23);
		r2.setCell(1, 0, 18);
		r2.setCell(1, 1, 33);
		r2.setCell(1, 2, 14);
		
		assertNull(a.add(c));
		assertNull(c.add(a));
		assertNull(c.add(d));
		assertNull(d.add(c));
		
		assertTrue(a.add(b).equals(r1));
		assertTrue(b.add(a).equals(r1));
		
		assertTrue(d.add(e).equals(r2));
		assertTrue(e.add(d).equals(r2));
	}
	
	@Test
	public void testTranspose() {
		IntegerArithmetic arith = new IntegerArithmetic();
		Matrix<Integer> d = new Matrix<Integer>(2, 3, arith);
		Matrix<Integer> dt = new Matrix<Integer>(3, 2, arith);
		Matrix<Integer> e = new Matrix<Integer>(2, 3, arith);
		Matrix<Integer> et = new Matrix<Integer>(3, 2, arith);
		
		d.setCell(0, 0, 3);
		d.setCell(0, 1, 14);
		d.setCell(0, 2, 15);
		d.setCell(1, 0, 9);
		d.setCell(1, 1, 26);
		d.setCell(1, 2, 5);
		
		dt.setCell(0, 0, 3);
		dt.setCell(1, 0, 14);
		dt.setCell(2, 0, 15);
		dt.setCell(0, 1, 9);
		dt.setCell(1, 1, 26);
		dt.setCell(2, 1, 5);
		
		e.setCell(0, 0, 3);
		e.setCell(0, 1, 5);
		e.setCell(0, 2, 8);
		e.setCell(1, 0, 9);
		e.setCell(1, 1, 7);
		e.setCell(1, 2, 9);
		
		et.setCell(0, 0, 3);
		et.setCell(1, 0, 5);
		et.setCell(2, 0, 8);
		et.setCell(0, 1, 9);
		et.setCell(1, 1, 7);
		et.setCell(2, 1, 9);
		
		assertTrue(e.transpose().equals(et));
		assertTrue(d.transpose().equals(dt));
		
		int height = (int) Math.floor(Math.random()*512);
		int width = (int) Math.floor(Math.random()*512);
		
		Matrix<Float> matrix = new Matrix<Float>(height, width, new FloatArithmetic());
		
		assertEquals(matrix.getColumns(), width);
		assertEquals(matrix.getRows(), height);
		
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				matrix.setCell(i, j, (float) Math.random()*100);
			}
		}
		
		assertTrue(matrix.equals(matrix.transpose().transpose()));
	}
	
	@Test
	public void testMinMax() {
		IntegerArithmetic arith = new IntegerArithmetic();
		FloatArithmetic arithF = new FloatArithmetic();
		
		Matrix<Float> b = new Matrix<Float>(3, 3, arithF);
		Matrix<Integer> c = new Matrix<Integer>(256, 512, arith);
		Matrix<Integer> d = new Matrix<Integer>(2, 3, arith);
		Matrix<Integer> e = new Matrix<Integer>(2, 3, arith);
		
		b.setCell(0, 0, (float) 3.14);
		b.setCell(0, 1, (float) -15.9);
		b.setCell(0, 2, (float) 2);
		b.setCell(1, 0, (float) 65.35);
		b.setCell(1, 1, (float) 8.97);
		b.setCell(1, 2, (float) 9);
		b.setCell(2, 0, (float) 3.238);
		b.setCell(2, 1, (float) 46.2);
		b.setCell(2, 2, (float) 6.4);
		
		d.setCell(0, 0, 3);
		d.setCell(0, 1, 14);
		d.setCell(0, 2, 15);
		d.setCell(1, 0, 9);
		d.setCell(1, 1, 26);
		d.setCell(1, 2, 5);
		
		e.setCell(0, 0, 3);
		e.setCell(0, 1, 5);
		e.setCell(0, 2, 8);
		e.setCell(1, 0, 9);
		e.setCell(1, 1, 7);
		e.setCell(1, 2, 9);
		
		assertEquals(b.getMinMax(true), new Float(-15.9));
		assertEquals(b.getMinMax(false), new Float(65.35));
		assertEquals(c.getMinMax(true), new Integer(0));
		assertEquals(c.getMinMax(false), new Integer(0));
		assertEquals(d.getMinMax(true), new Integer(3));
		assertEquals(d.getMinMax(false), new Integer(26));
		assertEquals(e.getMinMax(true), new Integer(3));
		assertEquals(e.getMinMax(false), new Integer(9));
		
	}
	
	@Test void testResize() {
		Matrix<Integer> a = new Matrix<Integer>(4, 4, new IntegerArithmetic());
		Matrix<Integer> b = new Matrix<Integer>(2, 3, new IntegerArithmetic());
		Matrix<Integer> c = new Matrix<Integer>(6, 5, new IntegerArithmetic());
		Matrix<Integer> d = new Matrix<Integer>(2, 8, new IntegerArithmetic());
		
		a.setCell(0, 0, 3);
		a.setCell(0, 1, 4);
		a.setCell(0, 2, 5);
		a.setCell(0, 3, 6);
		a.setCell(1, 0, 7);
		a.setCell(1, 1, 8);
		a.setCell(1, 2, 9);
		a.setCell(1, 3, 10);
		a.setCell(2, 0, 11);
		a.setCell(2, 1, 12);
		a.setCell(2, 2, 13);
		a.setCell(2, 3, 14);
		a.setCell(3, 0, 15);
		a.setCell(3, 1, 16);
		a.setCell(3, 2, 17);
		a.setCell(3, 3, 18);
		
		b.setCell(0, 0, 3);
		b.setCell(0, 1, 4);
		b.setCell(0, 2, 5);
		b.setCell(1, 0, 7);
		b.setCell(1, 1, 8);
		b.setCell(1, 2, 9);
		
		c.setCell(0, 0, 3);
		c.setCell(0, 1, 4);
		c.setCell(0, 2, 5);
		c.setCell(0, 3, 6);
		c.setCell(1, 0, 7);
		c.setCell(1, 1, 8);
		c.setCell(1, 2, 9);
		c.setCell(1, 3, 10);
		c.setCell(2, 0, 11);
		c.setCell(2, 1, 12);
		c.setCell(2, 2, 13);
		c.setCell(2, 3, 14);
		c.setCell(3, 0, 15);
		c.setCell(3, 1, 16);
		c.setCell(3, 2, 17);
		c.setCell(3, 3, 18);
		
		d.setCell(0, 0, 3);
		d.setCell(0, 1, 4);
		d.setCell(0, 2, 5);
		d.setCell(0, 3, 6);
		d.setCell(1, 0, 7);
		d.setCell(1, 1, 8);
		d.setCell(1, 2, 9);
		d.setCell(1, 3, 10);
		
		assertNull(a.resize(-5, 10));
		assertNull(a.resize(0, -6));
		assertTrue(a.resize(0, 0).equals(a));
		assertTrue(a.resize(-2, -1).equals(b));
		assertTrue(a.resize(2, 1).equals(c));
		assertTrue(c.resize(-4, -2).equals(b));
		assertTrue(a.resize(-2, 4).equals(d));
	}
	
	@Test void testEquals() {
		IntegerArithmetic arith = new IntegerArithmetic();
		Matrix<Integer> b = new Matrix<Integer>(4, 2, arith);
		Matrix<Integer> r2 = new Matrix<Integer>(4, 2, arith);
		Matrix<Integer> r3 = new Matrix<Integer>(4, 3, arith);
		Matrix<Integer> r4 = new Matrix<Integer>(1, 4, arith);
		
		b.setCell(0, 0, 1);
		b.setCell(0, 1, 1);
		b.setCell(1, 0, 0);
		b.setCell(1, 1, 2);
		b.setCell(2, 0, 3);
		b.setCell(2, 1, 1);
		b.setCell(3, 0, -1);
		b.setCell(3, 1, 2);
	
		r2.setCell(0, 0, -2);
		r2.setCell(0, 1, -1);
		r2.setCell(1, 0, 0);
		r2.setCell(1, 1, 0);
		r2.setCell(2, 0, 2);
		r2.setCell(2, 1, 1);
		r2.setCell(3, 0, -4);
		r2.setCell(3, 1, -2);
		
		r3.setCell(0, 0, 1);
		r3.setCell(0, 1, 0);
		r3.setCell(0, 2, 0);
		r3.setCell(1, 0, 0);
		r3.setCell(1, 1, -4);
		r3.setCell(1, 2, 2);
		r3.setCell(2, 0, 3);
		r3.setCell(2, 1, 4);
		r3.setCell(2, 2, -2);
		r3.setCell(3, 0, -1);
		r3.setCell(3, 1, -6);
		r3.setCell(3, 2, 3);
		
		r4.setCell(0, 0, 3);
		r4.setCell(0, 1, 2);
		r4.setCell(0, 2, 7);
		r4.setCell(0, 3, 0);
		
		assertTrue(r2.equals(r2));
		assertTrue(r4.equals(r4));
		assertFalse(b.equals(r2));
		assertFalse(b.equals(r3));
		assertFalse(b.equals(r4));
	}
	
	@Test
	public void testMathHomework() {
		
		IntegerArithmetic arith = new IntegerArithmetic();
		Matrix<Integer> x = new Matrix<Integer>(1, 2, arith);
		Matrix<Integer> y = new Matrix<Integer>(3, 1, arith);
		Matrix<Integer> z = new Matrix<Integer>(4, 1, arith);
		Matrix<Integer> a = new Matrix<Integer>(2, 3, arith);
		Matrix<Integer> b = new Matrix<Integer>(4, 2, arith);
		
		Matrix<Integer> r1 = new Matrix<Integer>(2, 1, arith);
		Matrix<Integer> r2 = new Matrix<Integer>(4, 2, arith);
		Matrix<Integer> r3 = new Matrix<Integer>(4, 3, arith);
		Matrix<Integer> r4 = new Matrix<Integer>(1, 4, arith);
		Matrix<Integer> r5 = new Matrix<Integer>(4, 4, arith);
		
		x.setCell(0, 0, 2);
		x.setCell(0, 1, 1);
		
		y.setCell(0, 0, 1);
		y.setCell(1, 0, 2);
		y.setCell(2, 0, 3);
		
		z.setCell(0, 0, -1);
		z.setCell(1, 0, 0);
		z.setCell(2, 0, 1);
		z.setCell(3, 0, -2);
		
		a.setCell(0, 0, 1);
		a.setCell(0, 1, 2);
		a.setCell(0, 2, -1);
		a.setCell(1, 0, 0);
		a.setCell(1, 1, -2);
		a.setCell(1, 2, 1);

		b.setCell(0, 0, 1);
		b.setCell(0, 1, 1);
		b.setCell(1, 0, 0);
		b.setCell(1, 1, 2);
		b.setCell(2, 0, 3);
		b.setCell(2, 1, 1);
		b.setCell(3, 0, -1);
		b.setCell(3, 1, 2);
		
		r1.setCell(0, 0, 2);
		r1.setCell(1, 0, -1);
		
		r2.setCell(0, 0, -2);
		r2.setCell(0, 1, -1);
		r2.setCell(1, 0, 0);
		r2.setCell(1, 1, 0);
		r2.setCell(2, 0, 2);
		r2.setCell(2, 1, 1);
		r2.setCell(3, 0, -4);
		r2.setCell(3, 1, -2);
		
		r3.setCell(0, 0, 1);
		r3.setCell(0, 1, 0);
		r3.setCell(0, 2, 0);
		r3.setCell(1, 0, 0);
		r3.setCell(1, 1, -4);
		r3.setCell(1, 2, 2);
		r3.setCell(2, 0, 3);
		r3.setCell(2, 1, 4);
		r3.setCell(2, 2, -2);
		r3.setCell(3, 0, -1);
		r3.setCell(3, 1, -6);
		r3.setCell(3, 2, 3);
		
		r4.setCell(0, 0, 3);
		r4.setCell(0, 1, 2);
		r4.setCell(0, 2, 7);
		r4.setCell(0, 3, 0);
		
		r5.setCell(0, 0, 1);
		r5.setCell(0, 1, 0);
		r5.setCell(0, 2, -1);
		r5.setCell(0, 3, 2);
		r5.setCell(1, 0, 0);
		r5.setCell(1, 1, 0);
		r5.setCell(1, 2, 0);
		r5.setCell(1, 3, 0);
		r5.setCell(2, 0, -1);
		r5.setCell(2, 1, 0);
		r5.setCell(2, 2, 1);
		r5.setCell(2, 3, -2);
		r5.setCell(3, 0, 2);
		r5.setCell(3, 1, 0);
		r5.setCell(3, 2, -2);
		r5.setCell(3, 3, 4);
		
		assertNull(a.mul(x));
		assertTrue(a.mul(y).equals(r1));
		assertTrue(z.mul(x).equals(r2));
		assertTrue(b.mul(a).equals(r3));
		assertTrue(x.mul(b.transpose()).equals(r4));
		assertTrue(b.mul(x.transpose()).equals(r4.transpose()));
		assertTrue(z.mul(z.transpose()).equals(r5));	
	}
}
