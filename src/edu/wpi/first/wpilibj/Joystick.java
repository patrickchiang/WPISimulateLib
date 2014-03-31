package edu.wpi.first.wpilibj;

/**
 *
 * @author Patrick
 */
public class Joystick {

    private int port;
    public static AxisType kX;
    public static AxisType kY;
    public static AxisType kZ;
    public static AxisType kTwist;
    public static AxisType kThrottle;

    public Joystick(int port) {
        this.port = port;
        
        
        
        kX = new AxisType(1);
        kY = new AxisType(2);
        kZ = new AxisType(3);
        kTwist = new AxisType(4);
        kThrottle = new AxisType(5);
    }
    
    public int getAxisChannel(Joystick.AxisType axis) {
        return axis.index;
    }
    
    public double getAxis(Joystick.AxisType axis) {
        return 0.0;
    }

    public class AxisType {

        public int index;
        public AxisType(int index) {
            this.index = index;
        }
    }
}
