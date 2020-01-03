package Simulator.Robot;

import Simulator.Utils.Interval;
import Simulator.Utils.Vector2;
import Simulator.Utils.console;

public class Robot
{
    //Initializing all of the robot variables
    public double r  = -1.57;
    public int fSize = 3658;
    public double x  = fSize*0.25;
    public double y  = fSize*0.25;
    public double motorPowerFL = 0;
    public double motorPowerFR = 0;
    public double motorPowerBL = 0;
    public double motorPowerBR = 0;
    public double speed;
    public Robot(Double robotSpeed)
    {
        speed = robotSpeed;
        //Looping the position calculating function on a new thread
        Interval interval = new Interval((Object object)->{calcPos();return 0;},15);
        interval.start();
    }
    public double getXPix(double screenSize)
    {
        return (x/fSize)*screenSize;
    }
    public double getYPix(double screenSize)
    {
        return (y/fSize)*screenSize;
    }
    private void calcPos()
    {
        //Calculates Motor positions
        Vector2 posFL = new Vector2(-228.6+(motorPowerFL*speed*15),+228.6+(motorPowerFL*speed*15));
        Vector2 posFR = new Vector2(+228.6+(motorPowerFR*speed*15),+228.6-(motorPowerFR*speed*15));
        Vector2 posBL = new Vector2(-228.6+(motorPowerBL*speed*15),-228.6-(motorPowerBL*speed*15));
        Vector2 posBR = new Vector2(+228.6+(motorPowerBR*speed*15),-228.6+(motorPowerBR*speed*15));

        //Calculating the robot's new position
        double FV = (posFL.x+posFR.x+posBL.x+posBR.x)*0.25;
        double RV = (posFL.y+posFR.y+posBL.y+posBR.y)*0.25;
        double rVecX = Math.cos(this.r);
        double rVecY = Math.sin(this.r);

        //Moving he robot according to the calculated velocities and rotation vector
        this.x += (((rVecX*FV)+(-rVecY*RV)));
        this.y += (((rVecY*FV)+(rVecX*RV)));

        //Calculates the rotation of each motor
        double roteFL = 0.17722719*(motorPowerFL*speed*15)*-1;
        double roteFR = 0.17722719*(motorPowerFR*speed*15);
        double roteBL = 0.17722719*(motorPowerBL*speed*15)*-1;
        double roteBR = 0.17722719*(motorPowerBR*speed*15);



        double turnAmount = 0.25*(roteFL+roteFR+roteBL+roteBR);



        r+= Math.toRadians(turnAmount);
    }
}
