package Robot;

public class Robot{
    public int r  = 1;
    public int size = 3658;
    public double x  = size*0.5;
    public double y  = size*0.5;
    public double motorPowerFL = 0;
    public double motorPowerFR = 0;
    public double motorPowerBL = 0;
    public double motorPowerBR = 0;
    public Robot()
    {

    }
    public double getXPix(double screenSize)
    {
        return (x/size)*screenSize;
    }
    public double getYPix(double screenSize)
    {
        return (y/size)*screenSize;
    }

}
