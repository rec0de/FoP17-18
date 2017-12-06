import processing.core.PApplet;
import processing.core.PVector;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by oliver on 31.07.17.
 */
public class CircleInMotion extends PApplet {
    private LinkedList<Circle> circles;
    private Circle controlled = null;
    private Statistics stats;
    private boolean pressed = false;
    private int oldMouseY;
    private Robot robot;

    @Override
    public void settings() {
        size(800, 600);

    }

    @Override
    public void setup() {
        background(0);
        //blendMode(MULTIPLY);
        circles = new LinkedList<Circle>();
        stats = new Statistics(this,circles);
        circles.add(new Circle(this, new PVector(100, 100), 50));
        circles.add(new Circle(this, new PVector(200, 200), 50));
    }

    @Override
    public void draw() {
        surface.setTitle(String.valueOf(frameRate));
        if (pressed){
            int count = (int) Math.signum(mouseY-oldMouseY);
            stats.scroll(count);
        }
        if (mouseX > width-10){
            stats.getScrollBar().highlight(true);
        } else {
            stats.getScrollBar().highlight(false);
        }
        oldMouseY = mouseY;
        background(255);
        line(width*0.75f,0,width*0.75f,height);
        for (Circle c : circles) {
            c.move();
            c.show();
        }
        stats.show();
        connections();
    }

    private void connections() {
        LinkedList<Circle> used = new LinkedList<>();
        for (Circle c : circles) {
            PVector center = c.getCenter();
            Circle near = nearest(center);
            if (near != null) {
                PVector center2 = near.getCenter();
                c.update(center, center2);
                if (!used.contains(c)) {
                    used.add(c);
                    strokeWeight(2);
                    arrow(center.x, center.y, center2.x, center2.y);
                    stroke(0);
                }
            }
        }
    }

    private void arrow(float x, float y, float x1, float y1) {
        line(x,y,x1,y1);
        PVector vec = PVector.sub(new PVector(x,y),new PVector(x1,y1));
        vec.setMag(10);
        vec.rotate(radians(30));
        line(x1,y1,x1+vec.x,y1+vec.y);
        vec.rotate(radians(-60));
        line(x1,y1,x1+vec.x,y1+vec.y);
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if (mouseX < width*0.75f-50) {
            if (event.getButton() == 37)
                circles.add(new Circle(this, new PVector(mouseX, mouseY), 50));
            if (event.getButton() == 39)
                removeCircleNear(new PVector(mouseX, mouseY));
            if (event.getButton() == 3)
                controlled = nearest(new PVector(mouseX, mouseY));
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
        if (mouseX > width-10){
            pressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        pressed = false;
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        stats.scroll(event.getCount());
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (controlled == null)
            return;
        else if (event.getKeyCode() == UP)
            controlled.move(new PVector(0,-1));
        else if (event.getKeyCode() == DOWN)
            controlled.move(new PVector(0,1));
        else if (event.getKeyCode() == LEFT)
            controlled.move(new PVector(-1,0));
        else if (event.getKeyCode() == RIGHT)
            controlled.move(new PVector(1,0));
    }

    private Circle nearest(PVector pVector) {
        Circle circle = null;
        float abs = Float.MAX_VALUE;
        for (Circle c : circles) {
            if (ToBeImplemented.isCloser(c.getCenter().x,c.getCenter().y,pVector.x,pVector.y,abs)) {
                circle = c;
                abs = c.getCenter().dist(pVector);
            }
        }
        return circle;
    }

    private void removeCircleNear(PVector pVector) {
        circles.remove(nearest(pVector));
    }

}
