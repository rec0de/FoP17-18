package imageFilter;

/**
 * 
 * @author Florian Kadner, Nils Rollshausen
 * @version 2.0 20171213
 */
public class Filter {

	public Filter() {

	}

	/**
	 * This method implements a linear filter with discrete convolution
	 * 
	 * @param image
	 *            the image which should be filtered
	 * @param kernel
	 *            the kernel used for the linear filter
	 * @return the filtered image
	 */
	public Image linearFilter(Image image, float[][] kernel) {

		Image filtered = image.clone();
		float maxBrightness = image.getMax();
		float[][] pixels = image.getData();

		// Loop over every pixel
		for(int i = 0; i < pixels.length; i++) {
			for(int j = 0; j < pixels[i].length; j++) {
				// Apply kernel
				float newPixelBrightness = 0;
				for(int k = 0; k < kernel.length; k++) {
					for(int l = 0; l < kernel[k].length; l++) {
						int effectiveX = Math.max(Math.min(j + (l - (int) Math.floor(kernel[k].length / 2)), pixels[i].length - 1), 0);
						int effectiveY = Math.max(Math.min(i + (k - (int) Math.floor(kernel.length / 2)), pixels.length - 1), 0);
						newPixelBrightness += pixels[effectiveY][effectiveX] * kernel[k][l];
					}
				}
				
				filtered.setPixel(Math.max(Math.min((float) Math.floor(newPixelBrightness), maxBrightness), 0), i, j);
			}
		}

		return filtered;
	}

	/**
	 * This method implements a nonlinear filter with median filter
	 * 
	 * @param image
	 *            the image which should be filtered
	 * @param filtersize
	 *            the size of the median filter
	 * @return the filtered image
	 */
	public Image nonLinearFilterMedian(Image image, int filtersize) {

		Image filtered = image.clone();
		float maxBrightness = image.getMax();
		float[][] pixels = image.getData();

		// Loop over every pixel
		for(int i = 0; i < pixels.length; i++) {
			for(int j = 0; j < pixels[i].length; j++) {
				// Apply filter
				LocalNeighbours neighbors = new LocalNeighbours(); // Mixing AE and BE spelling... I know I know
				for(int k = (int) Math.ceil(- filtersize / 2); k <= Math.floor(filtersize / 2); k++) {
					for(int l = (int) Math.ceil(- filtersize / 2); l <= Math.floor(filtersize / 2); l++) {
						int effectiveX = Math.max(Math.min(j + l, pixels[i].length - 1), 0);
						int effectiveY = Math.max(Math.min(i + k, pixels.length - 1), 0);
						neighbors.addPixel(pixels[effectiveY][effectiveX]);
					}
				}
				filtered.setPixel(Math.max(Math.min((float) Math.floor(neighbors.getMedian()), maxBrightness), 0), i, j);
			}
		}

		return filtered;
	}

}
