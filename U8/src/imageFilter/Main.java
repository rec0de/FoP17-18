package imageFilter;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
	
		// ACHTUNG: Überschreiben Sie nicht die von uns mitgelieferten Bilder, sonst laufen Ihre Tests am Ende nicht mehr.
		
		Image beer = new Image("Beer.pgm");
		Image mandarine = new Image("Mandarine.pgm");
		Image cat = new Image("Cat.pgm");

		Filter filter = new Filter();
		float[][] sharp = { { 0, -1, 0 }, { -1, 5, -1 }, { 0, -1, 0 } };
		float[][] blur = { { 1 * 1 / 16f, 2 * 1 / 16f, 1 * 1 / 16f }, { 2 * 1 / 16f, 4 * 1 / 16f, 2 * 1 / 16f }, { 1 * 1 / 16f, 2 * 1 / 16f, 1 * 1 / 16f } };
		
		//Image mandarine_sharp = filter.nonLinearFilterMedian(cat, 3);
		Image beer_median3 = filter.linearFilter(beer, blur);
		
		//mandarine_sharp.save("mandarine_med3.pgm");
	}

}
