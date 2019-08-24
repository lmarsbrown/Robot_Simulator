package Simulator.Utils;

public class Line {
    Vector2 p1;
    Vector2 p2;

    public Line(Vector2 p1, Vector2 p2)
    {
        this.p1 = p1;
        this.p2 = p2;
    }
    public double getLength()
    {
        return Math.hypot(p1.x-p2.x,p1.y-p2.y);
    }
}

