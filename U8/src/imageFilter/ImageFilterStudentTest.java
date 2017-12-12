package imageFilter;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

public class ImageFilterStudentTest {

	Filter filter = new Filter();
	static Image beer;
	static Image cat;
	static Image mandarine;
	static Image cow;
	static float[][] sharpKernel = { { 0, -1, 0 }, { -1, 5, -1 }, { 0, -1, 0 } };
	static float[][] blurKernel = { { 1 * 1 / 16f, 2 * 1 / 16f, 1 * 1 / 16f },
			{ 2 * 1 / 16f, 4 * 1 / 16f, 2 * 1 / 16f }, { 1 * 1 / 16f, 2 * 1 / 16f, 1 * 1 / 16f } };

	@BeforeClass
	public static void loadImagesBeforeClass() throws IOException {
		beer = new Image("Beer.pgm");
		cat = new Image("Cat.pgm");
		mandarine = new Image("Mandarine.pgm");
	}

	@Test
	public void testLinearFilterNormal() {
		Image beer_sharp = filter.linearFilter(beer, sharpKernel);
		Image cat_blur = filter.linearFilter(cat, blurKernel);
		assertEquals(26, beer_sharp.getPixel(10, 10), 0f);
		assertEquals(21,beer_sharp.getPixel(56, 34), 0f);
		assertEquals(140,cat_blur.getPixel(77, 77), 0f);
		assertEquals(93,cat_blur.getPixel(33, 11), 0f);
	}

	@Test
	public void testLinearFilterEdge() {
		Image beer_blur = filter.linearFilter(beer, blurKernel);
		Image cat_sharp = filter.linearFilter(cat, sharpKernel);
		assertEquals(25, beer_blur.getPixel(0, 0), 0f);
		assertEquals(24, beer_blur.getPixel(47, 0), 0f);
		assertEquals(54, cat_sharp.getPixel(cat_sharp.getHeight() - 1, cat_sharp.getWidth() - 1), 0f);
		assertEquals(86, cat_sharp.getPixel(0, cat_sharp.getWidth() - 1), 0f);
	}

	@Test
	public void testLinearFilterPicture() throws IOException {
		Image mandarine_blur_solution = new Image("Mandarine_blur.pgm");
		Image mandarine_blur = filter.linearFilter(mandarine, blurKernel);
		assertArrayEquals(mandarine_blur.getData(),mandarine_blur_solution.getData());
	}

	@Test
	public void testSortingAlgo() {
		LocalNeighbours test_neigh = new LocalNeighbours();
		test_neigh.addPixel(34);
		test_neigh.addPixel(66);
		test_neigh.addPixel(34);
		test_neigh.addPixel(11);
		test_neigh.addPixel(0);
		test_neigh.addPixel(55);
		test_neigh.addPixel(123);
		test_neigh.addPixel(6);
		test_neigh.sort();
		LocalNeighbours sorted = new LocalNeighbours();
		sorted.addPixel(0);
		sorted.addPixel(6);
		sorted.addPixel(11);
		sorted.addPixel(34);
		sorted.addPixel(34);
		sorted.addPixel(55);
		sorted.addPixel(66);
		sorted.addPixel(123);
		assertArrayEquals(sorted.getPixels(),test_neigh.getPixels());
	}

	@Test
	public void testMedian() {
		LocalNeighbours test_neigh_even = new LocalNeighbours();
		test_neigh_even.addPixel(3);
		test_neigh_even.addPixel(5);
		test_neigh_even.addPixel(6);
		test_neigh_even.addPixel(11);
		assertEquals(5.5,test_neigh_even.getMedian(), 0f);

		LocalNeighbours test_neigh_odd = new LocalNeighbours();
		test_neigh_odd.addPixel(1);
		test_neigh_odd.addPixel(2);
		test_neigh_odd.addPixel(3);
		test_neigh_odd.addPixel(5);
		test_neigh_odd.addPixel(5);
		test_neigh_odd.addPixel(5);
		test_neigh_odd.addPixel(7);
		assertEquals(5, test_neigh_odd.getMedian(), 0f);
	}

	@Test
	public void testNonLinearFilterNormal() {
		Image mandarine_median3 = filter.nonLinearFilterMedian(mandarine, 3);
		Image mandarine_median5 = filter.nonLinearFilterMedian(mandarine, 5);
		assertEquals(110, mandarine_median3.getPixel(4, 4), 0f);
		assertEquals(35, mandarine_median3.getPixel(36, 66), 0f);
		assertEquals(34, mandarine_median5.getPixel(22, 55), 0f);
		assertEquals(21, mandarine_median5.getPixel(23, 87), 0f);
	}

	@Test
	public void testNonLinearFilterEdge() {
		Image cat_median3 = filter.nonLinearFilterMedian(cat, 3);
		assertEquals(110, cat_median3.getPixel(0, 0), 0f);
		assertEquals(161, cat_median3.getPixel(cat_median3.getHeight() - 1, 0), 0f);
		assertEquals(111, cat_median3.getPixel(cat_median3.getHeight() - 1, cat_median3.getWidth() -1), 0f);
	}

	@Test
	public void testNonLinearFilterPicture() throws IOException {
		Image beer_median5_solution = new Image("Beer_median5.pgm");
		Image beer_median5 = filter.nonLinearFilterMedian(beer, 5);
		assertArrayEquals(beer_median5.getData(), beer_median5_solution.getData());
	}

}
