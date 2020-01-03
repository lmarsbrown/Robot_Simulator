package Simulator.Interface;

import Simulator.Utils.Interval;
import Simulator.Utils.console;

public class OpMode {
    public void run()
    {
        init();
        start();
        Interval loop = new Interval((Object)->{loop();return 0;},12);
        loop.start();
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
