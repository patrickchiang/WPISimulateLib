package edu.wpi.first.wpilibj;

/**
 *
 * @author Patrick
 */
public class Joystick {

    private int port;

    public Joystick(int port) {
        this.port = port;
    }

    public double getDirectionRadians() {
        return Math.atan2(getX(), -getY());
    }

    public double getDirectionDegrees() {
        return Math.toDegrees(getDirectionRadians());
    }

    public double getMagnitude() {
        return Math.sqrt(getX() * getX() + getY() * getY());
    }

    public double getRawAxis(int axis) {
        return 0.0;
    }

    public boolean getRawButton(int button) {
        return false;
    }

    public boolean getTrigger() {
        return getRawButton(1);
    }

    public double getX() {
        return getRawAxis(1);
    }

    public double getY() {
        return getRawAxis(2);
    }

    public double getZ() {
        return getRawAxis(3);
    }
}
