package TeamCode;
import Simulator.Interface.*;
import Simulator.Utils.*;

import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Math.abs;
import static java.lang.Math.min;


public class MainOpMode extends OpMode{
    public DcMotor lf;
    public DcMotor rf;
    public DcMotor lb;
    public DcMotor rb;
    private Interval rowPath;

    Rowboat rowboat;
    @Override
    public void init()
    {
        lf = hardwareMap.get(DcMotor.class,"fr ont_left");
        rf = hardwareMap.get(DcMotor.class,"front_right");
        lb = hardwareMap.get(DcMotor.class,"back_left");
        rb = hardwareMap.get(DcMotor.class,"back_right");
        rowboat = new Rowboat(lf, rf, lb, rb, 500);
        rowboat.addPoint(100,100);
        rowboat.addPoint(600,100);
        rowboat.addPoint(100,600);
        rowboat.addPoint(1200,800);
        rowPath = rowboat.run();

        console.log("init");
    }
    @Override
    public void start()
    {
        rowPath.start();




        /*
        //PurePursuit.goTo(new Vector2(3658,3658),1,5,lf,rf,lb,rb);
        AtomicReference<Interval> go = new AtomicReference<>();
        go.set(new Interval((Object)->{return 0;},50));
        Interval pursuit = new Interval((Object)->{
            Vector2 pur = PurePursuit.getPursuit(new Vector2(0,0),new Vector2(4000,4000),1000,1000);
            go.get().clear();
            go.set(PurePursuit.goTo(pur, 1, 5, lf, rf, lb, rb));
            return 0;
        },10);//commit
        pursuit.start();*/
    }

}
