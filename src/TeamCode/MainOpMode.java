package TeamCode;
import Simulator.Interface.*;
import Simulator.Utils.console;


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
        lf.setPower(1);
        lb.setPower(1);
    }
}
