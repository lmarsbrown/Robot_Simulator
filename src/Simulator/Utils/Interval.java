package Simulator.Utils;


public class Interval extends Thread{

    private boolean running = true;
    private Lambda func;
    private int wait = 0;
    public Interval(Lambda function,int delay)
    {
        func = function;
        wait = delay;
    }
    public void run()
    {
        console.log("Thread " + Thread.currentThread().getId() + " is running");
        while(running)
        {
            func.call(null);
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void clear()
    {
        running = false;
    }
}
