public class ToBeImplemented {

    /**
     *
     */
    public static boolean intersect(float x1, float y1, float x2, float y2, float r){
        return false;
    }

    /**
     *
     */
    public static boolean isCloser(float x1, float y1, float x2, float y2, float dist) {
        return false;
    }

    /**
     * .
     */
    public static float[] moveCircle(float x, float y, float dirX, float dirY) {
        return new float[]{x,y};
    }

    /**
     * 
     */
    public static float[] bounceOffWall(float x, float y, float radius, float width, int height, float dirX, float dirY) {
        return new float[]{dirX,dirY};
    }

    /**
     * 
     */
    public static float[] changeDirection(float x1, float y1, float x2, float y2, float dirX, float dirY, int attraction) {
        return new float[]{dirX,dirY};
    }
}
