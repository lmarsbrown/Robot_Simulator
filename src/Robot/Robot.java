package Robot;

import Utils.*;

import java.util.Vector;

public class Robot{
    //Initializing all of the robot variables
    public double r  = 0;
    public int fSize = 3658;
    public double x  = fSize*0.5;
    public double y  = fSize*0.5;
    public double motorPowerFL = 0;
    public double motorPowerFR = 1;
    public double motorPowerBL = 0;
    public double motorPowerBR = 1;
    public double speed;
    public Robot(Double robotSpeed)
    {
        speed = robotSpeed;
        //Looping that position calculating function on a new thread
        Interval interval = new Interval((Object object)->{calcPos();},15);
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
        double roteFL = 0.78740157
                *(motorPowerFL*speed*15)*-1;
        double roteFR = 0.17722719*(motorPowerFR*speed*15);
        double roteBL = 0.17722719*(motorPowerBL*speed*15)*-1;
        double roteBR = 0.17722719*(motorPowerBR*speed*15);

        //Calculating weighted average rotation of each wheel to get pivot point
        double wMulti = 1;
        if((roteFL+roteFR+roteBL+roteBR) != 0)
        {
            wMulti = 1/(roteFL+roteFR+roteBL+roteBR);
        }

        double wFL = roteBR*wMulti;
        double wFR = roteBL*wMulti;
        double wBL = roteFR*wMulti;
        double wBR = roteFL*wMulti;

        double xPivot = (wFL*-228.6+wFR*228.6+wBL*-228.6+wBR*228.6);
        double YPivot = (wFL*228.6+wFR*228.6+wBL*-228.6+wBR*-228.6);
        Vector2 pivot = new Vector2(xPivot,YPivot);

        double turnAmount = 0.25*(roteFL+roteFR+roteBL+roteBR);

        //Turing the robot according to previously calculated turn vars
        double oX = x-pivot.x;
        double oY = y-pivot.y;

        console.log(pivot.x);
        console.log(pivot.y);
        //x = (Math.cos(turnAmount)*oX-Math.sin(turnAmount)*oY)+pivot.x;
        //y = (Math.sin(turnAmount)*oX+Math.cos(turnAmount)*oY)+pivot.y;

        //console.log(turnAmount);

        r+= Math.toRadians(turnAmount);
        //console.log(turnAmount);



        /*WARNING: INVALID CODE!!!*/
        // Calculating the net direction of each sid of the robot

       // double LFV = (motorPowerFL+motorPowerBL)*0.5;
        //double RFV = (motorPowerFR+motorPowerBR)*0.5;
        //double FRV = (motorPowerFL-motorPowerFR)*0.5;
        //double BRV = (motorPowerBR-motorPowerBL)*0.5;
       // double FV = 0;
        //Calculating all of the total velocities and rotation

        //double roteV = 0;

        /*if(Math.signum(LFV)==Math.signum(RFV))
        {
            if(Math.signum(LFV)==1.0)
            {
                FV = Math.min(LFV,RFV);
            }
            else
            {
                FV = 0;
            }
            pivotR = (((Math.abs(LFV-RFV)*speed*15)%1436.3)/1436.3)*360*Math.signum(LFV);
        }
        else
        {
            FV=0;
            if(Math.abs(LFV)>Math.abs(RFV))
            {
                pivotR = (((Math.abs(LFV)*speed*15)%1436.3)/1436.3)*360*Math.signum(LFV);
                realR =  (((Math.abs(RFV))*speed*15%718.15)/718.15)*360*-Math.signum(RFV);
            }
        }*//*
        roteV = ((((LFV-RFV)*speed*15)%1436.3)/1436.3)*360;

        double RV = Math.min(FRV,BRV);
        FV = Math.min(LFV,RFV);
        //Calculating the vector that the robot travel forward on based on the angle
        double rVecX = Math.cos(this.r);
        double rVecY = Math.sin(this.r);

        //Moving he robot according to the calculated velocities and rotation vector
        this.x += (((rVecX*FV)+(-rVecY*RV))*speed*15);
        this.y += (((rVecY*FV)+(rVecX*RV))*speed*15);
        this.r += Math.toRadians(roteV);*/
    }
}
