package Simulator.Interface;

public class DcMotor {
    protected String motor = "";
    protected double mPower = 0;
    public DcMotor(String name)
    {
        motor = name;
        Interface.addMotor(this);
    }

    public void setPower(double power) {
        mPower = power;
        Interface.setPower(power,this);
    }
    public double getPower()
    {
        return mPower;
    }
}
