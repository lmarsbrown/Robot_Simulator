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
    public double speed = 0;

    public Rowboat(DcMotor lf,DcMotor rf,DcMotor lb,DcMotor rb, double lookAhead,double speed)
    {
        this.lf = lf;
        this.rf = rf;
        this.lb = lb;
        this.rb = rb;
        this.lookAhead = lookAhead;
        this.speed = speed;
    }

    public void addPoint(double x, double y)
    {
        points.add(new Vector2(x,y));
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
            Vector3 rPos = Interface.getRobotPos();
            Vector2 latestPursuitVector = null;
            //Gets distance from end point
            Vector2 LGDist = new Vector2(points.get(points.size()-1).x-rPos.x,points.get(points.size()-1).y-rPos.y);
            //Checks if end point is less than look ahead dist away
            if(Math.abs(LGDist.getLength())>lookAhead) {
                //Cycles through all goal points checking if vaalid
                for (int i = 1; i < points.size(); i++) {
                    Vector2 pursuitVector = getPursuit(points.get(i - 1), points.get(i), lookAhead, false);
                    if (!Double.isNaN(pursuitVector.x) && !Double.isNaN(pursuitVector.y)) {
                        latestPursuitVector = pursuitVector;
                    }
                }
                //Drives towards most recent goal pint  if not on pat
                if (latestPursuitVector == null) {
                    latestPursuitVector = new Vector2(points.get(lastGoal).x - rPos.x, points.get(lastGoal).y - rPos.y);//getPursuit(Interface.getRobotPos().getV2(),points.get(lastGoal),lookAhead,true);
                }
                //Moving robot along travel vector
                latestPursuitVector.normalize();
                setVec(latestPursuitVector, speed);
            }
            else
            {
                //If robot withing lookahead dist of end point drives towards end directly
                if(LGDist.getLength()>5)
                {
                    LGDist.normalize();
                    setVec(LGDist, speed);
                    return 0;
                }
                else
                {
                    stop();
                    return 1;
                }
            }
            return 0;
        },1);
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
    public void stop()
    {

        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);
    }

    private Vector2 getPursuit(Vector2 p1, Vector2 p2, double lookAhead,boolean log) {
        //Getting robot pos
        Vector3 rPos = Interface.getRobotPos();

        //Checking to see if line is within reach
        if (Math.hypot(p2.x - rPos.x, p2.y - rPos.y) < lookAhead) {
            return new Vector2(MyMath.NaN, MyMath.NaN);
        }

        //Calculating angle of line
        Vector2 tP2 = new Vector2(p2.x - p1.x, p2.y - p1.y);
        double lAngle = Math.atan2(tP2.y, tP2.x);
        double angle = -(lAngle - 0.5*Math.PI);

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

        /* Pure pursuit math. Demo can be found at https://www.desmos.com/calculator/qfzxv3mvmo */

        //Finding goal point
        Vector2 rR = new Vector2(p, Math.sqrt((lookAhead * lookAhead) - (p * p)));




        rR.y *= Math.signum(rP2.y - rP1.y);

        rR = MyMath.rotatePoint(new Vector2(0, 0), rR, -angle);


        return rR;
    }
}
