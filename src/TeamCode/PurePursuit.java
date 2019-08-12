package TeamCode;

import Simulator.Interface.DcMotor;
import Simulator.Interface.Interface;
import Simulator.Utils.*;

import static Simulator.Interface.Interface.getRobotPos;
import static java.lang.Math.abs;
import static java.lang.Math.min;

public class PurePursuit {

    public static Vector2 getPursuit(Vector2 p1, Vector2 p2,double lookAhead, double r)
    {
        //Calculating angle of line
        Vector2 tP2 = new Vector2(p2.x-p1.y,p2.y-p1.y);
        double angle = (Math.PI*0.5)-Math.atan2(tP2.x,tP2.y);

        //Getting robot pos
        Vector3 rPos = Interface.getRobotPos();

        //Rotating line to be parallel to robot
        Vector2 rP2 = p2.clone();
        rP2 = MyMath.rotatePoint(rPos.getV2(),p2,-angle);
        double p = rP2.x;
        p -= rPos.x;

        /* Pure pursuit math. Demo can be found at https://www.desmos.com/calculator/qfzxv3mvmo */

        //Finding goal point
        Vector2 gPoint = new Vector2(p,Math.sqrt((lookAhead*lookAhead)-(p*p)));

        //Finding d variable(Arc size/x)
        double d = (gPoint.x*0.5)+((gPoint.y*gPoint.y)/(gPoint.x*2));


        //Finding rR intersection(Check desmos)
        double rRx = ((r*r)/(2*d));
        double rRy = Math.sin(Math.acos(r/(2*d)))*r;
        Vector2 rR = new Vector2(rRx,rRy);
        //console.log(rR.x);
        //console.log(rR.y);
        //console.log(rPos.x);
        //console.log(rPos.y);
        rR = MyMath.rotatePoint(rPos.getV2(),rR,angle);

        console.log(p1.x);
        console.log(p1.y);

        //rR.x = Math.abs(rR.x);
        rR.y*=-1;
        rR.x += rPos.x;
        rR.y += rPos.y;

        return rR;
    }







    public static Interval goTo(Vector2 goal, double speed, int slop, DcMotor lf, DcMotor rf, DcMotor lb, DcMotor rb)
    {

        Vector3 rPosO = getRobotPos();
        Vector2 goalN = new Vector2(0,0);
        //Converting goal to vehicle cords
        goalN.x = goal.x- rPosO.x;
        goalN.y = goal.y- rPosO.y;
        //Getting original dist
        double distO = goal.getLength();

        //Interval for pos checking
        Interval fInterval = new Interval((Object)->{
            Vector3 rPos = getRobotPos();
            Vector2 sPos = new Vector2(rPos.x,rPos.y);
            goalN.x = goal.x- rPos.x;
            goalN.y = goal.y- rPos.y;
            //Getting dist between robot and goal
            double dist = goalN.getLength();


            if(true)
            {
                //Turns off motors and stops interval when target reached;
                //Normalizing to turn into valid vector(Good practice)
                goalN.normalize();
                //console.log(rPos.x);
                //console.log(rPos.y);

                //Rotating goal vector to account for for robot rotation
                Vector2 nRP = MyMath.rotatePoint(new Vector2(0,0),goalN,-rPos.r);

                goalN.y = nRP.y;
                //goalN.y = 0;
                //Calculates speed based on dist
                //double speed = Math.min(((1.3/(2*Math.pow((Math.max(Math.signum(distO)*4-dist,0)/(Math.signum(distO)*4+0.001))+0.001,4)+1))-0.3)*Math.signum((dist/(Math.signum(distO)*4+0.001)+0.001)),1);//(0.5-(distT.getLength()/dist)*0.5)+0.5;
                //Creates multipliers to Maximize motor power
                double turnMultiplierUnlimited = abs(goalN.x + goalN.y);
                double turnMultiplier = min(turnMultiplierUnlimited, 1) / turnMultiplierUnlimited;
                //Sets the motor powers
                lf.setPower(speed*turnMultiplier*(goalN.x-goalN.y));
                rf.setPower(speed*turnMultiplier*(goalN.x+goalN.y));
                lb.setPower(speed*turnMultiplier*(goalN.x+goalN.y));
                rb.setPower(speed*turnMultiplier*(goalN.x-goalN.y));

                return 1;
            }
            else
            {
                //Calculates turn speed
                double turnAmount = -Math.atan2(goalN.y,goalN.x);
                double transR = (Math.abs(rPos.r-Math.PI)%(Math.PI*2))-Math.PI;
                double turnDelta = ((transR-turnAmount)%Math.PI);
                double turnSpeed = (turnDelta/(Math.PI*2));
                //Applies smoothing function to turnSpeed
                turnSpeed = (5/(Math.pow(-0.835*(1-turnSpeed),8)+1)-4)*Math.signum(turnSpeed);
                turnSpeed = 0;
                //Normalizing to turn into valid vector(Good practice)
                goalN.normalize();

                //Rotating goal vector to account for for robot rotation
                Vector2 nRP = MyMath.rotatePoint(new Vector2(0,0),goalN,-rPos.r);
                goalN.x = nRP.x;
                goalN.y = nRP.y;

                //Calculates speed based on dist
                //double speed = Math.min(((1.3/(2*Math.pow((Math.max(Math.signum(distO)*4-dist,0)/(Math.signum(distO)*4+0.001))+0.001,4)+1))-0.3)*Math.signum((dist/(Math.signum(distO)*4+0.001)+0.001)),1);//(0.5-(distT.getLength()/dist)*0.5)+0.5;
                //Creates multipliers to Maximize motor power
                double turnMultiplierUnlimited = abs(goalN.x + goalN.y+turnSpeed);
                double turnMultiplier = min(turnMultiplierUnlimited, 1) / turnMultiplierUnlimited;
                //Sets the motor powers
                lf.setPower(speed*turnMultiplier*(goalN.x-goalN.y+turnSpeed));
                rf.setPower(speed*turnMultiplier*(goalN.x+goalN.y-turnSpeed));
                lb.setPower(speed*turnMultiplier*(goalN.x+goalN.y+turnSpeed));
                rb.setPower(speed*turnMultiplier*(goalN.x-goalN.y-turnSpeed));
                return 0;
            }
        },50);
        fInterval.start();
        return fInterval;
    }
}
