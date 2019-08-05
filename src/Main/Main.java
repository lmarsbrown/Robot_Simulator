package Main;

import Robot.Robot;
import Utils.Interval;
import Utils.console;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main extends Canvas{
    //Creates the robot object
    Robot robot = new Robot();
    int i = 0;


    static int canvasSize = 800;
    //Creates BufferedImage object
    BufferedImage bf = new BufferedImage(canvasSize,canvasSize,BufferedImage.TYPE_INT_RGB);
    static BufferedImage robotImg;
    public static void main(String[] args)
    {
        //Loads robot asset
        try
        {
            robotImg = ImageIO.read(new File("Assets/Robot.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Makes canvas object
        Canvas ctx = new Main();
        ctx.setSize(canvasSize,canvasSize);


        //Creates and sets up JFrame and JPanel
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.add(ctx);
        panel.setDoubleBuffered(true);
        panel.setBackground(Color.LIGHT_GRAY);
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


        //Loops drawing for animation
        Interval drawInt = new Interval((input)->{ctx.repaint();},10);
        drawInt.start();
    }
    public void paint(Graphics g)
    {
        //Double buffering
        draw(bf.getGraphics());
        g.drawImage(bf,0,0,null);
    }

    public void draw(Graphics ctx)
    {
        double rote = robot.r;


        //Gets graphics2d object
        Graphics2D g2d = (Graphics2D)ctx;


        //Draws Canvas Background
        ctx.clearRect(0,0,canvasSize,canvasSize);
        ctx.setColor(Color.GRAY);
        ctx.fillRect(0,0,canvasSize,canvasSize);
        ctx.setColor(Color.DARK_GRAY);
        ctx.fillRect(2,2,canvasSize-4,canvasSize-4);


        //Configuring transform object
        double xScale = (canvasSize*0.125)/robotImg.getWidth();
        double yScale = (canvasSize*0.125)/robotImg.getHeight();
        AffineTransform trans = new AffineTransform();
        double imgSize = (canvasSize*0.125);


        //Calculating the distance from the top left corner to the center of the robot
        double centDist = Math.sqrt(2*Math.pow(imgSize,2))*0.5;


        //rotating and draeing the robot
        trans.translate(robot.getXPix(canvasSize)-(0.5*imgSize)+((0.70710678118*centDist)-(Math.cos(rote+Math.PI*0.25)*centDist)),robot.getYPix(canvasSize)-(0.5*imgSize)+((0.70710678118*centDist)-(Math.sin(rote+Math.PI*0.25)*centDist)));
        trans.scale(xScale,yScale);
        trans.rotate(rote);

        g2d.drawImage(robotImg,trans,null);

        i++;
    }
    public void update(Graphics g)
    {
        paint(g);
    }
}

