package TeamCode;

import Simulator.Interface.DcMotor;
import Simulator.Interface.Interface;
import Simulator.Utils.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class Rowboat {
    //Setting up motor and misc variables
    List<Vector2> points  = new ArrayList<>();
    public DcMotor lf;
    public DcMotor rf;
    public DcMotor lb;
    public DcMotor rb;
    public int lastGoal = 0;
    public double lookAhead;

    public Rowboat(DcMotor lf,DcMotor rf,DcMotor lb,DcMotor rb, double lookAhead)
    {
        this.lf = lf;
        this.rf = rf;
        this.lb = lb;
        this.lb = lb;
        this.lookAhead = lookAhead;
    }

    public void addPoint(Vector2 point)
    {
        points.add(point);
    }
    public void removePoint(int index)
    {
        points.remove(index);
    }
    public Interval run()
    {
        //Creates interval for following path
        Interval out = new Interval((Object obj)->{
            //Deciding which line to follow(Also getting pursuit vector)
            Vector2 latestPursuitVector = null;
            for(int i = 1; i < points.size();i++)
            {
                Vector2 pursuitVector = getPursuit(points.get(i-1),points.get(i),lookAhead);
                if(pursuitVector.x != MyMath.NaN && pursuitVector.y != MyMath.NaN)
                {
                    latestPursuitVector = pursuitVector;
                }
            }
            if(latestPursuitVector == null)
            {
                latestPursuitVector = getPursuit(Interface.getRobotPos().getV2(),points.get(lastGoal),lookAhead);
            }
            setVec(latestPursuitVector,0.7);

            return 0;
        },20);
        return out;
    }
    private void setVec(Vector2 dir, double speed)
    {
        double turnMultiplierUnlimited = abs(dir.x + dir.y);
        double turnMultiplier = min(turnMultiplierUnlimited, 1) / turnMultiplierUnlimited;
        //Sets the motor powers
        lf.setPower(speed*turnMultiplier*(dir.x-dir.y));
        rf.setPower(speed*turnMultiplier*(dir.x+dir.y));
        lb.setPower(speed*turnMultiplier*(dir.x+dir.y));
        rb.setPower(speed*turnMultiplier*(dir.x-dir.y));
    }

    private Vector2 getPursuit(Vector2 p1, Vector2 p2, double lookAhead) {
        //Getting robot pos
        Vector3 rPos = Interface.getRobotPos();

        //Checking to see if line is within reach
        if (Math.hypot(p2.x - rPos.x, p2.y - rPos.y) < lookAhead) {
            return new Vector2(MyMath.NaN, MyMath.NaN);
        }

        //Calculating angle of line
        Vector2 tP2 = new Vector2(p2.x - p1.x, p2.y - p1.y);
        double lAngle = Math.atan2(tP2.y, tP2.x);
        double angle = (lAngle + Math.PI);
        console.log(angle);

        //Rotating line to be parallel to robot
        Vector2 rP2 = p2.clone();
        Vector2 rP1 = p1.clone();
        rP2.x -= rPos.x;
        rP2.y -= rPos.y;
        rP1.x -= rPos.x;
        rP1.y -= rPos.y;


        rP2 = MyMath.rotatePoint(new Vector2(0, 0), rP2, angle);
        rP1 = MyMath.rotatePoint(new Vector2(0, 0), rP1, angle);

        double p = rP2.x;
        console.log(p);

        /* Pure pursuit math. Demo can be found at https://www.desmos.com/calculator/qfzxv3mvmo */

        //Finding goal point
        Vector2 rR = new Vector2(p, Math.sqrt((lookAhead * lookAhead) - (p * p)));



        rR.y *= Math.signum(rP2.y - rP1.y);

        rR = MyMath.rotatePoint(new Vector2(0, 0), rR, -angle);


        return rR;
    }
}
