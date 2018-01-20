/**
 * 
 */
package matrix;

import java.util.LinkedList;

/**
 * @author Nils Rollshausen
 *
 */
public class Matrix<T extends Comparable<T>> {
	
	private Arithmetic<T> arithmetic;
	private LinkedList<LinkedList<T>> data;
	private int rows;
	private int columns;
	
	public Matrix(int rows, int columns, Arithmetic<T> arithmetic) {
		this.rows = rows;
		this.columns = columns;
		this.arithmetic = arithmetic;
		
		this.data = new LinkedList<LinkedList<T>>();
		for(int i = 0; i < rows; i++) {
			LinkedList<T> newrow = new LinkedList<T>();
			for(int j = 0; j < columns; j++) {
				newrow.add(arithmetic.zero());
			}
			this.data.add(newrow);
		}
		
	}
	
	public int getRows() {
		return this.rows;
	}
	
	public int getColumns() {
		return this.columns;
	}
	
	public T getCell(int row, int column) throws IndexOutOfBoundsException {
		return data.get(row).get(column);
	}
	
	public void setCell(int row, int column, T value) {
		data.get(row).set(column, value);
	}
	
	public Matrix<T> add(Matrix<T> other){
		if(other.getRows() != rows || other.getColumns() != columns)
			return null;
		
		Matrix<T> result = new Matrix<T>(rows, columns, arithmetic);
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				result.setCell(i, j, arithmetic.add(this.getCell(i, j), other.getCell(i, j)));
			}
		}
		
		return result;
	}

}
