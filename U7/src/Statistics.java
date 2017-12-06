import processing.core.PApplet;

import java.util.LinkedList;
import java.util.List;

public class Statistics {

    private PApplet applet;
    private int from = 0;
    private List<Circle> circles;
    private final float[] corsys;
    private ScrollBar scrollBar;

    public Statistics(PApplet applet, LinkedList<Circle> circles) {
        this.applet = applet;
        this.circles = circles;
        this.corsys = new float[] {applet.width*0.75f,0};
        scrollBar = new ScrollBar(applet.width-10,0,10,applet.height,applet);
    }

    public void show(){
        scrollBar.show(circles.size());
        applet.pushMatrix();
        applet.translate(corsys[0],corsys[1]);
        for (int i = 0; i < circles.size()-from; i++) {
            Circle c = circles.get(i+from);
            int[] color = c.getColor();
            applet.fill(color[0], color[1], color[2]);
            applet.ellipse(150, i * 50 + 20, 25, 25);
            applet.fill(0);
            applet.text(String.format("Circle %d", i + 1 + from), 10, i * 50 + 20);
            applet.text(String.format("Intersects with: %s", intersect(c)), 10, i * 50 + 40);
        }
        applet.popMatrix();
    }

    public ScrollBar getScrollBar() {
        return scrollBar;
    }

    public void scroll(int count){
        from += count;
        if (from < 0)
            from = 0;
        if (from >= circles.size())
            from = circles.size()-1;
        scrollBar.setCount(from);
    }

    private String intersect(Circle c) {
        StringBuilder builder = new StringBuilder(10);
        for (int i = 0; i < circles.size(); i++) {
            Circle tmp = circles.get(i);
            if (tmp != c) {
                if (ToBeImplemented.intersect(c.getCenter().x,c.getCenter().y,tmp.getCenter().x,tmp.getCenter().y,c.getRadius())) {
                    builder.append(i+1).append(",");
                }
            }
        }
        if (builder.length() >= 2)
            builder.replace(builder.length()-1,builder.length(),"");
        return builder.toString();
    }
}
