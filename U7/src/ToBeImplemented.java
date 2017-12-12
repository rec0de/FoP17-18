/**
 * Just a placeholder class with filled-in methods for circle movement and collision logic
 * 
 * @author Nils Rollshausen
 */
public class ToBeImplemented {

    /**
     * Checks if two circles defined by center point coordinates and common radius intersect
     * 
     * @param x1 x-value of center point of first circle
     * @param y1 y-value of center point of first circle
     * @param x2 x-value of center point of second circle
     * @param y2 y-value of center point of second circle
     * @param r radius of the circles
     * @return true if the circles intersect, false otherwise
     */
    public static boolean intersect(float x1, float y1, float x2, float y2, float r){
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)) < 2*r;
    }

    /**
     * Checks if the distance of two circles is smaller than the given reference distance
     * (Distance being defined as distance of center points)
     * 
     * @param x1 x-value of center point of first circle
     * @param y1 y-value of center point of first circle
     * @param x2 x-value of center point of second circle
     * @param y2 y-value of center point of second circle
     * @param dist distance threshold
     * @return true if the distance of circle 1 and 2 is smaller than the given distance, but larger than zero
     */
    public static boolean isCloser(float x1, float y1, float x2, float y2, float dist) {
    	double realDist = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        return  realDist < dist && realDist > 0;
    }

    /**
     * Calculates position of a given circle when moved by dirX in X direction and dirY in Y direction
     * 
     * @param x X-value of circle center point
     * @param y Y-value of circle center point
     * @param dirX Distance to move in x direction
     * @param dirY Distance to move in y direction
     * @return Array of two floats representing new circle coordinates
     */
    public static float[] moveCircle(float x, float y, float dirX, float dirY) {
        return new float[]{x + dirX, y + dirY};
    }

    /**
     * Updates the movement vector of a given circle if a collision with screen borders is detected in order to make the circle 'bounce off' the border
     * 
     * @param x X-coordinate of circle center point
     * @param y Y-coordinate of circle center point
     * @param radius Radius of the circle
     * @param width Width of the screen area
     * @param height Height of the screen area
     * @param dirX Current movement of circle in x direction
     * @param dirY Current movement of circle in y direction
     * @return Array of two floats representing new circle movement
     */
    public static float[] bounceOffWall(float x, float y, float radius, float width, int height, float dirX, float dirY) {
    	boolean collidesX = x < radius || x > width - radius;
    	boolean collidesY = y < radius || y > height - radius;
    	if (collidesX && collidesY)
    		return new float[]{-dirX, -dirY}; // Special case for that sweet dvd screensaver collision :)
    	else if (collidesX)
    		return new float[]{-dirX, dirY};
    	else if (collidesY)
    		return new float[]{dirX, -dirY};
    	else
    		return new float[]{dirX,dirY};
    }

    /**
     * Changes given movement vector to account for 'attraction' of circles.
     * Attraction is applied if the distance between the two center points is larger than the given threshold value.
     * It is assumed that the first coordinate pair corresponds to the circle with the given movement vector
     * 
     * @param x1 X-coordinate of first circle
     * @param y1 Y-coordinate of first circle
     * @param x2 X-coordinate of second circle
     * @param y2 Y-coordinate of second circle
     * @param dirX Current movement of first circle in X direction
     * @param dirY Current movement of first circle in Y direction
     * @param attraction Threshold value for attraction
     * @return New movement vector of first circle after attraction
     */
    public static float[] changeDirection(float x1, float y1, float x2, float y2, float dirX, float dirY, int attraction) {
    	if (Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)) > attraction) {
    		float[] vDist = new float[]{x2 - x1, y2 - y1}; // Contrary to the assignment, direction of the distance vector _does_ matter after all
    		double scale = 0.04 / Math.sqrt(Math.pow(vDist[0], 2) + Math.pow(vDist[1], 2));
    		float[] rawDir = new float[]{(float) (dirX + scale * vDist[0]), (float) (dirY + scale * vDist[1])};
    		scale = 1 / Math.sqrt(Math.pow(rawDir[0], 2) + Math.pow(rawDir[1], 2));
    		return new float[]{(float) (scale * rawDir[0]), (float) (scale * rawDir[1])};
    	}
        return new float[]{dirX,dirY};
    }
}