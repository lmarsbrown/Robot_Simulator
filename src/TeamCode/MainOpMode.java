package TeamCode;
import Simulator.Interface.DcMotor;
import Simulator.Interface.OpMode;
import Simulator.Interface.hardwareMap;
import Simulator.Utils.Interval;
import Simulator.Utils.console;


public class MainOpMode extends OpMode{
    public DcMotor lf;
    public DcMotor rf;
    public DcMotor lb;
    public DcMotor rb;
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
    }

}
