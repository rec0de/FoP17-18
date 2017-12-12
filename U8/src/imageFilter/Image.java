package imageFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * 
 * @author Lukas Roehrig, Florian Kadner
 * @version 1.0 20170913
 */
public class Image {

	private String magicNumber;
	private float[][] data;
	private float max;
	private int width;
	private int height;

	public String getMagicNumber() {
		return magicNumber;
	}

	public float getMax() {
		return max;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public float[][] getData() {
		return data;
	}


	public float getPixel(int height, int width) {
		return data[height][width];
	}


	public void setPixel(float value, int height, int width) {
		if (value > getMax())
			value = getMax();
		if (value < 0)
			value = 0;

		data[height][width] = value;
	}

	private Image(String magicNumber, int height, int width, float max) {
		this.magicNumber = magicNumber;
		this.width = width;
		this.height = height;
		this.max = max;
		data = new float[height][width];
	}

	/**
	 * this method is used to read in an image in pgm format
	 * @param path the path were the image is located
	 * @throws IOException
	 */
	public Image(String path) throws IOException {
		File imageFile = new File(path);
		BufferedReader reader = new BufferedReader(new FileReader(imageFile));

		String line = null;
		int lineCounter = 0;
		int heightCounter = 0;
		int widthCounter = 0;
		while ((line = reader.readLine()) != null) {
			if (line.charAt(0) == '#')
				continue;
			if (lineCounter == 0) {
				magicNumber = line;
			}

			if (lineCounter == 1) {
				String[] split = line.split(" ");
				width = Integer.valueOf(split[0]);
				height = Integer.valueOf(split[1]);
				data = new float[height][width];
			}

			if (lineCounter == 2) {
				max = Float.valueOf(line);
			}

			if (lineCounter > 2) {
				String[] split = line.split("\\s+");
				for (int i = 0; i < split.length; i++) {
					if (widthCounter == getWidth()) {
						widthCounter = 0;
						heightCounter++;
					}
					setPixel(Float.valueOf(split[i]), heightCounter, widthCounter);

					widthCounter++;
				}
			}

			lineCounter++;
		}

		reader.close();
	}

	/**
	 * this method save a pgm image to a specific path
	 * @param path the path were the image should be saved
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void save(String path) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(path, "ASCII");
		writer.println(getMagicNumber());
		writer.println(String.valueOf(getWidth()) + " " + String.valueOf(getHeight()));
		writer.println(String.valueOf((int) getMax()));
		for (int i = 0; i < getHeight(); i++) {
			if (i != 0)
				writer.println();

			for (int j = 0; j < getWidth(); j++) {
				writer.write(String.valueOf((int) getPixel(i, j)));
				if (j < getWidth() - 1) {
					writer.write(" ");
				}
			}
		}

		writer.close();
	}

	/**
	 * this method is used to clone an image
	 */
	public Image clone() {
		Image clone = new Image(getMagicNumber(), getHeight(), getWidth(), getMax());
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				clone.setPixel(getPixel(i, j), i, j);
			}
		}

		return clone;
	}

}
