package TeamCode;
import Simulator.Interface.DcMotor;
import Simulator.Interface.OpMode;
import Simulator.Interface.hardwareMap;
import Simulator.Utils.Interval;
import Simulator.Utils.Transform;
import Simulator.Utils.console;
import TeamCode.Robot.Robot_Controller;
import TeamCode.Robot.Robot_Localizer;


public class MainOpMode extends OpMode{

    private Robot_Localizer rowboat;
    private Robot_Controller control;

    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;

    @Override
    public void init() {
        leftFront           = hardwareMap.get(DcMotor.class, "front_left");
        rightFront          = hardwareMap.get(DcMotor.class, "front_right");
        leftBack            = hardwareMap.get(DcMotor.class, "back_left");
        rightBack           = hardwareMap.get(DcMotor.class, "back_right");

        rowboat = new Robot_Localizer(leftBack,rightFront,rightBack,0.958);
        control = new Robot_Controller(rightFront,leftFront,rightBack,leftBack,rowboat);
    }
    @Override
    public void start()
    {
        /*control.gotoPoint(new Transform(-1500,-1500,0),true,0.25,0.75,20,(Object a)->{
            return 0;
        });
        rowboat.relocalize();*/
    }

    @Override
    public void loop() {
        rowboat.relocalize();
        Transform dir = new Transform(0,1,0);
        dir.rotate(new Transform(0,0,0),-rowboat.pos.r);
        control.setVec(dir,0.5);
    }

}
