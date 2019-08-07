package Simulator.Interface;

public class hardwareMap {
    public DcMotor get(Object object, String name)
    {
        switch (name){
            case("front_left"):
            case("back_left"):
            case("front_right"):
            case("back_right"): {
                return new DcMotor(name);
            }
            default:
            {
                return null;
            }

        }
    }
}
