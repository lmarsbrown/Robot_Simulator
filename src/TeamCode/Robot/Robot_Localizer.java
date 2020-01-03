package TeamCode.Robot;

import Simulator.Interface.DcMotor;
import Simulator.Interface.Interface;
import Simulator.Utils.Lambda;
import Simulator.Utils.Transform;
import Simulator.Utils.Vector3;
import Simulator.Utils.console;

public class Robot_Localizer {
    public Transform pos = new Transform(0,0,0);
    public Lambda onLocalize;
    public Robot_Localizer(DcMotor encLeft, DcMotor encRight, DcMotor encSide, double calibrationConst) {

    }
    public void relocalize()
    {
        Vector3 posV = Interface.getRobotPos();
        if(onLocalize != null){onLocalize.call(0);}
        pos.x = -posV.y;
        pos.y = -posV.x;
        pos.r = posV.r;
    }
}
