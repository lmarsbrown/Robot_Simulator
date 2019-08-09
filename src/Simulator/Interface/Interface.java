package Simulator.Interface;

import Simulator.Main.Main;
import Simulator.Utils.*;

import java.awt.*;

public class Interface {
    private static DcMotor lF;
    private static DcMotor rF;
    private static DcMotor lB;
    private static DcMotor rB;


    protected static void addMotor(DcMotor motor)
    {
        //Creates cars for each motor incase needed later
        switch (motor.motor) {
            case ("front_left"): {
                lF = motor;
                break;
            }
            case ("back_left"): {
                lB = motor;
                break;
            }
            case ("front_right"): {
                rF = motor;
                break;
            }
            case ("back_right"): {
                rB = motor;
                break;
            }
        }
    }
    protected static void setPower(double power,DcMotor motor)
    {
        //Sets motor powers
        switch (motor.motor) {
            case ("front_left"): {
                Main.robot.motorPowerFR = power;
                break;
            }
            case ("back_left"): {
                Main.robot.motorPowerBR = power;
                break;
            }
            case ("front_right"): {
                Main.robot.motorPowerFL = power;
                break;
            }
            case ("back_right"): {
                Main.robot.motorPowerBL = power;
                break;
            }
        }
    }
    public static Vector3 getRobotPos()
    {
        return new Vector3(Main.robot.x,Main.robot.y,Main.robot.r);
    }
    public static Vector2 getMouse()
    {
        Point pos = Main.frame.getMousePosition();
        return new Vector2(pos.x,pos.y);
    }
}
