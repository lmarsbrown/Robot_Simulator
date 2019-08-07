package Simulator.Interface;

import Simulator.Utils.Interval;

public class OpMode {
    public void run()
    {
        init();
        start();
        Interval loop = new Interval((Object)->{loop();},12);
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
