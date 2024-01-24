// This class uses the Color class, which is part of a package called awt,
// which is part of Java's standard class library.
import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] imageOut;

		// Tests the horizontal flipping of an image:
		System.out.println();
	
		
		//// Write here whatever code you need in order to test your work.
		//// You can reuse / overide the contents of the imageOut array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		for (int i = 0; i < numRows; i++) {
        	for (int j = 0; j < numCols; j++) {
					int red = in.readInt();
					int green = in.readInt();
					int blue = in.readInt();
					image[i][j] = new Color(red, green, blue);
				}
        	}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		for (int i = 0; i < image.length; i++){
			for(int j = 0; j < image[0].length;j++){
				print (image[i][j]);
			}
			System.out.println();
		}
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		Color [][] flippedHorizontal = new Color[image.length][image[0].length];
		for (int i = 0; i < flippedHorizontal.length; i++){
			for (int j = 0; j < flippedHorizontal[0].length; j++){	
				flippedHorizontal[i][j] = image [i][image[0].length-j];
			}
		}
		return flippedHorizontal;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
		Color [][]  flippedVertical = new Color[image.length][image[0].length];
		for (int i = 0; i <  flippedVertical.length; i++){
			for (int j = 0; j <  flippedVertical[0].length; j++){	
				flippedVertical[i][j] = image [image.length-1-i][j];
			}
		}
		return  flippedVertical;
	}
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	public static Color luminance(Color pixel) {
		double r = pixel.getRed() * 0.299;
		double g = pixel.getGreen() * 0.587;
		double b = pixel.getBlue() * 0.114;
		int lum = (int) (r+g+b);
		Color luminace = new Color (lum,lum,lum);
		return luminace;
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		Color [][]  grayScaled = new Color[image.length][image[0].length];
		for (int i = 0; i <  grayScaled.length; i++){
			for (int j = 0; j <  grayScaled[0].length; j++){	
				grayScaled [i][j] = luminance (image[i][j]);
			}
		}

		return grayScaled;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		Color [][]  Scaled = new Color[height][width];
		for (int i = 0; i <  height; i++){
			for (int j = 0; j <  width; j++){	
				int row = (int)((double)i * ((double)image.length/(double)height));
				int column = (int)((double)j *((double)image[0].length/(double)width));
				Scaled[i][j] = image [row][column];
			}
		}
		
		return Scaled;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		int red1 = c1.getRed(); 
		int green1 = c1.getGreen();
		int blue1 = c1.getBlue(); 
		int red2 = c2.getRed(); 
		int green2 = c2.getGreen();
		int blue2 = c2.getBlue(); 

		int red = (int)(alpha*red1 + (1-alpha)*red2);
		int green =  (int)(alpha*green1 + (1-alpha)*green2);
		int blue =  (int)(alpha*blue1 + (1-alpha)*blue2);
		Color blend = new Color(red, green, blue);
		return blend;
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		Color [][]  ImageBlend = new Color[image1.length][image1[0].length];
		for (int i = 0; i <  image1.length; i++){
			for (int j = 0; j <  image1[0].length; j++){	
				ImageBlend [i][j] =  blend(image1[i][j], image2[i][j], alpha);
			}
		}
		return ImageBlend;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		Color [][] morph = new Color[source.length][source[0].length];
		if (source.length!=target.length || source[0].length!=target[0].length)
		{
			target = scaled(target, source.length, source[0].length);
		}
		for (int i = 0; i < n; i++)
		{
			double alpha = (double)((n - i) / (double)n);
			morph = blend (source, target, alpha);
			Runigram.setCanvas(morph);
			Runigram.display(morph);
			StdDraw.pause(500);
		}
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

