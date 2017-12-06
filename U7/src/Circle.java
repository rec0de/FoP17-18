import processing.core.PApplet;
import processing.core.PVector;

import java.util.Random;

/**
 * Created by oliver on 31.07.17.
 */
public class Circle {
    private PApplet applet;
    private int attraction;
    private PVector center;
    private PVector dir;
    private float diameter;
    private int[] color;

    public float getRadius() {
        return diameter/2;
    }

    public Circle(PApplet applet, PVector center, float diameter) {
        this.applet = applet;
        this.center = center;
        this.diameter = diameter;

        Random random = new Random();
        attraction = random.nextInt(251)+(int) diameter/2;
        dir = PVector.random2D();
        dir.normalize();
        color = new int[] {random.nextInt(256),random.nextInt(256),random.nextInt(256)};
    }

    public int[] getColor() {
        return color;
    }

    public void show(){
        applet.fill(color[0],color[1],color[2]);;
        applet.stroke(0);
        applet.ellipse(center.x,center.y, diameter, diameter);
        applet.noFill();
        applet.strokeWeight(5);
        PVector tmp = PVector.add(center,PVector.mult(dir, diameter /2));
        applet.line(center.x,center.y,tmp.x,tmp.y);
        applet.strokeWeight(1);
        applet.fill(0);
    }

    public void update(PVector center1, PVector center2){
        float[] direction = ToBeImplemented.changeDirection(center1.x,center1.y,center2.x,center2.y,dir.x,dir.y,attraction);
        dir.set(direction[0],direction[1]);
    }

    public PVector getCenter() {
        return center;
    }

    public void move(){
        float[] movedCenter = ToBeImplemented.moveCircle(center.x,center.y,dir.x,dir.y);
        center.set(movedCenter[0],movedCenter[1]);
        float[] bouncedDir = ToBeImplemented.bounceOffWall(center.x,center.y,diameter/2,applet.width*0.75f,applet.height,dir.x,dir.y);
        dir.set(bouncedDir[0],bouncedDir[1]);
    }

    public void move(PVector pVector) {
        dir = dir.add(pVector).normalize();
    }
}
