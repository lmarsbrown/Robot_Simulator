package TeamCode;
import Simulator.Interface.*;
import Simulator.Main.*;
import Simulator.Utils.*;

import java.util.Vector;

import static Simulator.Interface.Interface.getRobotPos;
import static java.lang.Math.abs;
import static java.lang.Math.min;


public class MainOpMode extends OpMode{
    DcMotor lf;
    DcMotor rf;
    DcMotor lb;
    DcMotor rb;
    @Override
    public void init()
    {
        lf = hardwareMap.get(DcMotor.class,"front_left");
        rf = hardwareMap.get(DcMotor.class,"front_right");
        lb = hardwareMap.get(DcMotor.class,"back_left");
        rb = hardwareMap.get(DcMotor.class,"back_right");
        console.log("init");
    }
    @Override
    public void start()
    {
        goTo(new Vector2(3658,3658),0.7,5);
    }


    public Interval goTo( Vector2 goal,double speed, int slop)
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


            if(dist<slop)
            {
                //Turns off motors and stops interval when target reached;
                lf.setPower(0);
                rf.setPower(0);
                lb.setPower(0);
                rb.setPower(0);
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
