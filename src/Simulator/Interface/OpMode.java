package Simulator.Interface;

import Simulator.Utils.Interval;

public class OpMode {
    public void run()
    {
        init();
        start();
        Interval loop = new Interval((Object)->{loop();return 0;},12);
    }
    public void init()
    {

    }
    public void start()
    {

    }
    public void loop()
    {

    }
}
