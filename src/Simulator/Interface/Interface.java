package Simulator.Interface;

import Simulator.Main.Main;

import java.util.List;

public class Interface {
    private static DcMotor lF;
    private static DcMotor rF;
    private static DcMotor lB;
    private static DcMotor rB;


    protected static void addMotor(DcMotor motor)
    {
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
        switch (motor.motor) {
            case ("front_left"): {
                Main.robot.motorPowerFL = power;
                break;
            }
            case ("back_left"): {
                Main.robot.motorPowerBL = power;
                break;
            }
            case ("front_right"): {
                Main.robot.motorPowerFR = power;
                break;
            }
            case ("back_right"): {
                Main.robot.motorPowerBR = power;
                break;
            }
        }
    }
}
