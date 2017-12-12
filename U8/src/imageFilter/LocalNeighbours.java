package imageFilter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * @author Florian Kadner, Nils Rollshausen
 * @version 2.0 20171212
 */
public class LocalNeighbours {

	public LocalNeighbours() {

	}

	private ArrayList<Float> pixels = new ArrayList<Float>();

	public Float[] getPixels() {
		Float[] pixelsArray = new Float[pixels.size()];
		pixelsArray = pixels.toArray(pixelsArray);
		return pixelsArray;
	}

	public int getSize() {
		return pixels.size();
	}

	public void addPixel(float Pixel) {
		pixels.add(Pixel);
	}

	/**
	 * sorts the local neighborhood with a specific sorting algorithm
	 */
	public void sort() {
		Float[] A = getPixels();
		
		int start = -1;
		int end = A.length;
		
		while(start < end) {
			start += 1;
			end -= 1;
			for(int i = start; i < end; i++) {
				if(A[i] > A[i+1]) {
					float temp = A[i];
					A[i] = A[i+1];
					A[i+1] = temp;
				}
			}
			
			for(int i = end; --i >= start;) {
				if(A[i] > A[i+1]) {
					float temp = A[i];
					A[i] = A[i+1];
					A[i+1] = temp;
				}
			}
		}

		this.pixels = new ArrayList<>(Arrays.asList(A));
	}

	/**
	 * Calculates median value of current local neighborhood
	 * 
	 * @return returns the median of the current local neighborhood
	 */
	public float getMedian() {
		this.sort();
		Float[] pixels = this.getPixels();
		
		// Median is in the middle of sorted list for uneven counts or average of the middle two values for uneven counts
		if(pixels.length % 2 == 0) {
			return (pixels[pixels.length / 2] + pixels[pixels.length / 2 - 1]) / 2;
		}
		
		return pixels[(int) Math.floor(pixels.length / 2)];
	}

}
