import processing.core.PApplet;

public class ScrollBar {
    private final PApplet applet;
    private int height;
    private int width;
    private int x,y;
    private int count;
    private float barHeight;
    private int barColor = 52;
    private int scrollBarColor = 150;

    public ScrollBar(int x, int y, int width, int height, PApplet applet) {
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
        this.applet = applet;
    }

    private float f(int t){
        float c = height-10;
        double k = Math.log((height-60)/c)/-2;
        return (float) (10+c*Math.pow(Math.E,-k*t));
    }

    public void show(int elementCount){
        if (elementCount > 0) {
            barHeight = f(elementCount);
            float step = PApplet.map(count+1, 1, elementCount, 0, height-barHeight);
            applet.fill(barColor);
            applet.rect(x, y, width, height);
            applet.fill(scrollBarColor);
            applet.rect(x, step, width, barHeight,PApplet.radians(260));
            applet.noFill();
        }
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void highlight(boolean highlight) {
        if (highlight)
            scrollBarColor = 200;
        else
            scrollBarColor = 150;
    }
}
