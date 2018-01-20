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
	
	public boolean equals(Matrix<T> other) {
		if(other.getRows() != rows || other.getColumns() != columns)
			return false;
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if(this.getCell(i, j).compareTo(other.getCell(i, j)) != 0)
					return false;
			}
		}
		
		return true;
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
	
	public Matrix<T> mul(Matrix<T> other){
		if(columns != other.getRows())
			return null;
		
		Matrix<T> result = new Matrix<T>(rows, other.getColumns(), arithmetic);
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < other.getColumns(); j++) {
				T cellValue = arithmetic.zero();
				for(int k = 0; k < columns; k++) {
					cellValue = arithmetic.add(cellValue, arithmetic.mul(this.getCell(i, k), other.getCell(k, j)));
				}
				result.setCell(i, j, cellValue);
			}
		}
		
		return result;
	}
	
	public Matrix<T> transpose(){
		Matrix<T> result = new Matrix<T>(columns, rows, arithmetic);
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				result.setCell(j, i, this.getCell(i, j));
			}
		}
		
		return result;
	}
	
	public T getMinMax(boolean min) {
		T result = this.getCell(0, 0);
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				T currentCell = this.getCell(i, j);
				if(min && currentCell.compareTo(result) < 0 || !min && currentCell.compareTo(result) > 0)
					result = currentCell;
			}
		}
		
		return result;
	}
	
	public Matrix<T> resize(int rowdiff, int coldiff){
		int newrows = rows + rowdiff;
		int newcols = columns + coldiff;
		
		if(newrows < 0 || newcols < 0)
			return null;
		
		Matrix<T> result = new Matrix<T>(newrows, newcols, arithmetic);
		
		for(int i = 0; i < Math.min(newrows, rows); i++) {
			for(int j = 0; j < Math.min(newcols,  columns); j++) {
				result.setCell(i, j, this.getCell(i, j));
			}
		}
		
		return result;
	}

}
