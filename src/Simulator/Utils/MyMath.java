package Simulator.Utils;

public class MyMath {
    public static float NaN = 0.0f/0.0f;
    public static Vector2 rotatePoint(Vector2 pivot,Vector2 point,double amount)
    {
        //Creates translated and normalized point
        Vector2 tPoint = new Vector2(point.x-pivot.x,point.y-pivot.y);
        if(tPoint.x != 0&&tPoint.y!=0) {
            double len = tPoint.getLength();
            tPoint.normalize();

            //rotates tPoint and scales it back up
            Vector2 output = new Vector2((tPoint.x * Math.cos(amount) - tPoint.y * Math.sin(amount)) * len, (tPoint.y * Math.cos(amount) + tPoint.x * Math.sin(amount)) * len);
            return output;
        }
        else
        {
            return point;
        }
    }
}
