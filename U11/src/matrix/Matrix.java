/**
 * 
 */
package matrix;

import java.util.LinkedList;

/**
 * Implements common matrix operations on arbitrary data types 
 * 
 * @author Nils Rollshausen
 */
public class Matrix<T extends Comparable<T>> {
	
	private Arithmetic<T> arithmetic;
	private LinkedList<LinkedList<T>> data;
	private int rows;
	private int columns;
	
	/**
	 * Main constructor for Matrix class - initializes an all-zero matrix of the specified size
	 * @param rows Number of rows in the created matrix
	 * @param columns Number of columns in the created matrix
	 * @param arithmetic Arithmetic object used to perform operations on the generic data values
	 */
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
	
	/**
	 * Returns the number of rows in the matrix
	 * @return Returns row count
	 */
	public int getRows() {
		return this.rows;
	}
	
	/**
	 * Returns the number of columns in the matrix
	 * @return Returns column count
	 */
	public int getColumns() {
		return this.columns;
	}
	
	/**
	 * Fetches a single value out of the Matrix at the given coordinates
	 * @param row The row number of the value to retrieve (zero-based)
	 * @param column The column number of the value to retrieve (zero-based)
	 * @return The value at the specified coordinates
	 * @throws IndexOutOfBoundsException Throws exception on invalid (out-of-bounds) coordinates
	 */
	public T getCell(int row, int column) throws IndexOutOfBoundsException {
		return data.get(row).get(column);
	}
	
	/**
	 * Writes a value to the position of the given coordinates
	 * @param row The row number of the value to write (zero-based)
	 * @param column The column number of the value to write (zero-based)
	 * @param value The value to write at the given position
	 * @throws IndexOutOfBoundsException Throws exception on invalid (out-of-bounds) coordinates
	 */
	public void setCell(int row, int column, T value) throws IndexOutOfBoundsException {
		data.get(row).set(column, value);
	}
	
	/**
	 * Checks if a given matrix is value-identical to the current one
	 * @param other The matrix to check equality to
	 * @return True if all values are equal, false if the matrices differ
	 */
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
	
	/**
	 * Adds a matrix of the same size as the current one to the current one and returns the sum
	 * @param other The matrix to add to the current one
	 * @return The sum of the two matrices or null on size mismatch
	 */
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
	
	/**
	 * Multiplies the current matrix with another matrix of matching size (A.columns = B.rows) and returns the product
	 * @param other The matrix to be multiplied by the current one
	 * @return The product of the current and the given matrix
	 */
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
	
	/**
	 * Transposes the matrix so that columns become rows (and vice-versa) and returns the result
	 * @return The transposed Matrix
	 */
	public Matrix<T> transpose(){
		Matrix<T> result = new Matrix<T>(columns, rows, arithmetic);
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				result.setCell(j, i, this.getCell(i, j));
			}
		}
		
		return result;
	}
	
	/**
	 * Finds the minimum or maximum value in the matrix
	 * @param min True if the minimum value should be returned, false if the maximum value should be returned
	 * @return The minimum / maximum value of the matrix
	 */
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
	
	/**
	 * Returns a resized matrix by adding / removing rows and columns and filling empty cells with zero
	 * @param rowdiff Number of rows to be added (negative means rows are removed) to/from the bottom
	 * @param coldiff Number of columns to be added (negative means columns are removed) to/from the right
	 * @return The resized matrix
	 */
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
